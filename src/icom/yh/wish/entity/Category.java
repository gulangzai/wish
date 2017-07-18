package icom.yh.wish.entity;

import java.util.ArrayList;
import java.util.List;

public class Category {
	private String name;
	private String itemId;
	public Category() {
	}
	public Category(String name, String itemId) {
		super();
		this.name = name;
		this.itemId = itemId;
	}
	private List<Category> list = new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public List<Category> getList() {
		return list;
	}
	public void setList(List<Category> list) {
		this.list = list;
	}
}
