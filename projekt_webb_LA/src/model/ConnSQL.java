package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Beans.ContentBean;
import Beans.UserBean;

public class ConnSQL {
	static Connection conn = null;
	static PreparedStatement stmt = null;
	static ResultSet rs = null;
	
	public static void closeConn() throws SQLException{
		if(conn != null) {
			conn.endRequest();
			conn.close();
			System.out.println("connection is closed");
			}
	}

	public static boolean connectSQL(String db) {
		try {
			// driver setup
			Class.forName("com.mysql.cj.jdbc.Driver");
		}
		// handle the error
		catch (Exception ex) {
			System.out.println("exe driver: " + ex);
			return false;
		}
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+db+"?serverTimezone=UTC", "root", "");
			return true;
		} catch (SQLException ex) {
			System.out.println("no connection");
			return false;
		}
	}

	public static boolean loginValidator(UserBean bean) throws SQLException {

		try {
			String requestQuery = "SELECT `userN`, `pass` FROM `login` WHERE `userN` LIKE ? AND `pass` LIKE ?";

			stmt = conn.prepareStatement(requestQuery);

			stmt.setString(1, bean.getUserN());
			stmt.setString(2, bean.getPassword());

			rs = stmt.executeQuery();
			while (rs.next()) {

				System.out.println(rs.getString(1) + "  " + rs.getString(2));
				return true;
				}
			

		} catch (Exception e) {
			return false;
		}
		
		finally {
			closeConn();
		}
		return false;

	}


	// method that handles the user content input
	public static void contentToDB(ContentBean cBean) {

		try {
			String requestQuery = "INSERT INTO `content`(`user`, `title`, `text`, `tags`) VALUES (?,?,?,?)";
			stmt = conn.prepareStatement(requestQuery);

			stmt.setString(1, cBean.getUser());
			stmt.setString(2, cBean.getTitle());
			stmt.setString(3, cBean.getText());
			stmt.setString(4, cBean.getTags());

			stmt.executeUpdate();
			System.out.println("working");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("not working");
		}
	}

	// Method for getting and returning the content from database
	public static ArrayList<ContentBean> contentToPage() throws SQLException {

		try {
			// create arraylist for content beans
			ArrayList<ContentBean> cbList = new ArrayList<>();

			// storing sql-query, select orderd by newest content to oldest through id nr
			String requestQuery = "SELECT * FROM `content` ORDER BY `content_id` DESC";
			stmt = conn.prepareStatement(requestQuery);
			rs = stmt.executeQuery();

			// Loop for creating beans and fill with content from db and store in list
			while (rs.next()) {
				ContentBean cBean = new ContentBean();
				cBean.setUser(rs.getString("user"));
				cBean.setTitle(rs.getString("title"));
				cBean.setText(rs.getString("text"));
				cBean.setTags(rs.getString("tags"));
				cbList.add(cBean);
			}
			return cbList;


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		finally {
			closeConn();
		}
	}
}
