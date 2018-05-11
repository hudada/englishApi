package com.example.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.bean.AdminBean;
import com.example.bean.BaseBean;
import com.example.bean.BookBean;
import com.example.bean.BookInfoBean;
import com.example.bean.UserBean;
import com.example.bean.WordBean;
import com.example.dao.AdminDao;
import com.example.dao.BookDao;
import com.example.dao.BookInfoDao;
import com.example.dao.StudyDao;
import com.example.dao.UserDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.spring.web.json.Json;

import com.example.WebSecurityConfig;

@Controller
public class PageLoginController {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookInfoDao bookInfoDao;
	@Autowired
	private StudyDao studyDao;
	@Autowired
	private WordDao wordDao;

	// 返回登录页面
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(ModelMap map) {
		map.addAttribute("title","后台管理");
		return "newlogin";
	}

	// 登录接口
	@RequestMapping(value = "/dologin", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<String> userLogin(@RequestBody AdminBean adminBean, HttpSession session) {
		AdminBean admin = adminDao.findByNameAndPwd(adminBean.getUserName(), adminBean.getPwd());
		if (admin != null) {
			session.setAttribute(WebSecurityConfig.SESSION_KEY, adminBean);
			return ResultUtils.resultSucceed("登陆成功");
		} else {
			return ResultUtils.resultError("账号或密码错误");
		}
	}

	// 退出登录接口
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String loginOut(HttpSession session) {
		session.removeAttribute(WebSecurityConfig.SESSION_KEY);
		return "redirect:/login";
	}

	private ModelMap getPub(ModelMap map, HttpSession session,int position) {
		AdminBean admin = (AdminBean) session.getAttribute(WebSecurityConfig.SESSION_KEY);
		map.addAttribute("name", admin.getUserName());
		map.addAttribute("title","后台管理");
		map.addAttribute("left",""
				+ "<li>"
					+ "<a href=\"#\">单词</a>"	
				+ "</li>"
				+ "<li "+isActive(5,position)+">"
					+ "<a href=\"/wordAdd\">"
					+ "<i class=\"icon-chevron-right\"></i>新增单词</a>"
				+ "</li>"
				+ "<li "+isActive(6,position)+">"
					+ "<a href=\"/wordManager\">"
					+ "<i class=\"icon-chevron-right\"></i>单词列表</a>"
				+ "</li>"
				+ "<li>"
					+ "<a href=\"#\">文章</a>"	
				+ "</li>"
				+ "<li "+isActive(1,position)+">"
					+ "<a href=\"/bookAdd\">"
					+ "<i class=\"icon-chevron-right\"></i>新增文章</a>"
				+ "</li>"
				+ "<li "+isActive(2,position)+">"
					+ "<a href=\"/bookManager\">"
					+ "<i class=\"icon-chevron-right\"></i>文章列表</a>"
				+ "</li>"
				+ "<li>"
					+ "<a href=\"#\">学习资料</a>"	
				+ "</li>"
				+ "<li "+isActive(3,position)+">"
					+ "<a href=\"/studyAdd\">"
					+ "<i class=\"icon-chevron-right\"></i>新增资料</a>"
				+ "</li>"
				+ "<li "+isActive(4,position)+">"
					+ "<a href=\"/studyManager\">"
					+ "<i class=\"icon-chevron-right\"></i>资料列表</a>"
				+ "</li>");
		return map;
	}

	private String isActive(int curr,int position) {
		return position==curr?"class=\"active\"":"";
	}

	// 返回管理首页
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(ModelMap map, HttpSession session) {
		getPub(map, session,0);
		return "newindex";
	}

	// ----------------------------------------------------------------
	@RequestMapping(value = "/bookManager", method = RequestMethod.GET)
	public String bookManager(ModelMap map, HttpSession session) {
		getPub(map, session,2);
		List<BookBean> list = bookDao.findAll();
		map.addAttribute("list", list);
		return "book/newtable";
	}
	
	@RequestMapping(value = "/bookAdd", method = RequestMethod.GET)
	public String bookAdd(ModelMap map, HttpSession session) {
		getPub(map, session,1);
		return "book/addform";
	}
	
	@RequestMapping(value = "/bookEdit/{id}", method = RequestMethod.GET)
	public String nameEdit(@PathVariable String id,
			ModelMap map, HttpSession session) {
		getPub(map, session,2);
		map.addAttribute("data", bookDao.findOne(Long.parseLong(id)));
		List<BookInfoBean> list = bookInfoDao.findByBid(Long.parseLong(id));
		map.addAttribute("list", list);
		map.addAttribute("total", list.size());
		return "book/editform";
	}
	
	@RequestMapping(value = "/infoManager/{id}", method = RequestMethod.GET)
	public String infoManager(@PathVariable String id,
			ModelMap map, HttpSession session) {
		getPub(map, session,2);
		List<BookInfoBean> list = bookInfoDao.findByBid(Long.parseLong(id));
		String title = bookDao.findOne(Long.parseLong(id)).getTitle();
		map.addAttribute("list", list);
		map.addAttribute("title",title);
		return "info/newtable";
	}
	
	@RequestMapping(value = "/infoEdit/{id}", method = RequestMethod.GET)
	public String infoEdit(@PathVariable String id,
			ModelMap map, HttpSession session) {
		getPub(map, session,2);
		BookInfoBean list = bookInfoDao.findOne(Long.parseLong(id));
		
		map.addAttribute("data", list);
		
		return "info/editform";
	}

	
	@RequestMapping(value = "/studyManager", method = RequestMethod.GET)
	public String studyManager(ModelMap map, HttpSession session) {
		getPub(map, session,4);
		map.addAttribute("list", studyDao.findAll());
		return "study/newtable";
	}
	
	@RequestMapping(value = "/studyAdd", method = RequestMethod.GET)
	public String studyAdd(ModelMap map, HttpSession session) {
		getPub(map, session,3);
		return "study/addform";
	}
	
	@RequestMapping(value = "/wordManager", method = RequestMethod.GET)
	public String wordManager(ModelMap map, HttpSession session) {
		getPub(map, session,6);
		List<WordBean> list = wordDao.findAll();
		map.addAttribute("list", list);
		return "word/newtable";
	}
	
	@RequestMapping(value = "/wordAdd", method = RequestMethod.GET)
	public String wordAdd(ModelMap map, HttpSession session) {
		getPub(map, session,5);
		return "word/addform";
	}
	
	@RequestMapping(value = "/wordEdit/{id}", method = RequestMethod.GET)
	public String wordEdit(@PathVariable String id,
			ModelMap map, HttpSession session) {
		getPub(map, session,6);
		map.addAttribute("data", wordDao.findOne(Long.parseLong(id)));
		return "word/editform";
	}
	
}
