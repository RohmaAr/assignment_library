/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scd_a1;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dell
 */



   
public class Library_l215757{
   private ArrayList<Book> items;
    public Library_l215757(){
        items=new ArrayList<>();
    }
  public static void main(String[] arr)
    {
        Library_l215757 lib=new Library_l215757(); 
        lib.loadFromFile();
        lib.Menu();
    }
     class ButtonRenderer extends DefaultTableCellRenderer {
        private JButton button = new JButton("Read");

        public ButtonRenderer() {
            button.setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(UIManager.getColor("Button.background"));
            }
            return button;
        }
    }
    class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private JTable table;

        public ButtonEditor(JTable table) {
            super(new JCheckBox());
            button = new JButton("Read");
            button.setOpaque(true);
            this.table = table;
            // Add an ActionListener to open the text file using data from the first column
            button.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
            int selectedRow = table.getEditingRow();
            if (selectedRow >= 0) {
                System.out.println("selected row in readL: " + selectedRow);
                String fileName = table.getValueAt(selectedRow, 0).toString();
                if (fileName != null) {
                    System.out.println(fileName);
                    readBook(fileName);
                }
            }
        
    }
});

        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }
    }

    
    public void readBook(String book)
    { 
        try {
            JTextArea litera=new JTextArea();
            JScrollPane scroll=new JScrollPane(litera);
            scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            JFrame frame=new JFrame(book);
            //File Obj=new File(book+".txt");
            File Obj=new File("alice in the wonderland.txt");
            Scanner scan=new Scanner(Obj);
            String read;
            while(scan.hasNextLine())
            {
                read=scan.nextLine();
                litera.append(read+"\n");
            }
            litera.setWrapStyleWord(true);
            litera.setLineWrap(true);
            litera.setEditable(false);
            frame.setSize(600, 600);
            frame.add(scroll);
            frame.setVisible(true);
            frame.addWindowListener(new WindowAdapter(){
                @Override
                public void windowClosing(WindowEvent e) {
                    Object message="Are you sure you want to exit?";
                    int choice= JOptionPane.showConfirmDialog(frame, message, "Confirm exit", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                     if(choice==JOptionPane.YES_OPTION)
                     {
                         frame.dispose();
                     }
                }
            });
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Library_l215757.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
   public void Menu()
   {
       JFrame frame=new JFrame("Menu");
        frame.setSize(500,600);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.setBackground(Color.GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[] colNames={"Title","Author","Year","Read"};
        String[][] menuArray=new String[items.size()][4];
        for(int i=0;i<items.size();i++){
           
           menuArray[i][0]=items.get(i).getTitle();
           menuArray[i][1]=items.get(i).getPubOrAuthor();
           menuArray[i][2]=String.valueOf(items.get(i).getYear());
            System.out.println(String.valueOf(items.get(i).getYear()));
        }
        for(int i=0;i<menuArray.length;i++)
        {
            for(int j=0;j<menuArray[i].length;j++)
            {
                System.out.print(menuArray[i][j]+" ");
            }
            System.out.println("");
        }

     JTable table;
      table=new JTable(menuArray,colNames);
        table.setDefaultEditor(Object.class, null);

// Make the table uneditable but allow row selection
table.setRowSelectionAllowed(true);
table.setColumnSelectionAllowed(false);

// Prevent focus on the table
table.setFocusable(false);
        JScrollPane scroll=new JScrollPane(table);
        frame.add(scroll);
        table.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());

        table.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(table));
        JButton deleteButton=new JButton("Delete");
        JButton editButton=new JButton("Edit");
        JButton addButton=new JButton("Add");
        frame.add(deleteButton);
        frame.add(editButton);
        frame.add(addButton);
        editButton.addActionListener(e->{
        editBook(frame);
        });
        addButton.addActionListener(e->{
        addBook(frame);
        });
        editButton.addActionListener(e -> {
        boolean editMode = table.isFocusable();
        table.setFocusable(!editMode);
        });
        deleteButton.addActionListener(e->{
        deleteBook(frame);
        });
        frame.setVisible(true);
       
   }
   public void editBook(JFrame f)
   {
       JDialog dialog =new JDialog(f);
       dialog.setTitle("Pick a Book");
       dialog.setLocation(30, 30);
       dialog.setSize(500,300);
       dialog.setLayout(new FlowLayout());
       JLabel pickaBookLabel=new JLabel("Pick a book you want to edit:");
       JComboBox<String> bookComboBox=new JComboBox<>();
       for (Book book : items) {
          bookComboBox.addItem(book.getTitle());
        }
       bookComboBox.setEditable(false);
       bookComboBox.addActionListener(e->{
       String clickedBook=(String)bookComboBox.getSelectedItem();
       bookComboBox.setVisible(false);
       pickaBookLabel.setText("Edit the fields you want to edit");
       int i;
       for(i=0;i<items.size();i++)
       {
           if(items.get(i).getTitle().equalsIgnoreCase(clickedBook))
           {
               break;
           }
       }
       final Book fo=items.get(i);
       final int id=fo.getId();
       JPanel p1=new JPanel(new FlowLayout());
       JLabel titleLabel=new JLabel("Title:");
       JTextField titleField=new JTextField(30);
       titleField.setText(fo.getTitle());
       p1.add(titleLabel);
       p1.add(titleField);
       //p1.setBorder(new LineBorder(Color.BLACK,2,true));
       JPanel p2=new JPanel(new FlowLayout());
       JLabel authorLabel=new JLabel("Author:");
       JTextField authorField=new JTextField(30);
       authorField.setText(fo.getPubOrAuthor());
       p2.add(authorLabel);
       p2.add(authorField);
       JPanel p3=new JPanel(new FlowLayout());
       JLabel yearLabel=new JLabel("Publication Year:");
       JTextField yearField=new JTextField(30);
       yearField.setText(Integer.toString(fo.getYear()));
       p3.add(yearLabel);
       p3.add(yearField);
       JButton submitButton=new JButton("Save Changes");
       JButton cancelButton=new JButton("Cancel Changes");
       JPanel buttons=new JPanel(new FlowLayout());
       buttons.add(submitButton);
       buttons.add(cancelButton);
       JPanel EditFields=new JPanel(new GridLayout(4,1));
       EditFields.add(p1);
       EditFields.add(p2);
       EditFields.add(p3);
       EditFields.add(buttons);
       dialog.add(EditFields);

       submitButton.addActionListener(ee->{
      
           try{
        int year=Integer.parseInt(yearField.getText());
        YearMonth currentYearMonth = YearMonth.now();
        int curryear = currentYearMonth.getYear();
        if(year>curryear)
        {
            throw new NumberFormatException();
        }
      }catch (NumberFormatException ex)
      {
          JOptionPane.showMessageDialog(dialog, "Incorrect input for year");
      }
           String change=titleField.getText()+" by "+authorField.getText()+","+Integer.parseInt(yearField.getText());
           int choice=JOptionPane.showConfirmDialog(dialog, fo.getTitle()+"by "+fo.getPubOrAuthor()+","+Integer.toString(fo.getYear())+" will be permanently changed to "+change, "Confirm Changes", JOptionPane.OK_CANCEL_OPTION);
       if(choice==JOptionPane.OK_OPTION){
           
           change=titleField.getText()+","+authorField.getText()+","+Integer.parseInt(yearField.getText())+","+Integer.toString(fo.getPop());
           System.out.println("Change "+change);
           System.out.println(fo.getTitle()+","+fo.getPubOrAuthor()+","+Integer.toString(fo.getYear()));
           findAndReplace(fo.getTitle()+","+fo.getPubOrAuthor()+","+Integer.toString(fo.getYear())+",",change);
           Book b=(Book)this.getItemById(id);
           b.setTitle(titleField.getText());
           b.setAuthor(authorField.getText());
           b.setYear(Integer.parseInt(yearField.getText()));
           b.displayInfo();
           JOptionPane.showMessageDialog(dialog, "Changes successfully saved");
           Menu();
           f.dispose();
           }
           else {
           dialog.dispose();
           }
       });
       cancelButton.addActionListener(eee->{
       dialog.dispose();
       });
       
       });
       dialog.add(pickaBookLabel);
       dialog.add(bookComboBox); 
       dialog.setVisible(true);
   }
   public void deleteBook(JFrame f)
   {
       JDialog dialog =new JDialog(f);
       dialog.setTitle("Delete Book");
       dialog.setLocation(30, 30);
       dialog.setSize(500,300);
       dialog.setLayout(new FlowLayout());
       JLabel pickaBookLabel=new JLabel("Pick a book you want to edit:");
       JComboBox<String> bookComboBox=new JComboBox<>();
       for (Book book : items) {
          bookComboBox.addItem(book.getTitle());
        }
       bookComboBox.setEditable(false);
       bookComboBox.addActionListener(e->{
       String clickedBook=(String)bookComboBox.getSelectedItem();
       bookComboBox.setVisible(false);
       Book fo = null;
       for(int i=0;i<items.size();i++)
       {
           if(items.get(i).getTitle().equalsIgnoreCase(clickedBook))
           {
               fo=items.get(i);
               break;
           }
       }
       final String fou=fo.getTitle().toUpperCase()+" by "+fo.getPubOrAuthor().toUpperCase()+", "+Integer.toString(fo.getYear());
      final String four=fo.getTitle()+","+fo.getPubOrAuthor()+","+fo.getYear()+",";
      final int id=fo.getId();
      pickaBookLabel.setText("Found book : "+fou);
      JButton deleteButton=new JButton("delete");
      JButton cancelButton=new JButton("cancel");
      JPanel p3=new JPanel(new FlowLayout());
       p3.add(deleteButton);
       p3.add(cancelButton);
       dialog.add(p3);
       cancelButton.addActionListener(ee->{
       dialog.dispose();
       });
       deleteButton.addActionListener(ee->{
           int choice=JOptionPane.showConfirmDialog(dialog, fou+" will be permanently deleted.", "Confirm Delete", JOptionPane.OK_CANCEL_OPTION);
           if(choice==JOptionPane.OK_OPTION){
           deleteItem(id);
           findAndReplace(four,"");
           JOptionPane.showMessageDialog(dialog, fou+" successfully deleted");
           Menu();
           f.dispose();
           }
           else {
           dialog.dispose();
           }
       });
       });
       dialog.add(pickaBookLabel);
       dialog.add(bookComboBox);
       dialog.setResizable(false);
     //   dialog.pack();
       dialog.setVisible(true);
   }
   private void findAndReplace(String oldString,String newString)
   {
       File fileToBeModified = new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\scd_a1\\src\\scd_a1\\data.txt");         
        String oldContent = "";         
        Scanner reader = null;              
        try{
            reader = new Scanner(new FileReader(fileToBeModified));
            String line;
            while (reader.hasNext()) 
            {
                line = reader.nextLine();
                oldContent = oldContent + line+"\n" ;
                
            }
            // System.out.println(oldContent);
             int start=oldContent.indexOf(oldString);
             oldString=oldString+oldContent.charAt(start+oldString.length());
             System.out.println("old string to be deleted: "+oldString+"new String "+newString);
             if(!newString.equals(""))
             {
                 newString+="\n";
             }
             String newContent = oldContent.replaceAll(oldString+"\n",newString ); 
         FileWriter   writer = new FileWriter(fileToBeModified);
             
            writer.write(newContent);
              try
            {
                reader.close();     
                writer.close();
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
        
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
   }
   public void addBook(JFrame f)
   {
       JDialog dialog =new JDialog(f);
       dialog.setTitle("Add Book");
       dialog.setLocation(30, 30);
       dialog.setSize(500,300);
       dialog.setLayout(new GridLayout(4,1));
       JPanel p1=new JPanel();
       p1.setLayout(new FlowLayout(FlowLayout.LEFT) );
       JPanel p2=new JPanel();
       p2.setLayout(new  FlowLayout(FlowLayout.LEFT));
       JPanel p3=new JPanel();
       p3.setLayout(new  FlowLayout(FlowLayout.LEFT) );
       JPanel p4=new JPanel();
       p4.setLayout(new BorderLayout());
       JLabel tlabel=new JLabel("Title:");
       JLabel alabel=new JLabel("Author:");
       JLabel ylabel=new JLabel("Publication Year:");
       JTextField titleField=new JTextField(30);
       JTextField authorField=new JTextField(30);
       JTextField yearField=new JTextField(30);
       JButton addButton=new JButton("Add");
       addButton.addActionListener(e->{
       String t=titleField.getText();
       String a=authorField.getText();
       String y=yearField.getText();
       if(t.isEmpty() || a.isEmpty() || y.isEmpty())
       {
           JOptionPane.showMessageDialog(authorField, "Please fill in all fields");
       }
       else
       {
           try{
        int year=Integer.parseInt(y);
        YearMonth currentYearMonth = YearMonth.now();
        int curryear = currentYearMonth.getYear();
        if(year>curryear)
        {
            throw new NumberFormatException();
        }
        else
        {
            Book newBook=new Book();
            newBook.setAuthor(a);
            newBook.setYear(year);
            newBook.setTitle(t);
            newBook.setPop(1);
            items.add(newBook);
            String savetoFile="\n"+t+","+a+","+y+",1";
            File Obj = new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\scd_a1\\src\\scd_a1\\data.txt");
            try (FileWriter writer = new FileWriter(Obj, true)) {
            writer.write(savetoFile);
            System.out.println("Line appended to the file."+savetoFile);
            writer.close();
            Menu();
            f.dispose();
     
        } catch (IOException io) {
            System.err.println("Error appending to the file: " + io.getMessage());
        }
        }
        
        }
        catch(NumberFormatException ex)
        {
               JOptionPane.showMessageDialog(yearField, "Invalid input for year.");
        }
       }
       });
       p1.add(tlabel);
       p1.add(titleField );
       p2.add(alabel);
       p2.add(authorField);
       p3.add(ylabel);
       p3.add(yearField);
       p4.add(addButton);
       dialog.add(p1);
       dialog.add(p2);
       dialog.add(p3);
       dialog.add(p4);
       dialog.setResizable(false);
        dialog.pack();
       dialog.setVisible(true);
   }
    private Book getItemById(int id)
    {
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).getId()==id)
            {
                return items.get(i);
            }
        }
        return null;
        
    }
    public void displayByObject(Item i)
    {
        i.displayInfo();
    }
    public void deleteItem(int id)
    {
        int i;
        boolean found=false;
        for(i=0;i<items.size();i++)
        {
            if(items.get(i).getId()==id)
            {
                found=true;
                break;
            }
        }
        if(!found)
        {
            return;
        }
        items.remove(i);
    }
    public void hotPicks()
    {
        ArrayList<Integer> popularities = new ArrayList<>();
        for(int i=0;i< items.size();i++)
 	{
            popularities.add(items.get(i).getPop());
 	}
        Collections.sort(popularities);
        System.out.println("Popular books these days are: ");
        for(int j=popularities.size()-1;j>0;j--)
        {
            for(int i=0;i<items.size();i++)
            {
                if(items.get(i).getPop()==popularities.get(j))
                {
                   items.get(i).displayInfo();
                }
            }
        }
    }
    private void populateArray(String s)
    {
            Book b=new Book();
             Scanner dataScan=new Scanner(s);   
             dataScan.useDelimiter(",");
              String sep;
             if(dataScan.hasNext()){
                 sep=dataScan.next();
                b.setTitle(sep);
             }
             if(dataScan.hasNext()){
                 sep=dataScan.next();
                b.setAuthor(sep);
             }
              if(dataScan.hasNext()){
            sep=dataScan.next().trim();
                b.setYear(Integer.valueOf(sep));
              }
               if(dataScan.hasNext()){
            sep=dataScan.next().trim();
                b.setPop(Integer.valueOf(sep));
               }
               b.setId();
                 b.displayInfo();
                 items.add(b);
              
    }
    public void loadFromFile(){
         try {
            File Obj = new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\scd_a1\\src\\scd_a1\\data.txt");
            Scanner Reader = new Scanner(Obj);
            while (Reader.hasNextLine()) {
                String data=Reader.nextLine();
                if(!data.isEmpty())
                populateArray(data);
            }
            Reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("An error has occurred.");
            e.printStackTrace();
        }
         System.out.println("Loaded items from data.txt.");
    }
    public void displayAllItems()
    {
        for(int i=0;i<items.size();i++)
        {
            items.get(i).displayInfo();
        }
    }
}
interface Configuration{
  void displayInfo();
  public String getPubOrAuthor();
  public int calculateCost(int c);
}
class Item implements Configuration{
    public Item(){}
    public String getPubOrAuthor(){
    return "";
    }
    @Override
    public void displayInfo() {
    }

