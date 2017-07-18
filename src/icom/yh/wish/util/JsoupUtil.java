package icom.yh.wish.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import icom.yh.wish.entity.ErrorProductId;
import icom.yh.wish.entity.ProductId;
import icom.yh.wish.entity.WishData;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JsoupUtil {

	private static int count = 0;

	private static String imgHttp = "http://7xp7yi.com1.z0.glb.clouddn.com/";

	public static ExecutorService fixedThreadPool = Executors.newFixedThreadPool(60);

	public static Map<String, String> login(String url, String loginCode, String loginPasseord, String validateCode)
			throws IOException {
		if (url == null || url.equals(""))
			url = "https://login.maijia.com/user/login?redirectURL=";
		Connection.Response res = Jsoup.connect(url)
				.data("loginCode", loginCode, "loginPassword", loginPasseord, "validateCode", validateCode)
				.method(Method.POST).execute();
		return res.cookies();
	}

	/**
	 * 
	 * @param url
	 *            ����(hot) ���(fly) ��Ʒ����(new/hot) ��Ʒ���(new/fly)
	 * @param cookies
	 * @return pageNo=187&pageSize=100
	 * @throws IOException
	 */
	public static Element html(String url, Map<String, String> cookies) throws IOException {
		if (url == null || url.equals(""))
			url = "http://www.maijia.com/restapi/data/wish/item/fly/list?pageSize=100";
		Document objectDoc = null;
		objectDoc = Jsoup.connect(url).cookies(cookies).get();
		return objectDoc.body();
	}

	public static List<String> itemList(int page, String type, String url, Map<String, String> cookies,
			Session session) {
		try {
			List<String> r = new ArrayList<>();
			// Element ele = html(url, cookies);
			if (url == null || url.equals(""))
				url = "http://www.maijia.com/restapi/data/wish/item/fly/list?pageSize=100";
			Document objectDoc = null;
			objectDoc = Jsoup.connect(url).cookies(cookies).get();
			String text = objectDoc.body().text();
			JSONObject json = JSONObject.fromObject(text);
			JSONArray data = json.getJSONArray("data");
			for (int i = 0; i < data.size(); i++) {
				JSONObject item = data.getJSONObject(i);
				String itemId = item.getString("itemId");
				r.add(itemId);
			}
			return r;
		} catch (JSONException e) {
			ErrorProductId errorProductId = new ErrorProductId(page, type);
			session.save(errorProductId);
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			ErrorProductId errorProductId = new ErrorProductId(page, type);
			session.save(errorProductId);
			e.printStackTrace();
		} catch (IOException e) {
			ErrorProductId errorProductId = new ErrorProductId(page, type);
			session.save(errorProductId);
			e.printStackTrace();
		}
		return null;
	}

	private static List<String> errorIds = new ArrayList<>();

	public static synchronized List<WishData> wishData(int num, String itemId) throws IOException {
		List<WishData> list = new ArrayList<>();
		String url = "https://www.wish.com/c/=" + itemId;
	//	String url = "https://www.wish.com/c/=580888ee463e807169f55b63";
		Document d = null;
		try {
			d = Jsoup.connect(url).get();
			Elements e = d.getElementsByAttributeValue("type", "application/ld+json");
			String c = e.html();
			if (!c.equals("")) {
				String merchantTags = "";
				String desc = regex("description(.)+\"", c);
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
		//		System.out.println(jo2);
				JSONObject extra_photo_urls = jo2.getJSONObject("extra_photo_urls");
				Collection<String> imgUrls = extra_photo_urls.values();
				String parentSku = "ZB" + new SimpleDateFormat("yyMMdd").format(new Date()) + (num + count);
			
				//下载图片
				List<String> imgNames = imgGet(itemId, parentSku, imgUrls);
			
				
				JSONObject commerce_product_info = jo2.getJSONObject("commerce_product_info");
				JSONArray variations = commerce_product_info.getJSONArray("variations");
				JSONArray merchant_tags = jo2.getJSONArray("merchant_tags");
				for(int i = 0; i < merchant_tags.size(); i++){
					merchantTags = merchantTags + merchant_tags.getJSONObject(i).getString("name")+",";
				}
				// ����ͼƬ�ļ���
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
				//	File imgFile = new File("c:/ys/img/");
				//	imgGet(imgFile, wishData.getParentSKU() + "_MAIN.jpg", wishData.getMainImgUrl());
					//wishData.setMainImgUrl(imgHttp + wishData.getParentSKU() + "_MAIN.jpg");
				//	wishData.setMainImgUrl(mainImgUrl);
					wishData.setDesc(desc);
					wishData.setLabel(jo2.getString("keywords"));
					wishData.setSjtime(jo2.getString("generation_time"));
				//	wishData.setImgs(imgNames);
					wishData.setImgs(imgUrls);
					// ����
					wishData.setSaleNum(jo2.getInt("num_bought"));
					wishData.setCollNum(jo2.getInt("num_wishes"));
					JSONObject v = variations.getJSONObject(i);
					// localized_price,localized_shipping��localized_retail_price
				//	wishData.setCollNum(0);
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
			errorIds.add(itemId);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			errorIds.add(itemId);
		} catch (ConnectException e) {
			e.printStackTrace();
			errorIds.add(itemId);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			errorIds.add(itemId);
		} catch (SocketException e) {
			e.printStackTrace();
			errorIds.add(itemId);
		} catch (Exception e) {
			e.printStackTrace();
			errorIds.add(itemId);
		}
		return list;
	}

	private static List<String> imgGet(String itemId, String parentSku, Collection<String> imgUrls) {
		List<String> list = new ArrayList<String>();
		String[] ss = imgUrls.toArray(new String[] {});
		for (int i = 0; i < ss.length; i++) {

			list.add(imgHttp + parentSku + "_" + (1 + i) + ".jpg");
			imgGet(new File("c:/ys/img/"), parentSku + "_" + (1 + i) + ".jpg", ss[i].replace("small", "large"));
		}
		return list;
	}

	//ʹ�ö��߳�ץȡͼƬ
	private static void imgGet(final File dir, final String imgName, final String imageUrl) {
		//System.out.println(dir.getPath());
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println("ͼƬ�����Ŀ¼��");
		}
	//	if (!new File(dir.getPath() + "/" + imgName).exists()) {
			fixedThreadPool.submit(new Runnable() {
				@Override
				public void run() {
						try {
							OutputStream os = new FileOutputStream(dir.getPath() + "/" + imgName); //
							URL url = new URL(imageUrl);
							//InputStream is = url.openStream();
							URLConnection connection = url.openConnection();
							connection.setConnectTimeout(5000);
							InputStream is = connection.getInputStream();
							byte[] buff = new byte[1024];
							while (true) {
								int readed = is.read(buff);
								if (readed == -1) {
									break;
								}
								byte[] temp = new byte[readed];
								System.arraycopy(buff, 0, temp, 0, readed); // д���ļ�
								os.write(temp);
							}
							is.close();
							os.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
			});
			
	//	}
	}

	public static void main(String[] args) {
		try {

			List<String> itemIds = ExcelUtil.excelToList(new FileInputStream(new File("c:/ys/ID.xls")));
			List<Object> list = new ArrayList<>();
			int i = 1;

			for (String itemId : itemIds) {
				List<WishData> dataList;
				dataList = wishData(i, itemId);
				i++;
				list.addAll(dataList);
				System.out.println(Thread.currentThread().getName() + "  " + Thread.currentThread().getId());
			//	 fixedThreadPool.submit(new MyRannable(i, itemId, list));
			//	 i++;
			}

			String[] title = { "ParentSKU", "SKU", "��Ʒ����", "��Ʒ����", "��Ʒ��ǩ", "Ʒ��", "UPC", "landingPageUrl", "msrp", "��ɫ",
					"�ߴ�ֵ", "��ԴURL", "�۸�($)", "�˷�($)", "�����", "��С�ʹ�ʱ��", "����ʹ�ʱ��", "��ͼ(URL)��ַ", "����ͼ(URL)��ַ",
					"��ͼ1(URL)��ַ", "��ͼ2(URL)��ַ", "��ͼ3(URL)��ַ", "��ͼ4(URL)��ַ", "��ͼ5(URL)��ַ", "��ͼ6(URL)��ַ", "��ͼ7(URL)��ַ",
					"��ͼ8(URL)��ַ", "��ͼ9(URL)��ַ", "��ͼ10(URL)��ַ", "��ͼ11(URL)��ַ", "��ͼ12(URL)��ַ", "��ͼ13(URL)��ַ",
					"��ͼ14(URL)��ַ", "��ͼ15(URL)��ַ", "��ͼ16(URL)��ַ", "��ͼ17(URL)��ַ", "��ͼ18(URL)��ַ", "��ͼ19(URL)��ַ",
					"��ͼ20(URL)��ַ", "�ղ���", "������", "���״̬", "������", "��ƷID" };
			ExportExcel.exportExcel("c:/ys/1.xls", title, list);
			System.err.println(errorIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void catchData(int i,String itemId, Session session){
		List<WishData> dataList;
		try {
			dataList = wishData(i, itemId);
			for (WishData wishData : dataList) {
				session.save(wishData);
			}
		//	productId.setIsC(1);
		//	session.update(productId);
			// list.addAll(dataList);
			System.out.println(Thread.currentThread().getName() + "  " + itemId+"    "+i);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ��������
	}
	private static String regex(String regex, String str) {
		Pattern p = Pattern.compile(regex, Pattern.DOTALL);
		Matcher m = p.matcher(str);
//		System.err.println(m.find());
	//	System.out.println(m.find());
		m.find();
		// if (m.find()) {
		// }
		return m.group();
	}

	public static class MyRannable implements Runnable {
		private int num;
		private ProductId productId;
		private Session session;

		public MyRannable(int num, ProductId productId, Session session) {
			this.num = num;
			this.productId = productId;
			this.session = session;
		}

		@Override
		public void run() {
			List<WishData> dataList;
			try {
				dataList = wishData(num, productId.getItemId());
				// ��������
				for (WishData wishData : dataList) {
					session.save(wishData);
					productId.setIsC(1);
					session.update(productId);
				}
				// list.addAll(dataList);
				System.out.println(Thread.currentThread().getName() + "  " + Thread.currentThread().getId());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
