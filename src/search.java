import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class search {
	public static void main(String[] args) throws SQLException {

		Scanner sc = new Scanner(System.in);
		String url = "jdbc:mysql://localhost:3306/student";
		String username = "root";
		String password = "123456";

		Connection con = DriverManager.getConnection(url, username, password);

		System.out.println("Connected");

		Statement st = con.createStatement();
		System.out.println("Student Information\n");
		System.out.print("Put Student marks  :  \n");
		String SId = sc.next();
		String result = ("SELECT * from marks where mark  > '%s' ").formatted(SId);
		ResultSet rs = st.executeQuery(result); 
		System.out.println("Id \t Student Name \t\t Marks");
		while (rs.next())
			System.out
					.println(rs.getInt("id") + " \t " + rs.getString("Student_Name") + "\t\t " + rs.getString("Mark"));

		System.out.println("\nData Correct");

		// connection close
		con.close();
		st.close();
		rs.close();
	}
}
