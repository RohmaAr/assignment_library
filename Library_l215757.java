/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scd_a1;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class Library_l215757 {
   private ArrayList<Item> items;
   private HashMap<Integer,Borrower> borrowRecord;
   
   public static void main(String[] arr)
    {
        Library_l215757 lib=new Library_l215757(); 
        lib.loadFromFile();
        lib.menu();
    }
    private Item getItemById(int id)
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
    public boolean borrow(int id,Borrower b)
    {
        if(!borrowRecord.containsKey(id))
        {
            System.out.println("ERROR BEFORE");
            boolean found=false;
            for(int i=0;i<items.size();i++)
            {
              if(items.get(i).getId()==id)
              {
                found=true;
                break;
              }
                System.out.println("ERROR IN LOO[");
            } 
            if(!found)
            {
            System.out.println("Item not found");
            return found;
            }
            else
            {
                borrowRecord.put(id, b);
                Item it=getItemById(id);
                System.out.println("You have borrowed:");
                it.setBorrowed(true);
                it.setPop(it.getPop()+1);
                it.displayInfo();
                return true;
            }
        
        }
        else
        {
            System.out.println("This item is already borrowed by another patron");
            return false;
        }
    }
    public void displayByObject(Item i)
    {
        i.displayInfo();
    }
    public void addItem()
    {
        System.out.println("What category of item would you like to add?");
        System.out.println("1.Book");
        System.out.println("2.Magazine");
        System.out.println("3.Newspaper");
        System.out.println("4.Exit");
        System.out.println("Pick your choice:");
        Scanner dataScan=new Scanner(System.in);
        String sep=dataScan.nextLine();
        int n=Integer.valueOf(sep);
        switch (n)
        {
            case 1: Book  item=new Book();
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
                break;
            case 2:   Magazine m=new Magazine();
                System.out.println("Enter title of Magazine:");
                sep=dataScan.nextLine();
                m.setTitle(sep);
                System.out.println("Enter magazine's authors' names, press enter to stop:");
                sep=dataScan.nextLine();
                while(!sep.isEmpty())
                {
                    m.addAuthor(sep);
                    sep=dataScan.nextLine();
                }
                System.out.println("Enter Magazine's Publisher's name: ");
                sep=dataScan.nextLine();
                m.setPub(sep);
                m.setPop(1);
                System.out.println("Enter the Magazine's sale price:");
                sep=dataScan.nextLine().trim();
                System.out.println(sep);
                m.setCost(m.calculateCost(Integer.valueOf(sep)));
                System.out.println("THe cost isss"+m.getCost());
                m.setId();
                items.add(m);
                System.out.println( "Magazine has been added to library records");
                break;
            case 3: Newspaper news=new Newspaper();
                System.out.println("Enter title of Newspaper:");
                sep=dataScan.nextLine();
                news.setTitle(sep);
                System.out.println("Enter Newspaper's Publisher's name: ");
                sep=dataScan.nextLine();
                news.setPub(sep);
                System.out.println("Enter publishing date in the format dd-MM-yyyy");
                sep=dataScan.nextLine().trim();
                try {
                        news.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(sep));
}               catch (ParseException e) {
                e.printStackTrace(); 
                }
                news.setPop(1);
                System.out.println("Enter the Newspaper's sale price:");
                sep=dataScan.nextLine().trim();
                news.setCost(news.calculateCost(Integer.valueOf(sep)));
                news.setId();  
                items.add(news);
                System.out.println( "Newspaper has been added to library records");
                
                break;
            case 4: return;
            default:
                System.out.println("Invalid value entered.");
        }
            
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
        if(items.get(i).getClass()==Newspaper.class)
        {
            Newspaper news=(Newspaper)items.get(i);
            System.out.println("What do you want to edit in "+news.getTitle() +"?");
            System.out.println("1.Title");
            System.out.println("2.Cost");
            System.out.println("3.Date");
            System.out.println("4.Publisher");
            System.out.println("5.Popularity");
            System.out.println("6.Exit");
            sep=dataScan.nextLine();
            n=Integer.valueOf(sep);
            switch(n){
                case 1:System.out.println("Enter new name:");
                      sep=dataScan.nextLine();
                    news.setTitle(sep);
                    System.out.println("Name changed successfully.");
                    break;
                case 2 : System.out.println("Enter new sale cost:");
                      sep=dataScan.nextLine().trim();
                      news.setCost(Integer.valueOf(sep));
                      System.out.println("Cost changed successfully");
                      break;
                case 3:
                System.out.println("Enter new publishing date in the format dd-MM-yyyy");
                sep=dataScan.nextLine().trim();
                try {
                        news.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(sep));
                        System.out.println("Date changed successfully");
}               catch (ParseException e) {
                e.printStackTrace(); 
                }
                    break;
                case 4:
                        System.out.println("Enter Newspaper's new publisher name: ");
                        sep=dataScan.nextLine();
                        news.setPub(sep);
                        break;
                case 5: System.out.println("Enter new popularity count:");
                         sep=dataScan.nextLine().trim();
                      news.setPop(Integer.valueOf(sep));
                      System.out.println("Popularity changed successfully");
                      break;  
                case 6: return false;
                default: System.out.println("Incorrect choice ");
                return false;
            }
           
        }
        else if(items.get(i).getClass()==Magazine.class)
        {
            Magazine mag=(Magazine)items.get(i);
            System.out.println("What do you want to edit in "+mag.getTitle()+"?");
            System.out.println("1.Title");
            System.out.println("2.Author names");
            System.out.println("3.Publisher");
            System.out.println("4.Cost");
            System.out.println("5.Popularity");
            System.out.println("6.Exit");
            sep=dataScan.nextLine();
            n=Integer.valueOf(sep);
            switch(n){
                case 1:System.out.println("Enter new title of Magazine:");
                sep=dataScan.nextLine();
                mag.setTitle(sep);
                break;
                case 2:System.out.println("Following are the authors: ");
                    mag.displayAuthors();
                    System.out.println("Would you like to add or remove a certain author? Pick 1 to add or 2 to remove, any other chracter to exit:");
                    sep=dataScan.nextLine();
                    if(sep.equals("1"))
                    {
                        System.out.println("Enter name of author you want to add:");
                        sep=dataScan.nextLine();
                        mag.addAuthor(sep);
                        System.out.println("Author name added");
                    }
                    else if(sep.equals("2"))
                    {
                        System.out.println("Enter a number between 1-"+mag.noOfAuthors()+"to delete the corresponding author:");
                        mag.displayAuthors();
                        sep=dataScan.nextLine().trim();
                        int picked=Integer.valueOf(sep);
                        if(picked>mag.noOfAuthors() || picked<1)
                        {
                            System.out.println("Incorrect input");
                            return false;
                        }
                        else
                        {
                           
                            System.out.println( mag.removeAuthor(--picked)+" has been removed from list of suthors");
                        }
                    }
                    else
                    {
                       return false;
                    }
                    break;
                case 3:
                System.out.println("Enter Magazine's new Publisher name: ");
                sep=dataScan.nextLine();
                mag.setPub(sep);
                    System.out.println("Publisher name successfully changed");
                    break;
                case 4:System.out.println("Enter the Magazine's new sale price:");
                sep=dataScan.nextLine().trim();
                mag.setCost(mag.calculateCost(Integer.valueOf(sep)));
                    System.out.println("Cost successfully changed.");
                    break;
                case 5: System.out.println("Enter Popularity count:");
                    sep=dataScan.nextLine().trim();                     
                    mag.setPop(Integer.valueOf(sep));
                    System.out.println("Popularity changed");
                    break;
                case 6: return false;
                default : System.out.println("Incorrect input");
                return false;
            }
        }
        else if(items.get(i).getClass()==Book.class)
        {
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
        }
        
        return found;
    }
    public void viewBorrowedList()
    {
        System.out.println("PATRON NAMES \t\t BORROWED ITEM");
        for(Map.Entry m:borrowRecord.entrySet())
        {
            Item it=getItemById((int)m.getKey());
            Borrower b=(Borrower)m.getValue();
            System.out.println(b.getName()+"\t\t"+it.getTitle());
        }
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
                case 2: System.out.println("Enter Id of the item you want to borrow: "); 
                        sep=obj.nextLine();
                        id=Integer.valueOf(sep);
                        if(borrow(id,b))
                            System.out.println(getItemById(id).getTitle()+" is now available to you.");
                        else
                            System.out.println("Could not borrow item");
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
                case 8: this.viewBorrowedList();
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
        if(s.charAt(0)=='1')
        {
            Book b=new Book();
             Scanner dataScan=new Scanner(s);   
             dataScan.useDelimiter(",");
             dataScan.next();
                 String sep=dataScan.next();
                b.setTitle(sep);
                 sep=dataScan.next();
                b.setAuthor(sep);
                 sep=dataScan.next().trim();
                b.setYear(Integer.valueOf(sep));
                 sep=dataScan.next().trim();
                b.setPop(Integer.valueOf(sep));
                sep=dataScan.next().trim();
                 b.setCost(b.calculateCost(Integer.valueOf(sep)));
                 b.setId();
                 items.add(b);
                // items.get(items.size()-1).displayInfo();
       }
        else if(s.charAt(0)=='2')
        {
            Magazine m=new Magazine();
             Scanner dataScan=new Scanner(s);
             dataScan.useDelimiter(",");
               dataScan.next();
                   String sep=dataScan.next();
                m.setTitle(sep);
                sep=dataScan.next();
                while(!sep.endsWith("."))
                {
                    m.addAuthor(sep);
                    sep=dataScan.next();
                }
                m.addAuthor(sep);
                sep=dataScan.next();
                m.setPub(sep);
                sep=dataScan.next().trim();
                m.setPop(Integer.valueOf(sep));
                sep=dataScan.next().trim();
                m.setCost(m.calculateCost(Integer.valueOf(sep)));
                m.setId();
                items.add(m);
                // items.get(items.size()-1).displayInfo();
        }
        else if(s.charAt(0)=='3')
        {
            Newspaper n=new Newspaper();
             Scanner dataScan=new Scanner(s);
             dataScan.useDelimiter(",");
             dataScan.next();
                   String sep=dataScan.next();
                n.setTitle(sep);
                sep=dataScan.next();
                n.setPub(sep);
                sep=dataScan.next().trim();
                
                try {
                        n.setDate(new SimpleDateFormat("dd-MM-yyyy").parse(sep));
}               catch (ParseException e) {
                e.printStackTrace(); 
                }
                sep=dataScan.next().trim();
                n.setPop(Integer.valueOf(sep));
                sep=dataScan.next().trim();
                n.setCost(n.calculateCost(Integer.valueOf(sep)));
                n.setId();
                  items.add(n);
                // items.get(items.size()-1).displayInfo();
           
        }
        else
            System.out.println("Incorrect format.");
            
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
  public int calculateCost(int c);
}
class Item implements Configuration{
    protected String title;
    protected boolean isBorrowed=false;
    protected int popCount;
    private static int incre=1;
    protected int id;
    protected int cost;
    public Item(){}
    @Override
    public void displayInfo() {
    }

