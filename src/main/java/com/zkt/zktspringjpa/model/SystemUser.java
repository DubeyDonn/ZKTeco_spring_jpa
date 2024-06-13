package com.zkt.zktspringjpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

// import com.zkt.zktspringjpa.util.SystemUserRoleEnum;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class SystemUser {

    @Id
    private Long id;

    private String role;
    private String name;
    private String username;
    private String password;

    @Transient
    private LocalDateTime createdAt;

    @Transient
    private LocalDateTime updatedAt;

}
