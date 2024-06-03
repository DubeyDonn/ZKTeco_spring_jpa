package com.zkt.zktspringjpa.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.model.MyAttendanceRecord;
import com.zkt.zktspringjpa.repository.AttendanceRecordRepository;
import com.zkt.zktspringjpa.sdk.terminal.ZKTerminal;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

public class AttendanceRecordService {
    private final ZKTerminal terminal;
    private final AttendanceRecordRepository attendanceRecordRepository;

    public AttendanceRecordService(ZKTerminal terminal, AttendanceRecordRepository attendanceRecordRepository) {
        this.terminal = terminal;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    public AttendanceRecordService(AttendanceRecordRepository attendanceRecordRepository) {
        this.terminal = null;
        this.attendanceRecordRepository = attendanceRecordRepository;
    }

    public AttendanceRecordService() {
        this.terminal = null;
        this.attendanceRecordRepository = null;
    }

    public List<MyAttendanceRecord> getAttendanceRecordsFromTerminal() throws Exception {
        System.out.println("Getting attendance records from terminal");
        return MyAttendanceRecord.convertList(terminal.getAttendanceRecords());
    }

    public void insertMultipleAttendanceRecords(List<MyAttendanceRecord> attendanceRecords) {

        List<MyAttendanceRecord> attendance_recordData = new ArrayList<>();

        System.out.println("Getting latest attendance record");
        List<MyAttendanceRecord> latestAttendanceRecord = this.attendanceRecordRepository.getLatestAttendanceRecord();

        if (latestAttendanceRecord.size() == 0) {
            for (MyAttendanceRecord attendance_record : attendanceRecords) {
                attendance_recordData.add(attendance_record);
            }
        } else {

            for (MyAttendanceRecord attendance_record : attendanceRecords) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime newRecordDateTime = LocalDateTime.parse(attendance_record.getRecordTime(), formatter);

                LocalDateTime latestRecordDateTime = LocalDateTime.parse(latestAttendanceRecord.get(0).getRecordTime(),
                        formatter);

                if (newRecordDateTime.compareTo(latestRecordDateTime) <= 0) {
                    continue;
                }

                attendance_recordData.add(attendance_record);
            }
        }

        System.out.println("Inserting " + attendance_recordData.size() + " new attendance records");

        this.attendanceRecordRepository.saveAll(attendance_recordData);
    }

    public List<MyAttendanceRecord> getAttendanceRecords() {
        return this.attendanceRecordRepository.findAll();
    }
}
