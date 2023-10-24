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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Dell
 */



   
public class Library_l215757{
   JTable table;
   private ArrayList<Book> items;
   private HashMap<Integer,Borrower> borrowRecord;

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
    
   public static void main(String[] arr)
    {
        Library_l215757 lib=new Library_l215757(); 
        lib.loadFromFile();
        lib.Menu();
    }
   public void Menu()
   {
       JFrame frame=new JFrame("Menu");
        frame.setSize(500,600);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.setBackground(Color.GRAY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        String[][] menuArray;
        String[] colNames={"Title","Author","Year","Read"};
        menuArray=new String[items.size()][4];
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
      
        String[][] originalData = new String[items.size()][4];
for (int i = 0; i < items.size(); i++) {
    originalData[i][0] = items.get(i).getTitle();
    originalData[i][1] = items.get(i).getPubOrAuthor();
    originalData[i][2] = String.valueOf(items.get(i).getYear());
}
        table=new JTable(menuArray,colNames);
           table.addMouseMotionListener(new MouseMotionListener(){
           @Override
           public void mouseDragged(MouseEvent e) {
             //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
           }

           @Override
           public void mouseMoved(MouseEvent e) {
            int row = table.rowAtPoint(e.getPoint());
    if (row >= 0) {
        table.getSelectionModel().setSelectionInterval(row, row); // Highlight the row
    }    
           }
        });
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
        addButton.addActionListener(e->{
        addBook(frame);
        });
        editButton.addActionListener(e -> {
    // Toggle the edit mode
    boolean editMode = table.isFocusable();
    table.setFocusable(!editMode);
});

        frame.setVisible(true);
       
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
//       p1.setBorder(new LineBorder(Color.BLACK,3,true));
//       p2.setBorder(new LineBorder(Color.BLUE,3,true));
//       p3.setBorder(new LineBorder(Color.GREEN,3,true));
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
           JOptionPane.showMessageDialog(authorField, "BALNK BITH");
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
            String savetoFile=t+","+a+","+y+",1";
            File Obj = new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\scd_a1\\src\\scd_a1\\data.txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(Obj, true))) {
            // The second argument "true" in FileWriter constructor is for appending
            writer.write(savetoFile); // Add a newline character if needed
            System.out.println("Line appended to the file."+savetoFile);
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
    public void addItem()
    {
        JFrame frame=new JFrame("Add Item");
        System.out.println("What category of item would you like to add?");
        System.out.println("1.Book");
        System.out.println("2.Magazine");
        System.out.println("3.Newspaper");
        System.out.println("4.Exit");
        System.out.println("Pick your choice:");
        Scanner dataScan=new Scanner(System.in);
        String sep=dataScan.nextLine();
        int n=Integer.valueOf(sep);
            Book  item=new Book();
                System.out.println("Enter title of book:");
                sep=dataScan.nextLine();
                item.setTitle(sep);
                System.out.println("Enter book's author's name:");
                 sep=dataScan.nextLine();
                item.setAuthor(sep);
                System.out.println("Enter book's year of publishment:");
                 sep=dataScan.nextLine().trim();
                item.setYear(Integer.valueOf(sep));
                item.setPop(1);
                System.out.println("Enter the sale price of the book:");
                sep=dataScan.nextLine().trim();
                 item.setCost(item.calculateCost(Integer.valueOf(sep)));
                 item.setId();
                 items.add(item);
                 System.out.println("Book has been added to library records");
            
    }
    public boolean editItem(int id)
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
            return found;
        }
        int n;
        Scanner dataScan=new Scanner(System.in);
         String sep;
        System.out.println("The item you picked is :");
        items.get(i).displayInfo();

