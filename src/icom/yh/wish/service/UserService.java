package icom.yh.wish.service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import icom.yh.wish.dao.UserDao;
import icom.yh.wish.entity.User;
import icom.yh.wish.util.EncrypMD5;

@Service
public class UserService {
	
	@Resource
	private UserDao userDao;

	public String login(String account, String password, Model model, HttpSession session) {
		User user = userDao.userByAccount(account);
		String view = "login";
		user = new User();
		user.setAccount("sea");
		user.setId(1);
		user.setPassword("123456");
		if(null == user){
			model.addAttribute("msg", "账号错误!");
		}else{
			password = EncrypMD5.getMD5ofStr(password).toLowerCase();
			System.out.println(password);
			//if(password.equalsIgnoreCase(user.getPassword())){
				//HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
				session.setAttribute("userId", user.getId());
				session.setAttribute("account", user.getAccount());;
				view = "redirect:/wish/wishDataList";
			//}else{
				//model.addAttribute("msg", "密码错误!");
			//}
		}
		return view;
	}
	
	public int sessionUserId(){
		HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		return (int) session.getAttribute("userId");
	}

	public String sessionAccount() {
		HttpSession session = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getSession();
		return session.getAttribute("account")+"";
	}
}
