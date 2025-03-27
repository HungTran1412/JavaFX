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

public class ProductDAO {
	@FXML
	private TextField codeTextField;
	@FXML
	private TextField productTextField;
	@FXML
	private TextField authorTextField;
	@FXML
	private TextField categoryTextField;
	@FXML
	private TextField releasesYearTextField;

	public static Product checkProduct(String code) {
		Product prod = null;

		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			String sql = "SELECT * FROM tblproduct WHERE code = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, code);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				prod = new Product();
				prod.setCode(code);
				prod.setNameProduct(rs.getString("nameProduct"));
				prod.setAuthor(rs.getString("author"));
				prod.setCategory(rs.getString("category"));
				prod.setReleasesYear(rs.getInt("releasesYear"));
				prod.setImgPath(rs.getString("imgPath"));
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

		return prod;
	}

	public static boolean checkImg(String imgPath) {
		boolean exists = false;

		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		String sql = "SELECT imgPath FROM tblproduct WHERE imgPath=?";

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, imgPath);

			ResultSet rs = ps.executeQuery();

			exists = rs.next();

			con.close();
			ps.close();
			rs.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return exists;
	}

	public static List<Product> listAll() {
		List<Product> listProd = new ArrayList<>();

		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			String sql = "Select * from tblproduct";
			Statement ps = con.createStatement();

			ResultSet rs = ps.executeQuery(sql);

			while (rs.next()) {
				Product prod = new Product();
				prod.setCode(rs.getString("code"));
				prod.setNameProduct(rs.getString("nameProduct"));
				prod.setAuthor(rs.getString("author"));
				prod.setCategory(rs.getString("category"));
				prod.setReleasesYear(rs.getInt("releasesYear"));
				prod.setImgPath(rs.getString("imgPath"));

				listProd.add(prod);
			}
			rs.close();
			ps.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Ko thấy driver");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return listProd;
	}

	public static void changeImage(Product prod) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		String sql = "UPDATE tblproduct SET imgPath=? WHERE code=?";
		boolean changed = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, prod.getImgPath());
			ps.setString(2, prod.getCode());

			changed = ps.executeUpdate() > 0;
			ps.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void addProd(Product prod) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		String sql = "INSERT INTO tblproduct (code,nameProduct,author,category,releasesYear,imgPath) VALUES (?,?,?,?,?,?)";
		boolean inserted = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, prod.getCode());
			ps.setString(2, prod.getNameProduct());
			ps.setString(3, prod.getAuthor());
			ps.setString(4, prod.getCategory());
			ps.setInt(5, prod.getReleasesYear());
			ps.setString(6, prod.getImgPath());

			inserted = ps.executeUpdate() > 0;
			ps.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void deleteProd(Product prod) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		String sql = "DELETE FROM tblproduct WHERE code=?";
		boolean deleted = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, prod.getCode());

			deleted = ps.executeUpdate() > 0;
			ps.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updateProd(Product prod, String oldCode) {
		String jdbcURL = "jdbc:ucanaccess://lib/QLNS.accdb";
		String jdbcUser = "";
		String jdbcPass = "";
		String sql = "UPDATE tblproduct SET code=?, nameProduct=?, author=?, category=?, releasesYear=? WHERE code=?";
		boolean updated = false;

		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			Connection con = DriverManager.getConnection(jdbcURL, jdbcUser, jdbcPass);
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setString(1, prod.getCode());
			ps.setString(2, prod.getNameProduct());
			ps.setString(3, prod.getAuthor());
			ps.setString(4, prod.getCategory());
			ps.setInt(5, prod.getReleasesYear());
			ps.setString(6, oldCode);

			updated = ps.executeUpdate() > 0;
			ps.close();
			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}