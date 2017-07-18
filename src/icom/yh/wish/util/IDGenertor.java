package icom.yh.wish.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class IDGenertor {
	public static String newToken(String userid) {
		return "id-" + userid + "@n-" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + "@u-"
				+ UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static int getUserID(String token) {
		String[] arr = token.split("@");
		if (token.equals(""))
			return 0;
		if (arr.length != 3) {
			return 0;
		}
		String s = "";
		if (arr[0].split("-").length > 1) {
			s = arr[0].split("-")[1];
		} else
			return 0;
		return Integer.parseInt(s);
	}

	public static String randomCode4() {
		Random random = new Random();
		return random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9);
	}

	public static String randomCode6() {
		Random random = new Random();
		return random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9) + "" + random.nextInt(9) + ""
				+ random.nextInt(9) + "" + random.nextInt(9);
	}

	// 订单交易验证码
	public static String getCode() {
		return new SimpleDateFormat("yyMMddHHmmss").format(new Date())
				+ UUID.randomUUID().toString().replaceAll("[a-zA-Z]", "").replaceAll("-", "");
	}

	public static String getOrderId() {
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) // 有可能是负数
			hashCodeV = -hashCodeV;
		return randomCode4() + String.format("%012d", hashCodeV);
	}

	public static void main(String[] args) throws Exception {
		/*
		 * Random random = new Random(); System.out.println(); for(int
		 * i=0;i<10000;i++){ System.out.println(randomCode()); }
		 */
		System.out.println(newToken("12"));
	}
}
