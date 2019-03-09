package com.ysu.xrandnet.models;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user_manual_files")
public class UserManualFile extends DBFile {


    public UserManualFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public UserManualFile() {
    }

}