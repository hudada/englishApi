package com.example.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.bean.BookBean;
import com.example.bean.UserBean;

public interface BookDao extends JpaRepository<BookBean, Long> {

//    @Query("from UserBean b where b.userName=:userName")
//    UserBean findUserByUserName(@Param("userName") String userName);
    
}
