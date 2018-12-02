package com.ysu.xrandnet.models;

public enum RoleName {
    ROLE_USER(1),
    ROLE_ADMIN(2);

    private final int roleNameId;

    private RoleName(int roleNameId) {
        this.roleNameId = roleNameId;
    }

    public int getRoleNameId() {
        return roleNameId;
    }
}
