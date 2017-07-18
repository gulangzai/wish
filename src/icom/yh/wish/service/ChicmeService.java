package icom.yh.wish.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;



import icom.yh.wish.dao.ChicmeDao;
import icom.yh.wish.entity.Category;
import icom.yh.wish.entity.ChicmeCateTag;
import icom.yh.wish.entity.ChicmeProduct;
import icom.yh.wish.entity.DlImg;
import icom.yh.wish.entity.TempImg;
import icom.yh.wish.entity.WishData;
import icom.yh.wish.entity.WishDataExcel;
import icom.yh.wish.util.CodeMsg;
import icom.yh.wish.util.ConnectionManager;
import icom.yh.wish.util.ExportExcel;
import icom.yh.wish.util.FileUtils;
import icom.yh.wish.util.RegexUtil;
import icom.yh.wish.util.ZipUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class ChicmeService {
	
	@Resource
	private ChicmeDao chicmeDao;
	
	@Resource
	private UserService userService;
	
	private CodeMsg codeMsg = new CodeMsg();
	
	List<Category> categories = new ArrayList<>();
	{
		Category DRESSES = new Category("DRESSES", "f9229b43-7fc9-45c4-b380-b4407267caa8");
		Category Bandage = new Category("Bandage", "036cb13e-2ad1-465a-acc5-c38b173789a4");
		DRESSES.getList().add(Bandage);
		Category Pencil = new Category("Pencil", "fcc2866a-6608-4c93-9712-201f005b9e07");	//0
		DRESSES.getList().add(Pencil);
		Category Maxi = new Category("Maxi", "4e6b93a9-2cf9-4e52-a0ca-cbaf1af32ee7");
		DRESSES.getList().add(Maxi);
		Category Lace = new Category("Lace", "e6a3204b-abfd-47c3-a5e6-bf6c36435408");
		DRESSES.getList().add(Lace);
		Category Chiffon = new Category("Chiffon", "5ff143c7-7697-4c71-92a6-31495f17b1a5");
		DRESSES.getList().add(Chiffon);
		Category Mini = new Category("Mini", "0b888505-a22f-495d-bf76-ebeabeca0446");
		DRESSES.getList().add(Mini);
		Category Vintage = new Category("Vintage", "edd8cfd7-8e45-4250-9aaa-357b9f6f6d0c");
		DRESSES.getList().add(Vintage);
		Category Evening = new Category("Evening", "63bf2858-cdcc-4a27-9d5f-0f49c07163b4");
		DRESSES.getList().add(Evening);
		Category LongSleeves = new Category("Long Sleeves", "14b4a564-1c11-4a5e-9a8f-94c628ab0867");
		DRESSES.getList().add(LongSleeves);
		Category Casual = new Category("Casual", "50f7d02b-381b-402f-8de4-33973c04db28");
		DRESSES.getList().add(Casual);
		Category Bodycon = new Category("Bodycon", "935eb0ee-a088-4b9e-8178-6d5b59ebacec");
		DRESSES.getList().add(Bodycon);
		Category FloralDress = new Category("Floral Dress", "d2e25cd7-19ad-4f91-b616-3a97edc62ca9");
		DRESSES.getList().add(FloralDress);
		Category TwoPiece = new Category("Two-Piece", "0674a76d-f9ea-4d0a-91a2-38701f6de521");
		DRESSES.getList().add(TwoPiece);
		categories.add(DRESSES);
		
		Category JUMPSUITS = new Category("JUMPSUITS", "d08d59c0-e694-4245-9e51-e6e2d783cd73");
		Category Romper = new Category("Romper", "ba40a372-c4af-40ce-9f46-0d401ae3b2fe");
		JUMPSUITS.getList().add(Romper);
		Category Jumpsuits = new Category("Jumpsuits", "3a60f712-b751-4f22-9461-dcb259aa9e3a");
		JUMPSUITS.getList().add(Jumpsuits);
		categories.add(JUMPSUITS);
		
		Category SWIMWEAR = new Category("SWIMWEAR", "a1cc2641-1a5d-4d67-b5e5-62c4a7b19c7d");
		Category Bikini = new Category("Bikini", "28eb8ee7-1bec-4057-84f0-0944449790f6");
		SWIMWEAR.getList().add(Bikini);
		Category OnePiece = new Category("One-Piece", "0be41940-7105-43dc-a760-a79e98195d7d");
		SWIMWEAR.getList().add(OnePiece);
		Category CoverUps = new Category("Cover Ups", "d3659a29-ef98-4a83-9437-b23e3ffaf138");
		SWIMWEAR.getList().add(CoverUps);
		Category Tankini = new Category("Tankini", "c505c9c3-3ef6-4bb7-aa1a-506c00160058");
		SWIMWEAR.getList().add(Tankini);
		Category PlusSize = new Category("Plus Size", "6d9e2f39-1905-4ab3-b4b6-a9bb87a7a4c6");
		SWIMWEAR.getList().add(PlusSize);
		categories.add(SWIMWEAR);
		
		Category SHOES = new Category("SHOES", "1Y4Z3h7S0N5j1s8l8W6G1j9Y7z");
		Category Sneakers = new Category("Sneakers", "1f4q8O9j5s419k0Q9D7U8r9k4t");
		SHOES.getList().add(Sneakers);
		Category Sandals = new Category("Sandals", "1H4p809L5C4X9f4s1l8a1C7n9T");
		SHOES.getList().add(Sandals);
		Category Flats = new Category("Flats", "1j4m8i935K4L9V4k2i6N2k8w0i");
		SHOES.getList().add(Flats);
		Category PumpsHeels = new Category("Pumps/Heels", "1H4f8e9s5e4S9n5R0Q1B5X1M8r");
		SHOES.getList().add(PumpsHeels);
		Category Wedges = new Category("Wedges", "1k4K8t9v5D4M9U5z7P6S3h4A0x");
		SHOES.getList().add(Wedges);
		Category Boots = new Category("Boots", "1b4V8S9y5L4s9k6f2w3t6M7M5D");
		SHOES.getList().add(Boots);
		categories.add(SHOES);
		
		Category TOPS = new Category("TOPS", "9b07e570-4896-4f95-aef2-85f36ddbe78a");
		Category TanksCropTops = new Category("Tanks & Crop Tops", "aa21d052-42f8-4acd-8523-77c840e07f61");
		TOPS.getList().add(TanksCropTops);
		Category Tshirt = new Category("T-shirt", "eb598c3f-0694-4427-93e6-af534dd3d9d2");
		TOPS.getList().add(Tshirt);
		Category Blouses = new Category("Blouses", "55bdc30a-fdf5-4a00-ac3b-791e24662fac");
		TOPS.getList().add(Blouses);
		Category SweatshirtsHoodies = new Category("Sweatshirts & Hoodies", "aa8999be-3e35-445b-91ab-034358d2b7f6");
		TOPS.getList().add(SweatshirtsHoodies);
		Category SweatersCardigans = new Category("Sweaters & Cardigans", "38f47bad-3b05-4520-a585-c3f9f81d0c5c");
		TOPS.getList().add(SweatersCardigans);
		Category SuitSets = new Category("Suit Sets", "e5907fe8-d18f-4533-b48a-5d5cdb08f133");
		TOPS.getList().add(SuitSets);
		categories.add(TOPS);
		
		Category BOTTOMS = new Category("BOTTOMS", "ee386e4c-068b-4217-a874-5b9685f6d576");
		Category Leggings = new Category("Leggings", "d02972b9-64ee-41b0-8d72-1f1f19786b34");
		BOTTOMS.getList().add(Leggings);
		Category PantsShorts = new Category("Pants & Shorts", "dd1f03cf-e0b1-44d6-9098-9029551dadb1");
		BOTTOMS.getList().add(PantsShorts);
		Category Skirts = new Category("Skirts", "ad3b6749-eb12-4c06-8f94-22297cd578d7");
		BOTTOMS.getList().add(Skirts);
		Category Jeans = new Category("Jeans", "685d0429-4b77-48c9-9793-d901ab7e985a");
		BOTTOMS.getList().add(Jeans);
		categories.add(BOTTOMS);
		
		Category OUTERWEAR = new Category("OUTERWEAR", "84ddb86e-4e1b-4c0f-b345-d4865576c531");
		Category JacketsCoats = new Category("Jackets & Coats", "1c9d0c88-6df9-4ac3-9cc8-d14382fbbf23");
		OUTERWEAR.getList().add(JacketsCoats);
		categories.add(OUTERWEAR);
		
		Category PlusSizes = new Category("PLUS SIZES", "b99b7a0e-55cc-4e05-bb17-9b115290c4d7");
		categories.add(PlusSizes);
		
		Category JEWELRY = new Category("JEWELRY", "1cde3837-ef1e-4c73-9b52-7b2a3fb57283");
		Category NecklacePendant = new Category("Necklace&Pendant", "432b409c-ae7b-4bd6-b538-4b5391bab01d");
		JEWELRY.getList().add(NecklacePendant);
		Category Earrings = new Category("Earrings", "87efdbe1-4427-44d3-8687-c64ab429fa16");
		JEWELRY.getList().add(Earrings);
		Category Rings = new Category("Rings", "9daea815-8efa-4f23-9c7c-f09463428a2a");
		JEWELRY.getList().add(Rings);
		Category Bracelet = new Category("Bracelet", "a0d16838-ef6f-4ff3-83f5-17b64a715961");
		JEWELRY.getList().add(Bracelet);
		Category Brooches = new Category("Brooches", "45117404-3034-4347-b869-d712aebbea3d");
		JEWELRY.getList().add(Brooches);
		categories.add(JEWELRY);
		
		Category LINGERIE = new Category("LINGERIE", "c49b6a0r-11cc-0t08-bb88-8b445290c3d1");
		Category Lingerie = new Category("Lingerie", "6c9d2c66-6df9-4ac8-0cc8-d14556fbbf23");
		LINGERIE.getList().add(Lingerie);
		categories.add(LINGERIE);
	}

	//http://www.chicme.com/v7/product/anon/66954b36-168f-4733-a281-155ae9264a48/show
	public List<String> productIds(String categoryId, int start) throws IOException {
		JSONObject params = new JSONObject();
		params.put("sorter", "pc_recommendation_001 desc");
		params.put("categoryId", categoryId);
		params.put("filterItems", new JSONArray());
		HttpPost httppost = new HttpPost("http://www.chicme.com/v7/product/anon/"+start+"/20/w-filter");
		StringEntity entity = new StringEntity(params.toString(), "utf-8");// 解决中文乱码问题
		entity.setContentEncoding("UTF-8");
		entity.setContentType("application/json");
		httppost.setEntity(entity);
		HttpResponse response = null;
		try {
			response = new DefaultHttpClient().execute(httppost);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 发送Post,并返回一个HttpResponse对象
		List<String> ids = new ArrayList<>();
		if (response.getStatusLine().getStatusCode() == 200) {// 如果状态码为200,就是正常返回
			try {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jo = JSONObject.fromObject(data);
				JSONArray result = jo.getJSONArray("result");
				if(null != result && result.size()>0){
					for(int i=0;i<result.size();i++){
						JSONObject r = result.getJSONObject(i);
						ids.add(r.getString("id"));
					}
				}
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		return ids;
	}
	
	private static List<TempImg> errImg = new ArrayList<>();
	
	private List<String> wishTags(String itemId) {
		List<String> list = new ArrayList<>();
		if (!itemId.equals("")) {
			String url = "https://www.wish.com/c/=" + itemId;
			// String url = "https://www.wish.com/c/=580888ee463e807169f55b63";
			Document d = null;
			try {
				d = Jsoup.connect(url).get();
				Elements e = d.getElementsByAttributeValue("type", "application/ld+json");
				String c = e.html();
				if (!c.equals("")) {
					String merchantTags = "";
					String desc = RegexUtil.regex("description(.)+\"", c);
					desc = desc.replace("description\" : \"", "");
					if (desc.lastIndexOf("\"") > 0)
						desc = desc.substring(0, desc.length() - 1);
					c = c.replaceAll("\n", "").replaceAll("\t", "").replaceAll("\r", "");
					// c = c.replaceAll("\t", "").replaceAll("\r", "");
					JSONObject jo1 = JSONObject.fromObject(c);
					e = d.getElementsByTag("script");
					String html = e.get(e.size() - 2).html();
					StringReader reader = new StringReader(html);
					BufferedReader reader2 = new BufferedReader(reader);
					html = reader2.readLine();
					html = html.replace("pageParams['mainContestObj'] =", "");
					JSONObject jo2 = JSONObject.fromObject(html);
					System.out.println(jo2);
					JSONObject commerce_product_info = jo2.getJSONObject("commerce_product_info");
					JSONArray variations = commerce_product_info.getJSONArray("variations");
					JSONArray merchant_tags = jo2.getJSONArray("merchant_tags");
					for (int i = 0; i < merchant_tags.size(); i++) {
						list.add(merchant_tags.getJSONObject(i).getString("name"));
					}
					JSONArray tags = jo2.getJSONArray("tags");
					for (int i = 0; i < tags.size(); i++) {
						list.add(tags.getJSONObject(i).getString("name"));
					}
				}
			} catch (HttpStatusException e1) {
				e1.printStackTrace();
				// errorIds.add(itemId);
			} catch (UnknownHostException e) {
				e.printStackTrace();
				// errorIds.add(itemId);
			} catch (ConnectException e) {
				e.printStackTrace();
				// errorIds.add(itemId);
			} catch (SocketTimeoutException e) {
				e.printStackTrace();
				// errorIds.add(itemId);
			} catch (SocketException e) {
				e.printStackTrace();
				// errorIds.add(itemId);
			} catch (Exception e) {
				e.printStackTrace();
				// errorIds.add(itemId);
			}
		}
		return list;
	}
	
	public static void main(String[] args) throws IOException {
		
		
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		ChicmeService chicmeService = (ChicmeService)context.getBean("chicmeService");
		SessionFactory sessionFactory = (SessionFactory)context.getBean("sessionFactory");
		//抓取商品信息
		/*
		List<String> list = new ArrayList<>();
		int start = 0;
		String categoryId = "45117404-3034-4347-b869-d712aebbea3d";
		String category = "JEWELRY", subCategory = "Brooches";
		List<String> ids = chicmeService.productIds(categoryId, start);
		System.out.println(ids.size());
		while (ids.size()>0) {
			list.addAll(ids);
			start = start + 20;
			ids = chicmeService.productIds(categoryId, start);
			System.out.println(".........."+ids.size());
		}
		Session session = sessionFactory.openSession();
		for(String itemId : list){
			List<ChicmeProduct> products = chicmeService.productDetail(itemId);
			for(ChicmeProduct chicmeProduct : products){
				chicmeProduct.setCategory(category);
				chicmeProduct.setSubCategory(subCategory);
				session.save(chicmeProduct);
			}
		}
		session.close();
		*/
		//抓取图片
		
		//List<String> tags = chicmeService.wishTags("556db6e58720eb25a4a26e0a");
		Session session = sessionFactory.openSession();
		/*String c = "Maxi";
		for(String tag : tags){
			ChicmeCateTag chicmeTag = new ChicmeCateTag(c,tag);
			session.save(chicmeTag);
		}*、
		Session session = sessionFactory.openSession();
		/*List<ChicmeProduct> list = session.createQuery("from ChicmeProduct group by itemId order by id asc").list();
		for(ChicmeProduct chicmeProduct : list){
			List<DlImg> imgs = chicmeProduct.getImgs();
			for(DlImg img : imgs){
				chicmeService.imgGet(img.getImgPath(), img.getImgUrl());
			}
		}
		System.out.println(errImg.size());
		for(TempImg tempImg : errImg){
			session.save(tempImg);
		}*/
		
		String sql = "select * from chicme_product where itemId in (select t.itemId from (select itemId from chicme_product where category='PLUS SIZES' and label is null group by itemId limit 50) t)";
		List<ChicmeProduct> list = session.createSQLQuery(sql)
				.setResultTransformer(Transformers.aliasToBean(ChicmeProduct.class)).list();
		List<WishDataExcel> dataExcels = new ArrayList<>();
		for(ChicmeProduct chicmeProduct : list){
			WishDataExcel dataExcel = new WishDataExcel(chicmeProduct);
			String param = chicmeProduct.getCategory()+"/";
			if(null != chicmeProduct.getSubCategory() && !chicmeProduct.getSubCategory().equals(""))
				param = param+chicmeProduct.getSubCategory()+"/";
			param = param + chicmeProduct.getItemId()+"/"+chicmeProduct.getParentSku();
			dataExcels.add(dataExcel);
			String label = chicmeProduct.getCategory()+","+chicmeProduct.getSubCategory()+",Sexy,Fashion";
			dataExcel.setLabel(label);
			dataExcel.setImgs(param);
			dataExcel.setFreight("3");
			chicmeProduct.setLabel(label);
			session.update(chicmeProduct);
			System.out.println(chicmeProduct.getLabel());
			
		}
		ChicmeProduct chicmeProduct = list.get(0);
		String[] title = {"ParentSKU", "SKU", "产品标题", "产品描述", "产品标签", "品牌", "UPC",
				"landingPageUrl", "msrp", "颜色", "尺寸值", "来源url", "价格($)", "运费($)", "库存量", "最小送达时间",
				"最大送达时间", "主图（URL）地址", "变种图（URL）地址", "附图1（URL）地址", "附图2（URL）地址",
				"附图3（URL）地址", "附图4（URL）地址", "附图5（URL）地址", "附图6（URL）地址", "附图7（URL）地址",
				"附图8（URL）地址", "附图9（URL）地址", "附图10（URL）地址", "附图11（URL）地址", "附图12（URL）地址",
				"附图13（URL）地址", "附图14（URL）地址", "附图15（URL）地址", "附图16（URL）地址", "附图17（URL）地址",
				"附图18（URL）地址", "附图19（URL）地址", "附图20（URL）地址"};
	
		
	/*	for(String itemId : itemIds){
			FileUtils.copyFolder(codeMsg.getProperty("path")+itemId, path+itemId);
		}*/
		ExportExcel.exportExcel("c:/ys/wish.xls", title, dataExcels);
		
	/*	for(String itemId : itemIds){
			FileUtils.copyFolder(codeMsg.getProperty("path")+itemId, path+itemId);
		}*/
		session.flush();
		session.clear();
		session.close();
	}
	
	private  ExecutorService fixedThreadPool = Executors.newFixedThreadPool(60);
	
	
	private static DefaultHttpClient client = ConnectionManager.getHttpClient();
	
	private void imgGet(File imgFile, String imgUrl){
		HttpGet get = new HttpGet(imgUrl);
        HttpEntity entity = null;
        try {
            HttpResponse response = client.execute(get);
            entity = response.getEntity();
            InputStream is = entity.getContent();
            OutputStream os = new FileOutputStream(imgFile);
            IOUtils.copy(is, os);
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
            System.out.println(imgFile.getAbsolutePath()+"完成....");
        } catch (Exception e) {
            e.printStackTrace();
            errImg.add(new TempImg(imgUrl, imgFile.getAbsolutePath()));
            System.out.println("图片保存失败");
            return ;
        }		
	}
	

	private void save(ChicmeProduct chicmeProduct) {
		if(!chicmeDao.has(chicmeProduct.getItemId()))
			chicmeDao.save(chicmeProduct);
	}

	private List<ChicmeProduct> productDetail(String itemId) {
		String url = "http://www.chicme.com/v7/product/anon/"+itemId+"/show";
		HttpGet httpGet = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = new DefaultHttpClient().execute(httpGet);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 发送Post,并返回一个HttpResponse对象
		List<ChicmeProduct> list = new ArrayList<>();
		if (response.getStatusLine().getStatusCode() == 200) {// 如果状态码为200,就是正常返回
			try {
				String data = EntityUtils.toString(response.getEntity());
				JSONObject jo = JSONObject.fromObject(data);
				JSONObject result = jo.getJSONObject("result");
				JSONObject product = result.getJSONObject("product");
				String name = product.getString("name");
				String description = product.getString("description");
				String parentSku = product.getString("parentSku");
				String brand = (null==product.get("brand")||"null".equals(product.get("brand").toString()))?"":product.getString("brand");
				String pcMainImage = product.getString("pcMainImage");
				String pcMainImage2 = (null==product.get("pcMainImage2") || "null".equals(product.get("pcMainImage2").toString()))?"":product.getString("pcMainImage2");
				JSONArray extraImages = (null==product.get("extraImages") || "null".equals(product.get("extraImages").toString()))?new JSONArray():product.getJSONArray("extraImages");
				JSONArray variants = product.getJSONArray("variants");
				int numberSaves = product.getInt("numberSaves");
				int numberSold = product.getInt("numberSold");
				for(int i=0;i<variants.size();i++){
					JSONObject variant = variants.getJSONObject(i);
					ChicmeProduct chicmeProduct = new ChicmeProduct(itemId,name,description,
							parentSku,brand,pcMainImage,pcMainImage2,extraImages,numberSaves,numberSold);
					chicmeProduct.setPrice(variant.getJSONObject("price").getString("amount"));
					chicmeProduct.setShippingPrice(variant.getJSONObject("shippingPrice").getString("amount"));
					chicmeProduct.setColor(variant.getString("color"));
					chicmeProduct.setSize(variant.getString("size"));
					chicmeProduct.setSku(variant.getString("sku"));
					chicmeProduct.setMsrp(variant.getJSONObject("msrp").getString("amount"));
					chicmeProduct.setSimpleDesc(variant.getString("description"));
					list.add(chicmeProduct);
				}
			} catch (ParseException | IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}


	public Model chicmeCate(Model model) {
		model.addAttribute("categories", categories);
		return model;
	}

	

	public String chicmeList(String category) {
		List<ChicmeProduct> list = chicmeDao.chicmeList(category);
		List<WishDataExcel> dataExcels = new ArrayList<>();
		String account = userService.sessionAccount();
		String path = codeMsg.getProperty("cpath")+account+"/";
		File dir = new File(path);
		if(dir.exists())
			FileUtils.recurDelete(dir);		
		dir.mkdirs();
		for(ChicmeProduct chicmeProduct : list){
			WishDataExcel dataExcel = new WishDataExcel(chicmeProduct);
			dataExcel.setImgs(chicmeProduct.getParentSku());
			dataExcels.add(dataExcel);
		}
		ChicmeProduct chicmeProduct = list.get(0);
		FileUtils.copyFolder(codeMsg.getProperty("cpath")+chicmeProduct.getCategory()+"/"+category, path+chicmeProduct.getParentSku(),chicmeProduct.getParentSku());
		String[] title = {"ParentSKU", "SKU", "产品标题", "产品描述", "产品标签", "品牌", "UPC",
				"landingPageUrl", "msrp", "颜色", "尺寸值", "来源url", "价格($)", "运费($)", "库存量", "最小送达时间",
				"最大送达时间", "主图（URL）地址", "变种图（URL）地址", "附图1（URL）地址", "附图2（URL）地址",
				"附图3（URL）地址", "附图4（URL）地址", "附图5（URL）地址", "附图6（URL）地址", "附图7（URL）地址",
				"附图8（URL）地址", "附图9（URL）地址", "附图10（URL）地址", "附图11（URL）地址", "附图12（URL）地址",
				"附图13（URL）地址", "附图14（URL）地址", "附图15（URL）地址", "附图16（URL）地址", "附图17（URL）地址",
				"附图18（URL）地址", "附图19（URL）地址", "附图20（URL）地址"};
	
		
	/*	for(String itemId : itemIds){
			FileUtils.copyFolder(codeMsg.getProperty("path")+itemId, path+itemId);
		}*/
		ExportExcel.exportExcel(path+"wish.xls", title, dataExcels);
		String dlPath = "";
		try {
			dlPath = ZipUtils.compress(dir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//System.out.println(dlPath);
		return dlPath;
	}
	
	/**
	 * select id from chicme_product group by itemId order by id asc limit 10;
	 */
}
