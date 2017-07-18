package icom.yh.wish.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Transient;

public class RespWishData{
	private int id;
	private String itemId;
	private String ParentSKU;
	private String SKU;
	private String name;
	private String _desc;
	private String label;
	private String merahant_tags;
	private String brand;
	private String UPC;
	private String landingPageUrl;
	private String msrp;
	private String color;
	private String size;
	private String url;
	private String price;
	private String freight;
	private int stock;
	private int minTime;
	private int maxTime;
	private String mainImgUrl;
	private String changeImgUrl;
	private String imgUrl1;
	private String imgUrl2;
	private String imgUrl3;
	private String imgUrl4;
	private String imgUrl5;
	private String imgUrl6;
	private String imgUrl7;
	private String imgUrl8;
	private String imgUrl9;
	private String imgUrl10;
	private String imgUrl11;
	private String imgUrl12;
	private String imgUrl13;
	private String imgUrl14;
	private String imgUrl15;
	private String imgUrl16;
	private String imgUrl17;
	private String imgUrl18;
	private String imgUrl19;
	private String imgUrl20;
	private int collNum;
	private int saleNum;
	private String status;
	private String merchant,proId;
	private String sjtime;
	public RespWishData() {
	}
	public RespWishData(WishData wishData) {
		this.id = wishData.getId();
		this.ParentSKU = wishData.getParentSKU();
		this.SKU = wishData.getSKU();
		this.name = wishData.getName();
		this._desc = wishData.getDesc();
		this.label = wishData.getLabel();
		this.merahant_tags = wishData.getMerahant_tags();
		this.brand = wishData.getBrand();
		this.UPC = wishData.getUPC();
		this.landingPageUrl = wishData.getLandingPageUrl();
		this.msrp = wishData.getMsrp();
		this.color = wishData.getColor();
		this.size = wishData.getSize();
		this.url = wishData.getUrl();
		this.price = wishData.getPrice();
		this.freight = wishData.getFreight();
		this.stock = wishData.getStock();
		this.minTime = wishData.getMinTime();
		this.maxTime = wishData.getMaxTime();
		this.mainImgUrl = wishData.getMainImgUrl();
		this.changeImgUrl = wishData.getChangeImgUrl();
		this.imgUrl1 = wishData.getImgUrl1();
		this.imgUrl2 = wishData.getImgUrl2();
		this.imgUrl3 = wishData.getImgUrl3();
		this.imgUrl4 = wishData.getImgUrl4();
		this.imgUrl5 = wishData.getImgUrl5();
		this.imgUrl6 = wishData.getImgUrl6();
		this.imgUrl7 = wishData.getImgUrl7();
		this.imgUrl8 = wishData.getImgUrl8();
		this.imgUrl9 = wishData.getImgUrl9();
		this.imgUrl10 = wishData.getImgUrl10();
		this.imgUrl11 = wishData.getImgUrl11();
		this.imgUrl12 = wishData.getImgUrl12();
		this.imgUrl13 = wishData.getImgUrl13();
		this.imgUrl14 = wishData.getImgUrl14();
		this.imgUrl15 = wishData.getImgUrl15();
		this.imgUrl16 = wishData.getImgUrl16();
		this.imgUrl17 = wishData.getImgUrl17();
		this.imgUrl18 = wishData.getImgUrl18();
		this.imgUrl19 = wishData.getImgUrl19();
		this.imgUrl20 = wishData.getImgUrl20();
		this.collNum = wishData.getCollNum();
		this.saleNum = wishData.getSaleNum();
		this.status = wishData.getStatus();
		this.merchant = wishData.getMerchant();
		this.proId = wishData.getProId();
		this.sjtime = wishData.getSjtime();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParentSKU() {
		return ParentSKU;
	}
	public void setParentSKU(String parentSKU) {
		ParentSKU = parentSKU;
	}
	public String getSKU() {
		return SKU;
	}
	public void setSKU(String sKU) {
		SKU = sKU;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String get_desc() {
		return _desc;
	}
	public void set_desc(String _desc) {
		this._desc = _desc;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getMerahant_tags() {
		return merahant_tags;
	}
	public void setMerahant_tags(String merahant_tags) {
		this.merahant_tags = merahant_tags;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getUPC() {
		return UPC;
	}
	public void setUPC(String uPC) {
		UPC = uPC;
	}
	public String getLandingPageUrl() {
		return landingPageUrl;
	}
	public void setLandingPageUrl(String landingPageUrl) {
		this.landingPageUrl = landingPageUrl;
	}
	public String getMsrp() {
		return msrp;
	}
	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = freight;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public int getMinTime() {
		return minTime;
	}
	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}
	public int getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
	public String getMainImgUrl() {
		return mainImgUrl;
	}
	public void setMainImgUrl(String mainImgUrl) {
		this.mainImgUrl = mainImgUrl;
	}
	public String getChangeImgUrl() {
		return changeImgUrl;
	}
	public void setChangeImgUrl(String changeImgUrl) {
		this.changeImgUrl = changeImgUrl;
	}
	public String getImgUrl1() {
		return imgUrl1;
	}
	public void setImgUrl1(String imgUrl1) {
		this.imgUrl1 = imgUrl1;
	}
	public String getImgUrl2() {
		return imgUrl2;
	}
	public void setImgUrl2(String imgUrl2) {
		this.imgUrl2 = imgUrl2;
	}
	public String getImgUrl3() {
		return imgUrl3;
	}
	public void setImgUrl3(String imgUrl3) {
		this.imgUrl3 = imgUrl3;
	}
	public String getImgUrl4() {
		return imgUrl4;
	}
	public void setImgUrl4(String imgUrl4) {
		this.imgUrl4 = imgUrl4;
	}
	public String getImgUrl5() {
		return imgUrl5;
	}
	public void setImgUrl5(String imgUrl5) {
		this.imgUrl5 = imgUrl5;
	}
	public String getImgUrl6() {
		return imgUrl6;
	}
	public void setImgUrl6(String imgUrl6) {
		this.imgUrl6 = imgUrl6;
	}
	public String getImgUrl7() {
		return imgUrl7;
	}
	public void setImgUrl7(String imgUrl7) {
		this.imgUrl7 = imgUrl7;
	}
	public String getImgUrl8() {
		return imgUrl8;
	}
	public void setImgUrl8(String imgUrl8) {
		this.imgUrl8 = imgUrl8;
	}
	public String getImgUrl9() {
		return imgUrl9;
	}
	public void setImgUrl9(String imgUrl9) {
		this.imgUrl9 = imgUrl9;
	}
	public String getImgUrl10() {
		return imgUrl10;
	}
	public void setImgUrl10(String imgUrl10) {
		this.imgUrl10 = imgUrl10;
	}
	public String getImgUrl11() {
		return imgUrl11;
	}
	public void setImgUrl11(String imgUrl11) {
		this.imgUrl11 = imgUrl11;
	}
	public String getImgUrl12() {
		return imgUrl12;
	}
	public void setImgUrl12(String imgUrl12) {
		this.imgUrl12 = imgUrl12;
	}
	public String getImgUrl13() {
		return imgUrl13;
	}
	public void setImgUrl13(String imgUrl13) {
		this.imgUrl13 = imgUrl13;
	}
	public String getImgUrl14() {
		return imgUrl14;
	}
	public void setImgUrl14(String imgUrl14) {
		this.imgUrl14 = imgUrl14;
	}
	public String getImgUrl15() {
		return imgUrl15;
	}
	public void setImgUrl15(String imgUrl15) {
		this.imgUrl15 = imgUrl15;
	}
	public String getImgUrl16() {
		return imgUrl16;
	}
	public void setImgUrl16(String imgUrl16) {
		this.imgUrl16 = imgUrl16;
	}
	public String getImgUrl17() {
		return imgUrl17;
	}
	public void setImgUrl17(String imgUrl17) {
		this.imgUrl17 = imgUrl17;
	}
	public String getImgUrl18() {
		return imgUrl18;
	}
	public void setImgUrl18(String imgUrl18) {
		this.imgUrl18 = imgUrl18;
	}
	public String getImgUrl19() {
		return imgUrl19;
	}
	public void setImgUrl19(String imgUrl19) {
		this.imgUrl19 = imgUrl19;
	}
	public String getImgUrl20() {
		return imgUrl20;
	}
	public void setImgUrl20(String imgUrl20) {
		this.imgUrl20 = imgUrl20;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getProId() {
		return proId;
	}
	public void setProId(String proId) {
		this.proId = proId;
	}
	public String getSjtime() {
		return sjtime;
	}
	public void setSjtime(String sjtime) {
		this.sjtime = sjtime;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setImgs(List<String> imgUrls) {
		String s[] = imgUrls.toArray(new String[]{});
		if(s.length>0)
			setImgUrl1(s[0]);
		if(s.length>1)
			setImgUrl2(s[1]);
		if(s.length>2)
			setImgUrl3(s[2]);
		if(s.length>3)
			setImgUrl4(s[3]);
		if(s.length>4)
			setImgUrl5(s[4]);
		if(s.length>5)
			setImgUrl6(s[5]);
		if(s.length>6)
			setImgUrl7(s[6]);
		if(s.length>7)
			setImgUrl8(s[7]);
		if(s.length>8)
			setImgUrl9(s[8]);
		if(s.length>9)
			setImgUrl10(s[9]);
		if(s.length>10)
			setImgUrl11(s[10]);
		if(s.length>11)
			setImgUrl12(s[11]);
		if(s.length>12)
			setImgUrl13(s[12]);
		if(s.length>13)
			setImgUrl14(s[13]);
		if(s.length>14)
			setImgUrl15(s[14]);
		if(s.length>15)
			setImgUrl16(s[15]);
		if(s.length>16)
			setImgUrl17(s[16]);
		if(s.length>17)
			setImgUrl18(s[17]);
		if(s.length>18)
			setImgUrl19(s[18]);
		if(s.length>19)
			setImgUrl20(s[19]);
		
	}
	
	public List<String> getImgs() {
		List<String> list = new  ArrayList<>();
		if(null != imgUrl1 && !imgUrl1.equals("")){
			list.add(imgUrl1);
		}
		if(null != imgUrl2 && !imgUrl2.equals(""))
			list.add(this.imgUrl2);
		if(null != imgUrl3 && !imgUrl3.equals(""))
			list.add(imgUrl3);
		if(null != imgUrl4 && !imgUrl4.equals(""))
			list.add(imgUrl4);
		if(null != imgUrl5 && !imgUrl5.equals(""))
			list.add(imgUrl5);
		if(null != imgUrl6 && !imgUrl6.equals(""))
			list.add(imgUrl6);
		if(null != imgUrl7 && !imgUrl7.equals(""))
			list.add(imgUrl7);
		if(null != imgUrl8 && !imgUrl8.equals(""))
			list.add(imgUrl8);
		if(null != imgUrl9 && !imgUrl9.equals(""))
			list.add(imgUrl1);
		if(null != imgUrl10 && !imgUrl10.equals(""))
			list.add(imgUrl10);
		if(null != imgUrl11 && !imgUrl11.equals(""))
			list.add(imgUrl11);
		if(null != imgUrl12 && !imgUrl12.equals(""))
			list.add(imgUrl12);
		if(null != imgUrl13 && !imgUrl13.equals(""))
			list.add(imgUrl13);
		if(null != imgUrl14 && !imgUrl14.equals(""))
			list.add(imgUrl14);
		if(null != imgUrl15 && !imgUrl15.equals(""))
			list.add(imgUrl15);
		if(null != imgUrl16 && !imgUrl16.equals(""))
			list.add(imgUrl16);
		if(null != imgUrl17 && !imgUrl17.equals(""))
			list.add(imgUrl17);
		if(null != imgUrl18 && !imgUrl18.equals(""))
			list.add(imgUrl18);
		if(null != imgUrl19 && !imgUrl19.equals(""))
			list.add(imgUrl19);
		if(null != imgUrl20 && !imgUrl20.equals(""))
			list.add(imgUrl20);
		return list;
	}
}
