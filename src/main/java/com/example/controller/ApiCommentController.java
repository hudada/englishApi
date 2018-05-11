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
import com.example.bean.ComentBean;
import com.example.bean.UserBean;
import com.example.bean.VoiceBean;
import com.example.bean.WordBean;
import com.example.dao.BookDao;
import com.example.dao.ComentDao;
import com.example.dao.UserDao;
import com.example.dao.VoiceDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/comment")
public class ApiCommentController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private WordDao wordDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private VoiceDao voiceDao;
	@Autowired
	private ComentDao comentDao;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<ComentBean>> list(HttpServletRequest request) {
		Long id = Long.parseLong(request.getParameter("id"));
		int type = Integer.parseInt(request.getParameter("type"));
		List<ComentBean> list = comentDao.findById(id, type);
		for (ComentBean comentBean : list) {
			comentBean.setUserBean(userDao.findOne(comentBean.getUid()));
		}
		return ResultUtils.resultSucceed(list);
	}

	@RequestMapping(value = "/mylist", method = RequestMethod.POST)
	public BaseBean<List<ComentBean>> mylist(HttpServletRequest request) {
		Long id = Long.parseLong(request.getParameter("id"));
		List<ComentBean> list = comentDao.findById(id);
		for (ComentBean comentBean : list) {
			if (comentBean.getType() == 0) {
				comentBean.setBname(bookDao.findOne(comentBean.getMid()).getTitle());
			} else {
				comentBean.setVname(voiceDao.findOne(comentBean.getMid()).getTitle());
			}
		}
		return ResultUtils.resultSucceed(list);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseBean<ComentBean> add(HttpServletRequest request) {
		Long id = Long.parseLong(request.getParameter("id"));
		Long uid = Long.parseLong(request.getParameter("uid"));
		int type = Integer.parseInt(request.getParameter("type"));
		String msg = request.getParameter("msg");
		ComentBean bean = new ComentBean();
		bean.setMid(id);
		bean.setMsg(msg);
		bean.setTime(new Date().getTime());
		bean.setType(type);
		bean.setUid(uid);
		return ResultUtils.resultSucceed(comentDao.save(bean));
	}
}
