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
import com.example.bean.StudyBean;
import com.example.bean.UserBean;
import com.example.bean.VoiceBean;
import com.example.bean.WordBean;
import com.example.dao.BookDao;
import com.example.dao.UserDao;
import com.example.dao.VoiceDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/voice")
public class ApiVoiceController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private WordDao wordDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private VoiceDao voiceDao;
	@Value("${bs.imagesPath}")
	private String location;
	@Autowired
	private ResourceLoader resourceLoader;

	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public BaseBean<List<VoiceBean>> list(HttpServletRequest request) {
		List<VoiceBean> list = voiceDao.findAll();
		for (VoiceBean voiceBean : list) {
			voiceBean.setName(userDao.findOne(voiceBean.getUid()).getUserName());
		}
		return ResultUtils.resultSucceed(list);
	}
	
	@RequestMapping(value = "/mylist", method = RequestMethod.POST)
	public BaseBean<List<VoiceBean>> mylist(HttpServletRequest request) {
		long uid = Long.parseLong(request.getParameter("uid"));
		List<VoiceBean> list = voiceDao.findByUid(uid);
		for (VoiceBean voiceBean : list) {
			voiceBean.setName(userDao.findOne(voiceBean.getUid()).getUserName());
		}
		return ResultUtils.resultSucceed(list);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseBean<VoiceBean> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String title = request.getParameter("title");
		long uid = Long.parseLong(request.getParameter("uid"));
		long size = Long.parseLong(request.getParameter("size"));
		if (!file.isEmpty()) {
			try {
				String path = "uid_" + System.currentTimeMillis() + "." + file.getOriginalFilename().split("\\.")[1];
				
				File root = new File(location);
		        if (!root.exists()) {
		        	root.mkdirs();
		        }
				
				
				Files.copy(file.getInputStream(), Paths.get(location, path));

				VoiceBean bean = new VoiceBean();
				bean.setTitle(title);
				bean.setPath("/voice/" + path);
				bean.setTime(new Date().getTime());
				bean.setUid(uid);
				return ResultUtils.resultSucceed(voiceDao.save(bean));
			} catch (IOException | RuntimeException e) {
				return ResultUtils.resultError("");
			}
		} else {
			return ResultUtils.resultError("");
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/voice/{filename:.+}")
	public ResponseEntity<?> getFile(@PathVariable String filename) {
		try {
			return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(location, filename).toString()));
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
}
