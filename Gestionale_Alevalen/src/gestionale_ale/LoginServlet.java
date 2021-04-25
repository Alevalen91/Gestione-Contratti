package gestionale_ale;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String user;
	private String password;
	private boolean logged;
	 HttpSession currentsession;
	 HttpSession oldsession;
		PrintWriter printer;
       
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 printer =response.getWriter(); 
		
		user = request.getParameter("user");
		password = request.getParameter("password");
		
			if (password.equals("YOURPASSWORD")) {
			
			
			 // set logged to true and print it for js login session setting
		      
		        logged = true;
		       printer.print(logged);
		       
			}
			else {
			  logged = false;
			  printer.print(logged);
			  
		
			}
			printer.close();
	}
}	
