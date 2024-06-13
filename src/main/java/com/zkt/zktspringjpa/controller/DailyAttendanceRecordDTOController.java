package com.zkt.zktspringjpa.controller;


import com.zkt.zktspringjpa.model.dto.DailyAttendanceRecordDTO;
import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.service.DailyAttendanceRecordDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping({ "/api" })
public class DailyAttendanceRecordDTOController {


    @Autowired
    private  DailyAttendanceRecordDTOService dailyAttendanceRecordDTOService;

    public DailyAttendanceRecordDTOController() {
    }

    @GetMapping("/daily_attendance_record")
    public ResponseEntity<Response<List<DailyAttendanceRecordDTO>>> getDailyAttendanceData(
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate,
            @RequestParam(value = "employeeIds", required = false) List<String> employeeIds) {

        System.out.println("Getting daily attendance data");
        try {
            List<DailyAttendanceRecordDTO> dailyAttendanceData;

            if (employeeIds == null || employeeIds.isEmpty()) {
                dailyAttendanceData = dailyAttendanceRecordDTOService.getDailyAttendanceRecordDTOsForDateRange(fromDate, toDate);
            } else {
                dailyAttendanceData = dailyAttendanceRecordDTOService.getDailyAttendanceRecordDTOsForDateRangeAndUserIds(fromDate, toDate, employeeIds);
            }

            Response<List<DailyAttendanceRecordDTO>> response = Response.<List<DailyAttendanceRecordDTO>>builder()
                    .success(true)
                    .data(dailyAttendanceData)
                    .message("Daily attendance data retrieved successfully")
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting daily attendance data");
            Response<List<DailyAttendanceRecordDTO>> response = Response.<List<DailyAttendanceRecordDTO>>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build();
            return ResponseEntity.ok(response);
        }
    }
}
