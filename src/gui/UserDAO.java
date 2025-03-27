/*
	Ho ten: Vũ Thanh Nga
  	Lớp: K67CNTTB
	Mã SV: 671715
*/
package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class UserDAO {
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;

	public static User checkUser(String username) {
		User user = null;

		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		String sql = "Select * from tbluser Where username = ?";
		
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setUsername(username);
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getInt("role"));

			}

			con.close();
			ps.close();
			rs.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ko thấy driver");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return user;
	}

}