package com.zkt.zktspringjpa.service;

import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.repository.AttendanceRecordRepository;
import com.zkt.zktspringjpa.repository.UserInfoRepository;
import com.zkt.zktspringjpa.sdk.Enum.CommandReplyCodeEnum;
import com.zkt.zktspringjpa.sdk.commands.ZKCommandReply;
import com.zkt.zktspringjpa.sdk.terminal.ZKTerminal;
import com.zkt.zktspringjpa.util.Constants;

@Service
public class ZKService {

    private final ZKTerminal terminal;

    private AttendanceRecordService attendanceRecordService;
    private UserInfoService userInfoService;

    public ZKService(AttendanceRecordRepository attendanceRecordRepository, UserInfoRepository userInfoRepository)
            throws Exception {
        this.terminal = new ZKTerminal(Constants.IP_ADDRESS, Constants.PORT);
        this.attendanceRecordService = new AttendanceRecordService(this.terminal, attendanceRecordRepository);
        this.userInfoService = new UserInfoService(this.terminal, userInfoRepository);

    }

    public ZKCommandReply connect() throws Exception {

        ZKCommandReply reply = terminal.connect();
        if (terminal.connectAuth(Constants.COM_KEY).getCode() == CommandReplyCodeEnum.CMD_ACK_OK) {
            return reply;
        } else {
            throw new Exception("Connection failed");
        }
    }

    public void createBackup() {
        terminal.createBackup();
    }

    public void disconnect() throws Exception {
        terminal.disconnect();
    }

    public void sync() throws Exception {
        this.connect();
        attendanceRecordService
                .insertMultipleAttendanceRecords(attendanceRecordService.getAttendanceRecordsFromTerminal());
        this.disconnect();
        this.connect();
        userInfoService.insertMultipleUsers(userInfoService.getUsersFromTerminal());
        this.disconnect();
    }
}
