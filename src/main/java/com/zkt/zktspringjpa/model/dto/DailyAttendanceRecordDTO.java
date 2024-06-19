package com.zkt.zktspringjpa.model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyAttendanceRecordDTO {
    private String day;
    private String userId;
    private String name;
    private String checkInTime;
    private String checkOutTime;
    private ShiftDTO shift;
}