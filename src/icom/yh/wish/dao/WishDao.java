package icom.yh.wish.dao;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import icom.yh.wish.entity.OutTimeImg;
import icom.yh.wish.entity.RespWishData;
import icom.yh.wish.entity.RespWishSimpleData;
import icom.yh.wish.entity.WishData;
import icom.yh.wish.entity.WishSimpleData;
import icom.yh.wish.entity.WishTask;

@Repository
public class WishDao extends BaseDao {

	@Resource
	private SessionFactory sessionFactory;

	public WishSimpleData simpleDataByItemId(String itemId, int userId) {
		return findUnique("from WishSimpleData where itemId=? and userId=?", itemId, userId);
	}

	public List<WishSimpleData> simpleDataList() {
		return find("from WishSimpleData");
	}

	public List<WishSimpleData> simpleDataList(String type, int userId) {
		String hql = "from WishSimpleData";
		if (type.equals("add")) {
			hql = "from WishSimpleData where _add=1 and userId=?";
		} else if (type.equals("select")) {
			hql = "from WishSimpleData where _select=1 and userId=?";
		} else {
			hql = "from WishSimpleData where search=1 and userId=?";
		}
		return find(hql, userId);
	}

	public List<String> myWishItemIds(int userId) {
		List<String> list = createSQLQuery("select itemId from wish_simple_data where userId=" + userId).list();
		return list;
	}

	public boolean hasMyId(String itemId) {
		List list = find("from MyProId where itemId=?", itemId);
		if (list.size() > 0)
			return true;
		return false;
	}

	public List<String> selectWishIds(int hot, int fly, int newhot, int newfly, int newshophot, int newshopfly,
			int currentPage, int pageSize) {
		String sql = "select itemId from product_id where hot=? and fly=? and newhot=? and newfly=? and newshophot=? and newshopfly=?";
		int first = (currentPage - 1) * pageSize;
		return createSQLQuery(sql, hot, fly, newhot, newfly, newshophot, newshopfly).setFirstResult(first)
				.setMaxResults(pageSize).list();
	}

	public int simpleDataTotal(int type, int isC, int userId, int isS) {
		String sql = "select count(d.id) from wish_simple_data d "
				+ "left join (select t.itemId,t.id from out_time_img t group by t.itemId) o "
				+ "on o.itemId=d.itemId where d.userId=? and d.isC=? and d.isS=?";
		if (type == 1) {
			sql = "select count(d.id) from wish_simple_data d "
					+ "left join (select t.itemId,t.id from out_time_img t group by t.itemId) o "
					+ "on o.itemId=d.itemId where d.userId=? and d.isC=? and d.isS=? and o.id is NULL";
		} else if (type == 2) {
			sql = "select count(d.id) from wish_simple_data d "
					+ "left join (select t.itemId,t.id from out_time_img t group by t.itemId) o "
					+ "on o.itemId=d.itemId where d.userId=? and d.isC=? and d.isS=? and o.id is not NULL";
		}
		// long total = total("select count(id) from WishSimpleData where
		// userId=?", userId);
		String total = createSQLQuery(sql, userId, isC, isS).uniqueResult() + "";
		return Integer.parseInt("" + total);
	}

	public List<RespWishSimpleData> simpleItmeIds(int type, int isC, int isS, int currentPage, int pageSize,
			int userId) {
		String sql = "select d.id,d.itemId,d.isC,o.id otId from wish_simple_data d "
				+ "left join (select t.itemId,t.id from out_time_img t group by t.itemId) o "
				+ "on o.itemId=d.itemId where d.userId=? and d.isC=? and d.isS=? ";
		if (type == 1) { // 下载完成的
			sql = "select d.id,d.itemId,d.isC,o.id otId from wish_simple_data d "
					+ "left join (select t.itemId,t.id from out_time_img t group by t.itemId) o "
					+ "on o.itemId=d.itemId where d.userId=? and d.isC=? and d.isS=? and o.id is NULL";

		} else if (type == 2) { // 下载失败的
			sql = "select d.id,d.itemId,d.isC,o.id otId from wish_simple_data d "
					+ "left join (select t.itemId,t.id from out_time_img t group by t.itemId) o "
					+ "on o.itemId=d.itemId where d.userId=? and d.isC=? and d.isS=? and o.id is not NULL";
		}
		return findBySqlSplit(RespWishSimpleData.class, sql, currentPage, pageSize, userId, isC, isS);
		// return findSplit("from WishSimpleData where userId=?", currentPage,
		// pageSize, userId);
	}

	public int selectWishTotal(int hot, int fly, int newhot, int newfly, int newshophot, int newshopfly) {
		long t = total(
				"select count(id) from ProductId where hot=? and fly=? and newhot=? and newfly=? and newshophot=? and newshopfly=?",
				hot, fly, newhot, newfly, newshophot, newshopfly);
		return Integer.parseInt(t + "");
	}

	public WishSimpleData get(int id, int userId) {
		return findUnique("from WishSimpleData where id=? and userId=?", id, userId);
	}

