package com.ysu.xrandnet.models;

import javax.persistence.*;

@Entity
@Table(name = "release_notes")
public class ReleaseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;

    @Column(name = "version", unique = true, nullable = false)
    private String version;

    @Column(name = "description", nullable = false)
    private String description;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
