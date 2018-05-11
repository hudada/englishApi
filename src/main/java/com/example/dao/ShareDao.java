package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.ShareBean;
import com.example.bean.UserBean;

public interface ShareDao extends JpaRepository<ShareBean, Long> {

    @Query("from ShareBean b where b.uid=:uid and b.vid=:vid")
    ShareBean findById(@Param("uid") Long uid,
    		@Param("vid") Long vid);
    
    @Query("from ShareBean b where b.uid=:uid")
    List<ShareBean> findByUId(@Param("uid") Long uid);
}
