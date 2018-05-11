package com.example.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.annotations.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.example.dao.AdminDao;
import com.example.dao.BookDao;
import com.example.dao.BookInfoDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.example.WebSecurityConfig;

@Controller
@RequestMapping(value = "/page/book")
public class PageBookController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookInfoDao bookInfoDao;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseBean<BookBean> add(HttpServletRequest request) {
		String title = request.getParameter("title");
		String info = request.getParameter("info");
		BookBean bookBean = new BookBean();
		bookBean.setLikeSum(0);
		bookBean.setTitle(title);
		long bid = bookDao.save(bookBean).getId();
		String[] infos= info.split(",");
		for (String string : infos) {
			String[] params = string.split("-");
			BookInfoBean bean = new BookInfoBean();
			bean.setBid(bid);
			try {
				bean.setMsg(params[0]);
			}catch (Exception e) {
				bean.setMsg("");
			}
			try {
				bean.setInfo(params[1]);
			}catch (Exception e) {
				bean.setInfo("");
			}
			
			bookInfoDao.save(bean);
		}
		return ResultUtils.resultSucceed("");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public BaseBean<BookBean> edit(HttpServletRequest request) {
		String title = request.getParameter("title");
		String info = request.getParameter("info");
		long id = Long.parseLong(request.getParameter("id"));
		BookBean bookBean = bookDao.findOne(id);
		bookBean.setLikeSum(0);
		bookBean.setTitle(title);
		long bid = bookDao.save(bookBean).getId();
		List<BookInfoBean> list = bookInfoDao.findByBid(bid);
		for (BookInfoBean bookInfoBean : list) {
			bookInfoDao.delete(bookInfoBean);
		}
		String[] infos= info.split(",");
		for (String string : infos) {
			String[] params = string.split("-");
			BookInfoBean bean = new BookInfoBean();
			bean.setBid(bid);
			try {
				bean.setMsg(params[0]);
			}catch (Exception e) {
				bean.setMsg("");
			}
			try {
				bean.setInfo(params[1]);
			}catch (Exception e) {
				bean.setInfo("");
			}
			
			bookInfoDao.save(bean);
		}
		return ResultUtils.resultSucceed("");
	}
	
	@RequestMapping(value = "/detele/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<BookBean> detele(@PathVariable String id) {
		List<BookInfoBean> list = bookInfoDao.findByBid(Long.parseLong(id));
		for (BookInfoBean bookInfoBean : list) {
			bookInfoDao.delete(bookInfoBean);
		}
		bookDao.delete(Long.parseLong(id));
		return ResultUtils.resultSucceed("");
	}
}
