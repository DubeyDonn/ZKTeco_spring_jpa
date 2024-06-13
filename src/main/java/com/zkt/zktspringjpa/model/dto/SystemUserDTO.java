package com.zkt.zktspringjpa.model.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SystemUserDTO {
    @Id
    private Long id;

    private Collection<? extends GrantedAuthority> roles;
    private String name;
    private String username;

}