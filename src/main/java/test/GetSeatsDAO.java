package test;
import java.sql.*;
import java.util.*;
public class GetSeatsDAO {
	private ArrayList<GetSeatsBean> al = new ArrayList<GetSeatsBean>();
	private GetSeatsBean gb = null;
	public ArrayList<GetSeatsBean> getSeats(TypeOfSeatBean type) {
		try {
			Connection con = DBConnection.getCon();
			String title = type.getTitle().trim();
			PreparedStatement ps = con.prepareStatement
					("SELECT seatNumber,booked,seatType,availability FROM "+title);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				gb = new GetSeatsBean();
				gb.setSeatNumber(rs.getInt(1));
				gb.setBooked(rs.getString(2));
				gb.setSeatType(rs.getString(3));				
				gb.setAvailability(rs.getString(4));
				al.add(gb);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return al;
	}
}
