package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.*;
import com.ysu.xrandnet.payloads.UploadFileResponse;
import com.ysu.xrandnet.repos.*;
import com.ysu.xrandnet.services.DBFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.Access;

@Controller
@RequestMapping(path = "/api/private")
public class PrivateDataController {
    private final com.ysu.xrandnet.services.DBFileStorageService dbFileStorageService;
    private final AnnouncementRepository announcementRepository;
    private final ReleaseNoteRepository releaseNoteRepository;
    private final BugRepository bugRepository;
    private final BooksRepository bookRepository;
    private final AboutInfoRepository aboutInfoRepository;
    private final UserManualFileRepository userManualFileRepository;
    private final SetupFileRepository setupFileRepository;

    @Autowired
    public PrivateDataController(AnnouncementRepository announcementRepository, ReleaseNoteRepository releaseNoteRepository,
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
    @PostMapping("/uploadSetupFile")
    public @ResponseBody
    UploadFileResponse uploadSetupFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeSetupFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/uploadUserManualFile")
    public @ResponseBody
    UploadFileResponse uploadUserManualFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = dbFileStorageService.storeUserManual(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/bugs/add", consumes = {"application/json"})
    public @ResponseBody
    Bug addNewBug(@RequestBody Bug bug) {
        this.bugRepository.save(bug);
        return bug;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/releaseNotes/add", consumes = {"application/json"})
    public @ResponseBody
    ReleaseNote addNewReleaseNote(@RequestBody ReleaseNote releaseNote) {
        this.releaseNoteRepository.save(releaseNote);
        return releaseNote;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/announcements/add", consumes = {"application/json"})
    public @ResponseBody
    Announcement addNewAnnouncement(@RequestBody Announcement announcement) {
        this.announcementRepository.save(announcement);
        return announcement;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/about/edit", consumes = {"application/json"})
    public @ResponseBody
    AboutInfo editAboutInfo(@RequestBody AboutInfo aboutInfo) {
        AboutInfo infoToBeUpdated = this.aboutInfoRepository.findAll().get(0);
        if (infoToBeUpdated == null) {
            infoToBeUpdated = new AboutInfo();
        }
        infoToBeUpdated.setContent(aboutInfo.getContent());
        this.aboutInfoRepository.deleteAll();
        this.aboutInfoRepository.save(infoToBeUpdated);
        return aboutInfo;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/bugs/all")
    public @ResponseBody
    Iterable<Bug> getBugs() {
        return this.bugRepository.findAll();
    }


//    @PostMapping("/uploadFile")
//    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
//        DBFile dbFile = DBFileStorageService.storeFile(file);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(dbFile.getId())
//                .toUriString();
//
//        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
//                file.getContentType(), file.getSize());
//    }


//
//    @PostMapping("/uploadMultipleFiles")
//    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
//        return Arrays.stream(files)
//                .map(this::uploadFile)
//                .collect(Collectors.toList());
//    }


}
