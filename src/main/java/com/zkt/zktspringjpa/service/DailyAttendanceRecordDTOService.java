package com.zkt.zktspringjpa.service;

import com.zkt.zktspringjpa.model.dto.AttendanceRecordDTO;
import com.zkt.zktspringjpa.model.dto.DailyAttendanceRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyAttendanceRecordDTOService {

    @Autowired
    private AttendanceRecordDTOService attendanceRecordDTOService;

    public DailyAttendanceRecordDTOService() {
    }

    public List<DailyAttendanceRecordDTO> getDailyAttendanceRecordDTOsForDateRange(String fromDate, String toDate) {
        List<AttendanceRecordDTO> attendanceRecordDTOs = attendanceRecordDTOService.getAttendanceRecordDTOsForDateRange(fromDate, toDate);

        return this.buildDailyAttendanceRecordDTOsFromAttendanceRecordDTOs(attendanceRecordDTOs);
    }

    public List<DailyAttendanceRecordDTO> getDailyAttendanceRecordDTOsForDateRangeAndUserIds(String fromDate, String toDate, List<String> userIds) {
        List<AttendanceRecordDTO> attendanceRecordDTOs = attendanceRecordDTOService.getAttendanceRecordDTOsForDateRangeAndUserIds(fromDate, toDate, userIds);

        return this.buildDailyAttendanceRecordDTOsFromAttendanceRecordDTOs(attendanceRecordDTOs);
    }

    public List<DailyAttendanceRecordDTO> buildDailyAttendanceRecordDTOsFromAttendanceRecordDTOs(List<AttendanceRecordDTO> attendanceRecordDTOs) {
        List<DailyAttendanceRecordDTO> dailyAttendanceRecordDTOs = new ArrayList<>();

        for (AttendanceRecordDTO attendanceRecordDTO : attendanceRecordDTOs) {

            DailyAttendanceRecordDTO dailyAttendanceRecordDTO = new DailyAttendanceRecordDTO();
            dailyAttendanceRecordDTO.setDay(attendanceRecordDTO.getDay());
            dailyAttendanceRecordDTO.setUserId(attendanceRecordDTO.getUserId());
            dailyAttendanceRecordDTO.setName(attendanceRecordDTO.getUsername());
            //get first check in and last check out

            if (!attendanceRecordDTO.getCheckInTime().isEmpty()) {
                //get time only from 2024-06-07 08:59:53
                String checkInTime = attendanceRecordDTO.getCheckInTime().get(0);
                String[] checkInTimeArray = checkInTime.split(" ");
                dailyAttendanceRecordDTO.setCheckInTime(checkInTimeArray[1]);
            }
            if (!attendanceRecordDTO.getCheckOutTime().isEmpty()) {
                String checkOutTime = attendanceRecordDTO.getCheckOutTime().get(attendanceRecordDTO.getCheckOutTime().size() - 1);
                String[] checkOutTimeArray = checkOutTime.split(" ");
                dailyAttendanceRecordDTO.setCheckOutTime(checkOutTimeArray[1]);
            }
            dailyAttendanceRecordDTOs.add(dailyAttendanceRecordDTO);
        }

        return dailyAttendanceRecordDTOs;
    }

}
