package com.zkt.zktspringjpa.service;


import com.zkt.zktspringjpa.model.MyAttendanceRecord;
import com.zkt.zktspringjpa.model.dto.AttendanceRecordDTO;
import com.zkt.zktspringjpa.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AttendanceRecordDTOService {

    @Autowired
    private MyAttendanceRecordService myAttendanceRecordService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    public AttendanceRecordDTOService() {
    }

    public List<AttendanceRecordDTO> getAllAttendanceRecordDTOs() {
        List<MyAttendanceRecord> attendanceRecords = myAttendanceRecordService.getAllMyAttendanceRecords();
        return this
                .buildAttendanceRecordDTOsFromMyAttendanceRecords(attendanceRecords);
    }

    public List<AttendanceRecordDTO> getAttendanceRecordDTOsForDateRange(String fromDate, String toDate) {
        List<MyAttendanceRecord> attendanceRecords = myAttendanceRecordService.getMyAttendanceRecordsForDateRange(fromDate, toDate);

        return this.buildAttendanceRecordDTOsFromMyAttendanceRecords(attendanceRecords);
    }

    public List<AttendanceRecordDTO> getAttendanceRecordDTOsForDateRangeAndUserIds(String fromDate, String toDate, List<String> userIds) {
        List<MyAttendanceRecord> attendanceRecords = myAttendanceRecordService.getMyAttendanceRecordsForDateRangeAndUserIds(fromDate, toDate, userIds);

        return this.buildAttendanceRecordDTOsFromMyAttendanceRecords(attendanceRecords);
    }


    // main join function that joins users data with the attendance records
    public List<AttendanceRecordDTO> buildAttendanceRecordDTOsFromMyAttendanceRecords(
            List<MyAttendanceRecord> attendanceRecords) {

        // Map of (day and map of (user and list of records))
        Map<String, Map<String, List<MyAttendanceRecord>>> recordsByDayAndUser = new HashMap<>();

        // Group records by day and user
        for (MyAttendanceRecord record : attendanceRecords) {
            String day = record.getRecordTime().split(" ")[0];
            String userId = record.getUserId();

            recordsByDayAndUser.putIfAbsent(day, new HashMap<>());
            recordsByDayAndUser.get(day).putIfAbsent(userId, new ArrayList<>());

            recordsByDayAndUser.get(day).get(userId).add(record);
        }

        List<AttendanceRecordDTO> attendanceRecordDTOs = new ArrayList<>();

        // Build AttendanceRecordDTOs
        for (Map.Entry<String, Map<String, List<MyAttendanceRecord>>> dayEntry : recordsByDayAndUser.entrySet()) {
            String day = dayEntry.getKey();
            Map<String, List<MyAttendanceRecord>> recordsByUser = dayEntry.getValue();

            for (Map.Entry<String, List<MyAttendanceRecord>> userEntry : recordsByUser.entrySet()) {
                String userId = userEntry.getKey();
                List<MyAttendanceRecord> recordsForUser = userEntry.getValue();

                AttendanceRecordDTO dto = new AttendanceRecordDTO();
                dto.setDay(day);

                String username = this.userInfoRepository.findNameByUserID(userId);

                dto.setUsername(username);
                dto.setUserId(userId);

                List<String> checkInTimes = new ArrayList<>();
                List<String> checkOutTimes = new ArrayList<>();
                List<String> breakInTimes = new ArrayList<>();
                List<String> breakOutTimes = new ArrayList<>();
                List<String> otInTimes = new ArrayList<>();
                List<String> otOutTimes = new ArrayList<>();

                for (MyAttendanceRecord record : recordsForUser) {
                    switch (record.getVerifyState()) {
                        case CHECK_IN:
                            checkInTimes.add(record.getRecordTime());
                            break;
                        case CHECK_OUT:
                            checkOutTimes.add(record.getRecordTime());
                            break;
                        case BREAK_IN:
                            breakInTimes.add(record.getRecordTime());
                            break;
                        case BREAK_OUT:
                            breakOutTimes.add(record.getRecordTime());
                            break;
                        case OT_IN:
                            otInTimes.add(record.getRecordTime());
                            break;
                        case OT_OUT:
                            otOutTimes.add(record.getRecordTime());
                            break;
                    }
                }

                dto.setCheckInTime(checkInTimes);
                dto.setCheckOutTime(checkOutTimes);
                dto.setBreakInTime(breakInTimes);
                dto.setBreakOutTime(breakOutTimes);
                dto.setOtInTime(otInTimes);
                dto.setOtOutTime(otOutTimes);

                attendanceRecordDTOs.add(dto);
            }
        }

        //sort by day and check in time
        attendanceRecordDTOs.sort((a, b) -> {
            if (a.getDay().equals(b.getDay())) {
                if (a.getCheckInTime().isEmpty() && b.getCheckInTime().isEmpty()) {
                    return 0; // Both lists are empty, consider them equal
                } else if (a.getCheckInTime().isEmpty()) {
                    return 1; // a's list is empty, so a should sort after b
                } else if (b.getCheckInTime().isEmpty()) {
                    return -1; // b's list is empty, so a should sort before b
                } else {
                    return a.getCheckInTime().get(0).compareTo(b.getCheckInTime().get(0));
                }
            } else {
                return a.getDay().compareTo(b.getDay());
            }
        });

        return attendanceRecordDTOs;
    }

}
