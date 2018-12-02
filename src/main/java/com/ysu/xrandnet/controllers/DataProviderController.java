package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.AboutInfo;
import com.ysu.xrandnet.models.Announcement;
import com.ysu.xrandnet.models.Bug;
import com.ysu.xrandnet.models.ReleaseNote;
import com.ysu.xrandnet.repos.AboutInfoRepository;
import com.ysu.xrandnet.repos.AnnouncementRepository;
import com.ysu.xrandnet.repos.BugRepository;
import com.ysu.xrandnet.repos.ReleaseNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class DataProviderController {
    private final AnnouncementRepository announcementRepository;
    private final ReleaseNoteRepository releaseNoteRepository;
    private final BugRepository bugRepository;
    private final AboutInfoRepository aboutInfoRepository;

    @Autowired
    public DataProviderController(AnnouncementRepository announcementRepository,
                                  ReleaseNoteRepository releaseNoteRepository,
                                  BugRepository bugRepository,
                                  AboutInfoRepository aboutInfoRepository) {
        this.announcementRepository = announcementRepository;
        this.releaseNoteRepository = releaseNoteRepository;
        this.bugRepository = bugRepository;
        this.aboutInfoRepository = aboutInfoRepository;

    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/announcements/add", consumes = {"application/json"})
    public @ResponseBody
    Announcement addNewAnnouncement(@RequestBody Announcement announcement) {
        this.announcementRepository.save(announcement);
        return announcement;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/announcements/all")
    public @ResponseBody
    Iterable<Announcement> getAnnouncements() {
        return this.announcementRepository.findAll();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/releaseNotes/add", consumes = {"application/json"})
    public @ResponseBody
    ReleaseNote addNewReleaseNote(@RequestBody ReleaseNote releaseNote) {
        this.releaseNoteRepository.save(releaseNote);
        return releaseNote;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/releaseNotes/all")
    public @ResponseBody
    Iterable<ReleaseNote> getReleaseNotes() {
        return this.releaseNoteRepository.findAll();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/bugs/add", consumes = {"application/json"})
    public @ResponseBody
    Bug addNewBug(@RequestBody Bug bug) {
        this.bugRepository.save(bug);
        return bug;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/bugs/all")
    public @ResponseBody
    Iterable<Bug> getBugs() {
        return this.bugRepository.findAll();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/about")
    public @ResponseBody
    AboutInfo getAboutInfo() {
        return this.aboutInfoRepository.findById(1);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/about/edit", consumes = {"application/json"})
    public @ResponseBody
    AboutInfo editAboutInfo(@RequestBody AboutInfo aboutInfo) {
        AboutInfo infoToBeUpdated = this.aboutInfoRepository.findById(1);
        if (infoToBeUpdated == null) {
            infoToBeUpdated = new AboutInfo();
        }
        infoToBeUpdated.setContent(aboutInfo.getContent());
        this.aboutInfoRepository.save(infoToBeUpdated);
        return aboutInfo;
    }
}
