package test;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
@SuppressWarnings("serial")
@WebServlet("/user")
public class UserNameServlet extends HttpServlet {
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		String uname = req.getParameter("uname");
		HttpSession hs = req.getSession(false);
		hs.setAttribute("uname",uname);
		RequestDispatcher rd = req.getRequestDispatcher("seatType.html");
		rd.include(req, res);
	}
}