package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
import com.example.bean.ShareBean;
import com.example.bean.UserBean;
import com.example.bean.WordBean;
import com.example.dao.BookDao;
import com.example.dao.BookInfoDao;
import com.example.dao.ShareDao;
import com.example.dao.UserDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/like")
public class ApiLikeController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private WordDao wordDao;
	@Autowired
	private ShareDao shareDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookInfoDao bookInfoDao;

	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public BaseBean<ShareBean> check(HttpServletRequest request) {
		Long uid = Long.parseLong(request.getParameter("uid"));
		Long bid = Long.parseLong(request.getParameter("bid"));

		return ResultUtils.resultSucceed(shareDao.findById(uid, bid));

	}

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<BookBean>> list(HttpServletRequest request) {
		Long uid = Long.parseLong(request.getParameter("uid"));
		List<ShareBean> list = shareDao.findByUId(uid);
		List<BookBean> list2 = new ArrayList<>();
		for (ShareBean shareBean : list) {
			BookBean bean = bookDao.findOne(shareBean.getVid());
			bean.setBookInfoBeans(bookInfoDao.findByBid(bean.getId()));
			list2.add(bean);
		}
		return ResultUtils.resultSucceed(list2);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseBean<ShareBean> add(HttpServletRequest request) {
		Long uid = Long.parseLong(request.getParameter("uid"));
		Long bid = Long.parseLong(request.getParameter("bid"));
		ShareBean bean = shareDao.findById(uid, bid);
		if (bean == null) {
			ShareBean bean1 = new ShareBean();
			bean1.setUid(uid);
			bean1.setVid(bid);
			BookBean bean2 = bookDao.findOne(bid);
			bean2.setLikeSum(bean2.getLikeSum() + 1);
			bookDao.save(bean2);
			return ResultUtils.resultSucceed(shareDao.save(bean1));
		} else {
			shareDao.delete(bean);
			BookBean bean2 = bookDao.findOne(bid);
			bean2.setLikeSum(bean2.getLikeSum() - 1);
			bookDao.save(bean2);
			return ResultUtils.resultSucceed("");
		}

	}
}
