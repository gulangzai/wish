package icom.yh.wish.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="out_time_img", uniqueConstraints={@UniqueConstraint(columnNames={"itemId","img"})})
public class OutTimeImg {
	private int id;
	private String itemId;
	private String img;
	public OutTimeImg() {
	}
	public OutTimeImg(String itemId, String img) {
		super();
		this.itemId = itemId;
		this.img = img;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
}
