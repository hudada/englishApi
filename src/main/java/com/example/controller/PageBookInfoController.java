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
@RequestMapping(value = "/page/bookinfo")
public class PageBookInfoController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookInfoDao bookInfoDao;

	@RequestMapping(value = "/detele/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<BookInfoBean> detele(@PathVariable String id) {
		bookInfoDao.delete(Long.parseLong(id));
		return ResultUtils.resultSucceed("");
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<BookInfoBean> edit(@RequestBody BookInfoBean bean) {
		bean.setBid(bookInfoDao.findOne(bean.getId()).getBid());
		bookInfoDao.save(bean);
		return ResultUtils.resultSucceed("");
	}
}
