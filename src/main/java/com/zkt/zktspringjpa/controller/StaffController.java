package com.zkt.zktspringjpa.controller;


import com.zkt.zktspringjpa.model.TableStaff;
import com.zkt.zktspringjpa.model.dto.StaffDTO;
import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.service.StaffDTOService;
import com.zkt.zktspringjpa.service.TableStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class StaffController {

    @Autowired
    private StaffDTOService staffDTOService;

    @Autowired
    private TableStaffService tableStaffService;

    public StaffController() {
    }

    @GetMapping("/employees")
    public ResponseEntity<Response<List<StaffDTO>>> getEmployees() {
        System.out.println("Getting all employees");
        try {
            List<StaffDTO> employees = staffDTOService.getStaffDTOs();
            Response<List<StaffDTO>> response = Response.<List<StaffDTO>>builder().success(true)
                    .data(employees).message("Employees retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting employees");
            Response<List<StaffDTO>> response = Response.<List<StaffDTO>>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/employee/edit")
    public ResponseEntity<Response<Object>> editStaff(
            @RequestParam("employeeId") String staffId,
            @RequestBody TableStaff updatedStaff
    ) {
        System.out.println("Editing staff with id: " + staffId);
        try {
            TableStaff editedStaff = tableStaffService.editTableStaff(staffId, updatedStaff);
            Response<Object> response = Response.<Object>builder().success(true)
                    .data(editedStaff).message("Staff updated successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error updating staff");
            Response<Object> response = Response.<Object>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/employee/delete")
    public ResponseEntity<Response<Object>> deleteStaff(
            @RequestParam("employeeId") String staffId
    ) {
        System.out.println("Deleting staff with id: " + staffId);
        try {
            TableStaff deletedStaff = tableStaffService.deleteTableStaff(staffId);
            Response<Object> response = Response.<Object>builder().success(true)
                    .message("Staff deleted successfully").data(deletedStaff).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error deleting staff");
            Response<Object> response = Response.<Object>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }
}
