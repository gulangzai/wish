package icom.yh.wish.entity;

import java.io.File;

public class DlImg {
	private String imgUrl;
	private File imgPath;
	public DlImg() {
	}
	public DlImg(String imgUrl, File imgPath) {
		super();
		this.imgUrl = imgUrl;
		this.imgPath = imgPath;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public File getImgPath() {
		return imgPath;
	}
	public void setImgPath(File imgPath) {
		this.imgPath = imgPath;
	}
}
