package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Announcement;
import org.springframework.data.repository.CrudRepository;

public interface AnnouncementRepository extends CrudRepository<Announcement, Integer> {
    Announcement findById(int id);
}