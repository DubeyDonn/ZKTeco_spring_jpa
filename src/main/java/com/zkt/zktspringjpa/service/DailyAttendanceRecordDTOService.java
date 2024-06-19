package com.zkt.zktspringjpa.service;

import com.zkt.zktspringjpa.model.dto.CustomizedAttendanceRecordDTO;
import com.zkt.zktspringjpa.model.dto.DailyAttendanceRecordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DailyAttendanceRecordDTOService {

    @Autowired
    private CustomizedAttendanceRecordDTOService customizedAttendanceRecordDTOService;

    public DailyAttendanceRecordDTOService() {
    }

    public List<DailyAttendanceRecordDTO> getDailyAttendanceRecordDTOsForDateRange(String fromDate, String toDate) {
        List<CustomizedAttendanceRecordDTO> customizedAttendanceRecordDTOS = customizedAttendanceRecordDTOService.getCustomizedAttendanceRecordDTOsForDateRange(fromDate, toDate);

        return this.buildDailyAttendanceRecordDTOsFromAttendanceRecordDTOs(customizedAttendanceRecordDTOS);
    }

    public List<DailyAttendanceRecordDTO> getDailyAttendanceRecordDTOsForDateRangeAndUserIds(String fromDate, String toDate, List<String> userIds) {
        List<CustomizedAttendanceRecordDTO> customizedAttendanceRecordDTOS = customizedAttendanceRecordDTOService.getCustomizedAttendanceRecordDTOsForDateRangeAndUserIds(fromDate, toDate, userIds);

        return this.buildDailyAttendanceRecordDTOsFromAttendanceRecordDTOs(customizedAttendanceRecordDTOS);
    }

    public DailyAttendanceRecordDTO buildDailyAttendanceRecordDTOFromAttendanceRecordDTO(CustomizedAttendanceRecordDTO customizedAttendanceRecordDTO) {
        DailyAttendanceRecordDTO dailyAttendanceRecordDTO = new DailyAttendanceRecordDTO();
        dailyAttendanceRecordDTO.setDay(customizedAttendanceRecordDTO.getDay());
        dailyAttendanceRecordDTO.setUserId(customizedAttendanceRecordDTO.getUserId());
        dailyAttendanceRecordDTO.setName(customizedAttendanceRecordDTO.getUsername());
        dailyAttendanceRecordDTO.setShift(customizedAttendanceRecordDTO.getShift());
        //get first check in and last check out

        if (!customizedAttendanceRecordDTO.getCheckInTime().isEmpty()) {
            //get time only from 2024-06-07 08:59:53
            String checkInTime = customizedAttendanceRecordDTO.getCheckInTime().get(0);
            String[] checkInTimeArray = checkInTime.split(" ");
            dailyAttendanceRecordDTO.setCheckInTime(checkInTimeArray[1]);
        }
        if (!customizedAttendanceRecordDTO.getCheckOutTime().isEmpty()) {
            String checkOutTime = customizedAttendanceRecordDTO.getCheckOutTime().get(customizedAttendanceRecordDTO.getCheckOutTime().size() - 1);
            String[] checkOutTimeArray = checkOutTime.split(" ");
            dailyAttendanceRecordDTO.setCheckOutTime(checkOutTimeArray[1]);
        }

        return dailyAttendanceRecordDTO;
    }

    public List<DailyAttendanceRecordDTO> buildDailyAttendanceRecordDTOsFromAttendanceRecordDTOs(List<CustomizedAttendanceRecordDTO> customizedAttendanceRecordDTOS) {
        List<DailyAttendanceRecordDTO> dailyAttendanceRecordDTOs = new ArrayList<>();

        for (CustomizedAttendanceRecordDTO customizedAttendanceRecordDTO : customizedAttendanceRecordDTOS) {
            DailyAttendanceRecordDTO dailyAttendanceRecordDTO = this.buildDailyAttendanceRecordDTOFromAttendanceRecordDTO(customizedAttendanceRecordDTO);
            dailyAttendanceRecordDTOs.add(dailyAttendanceRecordDTO);
        }

        return dailyAttendanceRecordDTOs;
    }

}
