package com.zkt.zktspringjpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.List;
import java.util.stream.Collectors;

import com.zkt.zktspringjpa.sdk.Enum.AttendanceStateEnum;
import com.zkt.zktspringjpa.sdk.Enum.AttendanceTypeEnum;
import com.zkt.zktspringjpa.sdk.commands.AttendanceRecord;

import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "attendance_record")
public class MyAttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int userSN;
    private String userID;

    @Enumerated(EnumType.STRING)
    private AttendanceTypeEnum verifyType;

    private String recordTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStateEnum verifyState;

    // Method to convert a single AttendanceRecord to MyAttendanceRecord
    public static MyAttendanceRecord convertToModel(AttendanceRecord sdkRecord) {
        // Implement the conversion logic here
        MyAttendanceRecord modelRecord = new MyAttendanceRecord();

        modelRecord.setUserSN(sdkRecord.getUserSN());
        modelRecord.setUserID(sdkRecord.getUserID());
        modelRecord.setVerifyType(sdkRecord.getVerifyType());
        modelRecord.setRecordTime(sdkRecord.getRecordTime());
        modelRecord.setVerifyState(sdkRecord.getVerifyState());

        return modelRecord;
    }

    // Method to convert the list
    public static List<MyAttendanceRecord> convertList(List<AttendanceRecord> sdkRecords) {
        return sdkRecords.stream()
                .map(MyAttendanceRecord::convertToModel)
                .collect(Collectors.toList());
    }
}
