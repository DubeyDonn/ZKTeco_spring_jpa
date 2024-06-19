package com.zkt.zktspringjpa.service;

import com.zkt.zktspringjpa.model.TableAttendanceRecord;
import com.zkt.zktspringjpa.model.TableStaff;
import com.zkt.zktspringjpa.sdk.commands.ZKCommandReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zkt.zktspringjpa.sdk.Enum.CommandReplyCodeEnum;
import com.zkt.zktspringjpa.sdk.terminal.ZKTerminal;
import com.zkt.zktspringjpa.util.Constants;

import java.util.List;

@Service
public class ZKTerminalService {

    private final ZKTerminal terminal;

    @Autowired
    private TableAttendanceRecordService tableAttendanceRecordService;

    @Autowired
    private TableStaffService tableStaffService;

    public ZKTerminalService() {
        this.terminal = new ZKTerminal(Constants.IP_ADDRESS, Constants.PORT);
    }

    public void connect() throws Exception {
        try {
            ZKCommandReply connectReply = terminal.connect();
            System.out.println("Connected to terminal");
            ZKCommandReply authReply = terminal.connectAuth(Constants.COM_KEY);
            if (authReply.getCode() == CommandReplyCodeEnum.CMD_ACK_OK) {
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

//    public void forceDisconnect() {
//        try{
//            this.disconnect();
//        }catch (Exception e) {
//            System.out.println("Error disconnecting from terminal: " + e.getMessage());
//        }
//    }

    public void sync() throws Exception {
        this.connect();
        tableStaffService.insertMultipleTableStaffs(this.getStaffsFromTerminal());
        this.disconnect();
        Thread.sleep(1000);
        this.connect();
        tableAttendanceRecordService
                .insertMultipleTableAttendanceRecords(this.getAttendanceRecordsFromTerminal());
        this.disconnect();
    }


    public List<TableAttendanceRecord> getAttendanceRecordsFromTerminal() throws Exception {
        System.out.println("Getting attendance records from terminal");
        return TableAttendanceRecord.convertList(terminal.getAttendanceRecords());
    }

    public List<TableStaff> getStaffsFromTerminal() throws Exception {
        System.out.println("Getting users from terminal");
        return TableStaff.convertList(terminal.getAllUsers());
    }


}