            Book book=(Book)items.get(i);
            System.out.println("Book");
            System.out.println("What do you want to edit in "+book.getTitle()+"?");
            System.out.println("1.Title");
            System.out.println("2.Author");
            System.out.println("3.Year of publishing");
            System.out.println("4.Popularity");
            System.out.println("5.Cost");
            System.out.println("6.Exit");
            sep=dataScan.nextLine();
            n=Integer.valueOf(sep);
            switch(n){
                case 1:System.out.println("Enter new title of book:");
                sep=dataScan.nextLine();
                book.setTitle(sep);
                    System.out.println("Title changed");
                    break;
                case 2: System.out.println("Enter new author's name:");
                 sep=dataScan.nextLine();
                book.setAuthor(sep);
                    System.out.println("Author name changed");
                    break;
                case 3:
                System.out.println("Enter book's year of publishment:");
                 sep=dataScan.nextLine().trim();
                book.setYear(Integer.valueOf(sep));
                    System.out.println("Year of publishing changed");
                    break;
                case 4: System.out.println("Enter new popularity count:");
                sep=dataScan.nextLine().trim();
                book.setPop(Integer.valueOf(sep));
                break;
                
                case 5: System.out.println("Enter the new sale price of the book:");
                sep=dataScan.nextLine().trim();
                 book.setCost(book.calculateCost(Integer.valueOf(sep)));
                 break;
                case 6: return false;
                default : System.out.println("Incorrect option");
                return false;
            }
        
