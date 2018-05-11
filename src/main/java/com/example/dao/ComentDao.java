package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.ComentBean;
import com.example.bean.UserBean;

public interface ComentDao extends JpaRepository<ComentBean, Long> {

    @Query("from ComentBean b where b.mid=:mid and b.type=:type")
    List<ComentBean> findById(@Param("mid") Long mid,
    		@Param("type") int type);
    
    @Query("from ComentBean b where b.uid=:uid")
    List<ComentBean> findById(@Param("uid") Long uid);
    
}
