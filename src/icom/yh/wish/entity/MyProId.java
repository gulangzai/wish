package icom.yh.wish.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="my_product_id")
public class MyProId {
	private int id;
	private int userId;
	private String itemId;
	private String day;
	private String type;
	public MyProId() {
	}
	public MyProId(int userId,String itemId, String day, String type) {
		super();
		this.userId = userId;
		this.itemId = itemId;
		this.day = day;
		this.type = type;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
