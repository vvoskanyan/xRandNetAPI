package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.*;
import com.ysu.xrandnet.repos.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/publicData")
public class DataProviderController {
    private final AnnouncementRepository announcementRepository;
    private final ReleaseNoteRepository releaseNoteRepository;
    private final BugRepository bugRepository;
    private final AboutInfoRepository aboutInfoRepository;
    private final UserManualFileRepository userManualFileRepository;
    private final SetupFileRepository setupFileRepository;

    @Autowired
    public DataProviderController(AnnouncementRepository announcementRepository,
                                  ReleaseNoteRepository releaseNoteRepository,
                                  BugRepository bugRepository,
                                  AboutInfoRepository aboutInfoRepository,
                                  UserManualFileRepository userManualFileRepository,
                                  SetupFileRepository setupFileRepository) {
        this.announcementRepository = announcementRepository;
        this.releaseNoteRepository = releaseNoteRepository;
        this.bugRepository = bugRepository;
        this.aboutInfoRepository = aboutInfoRepository;
        this.userManualFileRepository = userManualFileRepository;
        this.setupFileRepository = setupFileRepository;
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
    @GetMapping(path = "/setupFiles/all")
    public @ResponseBody
    String getSetupFiles() {
        List<File> arrayList = this.setupFileRepository.findAllFileNameAndIds();
        JSONArray jsonArray = new JSONArray();
        arrayList.forEach((file) -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", file.getId());
                jsonObject.put("name", file.getFile_Name());
                jsonArray.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
        return jsonArray.toString();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/userManualFile")
    public @ResponseBody
    ResponseEntity getUserManualFile() {
        File file = this.userManualFileRepository.findAllFileNameAndId();
        if (file != null) {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", file.getId());
                jsonObject.put("name", file.getFile_Name());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return ResponseEntity.ok().body(jsonObject.toString());
        } else {
            return ResponseEntity.notFound().build();
        }
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
