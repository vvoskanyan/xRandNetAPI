package com.ysu.xrandnet.models;

import javax.persistence.*;

@Entity
@Table(name = "about_info")
public class AboutInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private int id;

    @Column(name = "content",columnDefinition = "TEXT")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
