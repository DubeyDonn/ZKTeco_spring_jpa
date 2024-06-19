package com.zkt.zktspringjpa.service;

import java.util.List;

import com.zkt.zktspringjpa.model.TableStaff;
import com.zkt.zktspringjpa.repository.TableStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;


@Service
public class TableStaffService {

    @Autowired
    private TableStaffRepository tableStaffRepository;

    public TableStaffService() {
    }

    public void insertMultipleTableStaffs(List<TableStaff> allTerminalUsers) {

        List<TableStaff> usersData = new ArrayList<>();

        System.out.println("Getting all users from database");
        List<TableStaff> allExistingUsers = this.tableStaffRepository.findAll();
        for (TableStaff terminalUser : allTerminalUsers) {
            boolean userExists = false;

            for (TableStaff existingUser : allExistingUsers) {
                if (Objects.equals(existingUser.getUserId(), terminalUser.getUserId())) {
                    userExists = true;
                    break;
                }
            }

            if (!userExists) {
                usersData.add(terminalUser);
            }
        }

        System.out.println("Inserting " + usersData.size() + " new users");
        this.tableStaffRepository.saveAll(usersData);
    }

    public List<TableStaff> getAllTableStaffs() {
        return (List<TableStaff>) this.tableStaffRepository.findAllOrderByName();
    }

    public TableStaff editTableStaff(String staffId, TableStaff updatedStaff) {
        TableStaff staff = this.tableStaffRepository.findById(staffId).orElse(null);
        if (staff == null) {
            return null;
        }
        staff.setName(updatedStaff.getName());
//        staff.setDesignation(updatedStaff.getDesignation());
//        staff.setDepartment(updatedStaff.getDepartment());
        staff.setShift(updatedStaff.getShift());
        this.tableStaffRepository.save(staff);
        return staff;
    }

    public TableStaff deleteTableStaff(String staffId) {
        TableStaff staff = this.tableStaffRepository.findById(staffId).orElse(null);
        if (staff == null) {
            return null;
        }
        this.tableStaffRepository.delete(staff);
        return staff;
    }
}
