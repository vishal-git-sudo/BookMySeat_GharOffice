package test;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

/* Take input TypeOfSeat & NumberOfSeats from seatType.html
 * Update Availability status in the Movie01 according to the TypeOfSeat
 * Get SeatNumber, Booked & Availability from table
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/seatType")
public class SeatTypeServlet extends HttpServlet {
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		PrintWriter pw = res.getWriter();
		res.setContentType("text/html");
		HttpSession hs = req.getSession(false);
		String typeOfSeat = req.getParameter("type");
		int numberOfSeats = Integer.parseInt(req.getParameter("quantity"));
		hs.setAttribute("numberOfSeats",numberOfSeats);
		hs.setAttribute("typeOfSeat",typeOfSeat);
		String title = (String) hs.getAttribute("title");
		
		TypeOfSeatBean type = new TypeOfSeatBean();
		type.setTypeOfSeat(typeOfSeat);
		type.setTitle(title);
		int k = new UpdateAvailabilityDAO().update(type);
		if(k>0) {
			System.out.println("Availability status updated in the database!");
		}
		ArrayList<GetSeatsBean> seats = new GetSeatsDAO().getSeats(type);
		
		if(seats==null) {
			pw.println("Seats could not be returned from the database<br>");
		}
		else {
			pw.println("");
			pw.println();
			pw.println("<div style='text-align:center'><h3> Select The Seats </h3></div>");
			pw.println("<div style='text-align:center;border: 2px solid red;border-radius: 5px'>");
			pw.println("<form action = 'BookingConfirmation' method='post'>");
			int i=0;
			pw.println("<div><h5>Standard</h5></div>");
			while(i<seats.size()) {
				GetSeatsBean gsb = seats.get(i);
				pw.println(gsb.getSeatNumber());
				if(gsb.getBooked().trim().equals("f")) {
					if(gsb.getAvailability().trim().equals("t")) {
						pw.println("<input type='checkbox' value="+(i+1)+""+" name='seats' height='40px' width='40px'>");
					}
					else {
						pw.println("<input type='checkbox' value="+(i+1)+""+" name='seats' height='40px' width='40px' disabled>");
					}
				}
				else {
					pw.println("<input type='checkbox' value="+(i+1)+""+" name='seats' height='40px' width='40px' disabled>");
				}
				if((i+1)%10==0) {
					pw.println("<br>");
				}
				if((i+1)%70==0) {
					pw.println("<div><h5>Premium</h5></div>");
				}
				i++;
			}
			pw.println("<br><input type='submit' style='box-shadow: rgba(255, 255, 255, 0.3) 0 0 2px inset, "
					+ "rgba(0, 0, 0, 0.4) 0 1px 2px; text-decoration: none; transition-duration: .15s, .15s;' value='Book'>");
			pw.println("</form>");
			pw.println("</div>");
			
		}
	}
}