package com.zkt.zktspringjpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.model.MyAttendanceRecord;
import com.zkt.zktspringjpa.repository.AttendanceRecordRepository;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Service
public class MyAttendanceRecordService {

    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    public MyAttendanceRecordService() {
    }

    public void insertMultipleAttendanceRecords(List<MyAttendanceRecord> attendanceRecords) {

        List<MyAttendanceRecord> attendance_recordData = new ArrayList<>();

        System.out.println("Getting latest attendance record");
        List<MyAttendanceRecord> latestAttendanceRecord = this.attendanceRecordRepository.getLatestAttendanceRecord();

        if (latestAttendanceRecord.isEmpty()) {
            attendance_recordData.addAll(attendanceRecords);
        } else {

            for (MyAttendanceRecord attendance_record : attendanceRecords) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime newRecordDateTime = LocalDateTime.parse(attendance_record.getRecordTime(), formatter);

                LocalDateTime latestRecordDateTime = LocalDateTime.parse(latestAttendanceRecord.get(0).getRecordTime(),
                        formatter);

                if (!newRecordDateTime.isAfter(latestRecordDateTime)) {
                    continue;
                }

                attendance_recordData.add(attendance_record);
            }
        }

        System.out.println("Inserting " + attendance_recordData.size() + " new attendance records");

        this.attendanceRecordRepository.saveAll(attendance_recordData);
    }

    public List<MyAttendanceRecord> getAllMyAttendanceRecords() {
        return this.attendanceRecordRepository.findAll();
    }

    public List<MyAttendanceRecord> getMyAttendanceRecordsForDateRange(String fromDate, String toDate) {
        return this.attendanceRecordRepository.getAttendanceDataForDateRange(fromDate, toDate);
    }

//    public List<MyAttendanceRecord> getMyAttendanceRecordsForUser(String userId) {
//        return this.attendanceRecordRepository.getAttendanceDataForUser(userId);
//    }

    public List<MyAttendanceRecord> getMyAttendanceRecordsForDateRangeAndUserIds(String fromDate, String toDate, List<String> userIds) {
        return this.attendanceRecordRepository.getAttendanceDataForDateRangeAndUserIds(fromDate, toDate, userIds);
    }
}
