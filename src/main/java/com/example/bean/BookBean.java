package com.example.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
@Entity
public class BookBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private int likeSum;
	@Transient
	private List<BookInfoBean> bookInfoBeans;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getLikeSum() {
		return likeSum;
	}
	public void setLikeSum(int likeSum) {
		this.likeSum = likeSum;
	}
	public List<BookInfoBean> getBookInfoBeans() {
		return bookInfoBeans;
	}
	public void setBookInfoBeans(List<BookInfoBean> bookInfoBeans) {
		this.bookInfoBeans = bookInfoBeans;
	}
	
}
