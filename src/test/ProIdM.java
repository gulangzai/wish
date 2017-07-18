package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.ui.Model;

import icom.yh.wish.entity.ErrorProductId;
import icom.yh.wish.entity.ProductId;
import icom.yh.wish.entity.WishSimpleData;
import icom.yh.wish.util.JsoupUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ProIdM {

	
	public static List<Integer> errorPages = new ArrayList<>();
	
	
	/**
	 * http://www.maijia.com/restapi/data/wish/item/fly/list?pageSize=100&pageNo=1 (hot/fly)
	 * http://www.maijia.com/restapi/data/wish/item/new/fly/list?pageSize=100&pageNo=1 (newfly/newhot)
	 * http://www.maijia.com/restapi/data/wish/shop/item/new/hot/list?pageSize=100&pageNo=1 (newshophot/newshopfly)
	 * bao
	 * @param type
	 * @param auth
	 */
	public static void dataSave(String type, String auth, int page, String url){
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");// ��ȡbean.xml�е�����
		SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		try {
			List<Object[]> list = new ArrayList<>();
			Map<String, String> cookies = new HashMap<>();
			cookies.put("auth", auth);
			
			String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
			Session session = sessionFactory.openSession();
			List<String> itemList = JsoupUtil.itemList(page, type, url, cookies, session);
			// itemList = itemListN(i,itemList,url,cookies);
			if (null != itemList) {
				for (String itemId : itemList) {
					List<ProductId> pidlist = session.createQuery("from ProductId where itemId='" + itemId + "'").list();
					ProductId productId = null;
					if(null != pidlist && pidlist.size()>0){
						productId = pidlist.get(0);
						productId.setHot(1);
					}else{
						productId = new ProductId(page, itemId, 0, 1, 0, 0, 0, 0, day);
					}
					session.saveOrUpdate(productId);
				}
				session.flush();
				session.clear();
				System.out.println("����" + page);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * fly(4519) 
	 * hot(2427) 
	 * 
	 * @param args
	 */
	public static void main1(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");// ��ȡbean.xml�е�����
		SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		try {
			String type = "hot";
			int i = 2954;
			String url = "http://www.maijia.com/restapi/data/wish/item/hot/list?pageSize=100&pageNo=" + i;
			List<Object[]> list = new ArrayList<>();
			// ��½�˺�
			// Map<String, String> cookies = JsoupUtil.login(null,
			// "13661951253", "qq1434600875", "kcj3");
			Map<String, String> cookies = new HashMap<>();
			cookies.put("auth", "af42bf131ac2d015506aa19268a225607241d1c5");
			String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
			Session session = sessionFactory.openSession();
			List<String> itemList = JsoupUtil.itemList(i, type, url, cookies, session);
			
			// itemList = itemListN(i,itemList,url,cookies);
			if (null != itemList) {
				for (String itemId : itemList) {
					List<ProductId> pidlist = session.createQuery("from ProductId where itemId='" + itemId + "'").list();
					ProductId productId = null;
					if(null != pidlist && pidlist.size()>0){
						productId = pidlist.get(0);
						productId.setHot(1);
					}else{
						productId = new ProductId(i, itemId, 0, 1, 0, 0, 0, 0, day);
					}
					session.saveOrUpdate(productId);
				}
				session.flush();
				session.clear();
				System.out.println("����" + i);
			}
			while (null == itemList || itemList.size() > 0) {
				i = i + 1;
				System.err.println(i);
				url = "http://www.maijia.com/restapi/data/wish/item/hot/list?pageSize=100&pageNo=" + i;
				itemList = JsoupUtil.itemList(i, type, url, cookies, session);
				// itemList = itemListN(i, itemList, url, cookies);
				if (null != itemList) {
					for (String itemId : itemList) {
						List<ProductId> pidlist = session.createQuery("from ProductId where itemId='" + itemId + "'").list();
						ProductId productId = null;
						if(null != pidlist && pidlist.size()>0){
							productId = pidlist.get(0);
							productId.setHot(1);
						}else{
							productId = new ProductId(i, itemId, 0, 1, 0, 0, 0, 0, day);
						}
						session.saveOrUpdate(productId);
					}
					session.flush();
					session.clear();
					System.out.println("����" + i);

				}
			}
			for(Integer p : errorPages){
				ErrorProductId errorProductId = new ErrorProductId(p, type);
				session.save(p);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		ProIdM proIdM = new ProIdM();
		proIdM.wishDataList(1, 1, "580888ee463e807169f55b63", null);
	}
	public Model wishDataList(int currentPage, int pageSize, String key,
			/* String[] _itemIds, */ Model model) { 
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");// ��ȡbean.xml�е�����
		SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		if (!key.equals("")) {
			List<WishSimpleData> dataList = new ArrayList<>();
			if (currentPage == 1) {
				try {
					String url = "https://www.wish.com/search/" + key;
					url="https://www.wish.com/merchant/fashionshow#default";
					Document document = Jsoup.connect(url).get();
					Elements eles = document.body().getElementsByTag("Script");
					Element ele = eles.get(eles.size() - 1);
					String html = ele.html();
					BufferedReader bufferedReader = new BufferedReader(new StringReader(html));
					bufferedReader.readLine();
					bufferedReader.readLine();
					String orig_feed_items = bufferedReader.readLine();
					orig_feed_items = orig_feed_items.replace("pageParams['orig_feed_items'] = ", "").replace(";", "");
					JSONArray ja = JSONArray.fromObject(orig_feed_items);
					for (int i = 0; i < ja.size(); i++) {
						WishSimpleData simpleData = new WishSimpleData();
						JSONObject jo = ja.getJSONObject(i);
						simpleData.setDisplay_picture(jo.getString("display_picture"));
						simpleData.setFeed_tile_text(
								null == jo.get("feed_tile_text") ? "" : jo.getString("feed_tile_text"));
						simpleData.setItemId(jo.getString("id"));
						JSONObject commerce_product_info = jo.getJSONObject("commerce_product_info");
						JSONArray variations = commerce_product_info.getJSONArray("variations");
						JSONObject v = variations.getJSONObject(0);
						JSONObject localized_price = v.getJSONObject("localized_price");
						simpleData.setPrice(localized_price.getString("localized_value"));
						// model.addAttribute("data" + (i + 1), simpleData);
						// itemIds.add(simpleData.getItemId());
						dataList.add(simpleData);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}   
			//model.addAttribute("key", key);
			//model.addAttribute("currentPage", currentPage);   
		} 
		return model;
	}

	
}
