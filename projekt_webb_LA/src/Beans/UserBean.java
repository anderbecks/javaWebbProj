package Beans;

import java.sql.SQLException;

import model.ConnSQL;

public class UserBean {

	private String userN, password;

	public UserBean() {
	}

	public String getUserN() {
		return userN;
	}

	public void setUserN(String userN) {
		this.userN = userN;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean validate(UserBean bean) {
		String db = "lo";
		if (ConnSQL.connectSQL(db)) {
			try {
				return ConnSQL.loginValidator(bean);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public void resetUserBean() {
		this.password = null;
		this.userN = null;
	}

}
