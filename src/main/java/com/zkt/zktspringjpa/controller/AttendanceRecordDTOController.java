package com.zkt.zktspringjpa.controller;

import com.zkt.zktspringjpa.model.dto.AttendanceRecordDTO;
import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.service.AttendanceRecordDTOService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping({ "/api" })
public class AttendanceRecordDTOController {

    @Autowired
    private AttendanceRecordDTOService attendanceRecordDTOService;

    public AttendanceRecordDTOController() {
    }

    @GetMapping("/attendance_record")
    public ResponseEntity<Response<List<AttendanceRecordDTO>>> getAttendanceData() {
        System.out.println("Getting attendance data for date range");
        try {
            List<AttendanceRecordDTO> attendanceData = attendanceRecordDTOService.getAllAttendanceRecordDTOs();
            Response<List<AttendanceRecordDTO>> response = Response.<List<AttendanceRecordDTO>>builder().success(true)
                    .data(attendanceData).message("Attendance data retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting attendance data for date range");
            Response<List<AttendanceRecordDTO>> response = Response.<List<AttendanceRecordDTO>>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/attendance_record/date_range")
    public ResponseEntity<Response<List<AttendanceRecordDTO>>> getAttendanceData(
            @RequestParam("fromDate") String fromDateStr,
            @RequestParam("toDate") String toDateStr) {
        System.out.println("Getting attendance data for date range");
        try {
            List<AttendanceRecordDTO> attendanceData = attendanceRecordDTOService.getAttendanceRecordDTOsForDateRange(
                    fromDateStr,
                    toDateStr);
            Response<List<AttendanceRecordDTO>> response = Response.<List<AttendanceRecordDTO>>builder().success(true)
                    .data(attendanceData).message("Attendance data retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting attendance data for date range");
            Response<List<AttendanceRecordDTO>> response = Response.<List<AttendanceRecordDTO>>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }
}
