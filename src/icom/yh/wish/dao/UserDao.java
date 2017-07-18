package icom.yh.wish.dao;

import org.springframework.stereotype.Repository;

import icom.yh.wish.entity.User;

@Repository
public class UserDao extends BaseDao{

	public User userByAccount(String account) {
		return findUnique("from User where account=?", account);
	}

}
