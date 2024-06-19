package com.zkt.zktspringjpa.model;

import jakarta.persistence.*;

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
@Builder
@Table(name = "attendance_record")
public class TableAttendanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int userSN;  //usersn
    private String userId; //user_id

    @Enumerated(EnumType.STRING)
    private AttendanceTypeEnum verifyType;

    @Column(nullable = false)
    private String recordTime;

    @Enumerated(EnumType.STRING)
    private AttendanceStateEnum verifyState;


    // Method to convert a single AttendanceRecord to TableAttendanceRecord
    public static TableAttendanceRecord convertToModel(AttendanceRecord sdkRecord) {
        // Implement the conversion logic here
        TableAttendanceRecord modelRecord = new TableAttendanceRecord();

        modelRecord.setUserSN(sdkRecord.getUserSN());
        modelRecord.setUserId(sdkRecord.getUserID());
        modelRecord.setVerifyType(sdkRecord.getVerifyType());
        modelRecord.setRecordTime(sdkRecord.getRecordTime());
        modelRecord.setVerifyState(sdkRecord.getVerifyState());

        return modelRecord;
    }

    // Method to convert the list
    public static List<TableAttendanceRecord> convertList(List<AttendanceRecord> sdkRecords) {
        return sdkRecords.stream()
                .map(TableAttendanceRecord::convertToModel)
                .collect(Collectors.toList());
    }
}
