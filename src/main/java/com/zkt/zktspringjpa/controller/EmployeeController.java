package com.zkt.zktspringjpa.controller;


import com.zkt.zktspringjpa.model.dto.EmployeeDTO;
import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.service.EmployeeDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api")
public class EmployeeController {

    @Autowired
    private EmployeeDTOService employeeDTOService;

    public EmployeeController() {
    }

    @GetMapping("/employees")
    public ResponseEntity<Response<List<EmployeeDTO>>> getEmployees() {
        System.out.println("Getting all employees");
        try {
            List<EmployeeDTO> employees = employeeDTOService.getEmployeeDTOs();
            Response<List<EmployeeDTO>> response = Response.<List<EmployeeDTO>>builder().success(true)
                    .data(employees).message("Employees retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting employees");
            Response<List<EmployeeDTO>> response = Response.<List<EmployeeDTO>>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }

}
