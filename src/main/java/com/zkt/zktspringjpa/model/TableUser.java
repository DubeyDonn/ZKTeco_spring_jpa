package com.zkt.zktspringjpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

import java.time.LocalDateTime;

// import com.zkt.zktspringjpa.util.SystemUserRoleEnum;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user")
public class TableUser {

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
