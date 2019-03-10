package com.ysu.xrandnet.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Column(name = "status")
    private BugStatus status;

    @ManyToOne
    @JoinColumn(name = "software_version", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Software software;

    public Bug(String summary, String description, Software software, String reporter) {
        this.summary = summary;
        this.description = description;
        this.software = software;
        this.reporter = reporter;
        this.status = BugStatus.OPEN;
    }

    public Bug() {
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

    public BugStatus getStatus() {
        return status;
    }

    public void setStatus(BugStatus status) {
        this.status = status;
    }

    public ObjectNode toJson() {
        ObjectNode node = new ObjectMapper().createObjectNode();
        node.put("id", this.id);
        node.put("summary", this.summary);
        node.put("version", this.software.getVersion());
        node.put("reporter", this.reporter);
        node.put("status", this.status.getBugStatusId());
        node.put("description", this.description);
        node.put("createdAt", this.getCreatedAt().toString());
        node.put("updatedAt", this.getUpdatedAt().toString());
        return node;
    }
}
