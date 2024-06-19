package com.zkt.zktspringjpa.repository;

import java.util.Optional;

import com.zkt.zktspringjpa.model.TableUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TableUserRepository extends JpaRepository<TableUser, Long> {


    Optional<TableUser> findByUsername(String username);

    Boolean existsByUsername(String username);

}
