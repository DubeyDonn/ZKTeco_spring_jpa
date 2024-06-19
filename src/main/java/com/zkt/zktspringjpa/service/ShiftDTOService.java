package com.zkt.zktspringjpa.service;


import com.zkt.zktspringjpa.model.TableShift;
import com.zkt.zktspringjpa.model.dto.ShiftDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShiftDTOService {

    @Autowired
    private TableShiftService tableShiftService;

    public ShiftDTOService() {
    }

    public List<ShiftDTO> getAllShiftDTOs() {
        List<TableShift> shifts = tableShiftService.getAllTableShifts();
        return this.buildShiftDTOsFromShifts(shifts);
    }

    public ShiftDTO buildShiftDTOFromShift(TableShift shift) {
        if (shift == null) {
            return null;
        }

        ShiftDTO shiftDTO = new ShiftDTO();
        shiftDTO.setShiftStart(shift.getShiftStart());
        shiftDTO.setShiftEnd(shift.getShiftEnd());
        shiftDTO.setBreakStart(shift.getBreakStart());
        shiftDTO.setBreakEnd(shift.getBreakEnd());
        return shiftDTO;
    }

    public List<ShiftDTO> buildShiftDTOsFromShifts(List<TableShift> shifts) {
        List<ShiftDTO> shiftDTOs = new ArrayList<>();

        for (TableShift shift : shifts) {
            ShiftDTO shiftDTO = this.buildShiftDTOFromShift(shift);
        }

        return shiftDTOs;
    }


}
