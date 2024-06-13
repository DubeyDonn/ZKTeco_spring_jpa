package com.zkt.zktspringjpa.service;

import com.zkt.zktspringjpa.model.MyAttendanceRecord;
import com.zkt.zktspringjpa.model.MyUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.sdk.Enum.CommandReplyCodeEnum;
import com.zkt.zktspringjpa.sdk.commands.ZKCommandReply;
import com.zkt.zktspringjpa.sdk.terminal.ZKTerminal;
import com.zkt.zktspringjpa.util.Constants;

import java.util.List;

@Service
public class ZKTerminalService {

    private final ZKTerminal terminal;

    @Autowired
    private MyAttendanceRecordService myAttendanceRecordService;

    @Autowired
    private UserInfoService userInfoService;

    public ZKTerminalService() {
        this.terminal = new ZKTerminal(Constants.IP_ADDRESS, Constants.PORT);
    }

    public void connect() throws Exception {
        try {
            ZKCommandReply reply = terminal.connect();
            System.out.println("Connected to terminal");
            if (terminal.connectAuth(Constants.COM_KEY).getCode() == CommandReplyCodeEnum.CMD_ACK_OK) {
                System.out.println("Authenticated with terminal");
            } else {
                throw new Exception("Authentication failed");
            }
        } catch (Exception e) {
            throw new Exception("Error connecting to terminal: " + e.getMessage());
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
        userInfoService.insertMultipleUsers(this.getUsers());
        this.disconnect();
        this.connect();
        myAttendanceRecordService
                .insertMultipleAttendanceRecords(this.getAttendanceRecords());
        this.disconnect();
    }


    public List<MyAttendanceRecord> getAttendanceRecords() throws Exception {
        System.out.println("Getting attendance records from terminal");
        return MyAttendanceRecord.convertList(terminal.getAttendanceRecords());
    }

    public List<MyUserInfo> getUsers() throws Exception {
        System.out.println("Getting users from terminal");
        return MyUserInfo.convertList(terminal.getAllUsers());
    }


}
