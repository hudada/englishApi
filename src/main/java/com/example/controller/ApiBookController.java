package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bean.BaseBean;
import com.example.bean.BookBean;
import com.example.bean.BookInfoBean;
import com.example.bean.UserBean;
import com.example.bean.WordBean;
import com.example.dao.BookDao;
import com.example.dao.BookInfoDao;
import com.example.dao.UserDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/book")
public class ApiBookController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private WordDao wordDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookInfoDao bookInfoDao;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<BookBean>> list(HttpServletRequest request) {
		List<BookBean> list = bookDao.findAll();
		for (BookBean bookBean : list) {
			List<BookInfoBean> infoBeans = bookInfoDao.findByBid(bookBean.getId());
			bookBean.setBookInfoBeans(infoBeans);
		}
		return ResultUtils.resultSucceed(list);
	}

}
