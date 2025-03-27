/*
	Ho ten: Vũ Thanh Nga
  	Lớp: K67CNTTB
	Mã SV: 671715
*/
package gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.*;

public class LoginMain extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginGUI.fxml"));

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			 javafx.scene.image.Image imageView = new javafx.scene.image.Image("https://inkythuatso.com/uploads/images/2021/12/logo-doraemon-inkythuatso-21-08-43-16.jpg");

			 
			 // Đặt biểu tượng cho Stage
		        primaryStage.getIcons().add(imageView);
		      
			 
			 
			   scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
   
}
