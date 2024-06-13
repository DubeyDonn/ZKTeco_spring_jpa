package com.zkt.zktspringjpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zkt.zktspringjpa.model.SystemUser;

public interface SystemUserRepository extends JpaRepository<SystemUser, Long> {

    Optional<SystemUser> findByUsername(String username);

    Boolean existsByUsername(String username);

}
