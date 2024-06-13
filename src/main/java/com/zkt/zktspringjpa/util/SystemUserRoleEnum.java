package com.zkt.zktspringjpa.util;

public enum SystemUserRoleEnum {
    USER_DEFAULT(0),
    USER_ADMIN(1);

    private final int role;

    private SystemUserRoleEnum(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }

}
