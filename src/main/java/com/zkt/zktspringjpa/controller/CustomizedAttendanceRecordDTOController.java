package com.zkt.zktspringjpa.controller;

import com.zkt.zktspringjpa.model.dto.CustomizedAttendanceRecordDTO;
import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.service.CustomizedAttendanceRecordDTOService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping({ "/api" })
public class CustomizedAttendanceRecordDTOController {

    @Autowired
    private CustomizedAttendanceRecordDTOService customizedAttendanceRecordDTOService;

    public CustomizedAttendanceRecordDTOController() {
    }

    @GetMapping("/attendance_record")
    public ResponseEntity<Response<List<CustomizedAttendanceRecordDTO>>> getAttendanceData(
            @RequestParam(value = "fromDate", required = false) String fromDateStr,
            @RequestParam(value = "toDate", required = false) String toDateStr,
            @RequestParam(value = "employeeIds", required = false) List<String> employeeIds) {
        System.out.println("Getting attendance data for date range");
        try {
            List<CustomizedAttendanceRecordDTO> attendanceData;

            if (fromDateStr == null && toDateStr == null && (employeeIds == null || employeeIds.isEmpty())) {
                attendanceData = customizedAttendanceRecordDTOService.getAllCustomizedAttendanceRecordDTOs();
            } else if (fromDateStr != null && toDateStr != null && (employeeIds == null || employeeIds.isEmpty())) {
                attendanceData = customizedAttendanceRecordDTOService.getCustomizedAttendanceRecordDTOsForDateRange(fromDateStr, toDateStr);
            } else {
                attendanceData = customizedAttendanceRecordDTOService.getCustomizedAttendanceRecordDTOsForDateRangeAndUserIds(fromDateStr, toDateStr, employeeIds);
            }

            Response<List<CustomizedAttendanceRecordDTO>> response = Response.<List<CustomizedAttendanceRecordDTO>>builder().success(true)
                    .data(attendanceData).message("Attendance data retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting attendance data for date range");
            Response<List<CustomizedAttendanceRecordDTO>> response = Response.<List<CustomizedAttendanceRecordDTO>>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/attendance_record/date_range")
    public ResponseEntity<Response<List<CustomizedAttendanceRecordDTO>>> getAttendanceData(
            @RequestParam("fromDate") String fromDateStr,
            @RequestParam("toDate") String toDateStr) {
        System.out.println("Getting attendance data for date range");
        try {
            List<CustomizedAttendanceRecordDTO> attendanceData = customizedAttendanceRecordDTOService.getCustomizedAttendanceRecordDTOsForDateRange(
                    fromDateStr,
                    toDateStr);
            Response<List<CustomizedAttendanceRecordDTO>> response = Response.<List<CustomizedAttendanceRecordDTO>>builder().success(true)
                    .data(attendanceData).message("Attendance data retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting attendance data for date range");
            Response<List<CustomizedAttendanceRecordDTO>> response = Response.<List<CustomizedAttendanceRecordDTO>>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }
}
