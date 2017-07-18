package icom.yh.wish.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="wish_simple_data", uniqueConstraints={@UniqueConstraint(columnNames={"userId","itemId"})})
public class WishSimpleData {
	private int id;
	private int userId = 0;
	private String display_picture;
	private String feed_tile_text;
	private String price;
	private String itemId;
	private int search = 0;
	private int select = 0;
	private int add = 0;
	private int isC = 0;
	private int isS = 0;
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDisplay_picture() {
		return display_picture;
	}
	public void setDisplay_picture(String display_picture) {
		this.display_picture = display_picture;
	}
	public String getFeed_tile_text() {
		return feed_tile_text;
	}
	public void setFeed_tile_text(String feed_tile_text) {
		this.feed_tile_text = feed_tile_text;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = new BigDecimal(price).divide(new BigDecimal(7),2).toString();
	}
	@Column(name="itemId", nullable=false)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public int getSearch() {
		return search;
	}
	public void setSearch(int search) {
		this.search = search;
	}
	@Column(name="_select")
	public int getSelect() {
		return select;
	}
	public void setSelect(int select) {
		this.select = select;
	}
	@Column(name="_add")
	public int getAdd() {
		return add;
	}
	public void setAdd(int add) {
		this.add = add;
	}
	public int getIsC() {
		return isC;
	}
	public void setIsC(int isC) {
		this.isC = isC;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Override
	public String toString() {
		return "WishSimpleData [display_picture=" + display_picture + ", feed_tile_text=" + feed_tile_text + ", price="
				+ price + "]";
	}
	public static void main(String[] args) {
		System.out.println(new BigDecimal("9.0").divide(new BigDecimal(7),2).toString());
	}
	public int getIsS() {
		return isS;
	}
	public void setIsS(int isS) {
		this.isS = isS;
	}
}
