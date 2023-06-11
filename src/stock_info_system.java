import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class stock_info_system {

	private static Scanner sc = new Scanner(System.in);

	public static void starLine(int n, char ch) {

		for (int i = 0; i <= n; i++) {
			System.out.print(ch);
		}
		System.out.println();
	}

	public static void mainMenu() {
		starLine(150, '*');
		System.out.println("Main Menu for Stock Information System  ");
		starLine(150, '*');
		System.out.println(" 1  : Show All Stock Information");
		System.out.println(" 2  : Show All Stock Information by Category");
		System.out.println(" 3  : Show All Stock Information under reorder level");
		System.out.println(" 4  : Insert new Stock Information");
		System.out.println(" 5  : Search Stocks by Name");
		System.out.println(" 6 : Sorting Item Information");
		System.out.println(" 7 : Delete");
		System.out.println(" 8 : Selling");
		System.out.println(" 9  : Update Price");
		System.out.println(" 10  : Update Product Qty & price");
		System.out.println(" 11  : Exit");
		System.out.println(" Choose (1 - 5 )  :");

	}

	public static void showCategory() {
		starLine(30, '*');
		System.out.println(" Category List");
		starLine(30, '*');
		System.out.println("1 : Cosmetic");
		System.out.println("2 : Drink");
		System.out.println("3 : Food");
		System.out.println("4 : Fruit");
		System.out.println("5 : Others");

		starLine(30, '*');
		System.out.print(" Choose (1 - 5) ");
	}

	public static void main(String[] args) throws SQLException, IOException {

		int option;
		int catopt;
		int mopt;
		String choice;

		ArrayList<Item> itemList = new ArrayList<Item>();
		ItemDAO i = new ItemDAO();
		Item item = new Item();

		a: do {
			mainMenu();
			option = sc.nextInt();
			switch (option) {
			case 1:
				itemList = i.showAllItem();
				item.showItemHeader();
				starLine(100, '*');
				itemList.forEach(Item::showItem);

				;
				break;
			case 2:
				showCategory();
				catopt = sc.nextInt();
				String category = Item.checkCatogory(catopt);

				itemList = i.showItemByCategory(category);
				item.showItemHeader();
				starLine(100, '*');
				itemList.forEach(Item::showItem);
				;
				break;
			case 3:
				item.showItemHeader();
				starLine(100, '*');
				itemList = i.showItemByReorder();
				itemList.forEach(Item::showItem);
				;
				break;
			case 4:
				item = new Item();
				item.inputItem();
				i.addItem(item);

				;
				break;
			case 5: {
				System.out.print("Enter Search Word for Item Name : ");
				String iwrod = Item.br.readLine();
				itemList = i.searchByKeyWord(iwrod);
				starLine(100, '*');
				item.showItemHeader();
				starLine(100, '*');
				itemList.forEach(Item::showItem);
				starLine(100, '*');
			}
				;
				break;
			case 6: {
				item.showItemHeader();
				mopt = sc.nextInt();
				System.out.println("By");
				System.out.println(" 1. Ascending Order");
				System.out.println(" 2. Decending Order");
				System.out.print(" Choose (1 - 2) :");
				catopt = sc.nextInt();

				itemList = i.sortItemData(catopt, mopt);
				starLine(100, '*');
				item.showItemHeader();
				starLine(100, '*');
				itemList.forEach(Item::showItem);
				starLine(100, '*');
			}
				;
				break;
			case 7: {
				System.out.print("Enter Delete Word for Item Name : ");
				String iwrod = Item.br.readLine();
				itemList = i.searchByKeyWord(iwrod);
				starLine(100, '*');
				item.showItemHeader();
				starLine(100, '*');
				itemList.forEach(Item::showItem);
				starLine(100, '-');
				System.out.println("Are you sure to delete that items (yes|no) : ");
				String ch = sc.next();
				if (ch.equalsIgnoreCase("Yes") || ch.equalsIgnoreCase("y")) {
					String noOfDeletedItems = i.deleteByKeyWord(iwrod);
					System.out.println("Delete " + noOfDeletedItems + " Items Successfully...");
				}
			}
				;
				break;
			case 8: 
				    SaleDAO s=new SaleDAO();
				    System.out.print("Enter Casher Name :");
				    String cName=item.br.readLine();
				    int sid=s.addSale(cName);
				    System.out.println("Select Selling item from following list ..");
				    itemList=i.showAllItem();
				    starLine(100,'-');
				    item.showItemHeader();
				    starLine(100,'-');
				    itemList.forEach(Item::showItem);
				    starLine(100,'-');
				    while(sellItem(sid).equalsIgnoreCase("yes"));
				    genereatSlip(cName,sid);
				;break;
			case 9:
			String itemNo;
			int iNo,price =0;
			SaleDAO sdao=new SaleDAO();
			itemList=i.showAllItem();
			starLine(90,'-');
			item.showItemHeader();
			starLine(90,'-');
			itemList.forEach(Item::showItem);
			starLine(90,'-');
			System.out.println();
		
				do {
					System.out.println("Select Item No. : ");
					itemNo = sc.next();
					iNo = new ItemDAO().checkItemNo(itemNo);
					if (iNo == 0)
						System.out.println("Your Item No. does not exists...");
				} while (iNo == 0);
					System.out.println("Update price  for " + itemNo + " : ");
					
					System.out.println();
					price=sc.nextInt();
					i.updatePrice(itemNo, price);
					itemList=i.showAllItem();
					starLine(90,'-');
					item.showItemHeader();;
					starLine(90,'-');
					itemList.forEach(Item::showItem);
					starLine(90,'-');
			
				
				;break;
			case 10:
				int addQty,addprice;
				itemList=i.showAllItem();
				starLine(90,'-');
				item.showItemHeader();;
				starLine(90,'-');
				itemList.forEach(Item::showItem);
				starLine(90,'-');
				System.out.println();
				do {
					System.out.println("Select Item No. : ");
					itemNo = sc.next();
					iNo = new ItemDAO().checkItemNo(itemNo);
					if (iNo == 0)
						System.out.println("Your Item No. does not exists...");
				} while (iNo == 0);
					System.out.println("Update Quantity  for " + itemNo + " : ");
				addQty=sc.nextInt();
				System.out.println("Update Price  for " + itemNo + " : ");
				addprice=sc.nextInt();
				i.updatePrice_Qty(itemNo, addprice, addQty);
				itemList=i.showAllItem();
				starLine(90,'-');
				item.showItemHeader();;
				starLine(90,'-');
				itemList.forEach(Item::showItem);
				starLine(90,'-');
				
				;break;
			case 11:
				System.out.println("Thank You");
				break a;
			default:
				System.out.println("ERROR");
				break;
			}
			System.out.print("Do you want to continue(Y/N): ");
			choice = sc.next();
			starLine(20, '*');
		} while (choice.equalsIgnoreCase("yes") || choice.equalsIgnoreCase("y"));
		System.out.println("End of the Program");
	}

	private static String sellItem(int sid) throws SQLException {
		String itemNo;
		boolean isSell = false;

		int iNo, qty;
		do {
			System.out.println("Select Item No. : ");
			itemNo = sc.next();
			iNo = new ItemDAO().checkItemNo(itemNo);
			if (iNo == 0)
				System.out.println("Your Item No. does not exists...");
		} while (iNo == 0);

		do {
			System.out.println("Enter Quantity for " + itemNo + " : ");
			qty = sc.nextInt();
			isSell = new ItemDAO().checkItemQty(itemNo, qty);
			if (!isSell)
				System.out.println("Your quantity is not aailabel...");
		} while (!isSell);
		if (new SaleDAO().addSaleDetail(sid, itemNo, qty) == 0)
			System.out.println("Can't Sell " + itemNo);
		System.out.print("Continue o not (yes | no)  :");
		return sc.next();
	}	
	
	
	public static void genereatSlip(String cname,int sid) throws IOException, SQLException{
		 String fname="SaleSlip/Slip_"+Calendar.DATE+" "+Calendar.MONTH+" "+Calendar.YEAR+" "+Calendar.HOUR+Calendar.MINUTE+Calendar.SECOND+".txt";
		 PrintWriter pw=new PrintWriter(new FileWriter(fname));
		 pw.write("hello");
		 String sdate;
		 
		 //Date and Name 
		 sdate=String.format("%10s", "Date  :");
		 sdate+=String.format("%-30s", DateFormat.getInstance().format(new Date()));
		 sdate+=String.format("%20s", "Casher Name :");
		 sdate+=String.format("%-30s", cname);
		 pw.println(sdate);
		 pw.println(starLine('_',90));
		//Heading 
		 pw.println(starLine('-',90));
		 sdate=String.format("%-5s", "No .");
		 sdate=String.format("%-30s", "Item Name");
		 sdate=String.format("%10s", "Price");
		 sdate=String.format("%10s", "Quantity");
		 sdate=String.format("%10s", "Amount");
		 pw.println(sdate);
		 pw.println(starLine('-',90));
		 
		 int total=0; 
		 int amount=0;
		 ArrayList<SaleItem> sitem=new SaleDAO().getSaleDataByID(sid);
		 for(int i=0;i<sitem.size();i++) {
			  sdate=String.format("%-5s", i+1);
			  sdate+=String.format("%-30s", sitem.get(i).getItemName());
				 sdate+=String.format("%10s", sitem.get(i).getPrice());
				 sdate+=String.format("%10s", sitem.get(i).getQty());
				 amount=sitem.get(i).getPrice()*sitem.get(i).getQty();
				 sdate+=String.format("%10s", amount);
			  total+=amount;
			  pw.println(sdate);
		 }
		 
		 pw.println(starLine('-',90));
		 sdate=String.format("%55s", "Total : ");
		 sdate+=String.format("%10s", total);
		 pw.println(sdate);
		 pw.println(starLine('-',90));
		 pw.close();
	}
	public static String starLine(char ch,int n) {
		String str="";
		for(int i=0;i<n;i++)
			str+=ch;
		return str;
		
	}

}
