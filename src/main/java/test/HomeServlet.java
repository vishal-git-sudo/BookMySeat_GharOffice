package test;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.servlet.*;
import java.io.*;
@SuppressWarnings("serial")
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
	protected void doPost(HttpServletRequest req,HttpServletResponse res) throws ServletException,IOException {
		res.setContentType("text/html");
		String title = req.getParameter("title");
		HttpSession hs = req.getSession();
		hs.setAttribute("title",title.toLowerCase());
		RequestDispatcher rd = req.getRequestDispatcher("userName.html");
		rd.include(req, res);
	}
}
