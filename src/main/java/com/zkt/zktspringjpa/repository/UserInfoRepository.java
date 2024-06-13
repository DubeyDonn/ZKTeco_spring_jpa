package com.zkt.zktspringjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zkt.zktspringjpa.model.MyUserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<MyUserInfo, Integer> {

    // get user name by user id
    @Query("SELECT distinct mui.name FROM MyUserInfo mui " +
            "Inner Join MyAttendanceRecord mar ON mui.userId = mar.userId WHERE mui.userId = :#{#userID}")
    String findNameByUserID(@Param("userID") String userID);

    MyUserInfo findByName(String name);

    MyUserInfo findByUserId(String userId);
}
