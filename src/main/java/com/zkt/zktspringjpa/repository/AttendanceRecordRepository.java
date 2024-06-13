package com.zkt.zktspringjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zkt.zktspringjpa.model.MyAttendanceRecord;
import com.zkt.zktspringjpa.model.dto.AttendanceRecordDTO;

public interface AttendanceRecordRepository extends JpaRepository<MyAttendanceRecord, Long> {

        @Query("SELECT a FROM MyAttendanceRecord a ORDER BY a.recordTime DESC LIMIT 1")
        List<MyAttendanceRecord> getLatestAttendanceRecord();

        @Query("SELECT a FROM MyAttendanceRecord a WHERE a.recordTime BETWEEN :startDate AND :endDate ORDER BY a.recordTime")
        List<MyAttendanceRecord> getAttendanceDataForDateRange(@Param("startDate") String startDate,
                        @Param("endDate") String endDate);

        @Query("SELECT a FROM MyAttendanceRecord a WHERE a.recordTime BETWEEN :startDate AND :endDate AND a.userId IN :userIds ORDER BY a.recordTime")
        List<MyAttendanceRecord> getAttendanceDataForDateRangeAndUserIds(@Param("startDate") String startDate,
                        @Param("endDate") String endDate, @Param("userIds") List<String> userIds);
}