        return found;
    }
    public boolean deleteItem(int id)
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
            return found;
        }
        items.remove(i);
        if(borrowRecord.containsKey(id))
        {
            borrowRecord.remove(id);
        }
        return true;
    }
    public void menu()
    {
        Borrower b=new Borrower();
        System.out.println("Enter your name :");
        Scanner obj=new Scanner(System.in);
        String name=obj.nextLine();
        b.setName(name);
        while(true)
        {
            System.out.println("Library Management System Menu");
            System.out.println("1.Hot Picks!!");
            System.out.println("2.Borrow an item");
            System.out.println("3.Add item");
            System.out.println("4.Edit item");
            System.out.println("5.Delete item");
            System.out.println("6.View all items");
            System.out.println("7.View items by ID");
            System.out.println("8.View Borrowers list");
            System.out.println("9.Exit");
            System.out.println("Pick your choice:");
            String sep=obj.nextLine();
            int n=Integer.valueOf(sep);
        
            int id;
            switch (n)
            {
                case 1: hotPicks();
                    break;
                case 3:  addItem();
                    break;
                case 4:System.out.println("Enter id of item you want to edit:"); 
                    sep=obj.nextLine();
                    id=Integer.valueOf(sep);
                    if(editItem(id))
                    {
                        System.out.println("The changes you made have been saved");
                    }
                    else 
                    {
                        System.out.println("No changes made");
                    }
                    break;
                case 5:  System.out.println("Enter id:");
                    sep=obj.nextLine();
                    id=Integer.valueOf(sep);     
                    deleteItem(id);
                    if(deleteItem(id))
                    {
                        System.out.println("Deletion made");
                    }
                    else 
                    {
                        System.out.println("No changes made");
                    }
                    
                    break;
                case 6: displayAllItems();
                    break;
                case 7: System.out.println("Enter id:");
                        sep=obj.nextLine();
                        id=Integer.valueOf(sep);
                        this.displaySingleItem(id);
                    break;
                case 9: return;
                default :
                    System.out.println("Invalid input");
            }
            
        }
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
    public Library_l215757(){
        items=new ArrayList<>();
        borrowRecord=new HashMap<>();
    }
    public void displaySingleItem(int id)
    {
        boolean found=false;
        for(int i=0;i<items.size();i++)
        {
            if(items.get(i).getId()==id)
            {
                items.get(i).displayInfo();
                found=true;
            }
        }
        if(found==false)
            System.out.println("No item exists with the entered id.");
    }
    private void populateArray(String s)
    {
        //if(s.charAt(0)=='1')
        //{
            Book b=new Book();
             Scanner dataScan=new Scanner(s);   
             dataScan.useDelimiter(",");
                 String sep=dataScan.next();
                b.setTitle(sep);
                 sep=dataScan.next();
                b.setAuthor(sep);
                 sep=dataScan.next().trim();
                b.setYear(Integer.valueOf(sep));
                 sep=dataScan.next().trim();
                b.setPop(Integer.valueOf(sep));
                 b.setId();
                 b.displayInfo();
                 items.add(b);
                // items.get(items.size()-1).displayInfo();
//       }
//        else if(s.charAt(0)=='2')
//        {
//            Magazine m=new Magazine();
//             Scanner dataScan=new Scanner(s);
//             dataScan.useDelimiter(",");
//               dataScan.next();
//                   String sep=dataScan.next();
//                m.setTitle(sep);
//                sep=dataScan.next();
//                while(!sep.endsWith("."))
//                {
//                    m.addAuthor(sep);
//                    sep=dataScan.next();
//                }
//                m.addAuthor(sep);
//                sep=dataScan.next();
//                m.setPub(sep);
//                sep=dataScan.next().trim();
//                m.setPop(Integer.valueOf(sep));
//                sep=dataScan.next().trim();
//                m.setCost(m.calculateCost(Integer.valueOf(sep)));
//                m.setId();
//                items.add(m);
//                // items.get(items.size()-1).displayInfo();
//        }
//        else if(s.charAt(0)=='3')
//        {
//            Newspaper n=new Newspaper();
//             Scanner dataScan=new Scanner(s);
//             dataScan.useDelimiter(",");
//             dataScan.next();
//                   String sep=dataScan.next();
//                n.setTitle(sep);
//                sep=dataScan.next();
//                n.setPub(sep);
//                sep=dataScan.next().trim();
//                
//                try {
//                        n.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(sep));
//}               catch (ParseException e) {
//                e.printStackTrace(); 
//                }
//                sep=dataScan.next().trim();
//                n.setPop(Integer.valueOf(sep));
//                sep=dataScan.next().trim();
//                n.setCost(n.calculateCost(Integer.valueOf(sep)));
//                n.setId();
//                  items.add(n);
//                // items.get(items.size()-1).displayInfo();
//           
//        }
//        else
//            System.out.println("Incorrect format.");
//            
    }
    public void loadFromFile(){
         try {
            File Obj = new File("C:\\Users\\Dell\\Documents\\NetBeansProjects\\scd_a1\\src\\scd_a1\\data.txt");
            Scanner Reader = new Scanner(Obj);
            while (Reader.hasNextLine()) {
                String data=Reader.nextLine();
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
//class Magazine extends Item{
//    private String publisher;
//    private ArrayList<String> authors;
//    public void setPub(String s)
//    {
//        publisher=s;
//    }
//    public void displayAuthors()
//    {
//        for(int i=0;i<authors.size();i++){
//        System.out.println(i+1+" "+authors.get(i));
//        }
//    }
//    @Override
//    public String getPubOrAuthor()
//    {
//        return publisher;
//    }
//    public int noOfAuthors()
//    {
//        return authors.size();
//    }
//    public void addAuthor(String s)
//    {
//        if(authors==null)
//        {
//            authors= new ArrayList<>();
//        }
//        authors.add(s);
//    }
//    public String removeAuthor(int i)
//    {
//        return authors.remove(i);
//    }
//    @Override
//    public void displayInfo() {
//        
//        System.out.println("");
//        System.out.println("MAGAZINE Id: "+id);
//        System.out.println("Title: "+title);
//        System.out.println("Author: "+authors);
//        System.out.println("Publisher: "+publisher);
//        if(isBorrowed==true)
//        System.out.println("Status: unavailable");
//        else
//            System.out.println("Status: available");
//        System.out.println("Cost: "+cost+" Rs.");
//        System.out.println("Popularity count: "+popCount);
//        System.out.println("");
//    }
//
//    @Override
//    public final int calculateCost(int c) {
//        return (c*popCount);
//    }
//}
//class Newspaper extends Item{
//    private String publisher;
//    private Date date;
//    public void setPub(String s)
//    {
//        publisher=s;
//    }
//    public void setDate(Date d)
//    {
//        date=d;
//    }
//    public String getPubOrAuthor()
//    {
//        return publisher;
//    }
//    public Date getDate()
//    {
//        return date;
//    }
//    @Override
//    public void displayInfo() {
//    
//        System.out.println("");
//        System.out.println("NEWSPAPER Id: "+id);
//        System.out.println("Title: "+title);
//        System.out.println("Publisher: "+publisher);
//        System.out.println("Publishing Date: "+date);
//        if(isBorrowed==true)
//        System.out.println("Status: unavailable");
//        else
//            System.out.println("Status: available");
//        System.out.println("Cost: "+cost+" Rs.");
//        System.out.println("Popularity count: "+popCount);
//        System.out.println("");
//    }
//
//    @Override
//    public int calculateCost(int c) {
//        return (10+(5*c));
//    }
//}
class Borrower{
    private String name;
    public void setName(String n)
    {
        name=n;
    }
    public String getName()
    {
        return name;
    }
    @Override
    public boolean equals(Object o)
    {
        if(this==o)
            return true;
        if(o==null || getClass()!=o.getClass())
            return false;
        else
        {
            Borrower b=(Borrower)o;
            return getName().equals(b.getName());
            
        }
    }
    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
    
}