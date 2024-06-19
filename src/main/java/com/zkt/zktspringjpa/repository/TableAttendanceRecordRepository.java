package com.zkt.zktspringjpa.repository;

import java.util.List;

import com.zkt.zktspringjpa.model.TableAttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TableAttendanceRecordRepository extends JpaRepository<TableAttendanceRecord, Long> {

        @Query("SELECT a FROM TableAttendanceRecord a ORDER BY a.recordTime DESC LIMIT 1")
        List<TableAttendanceRecord> getLatestAttendanceRecord();

        @Query("SELECT a FROM TableAttendanceRecord a WHERE a.recordTime BETWEEN :startDate AND :endDate ORDER BY a.recordTime")
        List<TableAttendanceRecord> getAttendanceDataForDateRange(@Param("startDate") String startDate,
                                                                  @Param("endDate") String endDate);

        @Query("SELECT a FROM TableAttendanceRecord a WHERE a.recordTime BETWEEN :startDate AND :endDate AND a.userId IN :userIds ORDER BY a.recordTime")
        List<TableAttendanceRecord> getAttendanceDataForDateRangeAndUserIds(@Param("startDate") String startDate,
                                                                            @Param("endDate") String endDate,
                                                                            @Param("userIds") List<String> userIds);
}
