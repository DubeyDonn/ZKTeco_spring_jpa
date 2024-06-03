package com.zkt.zktspringjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zkt.zktspringjpa.model.MyUserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<MyUserInfo, Integer> {
}
