/*
	Ho ten: Vũ Thanh Nga
  	Lớp: K67CNTTB
	Mã SV: 671715
*/
package gui;

public class Product {
	private String code, nameProduct, author, category, imgPath;
	private int releasesYear;

	public Product() {}

	public Product(String nameProduct, String author) {
		this.nameProduct = nameProduct;
		this.author = author;
	}

	public Product(String code, String nameProduct, String author, String category, int releasesYear) {
		this.code = code;
		this.nameProduct = nameProduct;
		this.author = author;
		this.category = category;
		this.releasesYear = releasesYear;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getReleasesYear() {
		return releasesYear;
	}

	public void setReleasesYear(int releasesYear) {
		this.releasesYear = releasesYear;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}

}
