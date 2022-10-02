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
		String[] tickets = req.getParameterValues("seats");
		for(int i=0;i<tickets.length;i++) {
			ticketList.add(Integer.parseInt(tickets[i]));
		}
		System.out.println(tickets.toString());
		String title = (String) req.getSession().getAttribute("title");
		int[] k = new UpdateTicketDAO().update(ticketList,title.trim());
		if(k!=null) {
			pw.println("Booking Confirmed!<br>");
			HttpSession hs = req.getSession(false);
			String uname = (String) hs.getAttribute("uname");
			pw.println("User: "+uname+"<br>");
			pw.println("Movie: "+title+"<br>");
			pw.println("Seats Booked: ");
			for(int i=0;i<ticketList.size();i++) {
				pw.println(ticketList.get(i)+" ");
				if(i!=(ticketList.size()-1)) {
					pw.println(", ");
				}
			}
			hs.invalidate();
		}
		else {
			pw.println("Booking Unsuccessful..<br>Please try again...<br>");
			RequestDispatcher rd = req.getRequestDispatcher("home.html");
			rd.include(req, res);
		}
	}
}
