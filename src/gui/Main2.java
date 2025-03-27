package gui;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main2 extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("HomeGUI.fxml"));
			
			Scene scene = new Scene(root);
			 
			primaryStage.setScene(scene);
			scene.getStylesheets().add(getClass().getResource("application2.css").toExternalForm());
			
	 javafx.scene.image.Image imageView = new javafx.scene.image.Image("https://inkythuatso.com/uploads/images/2021/12/logo-doraemon-inkythuatso-21-08-43-16.jpg");

			 
			 // Đặt biểu tượng cho Stage
		        primaryStage.getIcons().add(imageView);
		      
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
