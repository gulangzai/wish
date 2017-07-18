package icom.yh.wish.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="product_id")
public class ProductId {
	private int id;
	private String itemId;
	private int fly;
	private int hot;
	private int newfly;
	private int newhot;
	private int newshophot;
	private int newshopfly;
	
	private String day;
	
	private int page;
	
	private int isC;
	public ProductId() {
	}

	public ProductId(int page, String itemId, int fly, int hot, int newfly, int newhot, int newshophot, int newshopfly,
			String day) {
		super();
		this.page = page;
		this.itemId = itemId;
		this.fly = fly;
		this.hot = hot;
		this.newfly = newfly;
		this.newhot = newhot;
		this.newshophot = newshophot;
		this.newshopfly = newshopfly;
		this.day = day;
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

	@Column(name="itemId", unique=true, nullable=false)
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getFly() {
		return fly;
	}

	public void setFly(int fly) {
		this.fly = fly;
	}

	public int getHot() {
		return hot;
	}

	public void setHot(int hot) {
		this.hot = hot;
	}

	public int getNewfly() {
		return newfly;
	}

	public void setNewfly(int newfly) {
		this.newfly = newfly;
	}

	public int getNewhot() {
		return newhot;
	}

	public void setNewhot(int newhot) {
		this.newhot = newhot;
	}

	public int getNewshophot() {
		return newshophot;
	}

	public void setNewshophot(int newshophot) {
		this.newshophot = newshophot;
	}

	public int getNewshopfly() {
		return newshopfly;
	}

	public void setNewshopfly(int newshopfly) {
		this.newshopfly = newshopfly;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getIsC() {
		return isC;
	}

	public void setIsC(int isC) {
		this.isC = isC;
	}

}	