    @Override
    public int calculateCost(int c) {
         return 0;
    }
    
}
class Book{
    protected String title;
    protected boolean isBorrowed=false;
    protected int popCount;
    private static int incre=1;
    protected int id;
    protected int cost;
    
    private String author;
    private int year;
    public void setAuthor(String s)
    {
        author=s;
    }
    public void setYear(int year)
    {
        this.year=year;
    }
    public String getPubOrAuthor()
    {
        return author;
    }
    public int getYear()
    {
        return year;
    }
    public void displayInfo() {
        System.out.println("");
        System.out.println("BOOK Id: "+id);
        System.out.println("Title: "+title);
        System.out.println("Author: "+author);
        System.out.println("Year: "+year);
        if(isBorrowed==true)
        System.out.println("Status: unavailable");
        else
            System.out.println("Status: available");
        System.out.println("Cost: "+cost+" Rs.");
        System.out.println("Popularity count: "+popCount);
        System.out.println("");
    }

    public int calculateCost(int c) {
         return (c*20/100)+c+200;
    }
    public void setTitle(String s)
    {
        title=s;
    }
    public String getTitle()
    {
        return title;
    }
    public int getPop()
    {
        return popCount;
    }
    public int getId()
    {
        return id;
    }
    public boolean getIsBorrowed()
    {
        return isBorrowed;
    }
    public int getCost()
    {
        return cost;
    }
    public void setBorrowed(boolean b)
    {
        isBorrowed=b;
    }
    public void setPop(int p)
    {
        popCount=p;
    }
    public void setId()
    {
        id=incre++;
    }
    public void setCost(int c)
    {
        cost=c;
    }
}