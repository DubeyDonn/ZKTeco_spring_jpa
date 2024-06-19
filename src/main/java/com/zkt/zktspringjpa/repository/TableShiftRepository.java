package com.zkt.zktspringjpa.repository;

import com.zkt.zktspringjpa.model.TableShift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableShiftRepository extends JpaRepository<TableShift, Integer>{
}
