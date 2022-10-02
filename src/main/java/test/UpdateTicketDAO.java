package test;
import java.util.ArrayList;
import java.sql.*;
public class UpdateTicketDAO {
	private int[] k=null;
	public UpdateTicketDAO() {}
	public int[] update(ArrayList<Integer> ticketList, String title) {
		try {
			Connection con = DBConnection.getCon();
			PreparedStatement ps = con.prepareStatement
					("UPDATE "+ title +" SET Booked='t' WHERE SeatNumber=?");
			int i=0;
			while(i<ticketList.size()) {
				ps.setInt(1,(ticketList.get(i)));
				ps.addBatch();
				i++;
			}
			k = ps.executeBatch();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return k;
	}
}
