package com.zkt.zktspringjpa.service;


import com.zkt.zktspringjpa.model.TableShift;
import com.zkt.zktspringjpa.repository.TableShiftRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableShiftService {

    @Autowired
    private TableShiftRepository tableShiftRepository;

    public TableShiftService() {
    }

    public List<TableShift> getAllTableShifts() {
        return tableShiftRepository.findAll();
    }

    public TableShift editTableShift(int shiftId, TableShift newShift) {
        TableShift existingShift = tableShiftRepository.findById(shiftId).orElse(null);
        if (existingShift != null) {
            existingShift.setName(newShift.getName());
            existingShift.setShiftStart(newShift.getShiftStart());
            existingShift.setShiftEnd(newShift.getShiftEnd());
            existingShift.setBreakStart(newShift.getBreakStart());
            existingShift.setBreakEnd(newShift.getBreakEnd());
            existingShift.setType(newShift.getType());

            tableShiftRepository.save(existingShift);
        }
        return existingShift;
    }

    public TableShift deleteTableShift(int shiftId) {
        TableShift existingShift = tableShiftRepository.findById(shiftId).orElse(null);
        if (existingShift != null) {
            tableShiftRepository.delete(existingShift);
        }
        return existingShift;
    }
}
