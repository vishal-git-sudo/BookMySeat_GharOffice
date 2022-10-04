package test;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
@SuppressWarnings("serial")
@WebServlet("/BookingConfirmation")
public class BookingConfirmationServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		res.setContentType("text/html");
		PrintWriter pw = res.getWriter();
		ArrayList<Integer> ticketList = new ArrayList<Integer>();
		String[] tickets = req.getParameterValues("seats[]");
		for(int i=0;i<tickets.length;i++) {
			ticketList.add(Integer.parseInt(tickets[i]));
		}
		int finalCost = 0;
		HttpSession hs = req.getSession(false);
		String typeOfSeat = (String) hs.getAttribute("typeOfSeat");
		if(typeOfSeat.equals("p")) {
			finalCost = tickets.length * 250;
		}
		else {
			finalCost = tickets.length * 150;
		}
		String title = (String) hs.getAttribute("title");
		String uname = (String) hs.getAttribute("uname");
		int[] k = new UpdateTicketDAO().update(ticketList,title.trim(),uname);
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
				+ "   			<a class='back' href='home.html'>Home</a>"
				+ " 		</div>"
				+ "	</div>");
		pw.println("<div style='font-size:18px'>");
		if(k!=null) {
			pw.println("<h3>Booking Confirmed!</h3>");
			pw.println("User Name: "+uname.toUpperCase()+"<br>");
			pw.println("Movie: "+updateTitle(title)+"<br>");
			pw.println("Seats Booked: ");
			for(int i=0;i<ticketList.size();i++) {
				pw.println(ticketList.get(i)+" ");
				if(i!=(ticketList.size()-1)) {
					pw.println(", ");
				}
			}
			pw.println("<br>Billing Amount: "+finalCost);
			hs.invalidate();
		}
		else {
			pw.println("Booking Unsuccessful..<br>Please try again...<br>");
			RequestDispatcher rd = req.getRequestDispatcher("home.html");
			rd.include(req, res);
		}
		pw.println("</div>");
		pw.println("</body>");
	}
	public String updateTitle(String title) {
		char[] arr = title.toCharArray();
		title="";
		for(int i=0;i<arr.length;i++) {
			if(Character.isLowerCase(arr[i])) {
				title += Character.toUpperCase(arr[i]);
			}
			else if(Character.isUpperCase(arr[i])) {
				title += " "+arr[i];
			}
		}
		return title;
	}
}
