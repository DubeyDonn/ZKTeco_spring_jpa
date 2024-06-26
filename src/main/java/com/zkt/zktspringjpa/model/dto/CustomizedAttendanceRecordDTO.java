package com.zkt.zktspringjpa.model.dto;

import java.util.List;

import com.zkt.zktspringjpa.model.TableShift;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomizedAttendanceRecordDTO {
    private String day;
    private String username;
    private String userId;
    private List<String> checkInTime;
    private List<String> checkOutTime;
    private List<String> breakInTime;
    private List<String> breakOutTime;
    private List<String> otInTime;
    private List<String> otOutTime;
    private ShiftDTO shift;
}