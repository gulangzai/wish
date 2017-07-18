package test;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import icom.yh.wish.entity.ProductId;
import icom.yh.wish.util.JsoupUtil;

public class WishDataM {

	public static void main(String[] args) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");// 读取bean.xml中的内容
		SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		Session session = sessionFactory.openSession();
		//从第一条到1000tiao
		int i = 5555;
		List<String> itemIds = productIds(i, 5000, session);
	//	System.out.println(itemIds);
		for (String itemId : itemIds) {
			/*List<WishData> dataList;
			dataList = JsoupUtil.wishData(i, itemId);
			i++;
			list.addAll(dataList);*/
		//	System.out.println(Thread.currentThread().getName() + "  " + Thread.currentThread().getId());
		//	JsoupUtil.fixedThreadPool.submit(new JsoupUtil.MyRannable(i, productId, session));
			JsoupUtil.catchData(i, itemId, session);
			i++;
		}
	}
	
	public static List<String> productIds(int first,int num, Session session){
		List<String> list = session.createSQLQuery("select itemId from product_id where isC=0").setFirstResult(first).setMaxResults(num).list();
		return list;
	}
}
