package com.ysu.xrandnet.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "image_files")
public class ImageFile extends DBFile {

    @OneToOne(mappedBy = "imageFile", cascade = CascadeType.ALL)
    private Person person;

    public ImageFile(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public ImageFile() {

    }


}
