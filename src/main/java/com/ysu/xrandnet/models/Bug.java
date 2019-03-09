package com.ysu.xrandnet.models;

import javax.persistence.*;

@Entity
@Table(name = "bugs")
public class Bug extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;

    @Column(name = "summary")
    private String summary;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @ManyToOne
    @JoinColumn
    private Software software;

    public Bug(String summary, String description, Software software, String reporter) {
        this.summary = summary;
        this.description = description;
        this.software = software;
        this.reporter = reporter;
    }

    @Column(name = "reporter")
    private String reporter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }


    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }
}
