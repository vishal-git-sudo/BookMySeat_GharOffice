package test;
import java.sql.*;
public class CreateTableDAO {
	private boolean k;
	public CreateTableDAO() {}
	public boolean create(String title) {
		try {
			Connection con = DBConnection.getCon();
			PreparedStatement pss1 = con.prepareStatement("SELECT * FROM ALL_TABLES WHERE TABLE_NAME='"+title.toUpperCase()+"'");
			ResultSet rss1 = pss1.executeQuery();
			if(rss1.next()) {
				System.out.println("Table exists");
				PreparedStatement pss2 = con.prepareStatement("SELECT * FROM "+title+"");
				ResultSet rss2 = pss2.executeQuery();
				if(rss2.next()) {
					System.out.println("Table has data");
				}
				else {
					System.out.println("Table does not have data");
					String query1 = "INSERT INTO "+ title +" VALUES (?,'s','f','f','')";
					String query2 = "INSERT INTO "+ title +" VALUES (?,'p','f','f','')";
					System.out.println(query1);
					System.out.println(query2);
					PreparedStatement ps1 = con.prepareStatement(query1);
					PreparedStatement ps2 = con.prepareStatement(query2);
					for(int i=1;i<=70;i++) {
						ps1.setInt(1,i);
						ps1.addBatch();
					}
					ps1.executeBatch();
					for(int i=71;i<=100;i++) {
						ps2.setInt(1,i);
						ps2.addBatch();
					}
					ps2.executeBatch();
					System.out.println("Data Inserted Into the Table");
				}
			}
			else {
				System.out.println("Table does not exist");
				PreparedStatement ps = con.prepareStatement
						("CREATE TABLE " +title
						+ " (seatNumber NUMBER(3), "
						+ "seatType VARCHAR2(9), "
						+ "booked VARCHAR2(5), "
						+ "availability VARCHAR2(5), "
						+ "userName VARCHAR2(30), "
						+ "PRIMARY KEY(seatNumber) )");
				ps.execute();
				System.out.println("Table Created");
				String query1 = "INSERT INTO "+ title +" VALUES (?,'s','f','f','')";
				String query2 = "INSERT INTO "+ title +" VALUES (?,'p','f','f','')";
				System.out.println(query1);
				System.out.println(query2);
				PreparedStatement ps1 = con.prepareStatement(query1);
				PreparedStatement ps2 = con.prepareStatement(query2);
				for(int i=1;i<=70;i++) {
					ps1.setInt(1,i);
					ps1.addBatch();
				}
				ps1.executeBatch();
				for(int i=71;i<=100;i++) {
					ps2.setInt(1,i);
					ps2.addBatch();
				}
				ps2.executeBatch();
				System.out.println("Data Inserted into the table");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return k;
	}
}