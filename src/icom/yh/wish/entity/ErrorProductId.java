package icom.yh.wish.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="error_product_id")
public class ErrorProductId {
	private int id;
	private int page;			
	private String type;		
	public ErrorProductId() {
	}
	public ErrorProductId(int page, String type) {
		super();
		this.page = page;
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
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
