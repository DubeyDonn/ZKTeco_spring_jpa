package com.zkt.zktspringjpa.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

import com.zkt.zktspringjpa.sdk.Enum.UserRoleEnum;
import com.zkt.zktspringjpa.sdk.commands.UserInfo;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "staff")
public class MyUserInfo {

    @Id
    private Integer uid;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
    private String password;

//    @Column(columnDefinition = "varchar(50) not null")
    private String name;
    private long cardno;

    // userId is int in the database
    @JsonProperty("user_id")
    private String userId;

    private String groupNumber;
    private int userTimeZoneFlag;
    private int timeZone1;
    private int timeZone2;
    private int timeZone3;
    private boolean enabled;

    // Method to convert a single UserInfo to MyUserInfo
    public static MyUserInfo convertToModel(UserInfo userInfo) {
        MyUserInfo myUserInfo = new MyUserInfo();



        myUserInfo.setUid(userInfo.getUid());
        myUserInfo.setRole(userInfo.getRole());
        myUserInfo.setPassword(userInfo.getPassword());
        myUserInfo.setName(userInfo.getName());
        myUserInfo.setCardno(userInfo.getCardno());
        myUserInfo.setUserId(String.valueOf(userInfo.getUserid()).replaceAll("\\P{Print}", ""));
//        myUserInfo.setUserId(userInfo.getUserid());
        myUserInfo.setGroupNumber(userInfo.getGroupNumber());
        myUserInfo.setUserTimeZoneFlag(userInfo.getUserTimeZoneFlag());
        myUserInfo.setTimeZone1(userInfo.getTimeZone1());
        myUserInfo.setTimeZone2(userInfo.getTimeZone2());
        myUserInfo.setTimeZone3(userInfo.getTimeZone3());
        myUserInfo.setEnabled(userInfo.isEnabled());

        return myUserInfo;
    }

    // Method to convert the list
    public static List<MyUserInfo> convertList(List<UserInfo> userInfos) {
        return userInfos.stream()
                .map(MyUserInfo::convertToModel)
                .collect(Collectors.toList());
    }

}
