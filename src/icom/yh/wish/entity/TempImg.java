package icom.yh.wish.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="temp_img")
public class TempImg {
	private int id;
	private String imgUrl;
	private String imgPath;
	public TempImg() {
	}
	public TempImg(String imgUrl, String imgPath) {
		this.imgUrl = imgUrl;
		this.imgPath = imgPath;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
