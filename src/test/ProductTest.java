package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.Random;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import icom.yh.wish.dao.TbProductDao;
import icom.yh.wish.dao.WishDao;
import icom.yh.wish.entity.TbProduct;
import icom.yh.wish.entity.WishData;
import icom.yh.wish.entity.WishSimpleData;
import icom.yh.wish.service.WishService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:beans.xml"})
@TransactionConfiguration(transactionManager="txManager" , defaultRollback=false)
@Transactional
public class ProductTest {
	 
	@Autowired
	WishService wishService;
	@Autowired
	TbProductDao tbProductDao;
	@Autowired
	WishDao wishDao;
	
	private static void imgGet(File dir, String imgName, String imageUrl) {
		System.out.println(dir.getPath());
		if (!dir.exists()) {
			dir.mkdirs();
			System.out.println("ͼƬ�����Ŀ¼��");
		}
		if (!new File(dir.getPath() + "/" + imgName).exists()) {
			try {
				OutputStream os = new FileOutputStream(dir.getPath() + "/" + imgName); //
				URL url = new URL(imageUrl);
				InputStream is = url.openStream();
				byte[] buff = new byte[1024];
				int len=0;
				while ((len = is.read(buff))!=-1) {  
					//byte[] temp = new byte[readed];
					//System.arraycopy(buff, 0, temp, 0, readed); // д���ļ�
					os.write(buff,0,len);
				}
				is.close();
				os.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Test
	public void testGetProductByClass(){
		String clazz = "top";
	/* ApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");// ��ȡbean.xml�е�����
		SessionFactory sessionFactory = (SessionFactory) ctx.getBean("sessionFactory");
		sessionFactory.openSession();
		  wishService = (WishService) ctx.getBean("wishService");
		  tbProductDao = (TbProductDao) ctx.getBean("tbProductDao");
	 */
	   for (int i = 1; i <=1; i++) {
		  List<WishSimpleData> wishSimpleDatas =  wishService.wishDataList(i, 24,clazz);
	      
		  if(wishSimpleDatas.size()!=0){
			  WishSimpleData wishSimpleData  = wishSimpleDatas.get(0);
			  WishSimpleData swishSimpleData = wishDao.simpleDataByItemId(wishSimpleData.getItemId(), 0);
			  if(swishSimpleData==null){
				  List<WishData>  list = wishService.wishData(Integer.parseInt(""+new Random().nextInt(10)), wishSimpleData.getItemId()); 
				  Serializable id = wishDao.save(wishSimpleData);
				  for (WishData data : list) {
						wishDao.save(data); 
						TbProduct tbProduct = new TbProduct(data);
						tbProductDao.save(tbProduct);
					} 
			  }else{
				  System.out.println(swishSimpleData.getItemId()+"已存在");
			  } 
		  }
		  
		  /*for (WishSimpleData wishSimpleData : wishSimpleDatas) {
			  List<WishData>  list = wishService.wishData(Integer.parseInt(""+new Random().nextInt(10)), wishSimpleData.getItemId()); 
				for (WishData data : list) { 
					TbProduct tbProduct = new TbProduct(data);
					tbProductDao.save(tbProduct);
				} 
		  } */ 
	   }
	} 
}















