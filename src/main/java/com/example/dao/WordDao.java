package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.UserBean;
import com.example.bean.WordBean;

public interface WordDao extends JpaRepository<WordBean, Long> {

    @Query("from WordBean b where b.wordEn like %:wordEn%")
    List<WordBean> findByYFH(@Param("wordEn") String wordEn);
    
    @Query("from WordBean b where b.wordCh like %:wordCh%")
    List<WordBean> findByHFY(@Param("wordCh") String wordCh);
}
