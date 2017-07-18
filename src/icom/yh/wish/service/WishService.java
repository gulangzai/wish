package icom.yh.wish.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.annotation.Resource;

import org.apache.commons.lang3.concurrent.ConcurrentUtils;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import icom.yh.wish.dao.BaseDao;
import icom.yh.wish.dao.TbProductDao;
import icom.yh.wish.dao.WishDao;
import icom.yh.wish.entity.OutTimeImg;
import icom.yh.wish.entity.RespData;
import icom.yh.wish.entity.RespWishData;
import icom.yh.wish.entity.RespWishSimpleData;
import icom.yh.wish.entity.TbProduct;
import icom.yh.wish.entity.WishData;
import icom.yh.wish.entity.WishDataExcel;
import icom.yh.wish.entity.WishSimpleData;
import icom.yh.wish.entity.WishTask;
import icom.yh.wish.entity.WishTaskLink;
import icom.yh.wish.util.CodeMsg;
import icom.yh.wish.util.ExportExcel;
import icom.yh.wish.util.FileUtils;
import icom.yh.wish.util.IDGenertor;
import icom.yh.wish.util.JsoupUtil;
import icom.yh.wish.util.RegexUtil;
import icom.yh.wish.util.ZipUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service(value="wishService")
public class WishService {

	private static int num = 1;

	@Resource
	private UserService userService;

	@Resource
	private WishDao wishDao;
	
	@Resource
	private TbProductDao tbProductDao;
	
	@Resource
	private BaseDao baseDao;

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(60);

	private CodeMsg codeMsg = new CodeMsg();