    @Override
    public int calculateCost(int c) {
         return 0;
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
class Book extends Item {
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
    public String getAuthor()
    {
        return author;
    }
    public int getYear()
    {
        return year;
    }
    @Override
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

    @Override
    public int calculateCost(int c) {
         return (c*20/100)+c+200;
    }
}
class Magazine extends Item{
    private String publisher;
    private ArrayList<String> authors;
    public void setPub(String s)
    {
        publisher=s;
    }
    public void displayAuthors()
    {
        for(int i=0;i<authors.size();i++){
        System.out.println(i+1+" "+authors.get(i));
        }
    }
    public String getPub()
    {
        return publisher;
    }
    public int noOfAuthors()
    {
        return authors.size();
    }
    public void addAuthor(String s)
    {
        if(authors==null)
        {
            authors= new ArrayList<>();
        }
        authors.add(s);
    }
    public String removeAuthor(int i)
    {
        return authors.remove(i);
    }
    @Override
    public void displayInfo() {
        
        System.out.println("");
        System.out.println("MAGAZINE Id: "+id);
        System.out.println("Title: "+title);
        System.out.println("Author: "+authors);
        System.out.println("Publisher: "+publisher);
        if(isBorrowed==true)
        System.out.println("Status: unavailable");
        else
            System.out.println("Status: available");
        System.out.println("Cost: "+cost+" Rs.");
        System.out.println("Popularity count: "+popCount);
        System.out.println("");
    }

    @Override
    public final int calculateCost(int c) {
        return (c*popCount);
    }
}
class Newspaper extends Item{
    private String publisher;
    private Date date;
    public void setPub(String s)
    {
        publisher=s;
    }
    public void setDate(Date d)
    {
        date=d;
    }
    public String getPub()
    {
        return publisher;
    }
    public Date getDate()
    {
        return date;
    }
    @Override
    public void displayInfo() {
    
        System.out.println("");
        System.out.println("NEWSPAPER Id: "+id);
        System.out.println("Title: "+title);
        System.out.println("Publisher: "+publisher);
        System.out.println("Publishing Date: "+date);
        if(isBorrowed==true)
        System.out.println("Status: unavailable");
        else
            System.out.println("Status: available");
        System.out.println("Cost: "+cost+" Rs.");
        System.out.println("Popularity count: "+popCount);
        System.out.println("");
    }

    @Override
    public int calculateCost(int c) {
        return (10+(5*c));
    }
}
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