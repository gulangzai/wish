package icom.yh.wish.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import icom.yh.wish.entity.ChicmeProduct;

@Repository
public class ChicmeDao extends BaseDao{

	public boolean has(String itemId) {
		List<ChicmeProduct> list = find("from ChicmeProduct where itemId=?", itemId);
		if(list.size()>0)
			return true;
		return false;
	}
	
	public List<ChicmeProduct> chicmeList(String category){
		return find("from ChicmeProduct where subCategory=?", category);
	}

}
