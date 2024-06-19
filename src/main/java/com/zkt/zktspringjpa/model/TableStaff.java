package com.zkt.zktspringjpa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Builder
@Table(name = "staff")
public class TableStaff {
    @Id
    private String userId;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;
    private String password;

    private String name;
    private long cardNo;


    private String groupNumber;
    private int userTimeZoneFlag;
    private int timeZone1;
    private int timeZone2;
    private int timeZone3;
    private boolean enabled;

    @ManyToOne
    @JoinColumn(name="shift_id", nullable=true)
    @JsonBackReference
    private TableShift shift;


    // Method to convert a single UserInfo to TableStaff
    public static TableStaff convertToModel(UserInfo userInfo) {
        TableStaff tableStaff = new TableStaff();

        tableStaff.setRole(userInfo.getRole());
        tableStaff.setPassword(userInfo.getPassword());
        tableStaff.setName(userInfo.getName());
        tableStaff.setCardNo(userInfo.getCardno());
        tableStaff.setUserId(String.valueOf(userInfo.getUserid()).replaceAll("\\P{Print}", ""));

        tableStaff.setGroupNumber(userInfo.getGroupNumber());
        tableStaff.setUserTimeZoneFlag(userInfo.getUserTimeZoneFlag());
        tableStaff.setTimeZone1(userInfo.getTimeZone1());
        tableStaff.setTimeZone2(userInfo.getTimeZone2());
        tableStaff.setTimeZone3(userInfo.getTimeZone3());
        tableStaff.setEnabled(userInfo.isEnabled());

        return tableStaff;
    }

    // Method to convert the list
    public static List<TableStaff> convertList(List<UserInfo> userInfos) {
        return userInfos.stream()
                .map(TableStaff::convertToModel)
                .collect(Collectors.toList());
    }

}
