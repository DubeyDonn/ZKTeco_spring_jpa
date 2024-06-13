package com.zkt.zktspringjpa.security.service;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zkt.zktspringjpa.model.SystemUser;

/**
 * This class is created so that we can provide our own implementation of
 * UserDetails interface and provide more details about the user other than
 * username and password and authorities(roles)
 */
public class CustomUserDetails implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(Long id, String name, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static CustomUserDetails build(SystemUser user) {
        // List<GrantedAuthority> authorities = user.getRoles().stream()
        // .map(role -> new SimpleGrantedAuthority(role.getName().name()))
        // .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRole())));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    // public SystemUser getUser() {
    // return new SystemUser(id, authorities, name, username, password);
    // }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CustomUserDetails user = (CustomUserDetails) o;
        return Objects.equals(id, user.id);
    }
}
