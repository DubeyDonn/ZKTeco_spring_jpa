package com.zkt.zktspringjpa.service;

import java.util.ArrayList;
import java.util.List;

import com.zkt.zktspringjpa.model.TableAttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.repository.TableAttendanceRecordRepository;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Service
public class TableAttendanceRecordService {

    @Autowired
    private TableAttendanceRecordRepository tableAttendanceRecordRepository;

    public TableAttendanceRecordService() {
    }

    public void insertMultipleTableAttendanceRecords(List<TableAttendanceRecord> attendanceRecords) {

        List<TableAttendanceRecord> attendance_recordData = new ArrayList<>();

        System.out.println("Getting latest attendance record");
        List<TableAttendanceRecord> latestAttendanceRecord = this.tableAttendanceRecordRepository.getLatestAttendanceRecord();

        if (latestAttendanceRecord.isEmpty()) {
            attendance_recordData.addAll(attendanceRecords);
        } else {

            for (TableAttendanceRecord attendance_record : attendanceRecords) {

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

        this.tableAttendanceRecordRepository.saveAll(attendance_recordData);
    }

    public List<TableAttendanceRecord> getAllTableAttendanceRecords() {
        return this.tableAttendanceRecordRepository.findAll();
    }

    public List<TableAttendanceRecord> getTableAttendanceRecordsForDateRange(String fromDate, String toDate) {
        return this.tableAttendanceRecordRepository.getAttendanceDataForDateRange(fromDate, toDate);
    }

//    public List<TableAttendanceRecord> getMyAttendanceRecordsForUser(String userId) {
//        return this.tableAttendanceRecordRepository.getAttendanceDataForUser(userId);
//    }

    public List<TableAttendanceRecord> getTableAttendanceRecordsForDateRangeAndUserIds(String fromDate, String toDate, List<String> userIds) {
        return this.tableAttendanceRecordRepository.getAttendanceDataForDateRangeAndUserIds(fromDate, toDate, userIds);
    }
}
