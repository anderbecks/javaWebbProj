package controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.UserBean;
import model.ConnSQL;

/**
 * Servlet implementation class SQLservlet
 */
@WebServlet("/SQLservlet")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (request.getSession().getAttribute("user") != null) {
			// get the user out of session
			UserBean bean = (UserBean) request.getSession().getAttribute("user");

			// Validate username and password again
			if (bean.validate(bean)) {

				// get the session and the request to go to the success page
				HttpSession session = request.getSession();
				session.setAttribute("user", bean);
				request.setAttribute("user", bean);

				RequestDispatcher rd = request.getRequestDispatcher("contentPage.jsp");
				rd.forward(request, response);
			} else {
				// this only happens if the sessionid is removed, manually or because it timed
				// out and you try to go directly to the "contentPage.jsp"
				// goto logout to clean up

				RequestDispatcher rd = request.getRequestDispatcher("Logout");
				rd.forward(request, response);

			}
		} else {
			// this should only happen if you try to goto "/Login" manually
			response.sendRedirect("loginPage.jsp");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// saving the user input in variables
		String user = request.getParameter("user");
		String password = request.getParameter("password");

		String db = "lo";
		ConnSQL.connectSQL(db);
		UserBean bean = new UserBean();
		bean.setUserN(user);
		bean.setPassword(password);
		
		if (bean.validate(bean)) {

			// generate an ID
			HttpSession session = request.getSession(); // its a part of the request

			// the user is logged in or not
			session.setAttribute("user", bean);

			request.setAttribute("user", bean);

			// RequestDispatcher for when we want to send the request to the new page
			RequestDispatcher rd = request.getRequestDispatcher("/ContentController");
			rd.forward(request, response);
			


		} else {
			// go to an error page that includes the index page to have the user try again
			RequestDispatcher rd = request.getRequestDispatcher("error.jsp");
			rd.forward(request, response);
		}

	}

}
