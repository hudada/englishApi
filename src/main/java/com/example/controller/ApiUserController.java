package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

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
import com.example.bean.UserBean;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/user")
public class ApiUserController {

	@Autowired
	private UserDao userDao;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public BaseBean<UserBean> add(HttpServletRequest request) {
		UserBean userBean = new UserBean();
		userBean.setUserName(request.getParameter("name"));
		userBean.setPwd(request.getParameter("pwd"));
		if (userDao.findUserByUserName(userBean.getUserName()) == null) {
			return ResultUtils.resultSucceed(userDao.save(userBean));
		} else {
			return ResultUtils.resultError("该账号已存在！");
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public BaseBean<UserBean> userLogin(HttpServletRequest request) {
		UserBean userBean = new UserBean();
		userBean.setUserName(request.getParameter("name"));
		userBean.setPwd(request.getParameter("pwd"));
		UserBean select = userDao.findUserByUserNameAndPwd(userBean.getUserName(), userBean.getPwd());
		if (select == null) {
			return ResultUtils.resultError("账号或密码错误");
		} else {
			return ResultUtils.resultSucceed(select);
		}
	}

	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public BaseBean<UserBean> change(HttpServletRequest request) {
		UserBean userBean = userDao.findOne(Long.parseLong(request.getParameter("id")));
		userBean.setName(request.getParameter("nic"));
		userBean.setAge(Integer.parseInt(request.getParameter("age")));
		return ResultUtils.resultSucceed(userDao.save(userBean));
	}
}
