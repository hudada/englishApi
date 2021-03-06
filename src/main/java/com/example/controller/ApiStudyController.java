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
import com.example.bean.StudyBean;
import com.example.bean.UserBean;
import com.example.bean.WordBean;
import com.example.dao.BookDao;
import com.example.dao.BookInfoDao;
import com.example.dao.StudyDao;
import com.example.dao.UserDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/study")
public class ApiStudyController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private WordDao wordDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookInfoDao bookInfoDao;
	@Autowired
	private StudyDao studyDao;
	@Value("${bs.imagesPath}")
	private String location;
	@Autowired
	private ResourceLoader resourceLoader;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<StudyBean>> list(HttpServletRequest request) {
		List<StudyBean> list = studyDao.findAll();
		return ResultUtils.resultSucceed(list);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/study/{filename:.+}")
	public ResponseEntity<?> getFile(@PathVariable String filename) {
		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(location, filename).toString()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
