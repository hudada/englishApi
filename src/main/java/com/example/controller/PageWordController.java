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
import com.example.bean.WordBean;
import com.example.dao.AdminDao;
import com.example.dao.BookDao;
import com.example.dao.BookInfoDao;
import com.example.dao.UserDao;
import com.example.dao.WordDao;
import com.example.utils.ResultUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.example.WebSecurityConfig;

@Controller
@RequestMapping(value = "/page/word")
public class PageWordController {

	@Autowired
	private UserDao userDao;
	@Autowired
	private BookDao bookDao;
	@Autowired
	private BookInfoDao bookInfoDao;
	@Autowired
	private WordDao wordDao;

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public BaseBean<WordBean> add(HttpServletRequest request) {
		String word = request.getParameter("word");
		String speek = request.getParameter("speek");
		String cn = request.getParameter("cn");
		WordBean wordBean = new WordBean();
		wordBean.setSpeek(speek);
		wordBean.setWordCh(cn);
		wordBean.setWordEn(word);
		return ResultUtils.resultSucceed(wordDao.save(wordBean));
	}
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	@ResponseBody
	public BaseBean<WordBean> edit(HttpServletRequest request) {
		String word = request.getParameter("word");
		String speek = request.getParameter("speek");
		String cn = request.getParameter("cn");
		WordBean wordBean = wordDao.findOne(Long.parseLong(request.getParameter("id")));
		wordBean.setSpeek(speek);
		wordBean.setWordCh(cn);
		wordBean.setWordEn(word);
		return ResultUtils.resultSucceed(wordDao.save(wordBean));
	}
	
	@RequestMapping(value = "/detele/{id}", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public BaseBean<WordBean> detele(@PathVariable String id) {
		wordDao.delete(Long.parseLong(id));
		return ResultUtils.resultSucceed("");
	}
}
