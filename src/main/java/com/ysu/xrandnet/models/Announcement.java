package com.ysu.xrandnet.models;

import javax.persistence.*;

@Entity
@Table(name = "announcements")
public class Announcement extends DateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
