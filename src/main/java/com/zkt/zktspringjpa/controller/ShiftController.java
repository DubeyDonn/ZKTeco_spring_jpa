package com.zkt.zktspringjpa.controller;

import com.zkt.zktspringjpa.model.TableShift;
import com.zkt.zktspringjpa.model.response.Response;
import com.zkt.zktspringjpa.service.TableShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class ShiftController {
    @Autowired
    private TableShiftService tableShiftService;

    public ShiftController() {
    }

    @GetMapping("/shifts")
    public ResponseEntity<Response<List<TableShift>>> getShifts() {
        System.out.println("Getting all shifts");
        try {
            List<TableShift> shifts = tableShiftService.getAllTableShifts();
            Response<List<TableShift>> response = Response.<List<TableShift>>builder().success(true)
                    .data(shifts).message("Shifts retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting shifts");
            Response<List<TableShift>> response = Response.<List<TableShift>>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }

    @PutMapping("/shift/edit")
    public ResponseEntity<Response<Object>> editShift(
            @RequestParam("shiftId") int shiftId,
            @RequestBody TableShift updatedShift
    ) {
        System.out.println("Editing shift with id: " + shiftId);
        try {
            TableShift shift = tableShiftService.editTableShift(shiftId, updatedShift);
            Response<Object> response = Response.<Object>builder().success(true)
                    .data(shift).message("Shift retrieved successfully").build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error getting shift");
            Response<Object> response = Response.<Object>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }

    @DeleteMapping("/shift/delete")
    public ResponseEntity<Response<Object>> deleteShift(
            @RequestParam("shiftId") int shiftId
    ) {
        System.out.println("Deleting shift with id: " + shiftId);
        try {
            TableShift deletedShift= tableShiftService.deleteTableShift(shiftId);
            Response<Object> response = Response.<Object>builder().success(true)
                    .message("Shift deleted successfully").data(deletedShift).build();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("Error deleting shift");
            Response<Object> response = Response.<Object>builder().success(false)
                    .message(e.getMessage()).build();
            return ResponseEntity.ok(response);
        }
    }
}
