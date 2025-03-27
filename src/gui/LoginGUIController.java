/*
	Ho ten: Vũ Thanh Nga
  	Lớp: K67CNTTB
	Mã SV: 671715
*/
package gui;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginGUIController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private TextField passwordTextField;
	@FXML
	private Label loginError;

	@FXML
	public void onClickLogin() {
		System.out.println("Tài khoản: " + usernameTextField.getText());
		System.out.println("Mật khẩu: " + passwordTextField.getText());
		// Lấy nội dung user, password
		String username = usernameTextField.getText().trim();
		String password = passwordTextField.getText().trim();

		// Xóa thông báo lỗi trước đó
		loginError.setText("");

		if (username.isEmpty() && password.isEmpty()) {
			loginError.setText("Hạy nhập tài khoản và mật khẩu!");
			System.out.println("Hạy nhập tài khoản và mật khâu!");
		} else if (username.isEmpty()) {
			loginError.setText("Hạy nhập tài khoản!");
			System.out.println("Nhập tài khoản đi tề");
		} else if (password.isEmpty()) {
			loginError.setText("Hạy nhập mật khẩu");
			System.out.println("Thiếu mật khẩu tề");
		} else {
			// Kiểm tra người dùng
			User user = UserDAO.checkUser(username);

			// Nếu không tìm thấy người dùng
			if (user == null || !user.getPassword().equals(password)) {
				loginError.setText("Tên đăng nhập hoặc mật khẩu nỏ đúng!");
				System.out.println("Sai tài khoản hoặc mật khẩu");
			} else {
				// chuyen ra man hinh home
				System.out.println("OK rồi đó");
				usernameTextField.getScene().getWindow().hide();
				goHomeScreen(user);
			}
		}
	}

	public void goHomeScreen(User loginedUser) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomeGUI.fxml"));
			Parent root = fxmlLoader.load();

			HomeGUIController hgc = fxmlLoader.getController();
			hgc.setLoginedUser(loginedUser);

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
			
			
			Stage homeStage = new Stage();
			homeStage.setScene(scene);
			homeStage.setTitle("Trang chủ");
		
			 javafx.scene.image.Image imageView = new javafx.scene.image.Image("https://inkythuatso.com/uploads/images/2021/12/logo-doraemon-inkythuatso-21-08-43-16.jpg");

			 
			 // Đặt biểu tượng cho Stage
			 homeStage.getIcons().add(imageView);
			homeStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
