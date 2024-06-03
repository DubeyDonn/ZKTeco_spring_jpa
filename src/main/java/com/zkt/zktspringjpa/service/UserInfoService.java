package com.zkt.zktspringjpa.service;

import java.util.List;

import com.zkt.zktspringjpa.model.MyUserInfo;
import com.zkt.zktspringjpa.repository.UserInfoRepository;
import com.zkt.zktspringjpa.sdk.terminal.ZKTerminal;

import java.util.ArrayList;

public class UserInfoService {

    private final ZKTerminal terminal;
    private final UserInfoRepository userInfoRepository;

    public UserInfoService(ZKTerminal terminal, UserInfoRepository userInfoRepository) {
        this.terminal = terminal;
        this.userInfoRepository = userInfoRepository;
    }

    public List<MyUserInfo> getUsersFromTerminal() throws Exception {
        System.out.println("Getting users from terminal");
        return MyUserInfo.convertList(terminal.getAllUsers());
    }

    public void insertMultipleUsers(List<MyUserInfo> users) {

        List<MyUserInfo> usersData = new ArrayList<>();

        System.out.println("Getting all users from database");
        List<MyUserInfo> allUsers = this.userInfoRepository.findAll();
        ;
        for (MyUserInfo user : users) {
            boolean userExists = false;

            for (MyUserInfo existingUser : allUsers) {
                if (existingUser.getUid() == user.getUid()) {
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

}
