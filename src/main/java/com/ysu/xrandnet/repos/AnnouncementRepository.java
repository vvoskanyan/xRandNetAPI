package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository extends JpaRepository<Announcement, Integer> {
    Announcement findById(int id);
}