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
			ArrayList<GetSeatsBean> seats = new GetSeatsDAO().getSeats(type);
			if(seats==null) {
				RequestDispatcher rd = req.getRequestDispatcher("error.html");
				rd.include(req, res);
			}
			else {
				pw.println("<head><style>body {"
						+ "		background:rgb(242, 242, 242);"
						+ "		background-repeat: no-repeat, repeat;"
						+ "		background-position: center; "
						+ "		background-size: cover;"
						+ "		"
						+ "		color:#665E5E ;"
						+ "		font-family: cursive;"
						+ "		font-weight: bold;"
						+ "		font-size: 14px;"
						+ "		 margin: 0;"
						+ "		text-align:center;"
						+ "} "
						+ ".topnav {"
						+ "  overflow: hidden;"
						+ "  background-color: #333;"
						+ "  "
						+ "}"
						+ ""
						+ ".topnav a {"
						+ "  float: left;"
						+ "  color: #f2f2f2;"
						+ "  text-align: center;"
						+ "  padding: 14px 16px;"
						+ "  text-decoration: none;"
						+ "  font-size: 22px;"
						+ "}"
						+ ""
						+ ".topnav a:hover {"
						+ "  background-color: #FB6464;"
						+ "  color: black;"
						+ "}"
						+ ""
						+ ".topnav a.active {"
						+ "  background-color: #F68888;"
						+ "  color: white;"
						+ "}"
						+ ".topnav-right {"
						+ "  float: right;"
						+ "}</style></head>");
				pw.println("<body><div class=\"topnav\">"
						+ "  		<a class=\"active\" href=\"home.html\"><b>Book My Seat</b></a>"
						+ "  		<div class=\"topnav-right\">"
						+ "   			<a class='back' href='seatType.html'>Back</a>"
						+ " 		</div>"
						+ "	</div>");
				pw.println("<h2 style='text-align:center'> Select The Seats </h2>");
				pw.println("<div style='text-align:center;border: 2px solid red;border-radius: 5px;box-shadow: 0 0 15px rgba(0,0,0,0.75);'>");
				pw.println("<form action = 'BookingConfirmation' method='post' name='form_name' id='form_name'>");
				int i=0;
				pw.println("<div><h3>Standard</h3></div>");
				while(i<seats.size()) {
					GetSeatsBean gsb = seats.get(i);
					pw.println(gsb.getSeatNumber());
					if(gsb.getBooked().trim().equals("f")) {
						if(gsb.getAvailability().trim().equals("t")) {
							pw.println("<input type='checkbox' value="+(i+1)+""+" name='seats[]' >&nbsp;&nbsp;");
						}
						else {
							pw.println("<input type='checkbox' value="+(i+1)+""+" name='seats[]' disabled>&nbsp;&nbsp;");
						}
					}
					else {
						pw.println("<input type='checkbox' value="+(i+1)+""+" name='seats[]' disabled>&nbsp;&nbsp;");
					}
					if((i+1)%10==0) {
						pw.println("<br>");
					}
					if((i+1)%70==0) {
						pw.println("<div><h3>Premium</h3></div>");
					}
					i++;
				}
				pw.println("<br><input type='submit' value='Book' style='padding: 12px 24px;margin: 4px 2px; background:#32a2a8; border:none; color:white; font-family:cursive;'>");
				pw.println("</form>");
				pw.println("</div>");
				pw.println("</body>");
				pw.println("</html>");
			}
		}
		else {
			RequestDispatcher rd = req.getRequestDispatcher("error.html");
			rd.include(req, res);
		}
	}
}