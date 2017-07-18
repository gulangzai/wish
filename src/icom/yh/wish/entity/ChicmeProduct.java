package icom.yh.wish.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import net.sf.json.JSONArray;

@Entity
@Table(name="chicme_product")
public class ChicmeProduct {
	private int id;
	private String category;
	private String subCategory;
	private String itemId;
	private String name;
	private String simpleDesc;
	private String description;
	private String parentSku;
	private String brand;
	private String label = "";
	private String pcMainImage;
	private String pcMainImage2;
	private String img1 = "";
	private String img2 = "";
	private String img3 = "";
	private String img4 = "";
	private String img5 = "";
	private String img6 = "";
	private String img7 = "";
	private String img8 = "";
	private String img9 = "";
	private String img10 = "";
	private String img11 = "";
	private String img12 = "";
	private String img13 = "";
	private String img14 = "";
	private String img15 = "";
	private String img16 = "";
	private String img17 = "";
	private String img18 = "";
	private String img19 = "";
	private String img20 = "";
	private String img21 = "";
	private String img22 = "";
	private String img23 = "";
	private String img24 = "";
	private String img25 = "";
	private String img26 = "";
	private String img27 = "";
	private String img28 = "";
	private String img29 = "";
	private String img30 = "";
	private String img31 = "";
	private String img32 = "";
	private String img33 = "";
	private String img34 = "";
	private String img35 = "";
	private String img36 = "";
	private String img37 = "";
	private String img38 = "";
	private String img39 = "";
	private String img40 = "";
	private String sku;
	private String color;
	private String size;
	private String msrp;
	private String shippingPrice;
	private String price;
	private int numberSold;
	private int numberSaves;
	public ChicmeProduct() {
	}
	public ChicmeProduct(String itemId, String name, String description, String parentSku, String brand,
			String pcMainImage, String pcMainImage2, JSONArray extraImages, int numberSaves,int numberSold) {
		this.itemId = itemId;
		this.name = name;
		this.description = description;
		this.parentSku = parentSku;
		this.brand = brand;
		this.pcMainImage = "https://dgzfssf1la12s.cloudfront.net/original/"+pcMainImage;
		if(!pcMainImage2.equals(""))
		this.pcMainImage2 = "https://dgzfssf1la12s.cloudfront.net/original/"+pcMainImage2;
		this.numberSaves = numberSaves;
		this.numberSold = numberSold;
		if(null!=extraImages && extraImages.size()>0){
			if(extraImages.size()>0)
				this.img1 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(0);
			if(extraImages.size()>1)
				this.img2 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(1);
			if(extraImages.size()>2)
				this.img3 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(2);
			if(extraImages.size()>3)
				this.img4 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(3);
			if(extraImages.size()>4)
				this.img5 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(4);
			if(extraImages.size()>5)
				this.img6 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(5);
			if(extraImages.size()>6)
				this.img7 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(6);
			if(extraImages.size()>7)
				this.img8 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(7);
			if(extraImages.size()>8)
				this.img9 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(8);
			if(extraImages.size()>9)
				this.img10 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(9);
			if(extraImages.size()>10)
				this.img11 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(10);
			if(extraImages.size()>11)
				this.img12 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(11);
			if(extraImages.size()>12)
				this.img13 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(12);
			if(extraImages.size()>13)
				this.img14 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(13);
			if(extraImages.size()>14)
				this.img15 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(14);
			if(extraImages.size()>15)
				this.img16 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(15);
			if(extraImages.size()>16)
				this.img17 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(16);
			if(extraImages.size()>17)
				this.img18 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(17);
			if(extraImages.size()>18)
				this.img19 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(18);
			if(extraImages.size()>19)
				this.img20 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(19);
			
			if(extraImages.size()>20)
				this.img21 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(20);
			if(extraImages.size()>21)
				this.img22 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(21);
			if(extraImages.size()>22)
				this.img23 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(22);
			if(extraImages.size()>23)
				this.img24 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(23);
			if(extraImages.size()>24)
				this.img25 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(24);
			if(extraImages.size()>25)
				this.img26 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(25);
			if(extraImages.size()>26)
				this.img27 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(26);
			if(extraImages.size()>27)
				this.img28 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(27);
			if(extraImages.size()>28)
				this.img29 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(28);
			if(extraImages.size()>29)
				this.img30 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(29);
			if(extraImages.size()>30)
				this.img31 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(30);
			if(extraImages.size()>31)
				this.img32 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(31);
			if(extraImages.size()>32)
				this.img33 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(32);
			if(extraImages.size()>33)
				this.img34 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(33);
			if(extraImages.size()>34)
				this.img35 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(34);
			if(extraImages.size()>35)
				this.img36 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(35);
			if(extraImages.size()>36)
				this.img37 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(36);
			if(extraImages.size()>37)
				this.img38 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(37);
			if(extraImages.size()>38)
				this.img39 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(38);
			if(extraImages.size()>39)
				this.img40 = "https://dgzfssf1la12s.cloudfront.net/original/"+extraImages.getString(39);
			
		}
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
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
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
	public String getSimpleDesc() {
		return simpleDesc;
	}
	public void setSimpleDesc(String simpleDesc) {
		this.simpleDesc = simpleDesc;
	}
	@Column(length=5000)
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getParentSku() {
		return parentSku;
	}
	public void setParentSku(String parentSku) {
		this.parentSku = parentSku;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getPcMainImage() {
		return pcMainImage;
	}
	public void setPcMainImage(String pcMainImage) {
		this.pcMainImage = pcMainImage;
	}
	public String getPcMainImage2() {
		return pcMainImage2;
	}
	public void setPcMainImage2(String pcMainImage2) {
		this.pcMainImage2 = pcMainImage2;
	}
	public String getImg1() {
		return img1;
	}
	public void setImg1(String img1) {
		this.img1 = img1;
	}
	public String getImg2() {
		return img2;
	}
	public void setImg2(String img2) {
		this.img2 = img2;
	}
	public String getImg3() {
		return img3;
	}
	public void setImg3(String img3) {
		this.img3 = img3;
	}
	public String getImg4() {
		return img4;
	}
	public void setImg4(String img4) {
		this.img4 = img4;
	}
	public String getImg5() {
		return img5;
	}
	public void setImg5(String img5) {
		this.img5 = img5;
	}
	public String getImg6() {
		return img6;
	}
	public void setImg6(String img6) {
		this.img6 = img6;
	}
	public String getImg7() {
		return img7;
	}
	public void setImg7(String img7) {
		this.img7 = img7;
	}
	public String getImg8() {
		return img8;
	}
	public void setImg8(String img8) {
		this.img8 = img8;
	}
	public String getImg9() {
		return img9;
	}
	public void setImg9(String img9) {
		this.img9 = img9;
	}
	public String getImg10() {
		return img10;
	}
	public void setImg10(String img10) {
		this.img10 = img10;
	}
	public String getImg11() {
		return img11;
	}
	public void setImg11(String img11) {
		this.img11 = img11;
	}
	public String getImg12() {
		return img12;
	}
	public void setImg12(String img12) {
		this.img12 = img12;
	}
	public String getImg13() {
		return img13;
	}
	public void setImg13(String img13) {
		this.img13 = img13;
	}
	public String getImg14() {
		return img14;
	}
	public void setImg14(String img14) {
		this.img14 = img14;
	}
	public String getImg15() {
		return img15;
	}
	public void setImg15(String img15) {
		this.img15 = img15;
	}
	public String getImg16() {
		return img16;
	}
	public void setImg16(String img16) {
		this.img16 = img16;
	}
	public String getImg17() {
		return img17;
	}
	public void setImg17(String img17) {
		this.img17 = img17;
	}
	public String getImg18() {
		return img18;
	}
	public void setImg18(String img18) {
		this.img18 = img18;
	}
	public String getImg19() {
		return img19;
	}
	public void setImg19(String img19) {
		this.img19 = img19;
	}
	public String getImg20() {
		return img20;
	}
	public void setImg20(String img20) {
		this.img20 = img20;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
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
	public String getMsrp() {
		return msrp;
	}
	public void setMsrp(String msrp) {
		this.msrp = msrp;
	}
	public String getShippingPrice() {
		return shippingPrice;
	}
	public void setShippingPrice(String shippingPrice) {
		this.shippingPrice = shippingPrice;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getNumberSold() {
		return numberSold;
	}
	public void setNumberSold(int numberSold) {
		this.numberSold = numberSold;
	}
	public int getNumberSaves() {
		return numberSaves;
	}
	public void setNumberSaves(int numberSaves) {
		this.numberSaves = numberSaves;
	}
	public String getImg21() {
		return img21;
	}
	public void setImg21(String img21) {
		this.img21 = img21;
	}
	public String getImg22() {
		return img22;
	}
	public void setImg22(String img22) {
		this.img22 = img22;
	}
	public String getImg23() {
		return img23;
	}
	public void setImg23(String img23) {
		this.img23 = img23;
	}
	public String getImg24() {
		return img24;
	}
	public void setImg24(String img24) {
		this.img24 = img24;
	}
	public String getImg25() {
		return img25;
	}
	public void setImg25(String img25) {
		this.img25 = img25;
	}
	public String getImg26() {
		return img26;
	}
	public void setImg26(String img26) {
		this.img26 = img26;
	}
	public String getImg27() {
		return img27;
	}
	public void setImg27(String img27) {
		this.img27 = img27;
	}
	public String getImg28() {
		return img28;
	}
	public void setImg28(String img28) {
		this.img28 = img28;
	}
	public String getImg29() {
		return img29;
	}
	public void setImg29(String img29) {
		this.img29 = img29;
	}
	public String getImg30() {
		return img30;
	}
	public void setImg30(String img30) {
		this.img30 = img30;
	}
	public String getImg31() {
		return img31;
	}
	public void setImg31(String img31) {
		this.img31 = img31;
	}
	public String getImg32() {
		return img32;
	}
	public void setImg32(String img32) {
		this.img32 = img32;
	}
	public String getImg33() {
		return img33;
	}
	public void setImg33(String img33) {
		this.img33 = img33;
	}
	public String getImg34() {
		return img34;
	}
	public void setImg34(String img34) {
		this.img34 = img34;
	}
	public String getImg35() {
		return img35;
	}
	public void setImg35(String img35) {
		this.img35 = img35;
	}
	public String getImg36() {
		return img36;
	}
	public void setImg36(String img36) {
		this.img36 = img36;
	}
	public String getImg37() {
		return img37;
	}
	public void setImg37(String img37) {
		this.img37 = img37;
	}
	public String getImg38() {
		return img38;
	}
	public void setImg38(String img38) {
		this.img38 = img38;
	}
	public String getImg39() {
		return img39;
	}
	public void setImg39(String img39) {
		this.img39 = img39;
	}
	public String getImg40() {
		return img40;
	}
	public void setImg40(String img40) {
		this.img40 = img40;
	}
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Transient
	public List<DlImg> getImgs() {
		List<DlImg> list = new ArrayList<>();
		String path = "c:/chicme/"+getCategory()+"/";
		if(null != getSubCategory() && !getSubCategory().equals(""))
			path = path + getSubCategory()+"/";
		path = path + getItemId()+"/";
		if(!new File(path).exists()){
			new File(path).mkdirs();
		}
		path = path + getParentSku();
		list.add(new DlImg(getPcMainImage(),new File(path+"_MAIN.jpg")));
		if(null !=getImg1() && !getImg1().equals(""))
			list.add(new DlImg(getImg1(),new File(path+"_1.jpg")));
		if(null !=getImg2() && !getImg2().equals(""))
			list.add(new DlImg(getImg2(),new File(path+"_2.jpg")));
		if(null !=getImg3() && !getImg3().equals(""))
			list.add(new DlImg(getImg3(),new File(path+"_3.jpg")));
		if(null !=getImg4() && !getImg4().equals(""))
			list.add(new DlImg(getImg4(),new File(path+"_4.jpg")));
		if(null !=getImg5() && !getImg5().equals(""))
			list.add(new DlImg(getImg5(),new File(path+"_5.jpg")));
		if(null !=getImg6() && !getImg6().equals(""))
			list.add(new DlImg(getImg6(),new File(path+"_6.jpg")));
		if(null !=getImg7() && !getImg7().equals(""))
			list.add(new DlImg(getImg7(),new File(path+"_7.jpg")));
		if(null !=getImg8() && !getImg8().equals(""))
			list.add(new DlImg(getImg8(),new File(path+"_8.jpg")));
		if(null !=getImg9() && !getImg9().equals(""))
			list.add(new DlImg(getImg9(),new File(path+"_9.jpg")));
		if(null !=getImg10() && !getImg10().equals(""))
			list.add(new DlImg(getImg10(),new File(path+"_10.jpg")));
		
		if(null !=getImg11() && !getImg11().equals(""))
			list.add(new DlImg(getImg11(),new File(path+"_11.jpg")));
		if(null !=getImg12() && !getImg12().equals(""))
			list.add(new DlImg(getImg12(),new File(path+"_12.jpg")));
		if(null !=getImg13() && !getImg13().equals(""))
			list.add(new DlImg(getImg13(),new File(path+"_13.jpg")));
		if(null !=getImg14() && !getImg14().equals(""))
			list.add(new DlImg(getImg14(),new File(path+"_14.jpg")));
		if(null !=getImg15() && !getImg15().equals(""))
			list.add(new DlImg(getImg15(),new File(path+"_15.jpg")));
		if(null !=getImg16() && !getImg16().equals(""))
			list.add(new DlImg(getImg16(),new File(path+"_16.jpg")));
		if(null !=getImg17() && !getImg17().equals(""))
			list.add(new DlImg(getImg17(),new File(path+"_17.jpg")));
		if(null !=getImg18() && !getImg18().equals(""))
			list.add(new DlImg(getImg18(),new File(path+"_18.jpg")));
		if(null !=getImg19() && !getImg19().equals(""))
			list.add(new DlImg(getImg19(),new File(path+"_19.jpg")));
		if(null !=getImg20() && !getImg20().equals(""))
			list.add(new DlImg(getImg20(),new File(path+"_20.jpg")));
		
		if(null !=getImg21() && !getImg21().equals(""))
			list.add(new DlImg(getImg21(),new File(path+"_21.jpg")));
		if(null !=getImg22() && !getImg22().equals(""))
			list.add(new DlImg(getImg22(),new File(path+"_22.jpg")));
		if(null !=getImg23() && !getImg23().equals(""))
			list.add(new DlImg(getImg23(),new File(path+"_23.jpg")));
		if(null !=getImg24() && !getImg24().equals(""))
			list.add(new DlImg(getImg24(),new File(path+"_24.jpg")));
		if(null !=getImg25() && !getImg25().equals(""))
			list.add(new DlImg(getImg25(),new File(path+"_25.jpg")));
		if(null !=getImg26() && !getImg26().equals(""))
			list.add(new DlImg(getImg26(),new File(path+"_26.jpg")));
		if(null !=getImg27() && !getImg27().equals(""))
			list.add(new DlImg(getImg27(),new File(path+"_27.jpg")));
		if(null !=getImg28() && !getImg28().equals(""))
			list.add(new DlImg(getImg28(),new File(path+"_28.jpg")));
		if(null !=getImg29() && !getImg29().equals(""))
			list.add(new DlImg(getImg29(),new File(path+"_29.jpg")));
		if(null !=getImg30() && !getImg30().equals(""))
			list.add(new DlImg(getImg30(),new File(path+"_30.jpg")));
		
		if(null !=getImg31() && !getImg31().equals(""))
			list.add(new DlImg(getImg31(),new File(path+"_31.jpg")));
		if(null !=getImg32() && !getImg32().equals(""))
			list.add(new DlImg(getImg32(),new File(path+"_32.jpg")));
		if(null !=getImg33() && !getImg33().equals(""))
			list.add(new DlImg(getImg33(),new File(path+"_33.jpg")));
		if(null !=getImg34() && !getImg34().equals(""))
			list.add(new DlImg(getImg34(),new File(path+"_34.jpg")));
		if(null !=getImg35() && !getImg35().equals(""))
			list.add(new DlImg(getImg35(),new File(path+"_35jpg")));
		if(null !=getImg36() && !getImg36().equals(""))
			list.add(new DlImg(getImg36(),new File(path+"_36.jpg")));
		if(null !=getImg37() && !getImg37().equals(""))
			list.add(new DlImg(getImg37(),new File(path+"_37.jpg")));
		if(null !=getImg38() && !getImg38().equals(""))
			list.add(new DlImg(getImg38(),new File(path+"_38.jpg")));
		if(null !=getImg39() && !getImg39().equals(""))
			list.add(new DlImg(getImg39(),new File(path+"_39.jpg")));
		if(null !=getImg40() && !getImg40().equals(""))
			list.add(new DlImg(getImg40(),new File(path+"_40.jpg")));
		return list;
	}
}
