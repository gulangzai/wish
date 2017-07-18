package icom.yh.wish.entity;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="chicme_cate_tag")
public class ChicmeCateTag {
	private int id;
	private String category;
	private String tag;
	public ChicmeCateTag() {
	}
	public ChicmeCateTag(String category, String tag) {
		super();
		this.category = category;
		this.tag = tag;
	}
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
}
