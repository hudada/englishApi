package com.example.controller;

import java.io.File;
import java.io.FileOutputStream;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.bean.BaseBean;
import com.example.bean.BookInfoBean;
import com.example.bean.StudyBean;
import com.example.bean.UserBean;
import com.example.dao.StudyDao;
import com.example.dao.UserDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/page/study")
public class PageStudyController {

	@Value("${bs.imagesPath}")
	private String location;
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	private StudyDao studyDao;

	@RequestMapping(value = "/detele/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<StudyBean> detele(@PathVariable String id) {
		studyDao.delete(Long.parseLong(id));
		return ResultUtils.resultSucceed("");
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public BaseBean<StudyBean> uploadImg(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String name = request.getParameter("name");
		if (!file.isEmpty()) {
			try {
				String path = "study_" + System.currentTimeMillis() + "." + file.getOriginalFilename().split("\\.")[1];
				
				File root = new File(location);
		        if (!root.exists()) {
		        	root.mkdirs();
		        }
				
				
				Files.copy(file.getInputStream(), Paths.get(location, path));

				StudyBean bean = new StudyBean();
				bean.setName(name);
				bean.setPath("/study/" + path);
				return ResultUtils.resultSucceed(studyDao.save(bean));
			} catch (IOException | RuntimeException e) {
				return ResultUtils.resultError("");
			}
		} else {
			return ResultUtils.resultError("文件内容为空");
		}
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
