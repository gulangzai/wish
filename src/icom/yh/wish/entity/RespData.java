package icom.yh.wish.entity;

public class RespData implements Comparable<RespData>{
	private int id;
	private String url;
	private String itemId;
	private String name = "";	
	private String price = "";		
	private String mainImgUrl = "";	
	private int isC;
	private int collNum = 0;		
	private int saleNum = 0;
	public RespData() {
	}
	public RespData(RespWishData wishData, String itemId) {
		this.id = wishData.getId();
		this.url = wishData.getUrl();
		this.name = wishData.getName();
		this.price = wishData.getPrice();
		this.mainImgUrl = wishData.getMainImgUrl();
		this.collNum = wishData.getCollNum();
		this.saleNum = wishData.getSaleNum();
		this.itemId = itemId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getMainImgUrl() {
		return mainImgUrl;
	}
	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}
	public int getCollNum() {
		return collNum;
	}
	public void setCollNum(int collNum) {
		this.collNum = collNum;
	}
	public int getSaleNum() {
		return saleNum;
	}
	public void setSaleNum(int saleNum) {
		this.saleNum = saleNum;
	}
	public int getIsC() {
		return isC;
	}
	public void setIsC(int isC) {
		this.isC = isC;
	}
	@Override
	public int compareTo(RespData o) {
		return o.saleNum-this.saleNum;
	}		
}
