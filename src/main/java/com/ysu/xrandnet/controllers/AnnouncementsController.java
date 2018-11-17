package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.Announcement;
import com.ysu.xrandnet.repos.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/announcements")
public class AnnouncementsController {
    private final AnnouncementRepository announcementRepository;

    @Autowired
    public AnnouncementsController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/add", consumes = {"application/json"})
    public @ResponseBody
    Announcement addNewAnnouncement(@RequestBody Announcement announcement) {
        this.announcementRepository.save(announcement);
        return announcement;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Announcement> getAnnouncements() {
        return this.announcementRepository.findAll();
    }
}
