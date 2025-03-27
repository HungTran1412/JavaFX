package gui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class HomeGUIController implements Initializable {
	@FXML
	private TableView<Product> tblproduct;
	@FXML
	private TableColumn<Product, String> codeCol;
	@FXML
	private TableColumn<Product, String> productCol;
	@FXML
	private TableColumn<Product, String> authorCol;
	@FXML
	private TableColumn<Product, String> categoryCol;
	@FXML
	private TableColumn<Product, Integer> releasesYearCol;
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
	@FXML
	private Label message;
	@FXML
	private ImageView imgView;
	@FXML
	private Hyperlink logout;
	@FXML
	private Button btnChangeImg;
	@FXML
	private Button btnNew;
	@FXML
	private Button btnAdd;
	@FXML
	private Button btnUpdate;
	@FXML
	private Button btnDel;

	private String oldCode;
	private User loginedUser;
	private String imgPath;

	private void showProd() {
		codeCol.setCellValueFactory(new PropertyValueFactory<Product, String>("code"));
		productCol.setCellValueFactory(new PropertyValueFactory<Product, String>("nameProduct"));
		authorCol.setCellValueFactory(new PropertyValueFactory<Product, String>("author"));
		categoryCol.setCellValueFactory(new PropertyValueFactory<Product, String>("category"));
		releasesYearCol.setCellValueFactory(new PropertyValueFactory<Product, Integer>("releasesYear"));

		List<Product> listProd = ProductDAO.listAll();

		ObservableList<Product> obList = FXCollections.observableArrayList(listProd);

		tblproduct.setItems(obList);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Platform.runLater(() -> {
			if (loginedUser != null) {
				message.setText("Xin chào " + loginedUser.getUsername());

				if (loginedUser.getRole() == 1) {
					// vô hiệu hóa chức năng
					btnAdd.setDisable(true);
					btnDel.setDisable(true);
					btnChangeImg.setDisable(true);
					btnUpdate.setDisable(true);
					btnNew.setDisable(true);
				} else if (loginedUser.getRole() == 0) {
					btnChangeImg.setDisable(true);
					btnDel.setDisable(true);
					btnUpdate.setDisable(true);
				}
			}
		});

		// tạo listener để lắng nghe sự kiện
		tblproduct.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
			if (newSelection == null) {
				btnChangeImg.setDisable(true);
			} else {
				if (loginedUser.getRole() == 1) {
					btnChangeImg.setDisable(true);
				} else if (loginedUser.getRole() == 0) {
					btnChangeImg.setDisable(false);
				}
			}
		});

		showProd();
	}

	@FXML
	public void onClickRow() {
		Product selectedprod = tblproduct.getSelectionModel().getSelectedItem();

		if (selectedprod == null) {
			btnChangeImg.setDisable(true);
			return;
		}

		codeTextField.setText(selectedprod.getCode());
		productTextField.setText(selectedprod.getNameProduct());
		authorTextField.setText(selectedprod.getAuthor());
		categoryTextField.setText(selectedprod.getCategory());
		releasesYearTextField.setText(String.valueOf(selectedprod.getReleasesYear()));
		imgPath = selectedprod.getImgPath();
		String rs = (imgPath != null) ? "Chọn: " + imgPath : "Chưa có ảnh được chọn: ";
		System.out.println(rs);

		// Dua anh vao ImageView
		FileInputStream input = null;
		try {

			if (selectedprod.getImgPath() != null) {
				input = new FileInputStream(selectedprod.getImgPath());
				Image image = new Image(input);
				imgView.setImage(image);
			} else {
				imgView.setImage(null);
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		oldCode = codeTextField.getText();

		if (loginedUser.getRole() == 0) {
			btnAdd.setDisable(true);
			btnDel.setDisable(false);
			btnUpdate.setDisable(false);
		}
	}

	@FXML
	public void buttonChangeImg() {
		Product selectedProd = tblproduct.getSelectionModel().getSelectedItem();

		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));
		File selectedFile = fc.showOpenDialog(null);

		if (selectedFile != null) {
			try {
				String fileName = selectedFile.getName();
				File imgDirectory = new File("img");
				if (!imgDirectory.exists()) {
					imgDirectory.mkdir();
				}

				File imgFile = new File(imgDirectory, fileName);
				String newImgPath = imgFile.getPath();

				// Kiểm tra ảnh trùng lặp trong cơ sở dữ liệu
				if (ProductDAO.checkImg(newImgPath) == true) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Lỗi");
					alert.setHeaderText("Ảnh bị ghi đè");
					alert.setContentText("Ảnh này đã tồn tại trong cơ sở dữ liệu.");
					alert.showAndWait();
					return;
				}

				// Sao chép ảnh vào thư mục img
				Files.copy(selectedFile.toPath(), imgFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				imgPath = newImgPath;
				selectedProd.setImgPath(imgPath);
				ProductDAO.changeImage(selectedProd);

				// Hiển thị ảnh mới
				FileInputStream input = new FileInputStream(imgPath);
				Image image = new Image(input);
				imgView.setImage(image);
				input.close();

				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Thông báo");
				alert.setHeaderText("Thành công");
				alert.setContentText("Đã thay đổi ảnh thành công.");
				alert.showAndWait();

				System.out.println("Ảnh selectedFile: " + selectedFile);
				System.out.println("Ảnh imgPath: " + imgPath);
			} catch (IOException e) {
				e.printStackTrace();
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Lỗi");
				alert.setHeaderText("Lỗi thay đổi ảnh");
				alert.setContentText("Không thể thay đổi ảnh. Vui lòng thử lại.");
				alert.showAndWait();
			}
		}

		if (loginedUser.getRole() == 0) {
			btnAdd.setDisable(true);
		}
	}

	@FXML
	public void buttonNew() {
		if (loginedUser.getRole() == 0) {
			btnAdd.setDisable(false);
			btnChangeImg.setDisable(true);
			btnDel.setDisable(true);
			btnUpdate.setDisable(true);
			clearTextFields();
		}
	}

	@FXML
	public void buttonAdd() {
		int yearRelease = Integer.parseInt(releasesYearTextField.getText());
		Product prod = new Product(codeTextField.getText(), productTextField.getText(), authorTextField.getText(),
				categoryTextField.getText(), yearRelease);
		prod.setImgPath(imgPath);
		ProductDAO.addProd(prod);
		clearTextFields();
		showProd();
		System.out.println("Đã thêm: " + prod.getCode() + " " + prod.getNameProduct() + " " + prod.getAuthor() + " "
				+ prod.getCategory() + " " + prod.getReleasesYear());
	}

	@FXML
	public void buttonUpdate() {
		int yearRelease = Integer.parseInt(releasesYearTextField.getText());
		Product prod = new Product(codeTextField.getText(), productTextField.getText(), authorTextField.getText(),
				categoryTextField.getText(), yearRelease);
		prod.setImgPath(imgPath);
		ProductDAO.updateProd(prod, oldCode);
		buttonNew();
		showProd();
		System.out.println("Đã sửa thành: " + prod.getCode() + " " + prod.getNameProduct() + " " + prod.getAuthor()
				+ " " + prod.getCategory() + " " + prod.getReleasesYear());
	}

	@FXML
	public void buttonDelete() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Xác nhận");
		alert.setHeaderText("Bạn có muốn xóa không?");
		Optional<ButtonType> rs = alert.showAndWait();

		if (rs.isPresent() && rs.get() == ButtonType.OK) {
			Product selectedProd = tblproduct.getSelectionModel().getSelectedItem();
			if (selectedProd != null) {
				// Xóa ảnh trong folder img của project
				String imgPathToDelete = imgPath;
				if (imgPathToDelete != null) {
					File imgFile = new File(imgPathToDelete);
					if (imgFile.exists()) {
						System.out.println("Ảnh: " + imgPathToDelete + " tồn tại");
						if (imgFile.delete()	) {
							System.out.println("Ảnh đã được xóa");
						} else {
							System.out.println("Không thể xóa");
						}

					} else {
						System.out.println(imgPathToDelete + " không tồn tại");
					}
				}

				ProductDAO.deleteProd(selectedProd);
				buttonNew();
				showProd();
			} else {
				System.out.println("Không có sản phẩm nào được chọn.");
			}
		}
	}

	@FXML
	public void logOut() {
		logout.getScene().getWindow().hide();
		System.out.println("Đã đăng xuất");
	}

	public User getLoginedUser() {
		return loginedUser;
	}

	public void setLoginedUser(User loginedUser) {
		this.loginedUser = loginedUser;
	}

	public void clearTextFields() {
		codeTextField.setText(null);
		productTextField.setText(null);
		authorTextField.setText(null);
		categoryTextField.setText(null);
		releasesYearTextField.setText(null);
		imgView.setImage(null);
		imgPath = null;
	}
}
