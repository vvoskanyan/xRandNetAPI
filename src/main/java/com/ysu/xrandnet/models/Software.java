package com.ysu.xrandnet.models;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "software")
public class Software {
    @Id
    @Column(name = "version", columnDefinition = "varchar(10)")
    private String version;

    @OneToMany(mappedBy = "software", cascade = CascadeType.ALL)
    private List<Bug> bugs;

    @OneToMany(mappedBy = "software", cascade = CascadeType.ALL)
    private List<ReleaseNote> releaseNotes;

    @OneToOne
    @JoinColumn(name = "file_id", referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SetupFile setupFile;

    public Software() {
        this.releaseNotes = new ArrayList<>();
        this.bugs = new ArrayList<>();
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

    public List<ReleaseNote> getReleaseNotes() {
        return releaseNotes;
    }

    public void setReleaseNotes(List<ReleaseNote> releaseNotes) {
        this.releaseNotes = releaseNotes;
    }

    public SetupFile getSetupFile() {
        return setupFile;
    }

    public void setSetupFile(SetupFile setupFile) {
        this.setupFile = setupFile;
    }
}
