package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.*;
import com.ysu.xrandnet.repos.*;
import com.ysu.xrandnet.services.DBFileStorageService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/api/public")
public class PublicDataController {
    private final AnnouncementRepository announcementRepository;
    private final ReleaseNoteRepository releaseNoteRepository;
    private final BugRepository bugRepository;
    private final BooksRepository bookRepository;
    private final AboutInfoRepository aboutInfoRepository;
    private final UserManualFileRepository userManualFileRepository;
    private final SetupFileRepository setupFileRepository;
    private final com.ysu.xrandnet.services.DBFileStorageService dbFileStorageService;

    @Autowired
    public PublicDataController(AnnouncementRepository announcementRepository, ReleaseNoteRepository releaseNoteRepository,
                                BugRepository bugRepository, BooksRepository bookRepository, AboutInfoRepository aboutInfoRepository,
                                UserManualFileRepository userManualFileRepository, SetupFileRepository setupFileRepository,
                                DBFileStorageService dbFileStorageService) {
        this.announcementRepository = announcementRepository;
        this.releaseNoteRepository = releaseNoteRepository;
        this.bugRepository = bugRepository;
        this.bookRepository = bookRepository;
        this.aboutInfoRepository = aboutInfoRepository;
        this.userManualFileRepository = userManualFileRepository;
        this.setupFileRepository = setupFileRepository;
        this.dbFileStorageService = dbFileStorageService;
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
        DBFile dbFile = this.dbFileStorageService.getFile(file.getId());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/releaseNotes/all")
    public @ResponseBody
    Iterable<ReleaseNote> getReleaseNotes() {
        return this.releaseNoteRepository.findAll();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/books/all")
    public @ResponseBody
    Iterable<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/about")
    public @ResponseBody
    AboutInfo getAboutInfo() {
        return this.aboutInfoRepository.findAll().get(0);
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
}
