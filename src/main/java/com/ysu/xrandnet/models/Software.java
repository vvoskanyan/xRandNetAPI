package com.ysu.xrandnet.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "software")
public class Software {
    @Id
    @Column(name = "version", columnDefinition = "varchar(10)")
    private String version;

    @OneToMany(mappedBy = "software", cascade = CascadeType.ALL)
    private List<Bug> bugs;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private SetupFile setupFile;

    public Software() {
    }

    public Software(String version, List<Bug> bugs, SetupFile setupFile) {
        this.version = version;
        this.bugs = bugs;
        this.setupFile = setupFile;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<Bug> getBugs() {
        return bugs;
    }

    public void setBugs(List<Bug> bugs) {
        this.bugs = bugs;
    }

    public SetupFile getSetupFile() {
        return setupFile;
    }

    public void setSetupFile(SetupFile setupFile) {
        this.setupFile = setupFile;
    }
}
