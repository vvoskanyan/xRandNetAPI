package com.ysu.xrandnet.models;

public enum LinkType {
    LITERATURE(0),
    GENERAL(1);

    private final int linkTypeId;

    private LinkType(int linkTypeId) {
        this.linkTypeId = linkTypeId;
    }

    public int getLinkTypeId() {
        return linkTypeId;
    }
}