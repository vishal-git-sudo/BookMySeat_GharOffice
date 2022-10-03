package test;
import java.sql.*;
public class UpdateAvailabilityDAO {
	private int k = 0;
	public int update(TypeOfSeatBean type) {
		try {
			Connection con = DBConnection.getCon();
			String typeOfSeat = type.getTypeOfSeat().trim();
			String title = type.getTitle().trim();
			PreparedStatement ps = con.prepareStatement("UPDATE "+title+" SET Availability=? WHERE SeatType=?");
			
			if(typeOfSeat.equals("p")) {
				ps.setString(1,"t");
				ps.setString(2,typeOfSeat);
				k = ps.executeUpdate();
				
				ps.setString(1,"f");
				ps.setString(2,"s");
				k = k + ps.executeUpdate();
			}
			else if(typeOfSeat.equals("s")){
				ps.setString(1,"t");
				ps.setString(2,typeOfSeat);
				k = ps.executeUpdate();
				
				ps.setString(1,"f");
				ps.setString(2,"p");
				k = k + ps.executeUpdate();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return k;
	}
}