	public List<RespWishData> myWishDataList(String itemId) {
		String sql = "select d.id,p.ParentSKU,p.SKU,p.name,p._desc,p.label,p.merahant_tags,p.brand,p.UPC,p.landingPageUrl,"
				+ "p.msrp,p.color,p.size,p.url,p.price,p.freight,p.stock,p.minTime,p.maxTime,p.mainImgUrl,p.changeImgUrl,"
				+ "p.imgUrl1,imgUrl2,imgUrl3,imgUrl4,imgUrl5,imgUrl6,imgUrl7,imgUrl8,imgUrl9,imgUrl10,"
				+ "p.imgUrl11,p.imgUrl12,imgUrl13,imgUrl14,imgUrl15,imgUrl16,imgUrl17,imgUrl18,imgUrl19,"
				+ "p.imgUrl20,p.collNum,p.saleNum,p.status,p.merchant,p.proId,p.sjtime from "
				+ "wish_product p,wish_simple_data d where d.itemId=? and d.itemId=p.itemId";
		List<RespWishData> list = createSQLQuery(sql, itemId)
				.setResultTransformer(Transformers.aliasToBean(RespWishData.class)).list();
		return list;
	}

	public List<RespWishData> myWishDataList(int userId, int isC) {
		String sql = "select d.id,p.ParentSKU,d.itemId,p.SKU,p.name,p._desc,p.label,p.merahant_tags,p.brand,p.UPC,p.landingPageUrl,"
				+ "p.msrp,p.color,p.size,p.url,p.price,p.freight,p.stock,p.minTime,p.maxTime,p.mainImgUrl,p.changeImgUrl,"
				+ "p.imgUrl1,imgUrl2,imgUrl3,imgUrl4,imgUrl5,imgUrl6,imgUrl7,imgUrl8,imgUrl9,imgUrl10,"
				+ "p.imgUrl11,p.imgUrl12,imgUrl13,imgUrl14,imgUrl15,imgUrl16,imgUrl17,imgUrl18,imgUrl19,"
				+ "p.imgUrl20,p.collNum,p.saleNum,p.status,p.merchant,p.proId,p.sjtime from "
				+ "wish_product p,wish_simple_data d where d.userId=? and d.itemId=p.itemId and d.isC=? and d.isS=0";
		List<RespWishData> list = createSQLQuery(sql, userId, isC)
				.setResultTransformer(Transformers.aliasToBean(RespWishData.class)).list();
		return list;
	}

	public WishSimpleData get(String itemId, int userId) {
		return findUnique("from WishSimpleData where userId=? and itemId=?", userId, itemId);
	}

	public List<WishData> isHasItemIdList(String itemId) {
		return find("from WishData where itemId=?", itemId);
	}

	public int simpleDataCall(int userId) {
		return createSQLQuery("update wish_simple_data set isC=1 where userId=?", userId).executeUpdate();
	}

	public int simpleDataDelAll(int userId) {
		return createSQLQuery("delete from wish_simple_data where isC=0 and userId=?", userId).executeUpdate();
	}

	public List<WishData> WishDataList(int userId) {
		return find("from WishData where userId=?", userId);
	}

	public List<WishData> wishDataList(String itemId) {
		return find("from WishData where itemId=?", itemId);
	}

	public List<String> allMyItemIds(int userId) {
		List<String> list = createSQLQuery("select itemId from wish_simple_data where userId=?", userId).list();
		return list;
	}

	public OutTimeImg isHasOutTimeImg(OutTimeImg outTimeImg) {
		return findUnique("from OutTimeImg where itemId=? and img=?", outTimeImg.getItemId(), outTimeImg.getImg());
	}

	public void taskComplete(String taskId) {
		createQuery("update WishTask set isS=1 where taskId=?", taskId).executeUpdate();
	}

	public List<WishTask> taskList(int userId, int currentPage, int pageSize) {
		return findSplit("from WishTask where userId=?", currentPage, pageSize, userId);
	}

	public int taskTotal(int userId) {
		long t = total("select count(id) from WishTask where userId=?", userId);
		return Integer.parseInt("" + t);
	}

	public List<RespWishSimpleData> simpleItmeIds(int taskId, int currentPage, int pageSize) {
		String sql = "select d.id,d.itemId,d.isC from wish_simple_data d,wish_task_link t where t.taskId=? and t.sdId=d.id and d.isS=1";
		return findBySqlSplit(RespWishSimpleData.class, sql, currentPage, pageSize, taskId);
	}

	public int simpleDataTotal(int taskId) {
		String sql = "select count(id) from wish_task_link where taskId=?";
		String total = createSQLQuery(sql, taskId).uniqueResult() + "";
		return Integer.parseInt("" + total);
	}

	public void taskLinkDel(int sdId, int taskId) {
		createQuery("delete from WishTaskLink where sdId=? and taskId=?", sdId, taskId).executeUpdate();
	}

}