	public Model wishDataList(int currentPage, int pageSize, String key,
			/* String[] _itemIds, */ Model model) {
		if (!key.equals("")) {
			List<WishSimpleData> dataList = new ArrayList<>();
			if (currentPage == 1) {
				try {
					String url ="https://www.wish.com/search/" + key;
					//url="https://www.wish.com/merchant/fashionshow";
					//url="https://www.wish.com/api/merchant?start=1&query=fashionshow&count=24&transform=true&_buckets=&_experiments";
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
			} else {
				try {
					// String q =
					// "start="+((currentPage-1)*pageSize)+"&query="+key+"&transform=true&last_cids%5B%5D=56ac32a85e47500f3fe1883f&last_cids%5B%5D=55b9fa0edeecbd33b97f1bf7&last_cids%5B%5D=57a18da81dcfcf37ccd13947&last_cids%5B%5D=582aa038fef40943ca9dbb6b&last_cids%5B%5D=547c17273dabbe37ab581d5e&last_cids%5B%5D=54acfd6a509baf592a6fc7b3&last_cids%5B%5D=560bbe21cd136f10e5a0ba0d&last_cids%5B%5D=5760b3fe4d1b7b109c87c500&last_cids%5B%5D=54b9dc696ad175094ea9b2b9&last_cids%5B%5D=56f519476860ec621b502fd1&last_cids%5B%5D=583fddaf7434ac4d20c496f4&last_cids%5B%5D=57a18d7a70f8b66ddd16c005&last_cids%5B%5D=557a4f7f4b06781bf00f0548&last_cids%5B%5D=57f3443452ae0318a4a4d8af&last_cids%5B%5D=55faa2fc5b39560ce7375608&last_cids%5B%5D=556d7f7eb560ab1b3880b0fd&last_cids%5B%5D=54b9dc556ad175094fa9b257&last_cids%5B%5D=54f82efe73f9e12632d395e1&last_cids%5B%5D=57cd4c7f03fe8915f62487f5&last_cids%5B%5D=57bd4cbe76907c2272b7c739&last_cids%5B%5D=548a505e40b378392bd23a74&last_cids%5B%5D=54a7a9ce24db577a62ae73f2&last_cids%5B%5D=5562f6f8b68ddd0e933991ec&last_cids%5B%5D=54d88c7ebbac340518001f06&_buckets=&_experiments=";
					String q = "start=" + ((currentPage - 1) * pageSize) + "&query=" + key
							+ "&is_commerce=true&transform=true&count=25&include_buy_link=true&_buckets=&_experiments=";
					/*
					 * for(String itemId : _itemIds){ q = q +
					 * "last_cids%5B%5D="+itemId+"&"; }
					 */
					if (q.lastIndexOf("&") > 0)
						q = q.substring(0, q.length() - 1);
					// System.out.println(q);
					String url = "https://www.wish.com/api/search?" + q;
					//url="https://www.wish.com/api/merchant?"+q;
					System.out.println(url);
					Map<String, String> cookies = new HashMap<>();
					cookies.put("_xsrf", "3e94d53c6fb74696abbd24b3b936120a");
					Document document = Jsoup.connect(url).ignoreContentType(true).cookies(cookies)
							.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
							.header("X-Requested-With", "XMLHttpRequest")
							.header("X-XSRFToken", "3e94d53c6fb74696abbd24b3b936120a").post();
					String text = document.text();
					JSONObject jsonObject = JSONObject.fromObject(text);
					JSONObject data = jsonObject.getJSONObject("data");
					JSONArray ja = data.getJSONArray("results");
					System.out.println(ja.size());
					for (int i = 0; i < ja.size(); i++) {
						WishSimpleData simpleData = new WishSimpleData();
						JSONObject jo = ja.getJSONObject(i);
						// System.out.println(jo);
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
			System.out.println("大小："+dataList.size());
			model.addAttribute("dataList", dataList);
			model.addAttribute("key", key);
			model.addAttribute("currentPage", currentPage);
			int userId = userService.sessionUserId();
			List<String> list = wishDao.allMyItemIds(userId);
			model.addAttribute("list", list);
		}
		//List<WishSimpleData> list = wishDao.simpleDataList("search", userService.sessionUserId());
		//	model.addAttribute("list", list);
		return model;
	}
	
	
	
	public List wishDataList(int currentPage, int pageSize, String key) {
		List<WishSimpleData> dataList = new ArrayList<>();
		if (!key.equals("")) { 
			if (currentPage == 1) {
				try {
					String url ="https://www.wish.com/search/" + key;
					//url="https://www.wish.com/merchant/fashionshow";
					//url="https://www.wish.com/api/merchant?start=1&query=fashionshow&count=24&transform=true&_buckets=&_experiments";
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
			} else {
				try {
					// String q =
					// "start="+((currentPage-1)*pageSize)+"&query="+key+"&transform=true&last_cids%5B%5D=56ac32a85e47500f3fe1883f&last_cids%5B%5D=55b9fa0edeecbd33b97f1bf7&last_cids%5B%5D=57a18da81dcfcf37ccd13947&last_cids%5B%5D=582aa038fef40943ca9dbb6b&last_cids%5B%5D=547c17273dabbe37ab581d5e&last_cids%5B%5D=54acfd6a509baf592a6fc7b3&last_cids%5B%5D=560bbe21cd136f10e5a0ba0d&last_cids%5B%5D=5760b3fe4d1b7b109c87c500&last_cids%5B%5D=54b9dc696ad175094ea9b2b9&last_cids%5B%5D=56f519476860ec621b502fd1&last_cids%5B%5D=583fddaf7434ac4d20c496f4&last_cids%5B%5D=57a18d7a70f8b66ddd16c005&last_cids%5B%5D=557a4f7f4b06781bf00f0548&last_cids%5B%5D=57f3443452ae0318a4a4d8af&last_cids%5B%5D=55faa2fc5b39560ce7375608&last_cids%5B%5D=556d7f7eb560ab1b3880b0fd&last_cids%5B%5D=54b9dc556ad175094fa9b257&last_cids%5B%5D=54f82efe73f9e12632d395e1&last_cids%5B%5D=57cd4c7f03fe8915f62487f5&last_cids%5B%5D=57bd4cbe76907c2272b7c739&last_cids%5B%5D=548a505e40b378392bd23a74&last_cids%5B%5D=54a7a9ce24db577a62ae73f2&last_cids%5B%5D=5562f6f8b68ddd0e933991ec&last_cids%5B%5D=54d88c7ebbac340518001f06&_buckets=&_experiments=";
					String q = "start=" + ((currentPage - 1) * pageSize) + "&query=" + key
							+ "&is_commerce=true&transform=true&count=25&include_buy_link=true&_buckets=&_experiments=";
					/*
					 * for(String itemId : _itemIds){ q = q +
					 * "last_cids%5B%5D="+itemId+"&"; }
					 */
					if (q.lastIndexOf("&") > 0)
						q = q.substring(0, q.length() - 1);
					// System.out.println(q);
					String url = "https://www.wish.com/api/search?" + q;
					//url="https://www.wish.com/api/merchant?"+q;
					System.out.println(url);
					Map<String, String> cookies = new HashMap<>();
					cookies.put("_xsrf", "3e94d53c6fb74696abbd24b3b936120a");
					Document document = Jsoup.connect(url).ignoreContentType(true).cookies(cookies)
							.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
							.header("X-Requested-With", "XMLHttpRequest")
							.header("X-XSRFToken", "3e94d53c6fb74696abbd24b3b936120a").post();
					String text = document.text();
					JSONObject jsonObject = JSONObject.fromObject(text);
					JSONObject data = jsonObject.getJSONObject("data");
					JSONArray ja = data.getJSONArray("results");
					System.out.println(ja.size());
					for (int i = 0; i < ja.size(); i++) {
						WishSimpleData simpleData = new WishSimpleData();
						JSONObject jo = ja.getJSONObject(i);
						// System.out.println(jo);
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
			System.out.println("大小："+dataList.size());   
		} 
		return dataList;
	}
	

	public static void main(String[] args) {
		/*
		 * Document document; try { document =
		 * Jsoup.connect("https://www.wish.com/search/magnets").get(); Elements
		 * eles = document.body().getElementsByTag("Script"); Element ele =
		 * eles.get(eles.size()-1); String html = ele.html(); BufferedReader
		 * bufferedReader = new BufferedReader(new StringReader(html));
		 * bufferedReader.readLine(); bufferedReader.readLine(); String
		 * orig_feed_items = bufferedReader.readLine(); orig_feed_items =
		 * orig_feed_items.replace("pageParams['orig_feed_items'] = ",
		 * "").replace(";", ""); JSONArray ja =
		 * JSONArray.fromObject(orig_feed_items); for(int i=0;i<ja.size();i++){
		 * WishSimpleData simpleData = new WishSimpleData(); JSONObject jo =
		 * ja.getJSONObject(i);
		 * simpleData.setDisplay_picture(jo.getString("display_picture"));
		 * simpleData.setFeed_tile_text(jo.getString("feed_tile_text"));
		 * JSONObject commerce_product_info =
		 * jo.getJSONObject("commerce_product_info"); JSONArray variations =
		 * commerce_product_info.getJSONArray("variations"); JSONObject v =
		 * variations.getJSONObject(0); JSONObject localized_price =
		 * v.getJSONObject("localized_price");
		 * simpleData.setPrice(localized_price.getString("localized_value"));
		 * System.out.println(simpleData); } } catch (IOException e) {
		 * e.printStackTrace(); }
		 */
		Map<String, String> params = new HashMap<>();
		params.put("start", 25 + "");
		params.put("query", "magnets");
		params.put("transform", true + "");
		params.put("_buckets", "");
		params.put("_experiments", "");
		try {
			String q = "start=1&query=basketball jerseys&transform=true";
			String url = "https://www.wish.com/api/search?" + q;
			Map<String, String> cookies = new HashMap<>();
			cookies.put("_xsrf", "3e94d53c6fb74696abbd24b3b936120a");
			Document document = Jsoup.connect(url).ignoreContentType(true).cookies(cookies)
					.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
					.header("X-Requested-With", "XMLHttpRequest")
					.header("X-XSRFToken", "3e94d53c6fb74696abbd24b3b936120a").post();
			String text = document.text();
			JSONObject jsonObject = JSONObject.fromObject(text);
			System.out.println(jsonObject);
			JSONObject data = jsonObject.getJSONObject("data");
			System.out.println(data);
			JSONArray ja = data.getJSONArray("results");
			System.out.println(ja.toString());
			System.out.println(ja.size());
			for (int i = 0; i < ja.size(); i++) {
				WishSimpleData simpleData = new WishSimpleData();
				JSONObject jo = ja.getJSONObject(i);
				// System.out.println(jo);
				// System.out.println(i);
				simpleData.setDisplay_picture(jo.getString("display_picture"));
				simpleData.setFeed_tile_text(null==jo.get("feed_tile_text")?"":jo.getString("feed_tile_text"));
				simpleData.setItemId(jo.getString("id"));
				JSONObject commerce_product_info = jo.getJSONObject("commerce_product_info");
				JSONArray variations = commerce_product_info.getJSONArray("variations");
				JSONObject v = variations.getJSONObject(0);
				JSONObject localized_price = v.getJSONObject("localized_price");
				simpleData.setPrice(localized_price.getString("localized_value"));
				System.out.println(i);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存选中的产品
	 * @param simpleData
	 * @return
	 */
	public Map<String, String> simpleDataSave(WishSimpleData simpleData) {
		Map<String, String> map = new HashMap<>();
		int userId = userService.sessionUserId();
		WishSimpleData wishSimpleData = wishDao.simpleDataByItemId(simpleData.getItemId(), userId);
		//该用户是否已经添加了该产品
		if (null == wishSimpleData) {
			simpleData.setUserId(userId);
			simpleData.setIsC(0);
			Serializable id = wishDao.save(simpleData);
			// 查询是否抓取过该产品
			List<WishData> list = wishDao.isHasItemIdList(simpleData.getItemId());
			if (null == list || list.size() == 0) {
				try {
					list = wishData(Integer.parseInt("" + id), simpleData.getItemId());//JsoupUtil.wishData(Integer.parseInt("" + id), simpleData.getItemId());
					for (WishData data : list) {
						wishDao.save(data); 
						TbProduct tbProduct = new TbProduct(data);
						tbProductDao.save(tbProduct);
					} 
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			map.put("msg", "保存成功");
			map.put("status", "ok");
			map.put("id", id + "");
		} else {
			if (simpleData.getAdd() == 1 && wishSimpleData.getAdd() == 0) {
				wishSimpleData.setAdd(1);
				map.put("msg", "更新成功");
				map.put("status", "ok");
				map.put("id", wishSimpleData.getItemId() + "");
				wishDao.update(wishSimpleData);
			} else if (simpleData.getSearch() == 1 && wishSimpleData.getSearch() == 0) {
				wishSimpleData.setSearch(1);
				map.put("msg", "更新成功");
				map.put("status", "ok");
				map.put("id", wishSimpleData.getId() + "");
				wishDao.update(wishSimpleData);
			} else if (simpleData.getSelect() == 1 && wishSimpleData.getSelect() == 0) {
				wishSimpleData.setSelect(1);
				map.put("msg", "更新成功");
				map.put("status", "ok");
				map.put("id", wishSimpleData.getId() + "");
				wishDao.update(wishSimpleData);
			} else {
				map.put("msg", "已经添加了该产品");
				map.put("status", "fail");
			}
		}
		return map;
	}

	public Map<String, Object> simpleDataDel(String itemId, String type) {
		WishSimpleData wishSimpleData = wishDao.get(itemId, userService.sessionUserId());
		Map<String, Object> map = new HashMap<>();
		if (null != wishSimpleData && wishSimpleData.getIsC()==0) {
			wishDao.delete(wishSimpleData);
		}
		List<WishSimpleData> list = wishDao.simpleDataList(type, userService.sessionUserId());
		// ��ӵĲ�Ʒ
		map.put("list", list);
		map.put("total", list.size());
		map.put("msg", "删除成功!");
		return map;
	}

	public void simpleDataDel(String itemId) {
		WishSimpleData wishSimpleData = wishDao.get(itemId, userService.sessionUserId());
		if (null != wishSimpleData) {
			wishDao.delete(wishSimpleData);
		}
	}

	public String myWishData(int isC, int type) {
		/*
		 * List<String> itemIds =
		 * wishDao.myWishItemIds(userService.sessionUserId()); List<Object> list
		 * = new ArrayList<>(); int i = 1; for (String itemId : itemIds) {
		 * List<WishData> dataList = wishData(i, itemId); for(WishData wishData
		 * : dataList){ wishDao.save(wishData); } i++; list.addAll(dataList); }
		 */
		int userId = userService.sessionUserId();
		String account = userService.sessionAccount();
		List<RespWishData> list = wishDao.myWishDataList(userId, isC);
		//List<WishData> list = wishDao.WishDataList(userId);
		List<WishDataExcel> dataExcels = new ArrayList<>();
		List<String> itemIds = new ArrayList<>();
		//将文件压缩打包
		String path = codeMsg.getProperty("path")+account+"/";
		File dir = new File(path);
		if(dir.exists())
			FileUtils.recurDelete(dir);		
		dir.mkdirs();
	//	Map<String,List<String>> map = new HashMap<>();
		//在这里下载图片
		for(RespWishData wishData : list){
			WishDataExcel dataExcel = new WishDataExcel(wishData);
		//	imgGet(fixedThreadPool, dir, wishData.getItemId() + "_MAIN.jpg", wishData.getMainImgUrl());
			//设置主图
		//	dataExcel.setMainImgUrl(codeMsg.getProperty("imgHttp")+wishData.getItemId() + "_MAIN.jpg");
		//	dataExcel.setImgs(wishData.getItemId());
			dataExcel.setImgs(wishData.getParentSKU());
			//System.out.println(dataExcel.getImgUrl1());
			if(itemIds.contains(wishData.getItemId())){//如果包含，则已经下载过图片了
				//dataExcel.setImgs(map.get(wishData.getItemId()));
			}else{
				itemIds.add(wishData.getItemId());
				if(type==2){//wish版本
					FileUtils.copyFolder(codeMsg.getProperty("path")+wishData.getItemId(), path,wishData.getParentSKU());
				}else{//店小秘
					FileUtils.copyFolder(codeMsg.getProperty("path")+wishData.getItemId(), path+wishData.getParentSKU(),wishData.getParentSKU());
				}
				
			//	List<String> imgs = wishData.getImgs();
			//	List<String> imgs2 = imgGet(fixedThreadPool, dir, wishData.getItemId(), imgs);
			//	dataExcel.setImgs(imgs2);
			//	map.put(wishData.getItemId(), imgs2);
			//	dataExcel.setImgs(imgs2);
			}
			dataExcels.add(dataExcel);
		}
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

	public List<WishData> wishData(int num, String itemId) {
		List<WishData> list = new ArrayList<>();
		File dir = new File(codeMsg.getProperty("imgPath") + itemId + "/");
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
					// String json =
					// StringEscapeUtils.escapeJava(html).replace("pageParams['mainContestObj']
					// =", "");//.replaceAll("\\\"", "").replaceAll("\\\\", "");
					html = html.replace("pageParams['mainContestObj'] =", "");
					JSONObject jo2 = JSONObject.fromObject(html);
					System.out.println(jo2.toString());
					// System.out.println(jo2);
					JSONObject extra_photo_urls = jo2.getJSONObject("extra_photo_urls");
					Collection<String> imgUrls = extra_photo_urls.values();
					String parentSku = "ZB" + new SimpleDateFormat("yyMMdd").format(new Date()) + (num);
				
					//下载图片
					List<String> imgNames = imgGetJiujiChina(fixedThreadPool, dir, itemId, imgUrls);
					
					JSONObject commerce_product_info = jo2.getJSONObject("commerce_product_info");
					JSONArray variations = commerce_product_info.getJSONArray("variations");
					JSONArray merchant_tags = jo2.getJSONArray("merchant_tags");
				
					for (int i = 0; i < merchant_tags.size(); i++) {
						merchantTags = merchantTags + merchant_tags.getJSONObject(i).getString("name") + ",";
					}
				
					for (int i = 0; i < variations.size(); i++) {
						WishData wishData = new WishData();
						wishData.setItemId(itemId);
						wishData.setParentSKU(parentSku);
						wishData.setProId("");
						wishData.setUrl(url);
						wishData.setName(jo1.getString("name"));
						wishData.setMainImgUrl(jo1.getString("image"));
						wishData.setMerahant_tags(merchantTags);
						// ץȡסͼ
						//获取主图
						Boolean succ  = imgGet(fixedThreadPool, dir, itemId + "-main.jpg", wishData.getMainImgUrl(), itemId, 1);
						
					//	wishData.setMainImgUrl(codeMsg.getProperty("imgHttp") + wishData.getParentSKU() + "_MAIN.jpg");
						// wishData.setMainImgUrl(mainImgUrl);
						
						wishData.setDesc(desc);
						wishData.setLabel(jo2.getString("keywords"));
						wishData.setSjtime(jo2.getString("generation_time"));
						// wishData.setImgs(imgNames);
						wishData.setImgs(imgUrls);
						// ����
						wishData.setSaleNum(jo2.getInt("num_bought"));
						wishData.setCollNum(jo2.getInt("num_wishes"));
						JSONObject v = variations.getJSONObject(i);
						// localized_price,localized_shipping��localized_retail_price
						// wishData.setSaleNum(0);
						wishData.setColor(v.getString("color"));
						// wishData.setMaxTime(v.getInt("max_shipping_time"));
						// wishData.setMinTime(v.getInt("min_shipping_time"));
						wishData.setSize(v.getString("size"));
						wishData.setSKU(wishData.getParentSKU(), wishData.getSize(), wishData.getColor());
						JSONObject localized_price = v.getJSONObject("localized_price");
						wishData.setPrice(localized_price.getString("localized_value"));
						JSONObject localized_shipping = v.getJSONObject("localized_shipping");
						wishData.setFreight(localized_shipping.getString("localized_value"));
						JSONObject localized_retail_price = v.getJSONObject("localized_retail_price");
						wishData.setMsrp(localized_retail_price.getString("localized_value"));
						// wishData.setStock(v.getInt("inventory"));
						list.add(wishData);
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

	private List<String> imgGet(ExecutorService fixedThreadPool, File dir, String itemId, Collection<String> imgUrls) {
		List<String> list = new ArrayList<String>();
		String[] ss = imgUrls.toArray(new String[] {});
		for (int i = 0; i < ss.length; i++) {
			list.add(codeMsg.getProperty("imgHttp") + itemId + "_" + (1 + i) + ".jpg");
			boolean succ = imgGet(fixedThreadPool, dir, itemId + "_" + (1 + i) + ".jpg",
					ss[i].replace("small", "large"), itemId, 1); 
		}
		return list;
	}
	
	
	private List<String> imgGetJiujiChina(ExecutorService fixedThreadPool, File dir, String itemId, Collection<String> imgUrls) {
		List<String> list = new ArrayList<String>();
		String[] ss = imgUrls.toArray(new String[] {});
		for (int i = 0; i < ss.length; i++) {
			list.add(codeMsg.getProperty("jiujichinaImgHttp") + itemId + "-" + (1 + i) +"-small"+ ".jpg");
			/*下载小图片*/
			boolean succ = imgGet(fixedThreadPool, dir, itemId + "-" + (1 + i) + "-small.jpg",
					ss[i], itemId, 1);
			/*下载大图片*/
			boolean succc = imgGet(fixedThreadPool, dir, itemId + "-" + (1 + i) + "-large.jpg",
					ss[i].replace("small", "large"), itemId, 1);
			//System.out.println(succ);
			/*int j = 1;
			while(!succ && j<5){	//下载图片失败,重新下载
				j++;
				succ = imgGet(fixedThreadPool, dir, itemId + "_" + (1 + i) + ".jpg",
							ss[i].replace("small", "large"));
			}
			if(!succ){	//任然失败
				//保存失败的产品图片
				 OutTimeImg outTimeImg = new OutTimeImg(itemId, ss[i].replace("small", "large"));
				 if(null == wishDao.isHasOutTimeImg(outTimeImg))
					 wishDao.save(outTimeImg);
			}*/
		}
		return list;
	}
	

	/**
	 * 抓取图片，返回抓取失败后的地址
	 * @param fixedThreadPool
	 * @param dir
	 * @param imgName
	 * @param imageUrl
	 * @return
	 */
	private boolean imgGet(final ExecutorService fixedThreadPool, final File dir, final String imgName, final String imageUrl, String itemId, int j) {
		// System.out.println(dir.getPath());
		boolean flag = false;
			if (!dir.exists()) {
				dir.mkdirs(); 
			}
			Future<Boolean> future = fixedThreadPool.submit(new Callable<Boolean>() {
				@Override
				public Boolean call() {
					boolean succ = true;
					try {
						File file = new File(dir.getPath() + "/" + imgName);
						if(!file.exists()){
							OutputStream os = new FileOutputStream(file); //
							URL url = new URL(imageUrl);
							// InputStream is = url.openStream();
							URLConnection connection = url.openConnection();
							connection.setConnectTimeout(5000);
							InputStream is = connection.getInputStream();
							byte[] buf = new byte[1024];
							int len = 0;
							while ((len=is.read(buf))!=-1) { 
								os.write(buf,0,len);
							}
							is.close();
							os.close();
						} 
					} catch (Exception e) {
						succ = false;
						e.printStackTrace();
					}
					return succ;
				}
				
			}); 
			try {
				flag = future.get();
			//	int j = 1;
				while(!flag && j<5){	//下载图片失败,重新下载
					j++;
					flag = imgGet(fixedThreadPool, dir, imgName, imageUrl, itemId, j);
				}
				if(!flag){	//任然失败
					//保存失败的产品图片
					 OutTimeImg outTimeImg = new OutTimeImg(itemId, imageUrl);
					 if(null == wishDao.isHasOutTimeImg(outTimeImg))
						 wishDao.save(outTimeImg);
				}
			} catch (InterruptedException e) {
				flag = false;
				e.printStackTrace();
			} catch (ExecutionException e) {
				flag = false;
				e.printStackTrace();
			} 
		return flag; 
	}
	
	

	/**
	 * 添加自己添加的ID
	 * 
	 * @param itemIds
	 * @return
	 */
	public Model myProIdAdd(String[] itemIds,Model model) {
		String day = new SimpleDateFormat("yyyyMMdd").format(new Date());
		// System.out.println(itemIds.length);
		for (String itemId : itemIds) {
			// System.out.println(itemId);
			/*
			 * if(!wishDao.hasMyId(itemId)){ int userId =
			 * userService.sessionUserId(); MyProId myProId = new
			 * MyProId(userId, itemId, day, ""); wishDao.save(myProId); }
			 */
			int userId = userService.sessionUserId();
			WishSimpleData wishSimpleData = wishDao.simpleDataByItemId(itemId, userId);
			if (null == wishSimpleData) { // 如果不存该手动添加的ID，则添加
				try {
					// List<WishData> list = JsoupUtil.wishData(1, itemId);
					WishSimpleData simpleData = new WishSimpleData();
					simpleData.setUserId(userId);
					simpleData.setItemId(itemId);
					simpleData.setDisplay_picture("");
					simpleData.setPrice("");
					simpleData.setFeed_tile_text("");
					simpleData.setAdd(1);
					simpleData.setSearch(0);
					simpleData.setSelect(0);
					Serializable id = wishDao.save(simpleData);
					boolean flag = false;
					List<WishData> list = wishDao.isHasItemIdList(itemId);
					if (null == list || list.size() == 0) {
						list = wishData(Integer.parseInt("" + id), itemId);
						flag = true;
					}
					if (list.size() > 0) {
						WishData wishData = list.get(0);
						simpleData.setDisplay_picture(wishData.getMainImgUrl());
						simpleData.setPrice(wishData.getPrice());
						simpleData.setFeed_tile_text(wishData.getSaleNum() + " bought this");
						wishData.setId(Integer.parseInt("" + id));
						wishDao.update(wishData);
						if (flag) {
							for (WishData data : list) {
								wishDao.save(data);
							}
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else { // 如果存在该id，更新
				wishSimpleData.setAdd(1);
				wishDao.update(wishSimpleData);
			}
		}
		model.addAttribute("msg", "保存成功");
		return model;
	}

	public Model selectWishData(int hot, int fly, int newhot, int newfly, int newshophot, int newshopfly,
			int currentPage, int pageSize, Model model) {
		model.addAttribute("hot", hot);
		model.addAttribute("fly", fly);
		model.addAttribute("newhot", newhot);
		model.addAttribute("newfly", newfly);
		model.addAttribute("newshophot", newshophot);
		model.addAttribute("newshopfly", newshopfly);
		List<String> itemIds = wishDao.selectWishIds(hot, fly, newhot, newfly, newshophot, newshopfly, currentPage,
				pageSize);
		List<WishSimpleData> mylist = wishDao.simpleDataList("select", userService.sessionUserId());
		List<WishSimpleData> dataList = new ArrayList<>();
		for (String itemId : itemIds) {
			// WishSimpleData wishSimpleData =
			// wishDao.simpleDataByItemId(itemId);
			// if (null == wishSimpleData) {
			try {
				List<WishData> list = JsoupUtil.wishData(1, itemId);
				if (list.size() > 0) {
					WishData wishData = list.get(0);
					WishSimpleData simpleData = new WishSimpleData();
					simpleData.setItemId(itemId);
					simpleData.setDisplay_picture(wishData.getMainImgUrl());
					simpleData.setPrice(wishData.getPrice());
					simpleData.setFeed_tile_text(wishData.getSaleNum() + "k+ bought this");
					simpleData.setAdd(0);
					simpleData.setSearch(0);
					simpleData.setSelect(1);
					// Serializable id = wishDao.save(simpleData);
					dataList.add(simpleData);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// }
		model.addAttribute("list", mylist);
		model.addAttribute("dataList", dataList);
		// System.out.println(dataList);
		model.addAttribute("currentPage", currentPage);
		int total = wishDao.selectWishTotal(hot, fly, newhot, newfly, newshophot, newshopfly);
		int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		model.addAttribute("totalPage", totalPage);
		return model;
	}

	public Model add(Model model) {
		List<WishSimpleData> list = wishDao.simpleDataList("add", userService.sessionUserId());
		model.addAttribute("list", list);
		return model;
	}

	/**
	 * 
	 * @param type (这里是下载完成或未完成的)
	 * @param currentPage
	 * @param pageSize
	 * @param model
	 * @return
	 */
	public Model myWishDataList(int type, int  isC, int isS, int currentPage, int pageSize, Model model) {
		int userId = userService.sessionUserId();
		int total = wishDao.simpleDataTotal(type, isC, userId, isS);
		model.addAttribute("currentPage", currentPage);
		int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		model.addAttribute("totalPage", totalPage);
		List<RespWishSimpleData> datas = wishDao.simpleItmeIds(type, isC, isS, currentPage, pageSize, userId);
	
		List<RespData> list = new ArrayList<>();
	//	int userId = userService.sessionUserId();
		for (RespWishSimpleData data : datas) {
			List<RespWishData> wishDatas = wishDao.myWishDataList(data.getItemId());
			if (null != wishDatas && wishDatas.size() > 0) {
				RespWishData wishData = wishDatas.get(0);
				wishData.setId(data.getId());
				RespData respData = new RespData(wishData, data.getItemId());
				respData.setIsC(data.getIsC());
				list.add(respData);
			}
		}
		model.addAttribute("list", list);
		model.addAttribute("isC", isC);
		//System.out.println(list);
		return model;
	}

	public void dataC(String itemId) {
		int userId = userService.sessionUserId();
		WishSimpleData simpleData = wishDao.simpleDataByItemId(itemId, userId);
		if (null != simpleData) {
			simpleData.setIsC(1);
			wishDao.update(simpleData);
		}
	}

	public void dataCAll() {
		int userId = userService.sessionUserId();
		wishDao.simpleDataCall(userId);
	}

	public void simpleDataDelAll() {
		int userId = userService.sessionUserId();
		wishDao.simpleDataDelAll(userId);
	}

	public Map<String, String> keyWordsTask(String[] keyWords, final int num) {
		if(keyWords.length>5){
			keyWords = new String[]{keyWords[0],keyWords[1],keyWords[2],keyWords[3],keyWords[4]};
		}
		final int userId = userService.sessionUserId();
		Map<String,String> map = new HashMap<>();
		final LinkedList<WishSimpleData> list = new LinkedList<>();
		ExecutorService executorService = Executors.newFixedThreadPool(5);  
		String taskId = IDGenertor.getOrderId();
		for(String keyword : keyWords){
			final String key = keyword.trim();
			WishTask task = new WishTask(userId, key, 0, new Timestamp(System.currentTimeMillis()),taskId);
			Serializable id = wishDao.save(task);
			final int tid = Integer.parseInt(id+"");
			System.out.println("tid="+tid);
			executorService.submit(new Runnable() {
				@Override
				public void run() {
					int[] params = query(1, 1, userId, key, list, tid);
					while(params[0]<=num){
						params = query(params[0], params[1], userId, key, list, tid);
					}
				}
			});
		}
		executorService.shutdown();
		while(true){
		//	System.err.println(list.size());
			if (executorService.isTerminated()) {  
				wishDao.taskComplete(taskId);
				System.out.println(list.size());
                System.out.println("结束了！"+new String(list.toString()).getBytes().length/1024);  
                for(WishSimpleData simpleData : list){
               // 	System.out.println(simpleData);
                	WishSimpleData wishSimpleData = wishDao.simpleDataByItemId(simpleData.getItemId(), userId);
                	int sdId = 0;
            	//	System.err.println(wishSimpleData);
                	//该用户是否已经添加了该产品
                	int _taskId = simpleData.getAdd();
            		if (null == wishSimpleData) {
            			simpleData.setUserId(userId);
            			simpleData.setIsC(0);
            			simpleData.setAdd(0);
            			simpleData.setSearch(1);
            			simpleData.setSelect(0);
            			simpleData.setIsS(1);
            			Serializable id = wishDao.save(simpleData);
            			sdId = Integer.parseInt(""+id);
            			// 查询是否抓取过该产品
            			List<WishData> wishDatas = wishDao.isHasItemIdList(simpleData.getItemId());
            			if (null == wishDatas || wishDatas.size() == 0) {
            				try {
            					wishDatas = wishData(Integer.parseInt("" + id), simpleData.getItemId());
            					for (WishData data : wishDatas) {
            						wishDao.save(data);
            					}
            				} catch (NumberFormatException e) {
            					e.printStackTrace();
            				} catch (Exception e) {
            					e.printStackTrace();
            				}
            			}
            		}else{
            			sdId = wishSimpleData.getId();
            		}
            		System.out.println("sdid="+sdId+"   taskId="+_taskId);
            		WishTaskLink link = new WishTaskLink(_taskId,sdId);
                	wishDao.save(link);
            		
                }
                System.out.println("over.........");
                break;  
            }/*else{
            	System.out.println(list.isEmpty());
            	if(!list.isEmpty()){
            		WishSimpleData simpleData = list.removeFirst();
            		WishSimpleData wishSimpleData = wishDao.simpleDataByItemId(simpleData.getItemId(), userId);
            		//该用户是否已经添加了该产品
            		if (null == wishSimpleData) {
            			simpleData.setUserId(userId);
            			simpleData.setIsC(0);
            			simpleData.setAdd(0);
            			simpleData.setSearch(1);
            			simpleData.setSelect(0);
            			simpleData.setIsS(1);
            			Serializable id = wishDao.save(simpleData);
            			// 查询是否抓取过该产品
            			List<WishData> wishDatas = wishDao.isHasItemIdList(simpleData.getItemId());
            			if (null == wishDatas || wishDatas.size() == 0) {
            				try {
            					wishDatas = wishData(Integer.parseInt("" + id), simpleData.getItemId());
            					for (WishData data : wishDatas) {
            						wishDao.save(data);
            					}
            				} catch (NumberFormatException e) {
            					e.printStackTrace();
            				} catch (Exception e) {
            					e.printStackTrace();
            				}
            			}
            		}
            	}
            }*/
		}
		map.put("msg", "正在采集数据...");
		return map;
	}
	
	private int[] query(int page, int start, int userId, String key, List<WishSimpleData> list, int tid){
			try {
				String q = "start=" + start + "&query=" + key
						+ "&transform=true&_buckets=&_experiments=";
				if (q.lastIndexOf("&") > 0)
					q = q.substring(0, q.length() - 1);
				// System.out.println(q);
				String url = "https://www.wish.com/api/search?" + q;
				Map<String, String> cookies = new HashMap<>();
				cookies.put("_xsrf", "3e94d53c6fb74696abbd24b3b936120a");
				Document document = Jsoup.connect(url).ignoreContentType(true).cookies(cookies)
						.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
						.header("X-Requested-With", "XMLHttpRequest")
						.header("X-XSRFToken", "3e94d53c6fb74696abbd24b3b936120a").post();
				String text = document.text();
				JSONObject jsonObject = JSONObject.fromObject(text);
				JSONObject data = jsonObject.getJSONObject("data");
				JSONArray ja = data.getJSONArray("results");
			//	System.out.println(ja);
				start = start+ja.size();
				for (int i = 0; i < ja.size(); i++) {
					WishSimpleData simpleData = new WishSimpleData();
					simpleData.setAdd(tid);
					JSONObject jo = ja.getJSONObject(i);
					// System.out.println(jo);
					// System.out.println(i);
					simpleData.setDisplay_picture(jo.getString("display_picture"));
					simpleData.setFeed_tile_text(null==jo.get("feed_tile_text")?"":jo.getString("feed_tile_text"));
					simpleData.setItemId(jo.getString("id"));
					JSONObject commerce_product_info = jo.getJSONObject("commerce_product_info");
					JSONArray variations = commerce_product_info.getJSONArray("variations");
					JSONObject v = variations.getJSONObject(0);
					JSONObject localized_price = v.getJSONObject("localized_price");
					simpleData.setPrice(localized_price.getString("localized_value"));
				//	if(saleNum(simpleData.getFeed_tile_text())>1000)
					list.add(simpleData);
					/*	WishSimpleData wishSimpleData = wishDao.simpleDataByItemId(simpleData.getItemId(), userId);
					if(null == wishSimpleData){		//如果没有 该产品，添加
						simpleData.setAdd(0);
						simpleData.setIsC(0);
						simpleData.setSearch(1);
						simpleData.setSearch(0);
						simpleData.setIsS(1);
						Serializable id = wishDao.save(simpleData);
						List<WishData> wishDatas = wishDao.isHasItemIdList(simpleData.getItemId());
						if (null == wishDatas || wishDatas.size() == 0) {
							try {
								list = wishData(Integer.parseInt("" + id), simpleData.getItemId());
								for (WishData wishData : wishDatas) {
									wishDao.save(wishData);
								}
							} catch (NumberFormatException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}*/
				}
				return  new int[]{page+1,start};
			} catch (IOException e) {
				e.printStackTrace();
				page = page + 1;
			}
			return  new int[]{page+1,start};
	}

	private int saleNum(String t) {
		if(null == t || t.trim().equals(""))
			return 0;
		return 0;
	}

	public Model taskList(int currentPage, int pageSize, Model model) {
		int userId = userService.sessionUserId();
		List<WishTask> list = wishDao.taskList(userId, currentPage, pageSize);
		model.addAttribute("list", list);
		model.addAttribute("currentPage", currentPage);
		int total = wishDao.taskTotal(userId);
		int totalPage = total%pageSize==0?total/pageSize:total/pageSize+1;
		model.addAttribute("totalPage", totalPage);
		return model;
	}

	public Model taskDataList(int taskId, int currentPage, int pageSize, Model model) {
		List<RespWishSimpleData> datas = wishDao.simpleItmeIds(taskId, currentPage, pageSize);
		List<RespData> list = new ArrayList<>();
		//	int userId = userService.sessionUserId();
			for (RespWishSimpleData data : datas) {
				List<RespWishData> wishDatas = wishDao.myWishDataList(data.getItemId());
				if (null != wishDatas && wishDatas.size() > 0) {
					RespWishData wishData = wishDatas.get(0);
					wishData.setId(data.getId());
					RespData respData = new RespData(wishData, data.getItemId());
					respData.setIsC(data.getIsC());
					list.add(respData);
				}
			}
		Collections.sort(list);
		model.addAttribute("list", list);
		int total = wishDao.simpleDataTotal(taskId);
		model.addAttribute("currentPage", currentPage);
		int totalPage = total % pageSize == 0 ? total / pageSize : total / pageSize + 1;
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("taskId", taskId);
		return model;	
	}

	public void taskDel(int sdId, int taskId) {
		wishDao.taskLinkDel(sdId,taskId);
		WishSimpleData data = wishDao.get(WishSimpleData.class, sdId);
		if(null != data){
			wishDao.delete(data);
		}
	}

	public void listAdd(int sdId) {
		WishSimpleData data = wishDao.get(WishSimpleData.class, sdId);
		if(null != data){
			data.setIsS(0);
			wishDao.update(data);
		}
	}
}
