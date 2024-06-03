package com.zkt.zktspringjpa.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.zkt.zktspringjpa.model.MyAttendanceRecord;
import com.zkt.zktspringjpa.model.MyUserInfo;

public interface AttendanceRecordRepository extends JpaRepository<MyAttendanceRecord, Long> {

    @Query("SELECT a FROM MyAttendanceRecord a ORDER BY a.recordTime DESC LIMIT 1")
    List<MyAttendanceRecord> getLatestAttendanceRecord();

    // @Query("SELECT new map(a.recordTime as day, u.name as username, " +
    // "CASE WHEN a.verifyState = 'CHECK_IN' THEN a.recordTime ELSE NULL END as
    // check_in_time, " +
    // "CASE WHEN a.verifyState = 'CHECK_OUT' THEN a.recordTime ELSE NULL END as
    // check_out_time, " +
    // "CASE WHEN a.verifyState = 'BREAK_IN' THEN a.recordTime ELSE NULL END as
    // break_in_time, " +
    // "CASE WHEN a.verifyState = 'BREAK_OUT' THEN a.recordTime ELSE NULL END as
    // break_out_time, " +
    // "CASE WHEN a.verifyState = 'OT_IN' THEN a.recordTime ELSE NULL END as
    // ot_in_time, " +
    // "CASE WHEN a.verifyState = 'OT_OUT' THEN a.recordTime ELSE NULL END as
    // ot_out_time) " +
    // "FROM MyAttendanceRecord a INNER JOIN MyUserInfo u ON a.userid = u.userid")
    // List<Map<String, Object>> getAttendanceData();

    // @Query("SELECT new map(a.recordTime as day, u.name as username, " +
    // "CASE WHEN a.verifyState = 'CHECK_IN' THEN a.recordTime ELSE NULL END as
    // check_in_time, " +
    // "CASE WHEN a.verifyState = 'CHECK_OUT' THEN a.recordTime ELSE NULL END as
    // check_out_time, " +
    // "CASE WHEN a.verifyState = 'BREAK_IN' THEN a.recordTime ELSE NULL END as
    // break_in_time, " +
    // "CASE WHEN a.verifyState = 'BREAK_OUT' THEN a.recordTime ELSE NULL END as
    // break_out_time, " +
    // "CASE WHEN a.verifyState = 'OT_IN' THEN a.recordTime ELSE NULL END as
    // ot_in_time, " +
    // "CASE WHEN a.verifyState = 'OT_OUT' THEN a.recordTime ELSE NULL END as
    // ot_out_time) " +
    // "FROM MyAttendanceRecord a INNER JOIN MyUserInfo u ON a.userid = u.userid " +
    // "WHERE a.recordTime BETWEEN :fromDate AND :toDate")
    // List<Map<String, Object>> getAttendanceDataForDateRange(@Param("fromDate")
    // LocalDateTime fromDate,
    // @Param("toDate") LocalDateTime toDate);
}
