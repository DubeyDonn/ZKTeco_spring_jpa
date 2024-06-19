package com.zkt.zktspringjpa.service;


import com.zkt.zktspringjpa.model.TableAttendanceRecord;
import com.zkt.zktspringjpa.model.TableStaff;
import com.zkt.zktspringjpa.model.dto.CustomizedAttendanceRecordDTO;
import com.zkt.zktspringjpa.repository.TableStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomizedAttendanceRecordDTOService {

    @Autowired
    private ShiftDTOService shiftDTOService;

    @Autowired
    private TableAttendanceRecordService tableAttendanceRecordService;

    @Autowired
    private TableStaffRepository tableStaffRepository;

    public CustomizedAttendanceRecordDTOService() {
    }

    public List<CustomizedAttendanceRecordDTO> getAllCustomizedAttendanceRecordDTOs() {
        List<TableAttendanceRecord> attendanceRecords = tableAttendanceRecordService.getAllTableAttendanceRecords();
        return this
                .buildCustomizedAttendanceRecordDTOsFromMyAttendanceRecords(attendanceRecords);
    }

    public List<CustomizedAttendanceRecordDTO> getCustomizedAttendanceRecordDTOsForDateRange(String fromDate, String toDate) {
        List<TableAttendanceRecord> attendanceRecords = tableAttendanceRecordService.getTableAttendanceRecordsForDateRange(fromDate, toDate);

        return this.buildCustomizedAttendanceRecordDTOsFromMyAttendanceRecords(attendanceRecords);
    }

    public List<CustomizedAttendanceRecordDTO> getCustomizedAttendanceRecordDTOsForDateRangeAndUserIds(String fromDate, String toDate, List<String> userIds) {
        List<TableAttendanceRecord> attendanceRecords = tableAttendanceRecordService.getTableAttendanceRecordsForDateRangeAndUserIds(fromDate, toDate, userIds);

        return this.buildCustomizedAttendanceRecordDTOsFromMyAttendanceRecords(attendanceRecords);
    }


    // main join function that joins users data with the attendance records
    public List<CustomizedAttendanceRecordDTO> buildCustomizedAttendanceRecordDTOsFromMyAttendanceRecords(
            List<TableAttendanceRecord> attendanceRecords) {

        // Map of (day and map of (user and list of records))
        Map<String, Map<String, List<TableAttendanceRecord>>> recordsByDayAndUser = new HashMap<>();

        // Group records by day and user
        for (TableAttendanceRecord record : attendanceRecords) {
            String day = record.getRecordTime().split(" ")[0];
            String userId = record.getUserId();

            recordsByDayAndUser.putIfAbsent(day, new HashMap<>());
            recordsByDayAndUser.get(day).putIfAbsent(userId, new ArrayList<>());

            recordsByDayAndUser.get(day).get(userId).add(record);
        }

        List<CustomizedAttendanceRecordDTO> customizedAttendanceRecordDTOS = new ArrayList<>();

        // Build AttendanceRecordDTOs
        for (Map.Entry<String, Map<String, List<TableAttendanceRecord>>> dayEntry : recordsByDayAndUser.entrySet()) {
            String day = dayEntry.getKey();
            Map<String, List<TableAttendanceRecord>> recordsByUser = dayEntry.getValue();

            for (Map.Entry<String, List<TableAttendanceRecord>> userEntry : recordsByUser.entrySet()) {
                String userId = userEntry.getKey();
                List<TableAttendanceRecord> recordsForUser = userEntry.getValue();

                CustomizedAttendanceRecordDTO dto = new CustomizedAttendanceRecordDTO();
                dto.setDay(day);

                TableStaff tableStaff = this.tableStaffRepository.findById(userId).orElse(null);

                //check if the user is in the tableStaff
                if (tableStaff == null) {
                    continue;
                }

                String username = tableStaff.getName();
                dto.setShift(shiftDTOService.buildShiftDTOFromShift(tableStaff.getShift()));
                dto.setUsername(username);
                dto.setUserId(userId);


                List<String> checkInTimes = new ArrayList<>();
                List<String> checkOutTimes = new ArrayList<>();
                List<String> breakInTimes = new ArrayList<>();
                List<String> breakOutTimes = new ArrayList<>();
                List<String> otInTimes = new ArrayList<>();
                List<String> otOutTimes = new ArrayList<>();

                for (TableAttendanceRecord record : recordsForUser) {
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

                customizedAttendanceRecordDTOS.add(dto);
            }
        }

        //sort by day and check in time
        customizedAttendanceRecordDTOS.sort((a, b) -> {
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

        return customizedAttendanceRecordDTOS;
    }

}
