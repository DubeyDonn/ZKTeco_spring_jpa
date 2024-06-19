package com.zkt.zktspringjpa.service;


import com.zkt.zktspringjpa.model.TableStaff;
import com.zkt.zktspringjpa.model.dto.StaffDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StaffDTOService {

    @Autowired
    private TableStaffService tableStaffService;

    public StaffDTOService() {
    }

    public List<StaffDTO> getStaffDTOs() {
        List<TableStaff> users = tableStaffService.getAllTableStaffs();
        return this.buildStaffDTOsFromUsers(users);
    }

    private StaffDTO buildStaffDTOFromUser(TableStaff user) {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(user.getUserId());
        staffDTO.setName(user.getName());
        staffDTO.setShift(user.getShift());
        return staffDTO;
    }

    private List<StaffDTO> buildStaffDTOsFromUsers(List<TableStaff> users) {
        List<StaffDTO> staffDTOS = new ArrayList<>();

        for (TableStaff user : users) {
            StaffDTO staffDTO = this.buildStaffDTOFromUser(user);
            staffDTOS.add(staffDTO);
        }

        return staffDTOS;
    }
}
