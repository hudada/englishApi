package com.example.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
@Entity
public class WordBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String wordEn;
	private String speek;  //音标
	private String wordCh;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWordEn() {
		return wordEn;
	}
	public void setWordEn(String wordEn) {
		this.wordEn = wordEn;
	}
	public String getSpeek() {
		return speek;
	}
	public void setSpeek(String speek) {
		this.speek = speek;
	}
	public String getWordCh() {
		return wordCh;
	}
	public void setWordCh(String wordCh) {
		this.wordCh = wordCh;
	}
	
}
