package com.example.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.UserBean;
import com.example.bean.VoiceBean;

public interface VoiceDao extends JpaRepository<VoiceBean, Long> {

    @Query("from VoiceBean b where b.uid=:uid")
    List<VoiceBean> findByUid(@Param("uid") Long uid);
    
}
