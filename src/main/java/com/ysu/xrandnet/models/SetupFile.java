package com.ysu.xrandnet.models;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "setup_files")
public class SetupFile extends DBFile {

    @OneToOne(mappedBy = "setupFile")
    private Software software;

    public SetupFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public SetupFile() {

    }


}
