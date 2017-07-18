package icom.yh.wish.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpsUtil {

	private static class TrustAnyTrustManager implements X509TrustManager {

		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[] {};
		}
	}

	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	/**
	 * post鏂瑰紡璇锋眰鏈嶅姟锟�??(https鍗忚)
	 * 
	 * @param url
	 *            璇锋眰鍦板潃
	 * @param content
	 *            鍙傛暟
	 * @param charset
	 *            缂栫爜
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws KeyManagementException
	 * @throws IOException
	 */
	public static byte[] post(String url, String content, String charset)
			throws NoSuchAlgorithmException, KeyManagementException,
			IOException {
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
				new java.security.SecureRandom());

		URL console = new URL(url);
		HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
		conn.setDoOutput(true);
		conn.connect();
		DataOutputStream out = new DataOutputStream(conn.getOutputStream());
		out.write(content.getBytes(charset));
		// 鍒锋柊銆佸叧锟�??
		out.flush();
		out.close();
		InputStream is = conn.getInputStream();
		if (is != null) {
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = is.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			is.close();
			return outStream.toByteArray();
		}
		return null;
	}
	
	
	
	public static void main(String[] args)  {
	/*	WxMpService mpService = new WxMpServiceImpl();
		WxMpInMemoryConfigStorage wxConfigProvider = new WxMpInMemoryConfigStorage();
		wxConfigProvider.setAppId("wx88c79d3ac7fa1923");
		wxConfigProvider.setSecret("583fdf63fd79f4b35a4504915e2beb8f");
		mpService.setWxMpConfigStorage(wxConfigProvider);
		WxMenu menu = new WxMenu();
		List<WxMenuButton> buttons = new ArrayList<>();
		WxMenuButton e = new WxMenuButton();
		e.setName("铚昏湏鏀粯");
		e.setType("scancode_push");
		e.setKey("qtzf");
		buttons.add(e);
	
		WxMenuButton e1 = new WxMenuButton();
		e1.setName("铚昏湏鍋滆溅");
		List<WxMenuButton> subButtons = new ArrayList<>();
		WxMenuButton e11 = new WxMenuButton();
		CodeMsg configUtil = new CodeMsg();
		e11.setName("鍋滆溅璁板綍");
		e11.setType("view");
		String redirect_uri = configUtil.getProperty("domain")+"mtc_robot/wechat/baseAuth/tcjl";
		String url = mpService.oauth2buildAuthorizationUrl(redirect_uri, "snsapi_base", null);
		System.out.println(url);
		e11.setUrl(url);
		WxMenuButton e12 = new WxMenuButton();
		e12.setName("鎴戠殑杞﹁締");
		e12.setType("view");
		redirect_uri = configUtil.getProperty("domain")+"mtc_robot/wechat/baseAuth/wdtc";
		url = mpService.oauth2buildAuthorizationUrl(redirect_uri, "snsapi_base", null);
		System.out.println(url);
		e12.setUrl(url);
		WxMenuButton e13 = new WxMenuButton();
		e13.setName("鎴戠殑閽卞寘");
		e13.setType("view");
		redirect_uri = configUtil.getProperty("domain")+"mtc_robot/wechat/baseAuth/wdqb";
		url = mpService.oauth2buildAuthorizationUrl(redirect_uri, "snsapi_base", null);
		System.out.println(url);
		e13.setUrl(url);
		WxMenuButton e14 = new WxMenuButton();
		e14.setName("铚昏湏浼氬憳");
		e14.setType("view");
		redirect_uri = configUtil.getProperty("domain")+"mtc_robot/wechat/baseAuth/qthy";
		url = mpService.oauth2buildAuthorizationUrl(redirect_uri, "snsapi_base", null);
		System.out.println(url);
		e14.setUrl(url);
		WxMenuButton e15 = new WxMenuButton();
		e15.setName("浼氬憳娉ㄥ唽");
		e15.setType("view");
		redirect_uri = configUtil.getProperty("domain")+"mtc_robot/wechat/baseAuth/zc";
		url = mpService.oauth2buildAuthorizationUrl(redirect_uri, "snsapi_base", null);
		System.out.println(url);
		e15.setUrl(url);
		subButtons.add(e11);
		subButtons.add(e12);
		subButtons.add(e13);
		subButtons.add(e14);
		subButtons.add(e15);
		e1.setSubButtons(subButtons);
		buttons.add(e1);
		
		WxMenuButton e2 = new WxMenuButton();
		e2.setName("鏇村鏈嶅姟");
		List<WxMenuButton> subButtons2 = new ArrayList<>();
		WxMenuButton e21 = new WxMenuButton();
		e21.setName("app涓嬭浇");
		e21.setType("view");
		e21.setUrl("http://wx.jitutech.com/qt_download.html");
		WxMenuButton e22 = new WxMenuButton();
		e22.setName("浼樻儬娲诲姩");
		e22.setType("view");
		e22.setUrl("http://wx.jitutech.com/qt_act.html");
		WxMenuButton e23 = new WxMenuButton();
		e23.setName("鑱旂郴瀹㈡湇");
		e23.setType("click");
		e23.setKey("lxkf");
		WxMenuButton e24 = new WxMenuButton();
		e24.setName("鍏充簬铚昏湏鍋滆溅");
		e24.setType("view");
		e24.setUrl("http://wx.jitutech.com/qt_about.html");
		subButtons2.add(e21);
		subButtons2.add(e22);
		subButtons2.add(e23);
		subButtons2.add(e24);
		e2.setSubButtons(subButtons2);
		buttons.add(e2);
		
		menu.setButtons(buttons);
		mpService.getMenuService().menuCreate(menu);*/
	}

}

