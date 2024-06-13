package com.zkt.zktspringjpa.service;


import com.zkt.zktspringjpa.model.MyUserInfo;
import com.zkt.zktspringjpa.model.dto.EmployeeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDTOService {

    @Autowired
    private UserInfoService userInfoService;

    public EmployeeDTOService() {
    }

    public List<EmployeeDTO> getEmployeeDTOs() {
        List<MyUserInfo> users = userInfoService.getAllUsers();
        return this.buildEmployeeDTOsFromUsers(users);
    }

    private List<EmployeeDTO> buildEmployeeDTOsFromUsers(List<MyUserInfo> users) {
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();

        for (MyUserInfo user : users) {
            EmployeeDTO employeeDTO = new EmployeeDTO();
            employeeDTO.setId(user.getUserId());
            employeeDTO.setName(user.getName());
            employeeDTOs.add(employeeDTO);
        }

        return employeeDTOs;
    }
}
