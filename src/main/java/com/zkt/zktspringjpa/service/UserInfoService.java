package com.zkt.zktspringjpa.service;

import java.util.List;

import com.zkt.zktspringjpa.model.MyUserInfo;
import com.zkt.zktspringjpa.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;


@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    public UserInfoService() {
    }

    public void insertMultipleUsers(List<MyUserInfo> users) {

        List<MyUserInfo> usersData = new ArrayList<>();

        System.out.println("Getting all users from database");
        List<MyUserInfo> allUsers = this.userInfoRepository.findAll();
        for (MyUserInfo user : users) {
            boolean userExists = false;

            for (MyUserInfo existingUser : allUsers) {
                if (Objects.equals(existingUser.getUid(), user.getUid())) {
                    userExists = true;
                    break;
                }
            }

            if (!userExists) {
                usersData.add(user);
            }
        }

        System.out.println("Inserting " + usersData.size() + " new users");
        this.userInfoRepository.saveAll(usersData);
    }

    public List<MyUserInfo> getAllUsers() {
        return this.userInfoRepository.findAll();
    }
}
