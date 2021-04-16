package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Beans.ContentBean;
import Beans.UserBean;
import model.ConnSQL;

/**
 * Servlet implementation class ContentController
 */
@WebServlet("/ContentController")
public class ContentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ContentController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getSession().getAttribute("user") != null) {
			// get the user out of session
			UserBean bean = (UserBean) request.getSession().getAttribute("user");

			// Validate username and password again
			if (bean.validate(bean)) {

				// get the session and the request to go to the content page
				HttpSession session = request.getSession();
				session.setAttribute("user", bean);
				request.setAttribute("user", bean);

				RequestDispatcher rd = request.getRequestDispatcher("contentPage.jsp");
				rd.forward(request, response);
			} else {
				// this only happens if the sessionid is removed, manually or because it timed
				// out and you try to go directly to the "success.jsp"
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
		// creates a content bean
		ContentBean cBean = new ContentBean();

		//
		HttpSession session = request.getSession();
		UserBean Bean = (UserBean) session.getAttribute("user");
		cBean.setUser(Bean.getUserN());
		String title = request.getParameter("title");
		String content = request.getParameter("text");
		String tags = request.getParameter("tags");

		cBean.setTitle(title);
		cBean.setText(content);
		cBean.setTags(tags);

		String db = "site";
		ConnSQL.connectSQL(db);

		ConnSQL.contentToDB(cBean);
		ArrayList<ContentBean> cbList = new ArrayList<>();

		try {
			cbList = ConnSQL.contentToPage();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		session.setAttribute("content", cbList);

		RequestDispatcher rd = request.getRequestDispatcher("contentPage.jsp");
		rd.forward(request, response);

	}

}
