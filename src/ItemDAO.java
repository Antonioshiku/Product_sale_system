
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ItemDAO {
	private Connection Con;

	public ItemDAO() {
		try {
			Con = DriverManager.getConnection("jdbc:mysql://localhost:3306/productdb", "root", "123456");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void close(Connection con, PreparedStatement pst, ResultSet rs) throws SQLException {
		if (con != null)
			Con.close();
		if (pst != null)
			pst.close();
		if (rs != null)
			rs.close();
	}

	public ArrayList<Item> showAllItem() throws SQLException {

		ArrayList<Item> item = new ArrayList<Item>();

		PreparedStatement stmt = Con.prepareStatement("Select * from product");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			item.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
					rs.getInt(6)));
		}
	
		return item;

	}

	public ArrayList<Item> showItemByCategory(String cat) throws SQLException {
		ArrayList<Item> i= new ArrayList<Item>();
		PreparedStatement stmt = Con.prepareStatement("select * from product where Category=? ");
		
		stmt.setString(1,cat);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
		i.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
					rs.getInt(6)));
		} 
		
		return i;

	}
	
	public ArrayList<Item> showItemByReorder() throws SQLException{
		ArrayList<Item> item = new ArrayList<Item>();
		PreparedStatement stmt = Con.prepareStatement("select * from product where Reorder_level >= Quantity ");
		ResultSet rs=stmt.executeQuery();
		
		while (rs.next()) {
			item.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6)));
			} 
		close(Con,stmt,rs);
		return item;
	}
	
	public int checkItemNo(String iNo) throws SQLException{
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement stmt = Con.prepareStatement("select * from product where Item_No=? ");
		stmt.setString(1, iNo);
		ResultSet rs=stmt.executeQuery();

		while (rs.next()) {
			item.add(new Item(rs.getString(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5),
						rs.getInt(6)));
			}
		
		return item.size();
		 
	}
	
	public String addItem(Item item) throws SQLException {
		 String msg=null;
			PreparedStatement stmt = Con.prepareStatement("insert into product values(?,?,?,?,?,?)");
			stmt.setString(1, item.getItemNo());
			stmt.setString(2, item.getItemName());
			stmt.setString(3, item.getCategory());
			stmt.setInt(4, item.getPrice());
			stmt.setInt(5, item.getQty());
			stmt.setInt(6, item.getRlevel());
			
			int i = stmt.executeUpdate();
			
			if(i>0) msg="Save successfully for"+ i +"rows";
			
			close(Con,stmt,null);
			return msg;
	}
	
	public ArrayList<Item> searchByKeyWord(String kword) throws SQLException{
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst = Con.prepareStatement("select * from product where Item_Name like ?");
		pst.setString(1,'%'+kword+'%');
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		return item;
	}
	
	public ArrayList<Item> sortItemData(int order,int fieldType) throws SQLException{
		ArrayList<Item> item = new ArrayList<Item>();
		String sql;
		if(order == 1) {
			switch(fieldType) {
				case 1:sql="select * from product order by Item_No";break;
				case 2:sql="select * from product order by Item_Name";break;
				case 3:sql="select * from product order by Category";break;
				case 4:sql="select * from product order by Price";break;
				case 5:sql="select * from product order by Quantity";break;
				case 6:sql="select * from product order by Reorder_Level";break;
				default:sql="select * from product";
			}
		}
		else if(order == 2) {
			switch(fieldType) {
				case 1:sql="select * from product order by Item_No desc";break;
				case 2:sql="select * from product order by Item_Name desc";break;
				case 3:sql="select * from product order by Category desc";break;
				case 4:sql="select * from product order by Price desc";break;
				case 5:sql="select * from product order by Quantity desc";break;
				case 6:sql="select * from product order by Reorder_Level desc";break;
				default:sql="select * from product";
			}
		}
		else {
			sql="select * from product";
		}
		
		PreparedStatement pst=Con.prepareStatement(sql);
		ResultSet rs = pst.executeQuery();
		while(rs.next()) {
			item.add(new Item(rs.getString(1),rs.getString(2),rs.getString(3),rs.getInt(4),rs.getInt(5),rs.getInt(6)));
		}
		pst.close();
		rs.close();
		
		return item;
	}
	
	public String deleteByKeyWord(String kword) throws SQLException{
		PreparedStatement pst = Con.prepareStatement("delete from product where Item_Name like ?");
		pst.setString(1,'%'+kword+'%');
		int i = pst.executeUpdate();
	
		String msg = null;
		if(i>0)
			msg = "Delete Successfully for "+i+" rows";
		pst.close();
		return msg;
	}
	
	public boolean checkItemQty(String itemNo,int q) throws SQLException{
  		 boolean result=false;
  		 int qty=0;
  		 PreparedStatement pst=Con.prepareStatement("select Quantity from product where Item_No=?");
  		 pst.setString(1, itemNo);
  		 ResultSet rs=pst.executeQuery();
  		 while(rs.next()) {
  			   qty=rs.getInt(1);
  		 }
  		 if(qty>=q) {
  			  pst=Con.prepareStatement("update product set Quantity=? where Item_No=?");
  			  pst.setInt(1, qty-q);
  			  pst.setString(2, itemNo);
  			  pst.executeUpdate();
  			  result=true;
  			  
  		 }
  		 close(Con,pst,rs);
  		 return result;
  	}
	
	public ArrayList<Item> updatePrice(String ino,int p) throws SQLException{
	
		int a=0;
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst=Con.prepareStatement("update product set price=? where item_no=?");
		pst.setInt(1, p);
		pst.setString(2, ino);
		a=pst.executeUpdate();
		
		close(null,pst,null);
		return item;
	}
	
	
	public ArrayList<Item> updatePrice_Qty(String ino,int p,int q) throws SQLException{
	
		int a=0;
		int quantity=0;
		ArrayList<Item> item=new ArrayList<Item>();
		PreparedStatement pst=Con.prepareStatement("update product set price=? where item_no=?");
		pst.setInt(1, p);
		pst.setString(2, ino);
		pst.executeUpdate();
 		  pst=Con.prepareStatement("select Quantity from product where Item_No=?");
 		 pst.setString(1, ino);
 		 ResultSet rs=pst.executeQuery();
 		 while(rs.next()) {
 			   quantity=rs.getInt(1);
 		 }
 		
 			  pst=Con.prepareStatement("update product set Quantity=? where Item_No=?");
 			  pst.setInt(1, quantity+q);
 			  pst.setString(2, ino);
 			  pst.executeUpdate();
 			
 			  
 		 
		
		close(null,pst,null);
		return item;
	}
  	

	
	
}
