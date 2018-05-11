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
import com.example.bean.UserBean;
import com.example.bean.WordBean;
import com.example.dao.UserDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/search")
public class ApiSearchController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private WordDao wordDao;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<WordBean>> list(HttpServletRequest request) {
		String key = request.getParameter("key");
		int type = Integer.parseInt(request.getParameter("type"));
		if (type == 0) {
			return ResultUtils.resultSucceed(wordDao.findByYFH(key));
		}else {
			return ResultUtils.resultSucceed(wordDao.findByHFY(key));
		}

	}

}
