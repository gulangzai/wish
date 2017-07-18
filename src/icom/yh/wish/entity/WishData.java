package icom.yh.wish.entity;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="wish_product")
public class WishData {
	private int id;
	private String itemId;
	private String ParentSKU = "";
	private String SKU = "";
	private String name = "";		
	private String desc = "";
	private String label = "";	
	private String merahant_tags = "";
	private String brand = "";	
	private String UPC = "";
	private String landingPageUrl = "";
	private String msrp = "";
	private String color = "";		//��ɫ
	private String size = "";		//�ߴ�ֵ
	private String url = "";			//��ԴURL
	private String price = "";		//�۸�($)
	private String freight = "";		//�˷�
	private int stock = 1000;		//�����
	private int minTime = 7;		//��С�ʹ�ʱ��
	private int maxTime = 28;		//����ʹ�ʱ��
	private String mainImgUrl = "";	//��ͼ(URL)����
	private String changeImgUrl = "";//����ͼ(URL)��ַ
	private String imgUrl1 = "";		//��ͼ(URL)��ַ
	private String imgUrl2 = "";		//��ͼ(URL)��ַ
	private String imgUrl3 = "";		//��ͼ(URL)��ַ
	private String imgUrl4 = "";		//��ͼ(URL)��ַ
	private String imgUrl5 = "";		//��ͼ(URL)��ַ
	private String imgUrl6 = "";		//��ͼ(URL)��ַ
	private String imgUrl7 = "";		//��ͼ(URL)��ַ
	private String imgUrl8 = "";		//��ͼ(URL)��ַ
	private String imgUrl9 = "";		//��ͼ(URL)��ַ
	private String imgUrl10 = "";	//��ͼ(URL)��ַ
	private String imgUrl11 = "";	//��ͼ(URL)��ַ
	private String imgUrl12 = "";	//��ͼ(URL)��ַ
	private String imgUrl13 = "";	//��ͼ(URL)��ַ
	private String imgUrl14 = "";	//��ͼ(URL)��ַ
	private String imgUrl15 = "";	//��ͼ(URL)��ַ
	private String imgUrl16 = "";	//��ͼ(URL)��ַ
	private String imgUrl17 = "";	//��ͼ(URL)��ַ
	private String imgUrl18 = "";	//��ͼ(URL)��ַ
	private String imgUrl19 = "";	//��ͼ(URL)��ַ
	private String imgUrl20 = "";	//��ͼ(URL)��ַ
	private int collNum = 0;		//�ղ���
	private int saleNum = 0;		//������
	private String status = "";		//���״̬
	private String merchant = "";	//������
	private String proId = "";		//��Ʒ��
	private String sjtime;
	public WishData() {
	}
	@Id
	@GeneratedValue
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
	@Column(name="_desc", length=5000)
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		if(null != label){
			String[] keywords = label.split(",");
			if(keywords.length>10){
				for(int i=0;i<10;i++){
					this.label = this.label + keywords[i]+",";
				}
				if(this.label.lastIndexOf(",")>0)
					this.label = this.label.substring(0, this.label.length()-1);
			}
			else
				this.label = label;				
		}
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
		this.msrp = new BigDecimal(msrp).divide(new BigDecimal(7),2).toString();;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		if(null != color && !color.equals("") && !color.equals("null"))
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		if(null != size && !size.equals("") && !size.equals("null"))
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
		this.price = new BigDecimal(price).divide(new BigDecimal(7),2).toString();
	}
	public String getFreight() {
		return freight;
	}
	public void setFreight(String freight) {
		this.freight = new  BigDecimal(freight).divide(new BigDecimal(freight),2).toString();
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
	public String getMerahant_tags() {
		return merahant_tags;
	}
	public void setMerahant_tags(String merahant_tags) {
		this.merahant_tags = merahant_tags;
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
	@Override
	public String toString() {
		return "id,ParentSKU ,SKU,name,desc,label,merahant_tags,brand"+
				 ",UPC,landingPageUrl,msrp,color,size,url,price,freight,stock"+
				 ",minTime,maxTime,mainImgUrl,changeImgUrl,imgUrl1,imgUrl2,imgUrl3,"+
				 "imgUrl4,imgUrl5,imgUrl6,imgUrl7,imgUrl8,imgUrl9,imgUrl10,imgUrl11,"+
				 "imgUrl12,imgUrl13,imgUrl14,imgUrl15,imgUrl16,imgUrl17,imgUrl18,"+
				 "imgUrl19,imgUrl20,collNum,saleNum,status,merchant,proId,sjtime";
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
	public void setSKU(String parentSKU, String size, String color) {
		if((null != size && !size.equals("null") && !size.equals("")) && (null != color && !color.equals("null") && !color.equals("")))
			this.SKU = ParentSKU+"-"+size+"-"+color;
		if((null != size && !size.equals("null") && !size.equals("")) && (null == color || color.equals("null") || color.equals("")))
			this.SKU = ParentSKU+"-"+size;
		if((null == size || size.equals("null") || size.equals("")) && (null != color && !color.equals("null") && !color.equals("")))
			this.SKU = ParentSKU+"-"+color;
		if((null == size || size.equals("") || size.equals("null")) && (null == color || color.equals("") || color.equals("null")))
			this.SKU = parentSKU;
	}
	public void setImgs(Collection<String> imgUrls) {
		String imgs[] = imgUrls.toArray(new String[]{});
		List<String> list = Arrays.asList(imgs);
		setImgs(list);
	}
	public static void main(String[] args) {
		System.err.println(new WishData());
	}
	@Transient
	public List<String> getImgs() {
		List<String> list = new  ArrayList<>();
		if(null != imgUrl1 && imgUrl1.equals(""))
			list.add(imgUrl1);
		if(null != imgUrl2 && imgUrl2.equals(""))
			list.add(imgUrl2);
		if(null != imgUrl3 && imgUrl3.equals(""))
			list.add(imgUrl3);
		if(null != imgUrl4 && imgUrl4.equals(""))
			list.add(imgUrl4);
		if(null != imgUrl5 && imgUrl5.equals(""))
			list.add(imgUrl5);
		if(null != imgUrl6 && imgUrl6.equals(""))
			list.add(imgUrl6);
		if(null != imgUrl7 && imgUrl7.equals(""))
			list.add(imgUrl7);
		if(null != imgUrl8 && imgUrl8.equals(""))
			list.add(imgUrl8);
		if(null != imgUrl9 && imgUrl9.equals(""))
			list.add(imgUrl1);
		if(null != imgUrl10 && imgUrl10.equals(""))
			list.add(imgUrl10);
		if(null != imgUrl11 && imgUrl11.equals(""))
			list.add(imgUrl11);
		if(null != imgUrl12 && imgUrl12.equals(""))
			list.add(imgUrl12);
		if(null != imgUrl13 && imgUrl13.equals(""))
			list.add(imgUrl13);
		if(null != imgUrl14 && imgUrl14.equals(""))
			list.add(imgUrl14);
		if(null != imgUrl15 && imgUrl15.equals(""))
			list.add(imgUrl15);
		if(null != imgUrl16 && imgUrl16.equals(""))
			list.add(imgUrl16);
		if(null != imgUrl17 && imgUrl17.equals(""))
			list.add(imgUrl17);
		if(null != imgUrl18 && imgUrl18.equals(""))
			list.add(imgUrl18);
		if(null != imgUrl19 && imgUrl19.equals(""))
			list.add(imgUrl19);
		if(null != imgUrl20 && imgUrl20.equals(""))
			list.add(imgUrl20);
		return list;
	}
	
}
