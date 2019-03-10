package com.ysu.xrandnet.models;

public enum BugStatus {
    OPEN(0),
    FIXED(1),
    NOT_A_BUG(2);

    private final int bugStatusId;

    private BugStatus(int bugStatusId) {
        this.bugStatusId = bugStatusId;
    }

    public int getBugStatusId() {
        return bugStatusId;
    }

    public static BugStatus getFromValue(int value) throws Exception {
        switch (value) {
            case 0:
                return BugStatus.OPEN;
            case 1:
                return BugStatus.FIXED;
            case 2:
                return BugStatus.NOT_A_BUG;
            default:
                throw new Exception("Invalid value");
        }

    }
}
