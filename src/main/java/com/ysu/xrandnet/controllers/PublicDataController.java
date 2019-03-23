package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.exceptions.BadRequestException;
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
    private final PersonRepository personRepository;
    private final LinkRepository linkRepository;
    private final BooksRepository bookRepository;
    private final AboutInfoRepository aboutInfoRepository;
    private final UserManualFileRepository userManualFileRepository;
    private final SoftwareRepository softwareRepository;
    private final SetupFileRepository setupFileRepository;
    private final com.ysu.xrandnet.services.DBFileStorageService dbFileStorageService;

    @Autowired
    public PublicDataController(AnnouncementRepository announcementRepository, ReleaseNoteRepository releaseNoteRepository,
                                BooksRepository bookRepository, AboutInfoRepository aboutInfoRepository,
                                UserManualFileRepository userManualFileRepository, SoftwareRepository softwareRepository, SetupFileRepository setupFileRepository,
                                DBFileStorageService dbFileStorageService, LinkRepository linkRepository, PersonRepository personRepository) {
        this.announcementRepository = announcementRepository;
        this.releaseNoteRepository = releaseNoteRepository;
        this.personRepository = personRepository;
        this.bookRepository = bookRepository;
        this.aboutInfoRepository = aboutInfoRepository;
        this.userManualFileRepository = userManualFileRepository;
        this.setupFileRepository = setupFileRepository;
        this.softwareRepository = softwareRepository;
        this.dbFileStorageService = dbFileStorageService;
        this.linkRepository = linkRepository;
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
        List<SetupFile> arrayList = this.setupFileRepository.findAll();
        JSONArray jsonArray = new JSONArray();
        arrayList.forEach((file) -> {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", file.getId());
                jsonObject.put("name", file.getFileName());
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
        UserManualFile file = this.userManualFileRepository.findAll().get(0);
        DBFile dbFile = this.dbFileStorageService.getUserManualFile(file.getId());
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
    @GetMapping(path = "/releaseNotes/{version}")
    public @ResponseBody
    Iterable<ReleaseNote> getReleaseNotes(@PathVariable("version") String softwareVersion) {
        return this.releaseNoteRepository.findBySoftware_Version(softwareVersion);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/books/all")
    public @ResponseBody
    Iterable<Book> getBooks() {
        return this.bookRepository.findAll();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/people/all")
    public @ResponseBody
    Iterable<Person> getPeople() {
        return this.personRepository.findAll();
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/links/{type}/all")
    public @ResponseBody
    Iterable<Link> getLiteratureLinks(@PathVariable("type") String type) {
        if (type.equals("general"))
            return this.linkRepository.findLinkByType(LinkType.GENERAL);
        else if (type.equals("literature")) {
            return this.linkRepository.findLinkByType(LinkType.LITERATURE);
        }
        throw new BadRequestException("Unknown type of link.");
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/about")
    public @ResponseBody
    AboutInfo getAboutInfo() {
        List<AboutInfo> list = this.aboutInfoRepository.findAll();
        if (list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/softwareVersions")
    public @ResponseBody
    Iterable<String> getSoftwareVersions() {
        return softwareRepository.getAllVersions();
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
