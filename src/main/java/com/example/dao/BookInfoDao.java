package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.BookInfoBean;
import com.example.bean.UserBean;

public interface BookInfoDao extends JpaRepository<BookInfoBean, Long> {

    @Query("from BookInfoBean b where b.bid=:bid")
    List<BookInfoBean> findByBid(@Param("bid") Long bid);
    
}
