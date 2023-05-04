import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class Item {
    private String itemNo;
    private String itemName;
    private String category;
    private int price;
    private int qty;
    private int rlevel;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


    public Item(){}

    public Item(String no,String name,String cat,int p,int q,int lev){
        itemNo=no;
        itemName=name;
        category=cat;
        price=p;
        qty=q;
        rlevel=lev;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getRlevel() {
        return rlevel;
    }

    public void setRlevel(int rlevel) {
        this.rlevel = rlevel;
    }

    public void showItemHeader() {
   	 System.out.print(String.format("%7s", "Item No"));
   	 System.out.print(String.format("%22s", "Item Name"));
   	 System.out.print(String.format("%15s", "Category"));
   	 System.out.print(String.format("%15s", "Price "));
   	 System.out.print(String.format("%15s", "Quantity"));
   	 System.out.println(String.format("%17s", "Reorder Level"));
   }
    
    public void showItem() {
    	 System.out.print(String.format("%-12s", itemNo));
    	 System.out.print(String.format("%-25s", itemName));
    	 System.out.print(String.format("%-15s", category));
    	 System.out.print(String.format("%-13d", price));
    	 System.out.print(String.format("%-15d", qty));
    	 System.out.println(String.format("%-12s", rlevel));
    }
    
    

    public static String checkCatogory(int opt) {
    	String cat=null;
    	switch(opt) {
    	case 1: cat="Cosmetic";break;
    	case 2: cat="Drink";break;
    	case 3: cat="Food";break;
    	case 4: cat="Other";break;
    	default:cat="All";

    	}
    	
		return cat;
    	 
    }
    
    public boolean inputItemNo() throws IOException, SQLException{
        boolean result=false;
        System.out.print("Item No(Eg. Item-0000) \t :");
        itemNo=br.readLine();
        if(itemNo.matches("Item-[0-9]{4}")) {
            if(new ItemDAO().checkItemNo(itemNo)==0) {
                result=true;
            }else {
            	 System.out.println("Your Item Already exists");
            }

        }
        else {
            System.out.println("Wrong Input, Your input must be as Item-0000");
        }
        return result;


    }
    
    public  boolean inputItemName() throws IOException {
    	 boolean result=false;
    	 System.out.print("Item Name : ");
    	 itemName=br.readLine();
    	 if(itemName.matches("[a-z\\\\s{2}A-Z]*")) {
    		  result=true;
    	 }
    	 else{
    		 System.out.println("Wrong Input ,Your input must be as Capital letters");
    	 }
    	 
    	 return result;
    }
    
    public void inputCategory() throws NumberFormatException, IOException {
     	String cat;
     	int opt;
     	new stock_info_system().showCategory();
     	opt=Integer.parseInt(br.readLine());
    	switch(opt) {
    	case 1: category="Cosmetic";break;
    	case 2: category="Drink";break;
    	case 3: category="Food";break;
    	case 4: category="Other";break;
    	default:cat="All";

    	}
    }
    
    public boolean inputPrice(){
        boolean result =false;
        System.out.print("Price \t : ");
        try{
            price=Integer.parseInt(br.readLine());
            if(price<=0 || price >=1000){
                System.out.println("Your input must be within 1- 999");
            }else result=true;
        }catch(Exception e){//input mismatch String =  price (int)
            System.out.println("Your input must be digit");
        }return result;
    }
    
    public boolean inputQuantity(){
        boolean result =false;
        System.out.print("Quantity \t : ");
        try{
            qty=Integer.parseInt(br.readLine());
            if(qty<=0 || qty >=100){
                System.out.println("Your input must be within 1- 99");
            }else result=true;
        }catch(Exception e){//input mismatch String =  price (int)
            System.out.println("Your input must be digit");
        }return result;
    }
    
    public boolean inputReorder(){
        boolean result =false;
        System.out.print("Reorder \t : ");
        try{
            rlevel=Integer.parseInt(br.readLine());
            if(rlevel<=0 || rlevel >=100){
                System.out.println("Your input must be within 1- 99");
            }else result=true;
        }catch(Exception e){//input mismatch String =  price (int)
            System.out.println("Your input must be digit");
        }return result;
    }
    
	public void inputItem() throws IOException,SQLException{
		 while(!inputItemNo());
		 while(!inputItemName());
		 inputCategory();
		 while(!inputPrice());
		 while(!inputQuantity());
		 while(!inputReorder());
	}
    
    
    
    
    
}