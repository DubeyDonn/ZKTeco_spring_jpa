package com.zkt.zktspringjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.zkt.zktspringjpa.model.TableStaff;

@Repository
public interface TableStaffRepository extends JpaRepository<TableStaff, String> {

    // get user name by user id
    @Query("SELECT distinct mui.name FROM TableStaff mui " +
            "Inner Join TableAttendanceRecord mar ON mui.userId = mar.userId WHERE mui.userId = :userId")
    String findNameByUserID(@Param("userId") Integer userId);

//    TableStaff findByName(String name);

//    TableStaff findByUserId(String userId);

    //find all order by name
    @Query("SELECT mui FROM TableStaff mui ORDER BY mui.name")
    Iterable<TableStaff> findAllOrderByName();
}
