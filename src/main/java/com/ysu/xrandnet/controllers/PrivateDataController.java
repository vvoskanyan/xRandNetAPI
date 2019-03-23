package com.ysu.xrandnet.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ysu.xrandnet.exceptions.BadRequestException;
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

import java.util.List;

@Controller
@RequestMapping(path = "/api/private")
public class PrivateDataController {
    private final com.ysu.xrandnet.services.DBFileStorageService dbFileStorageService;
    private final AnnouncementRepository announcementRepository;
    private final ReleaseNoteRepository releaseNoteRepository;
    private final BugRepository bugRepository;
    private final BooksRepository bookRepository;
    private final SoftwareRepository softwareRepository;
    private final PersonRepository personRepository;
    private final AboutInfoRepository aboutInfoRepository;
    private final UserManualFileRepository userManualFileRepository;
    private final SetupFileRepository setupFileRepository;
    private final LinkRepository linkRepository;

    @Autowired
    public PrivateDataController(AnnouncementRepository announcementRepository, ReleaseNoteRepository releaseNoteRepository,
                                 BugRepository bugRepository, SoftwareRepository softwareRepository, BooksRepository bookRepository, AboutInfoRepository aboutInfoRepository,
                                 UserManualFileRepository userManualFileRepository, SetupFileRepository setupFileRepository,
                                 DBFileStorageService dbFileStorageService, LinkRepository linkRepository, PersonRepository personRepository) {
        this.announcementRepository = announcementRepository;
        this.releaseNoteRepository = releaseNoteRepository;
        this.bugRepository = bugRepository;
        this.softwareRepository = softwareRepository;
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
        this.aboutInfoRepository = aboutInfoRepository;
        this.userManualFileRepository = userManualFileRepository;
        this.setupFileRepository = setupFileRepository;
        this.dbFileStorageService = dbFileStorageService;
        this.linkRepository = linkRepository;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/uploadSoftware")
    public @ResponseBody
    UploadFileResponse uploadSetupFile(@RequestParam("file") MultipartFile file, @RequestParam("software") String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        Software software = null;
        try {
            JsonNode jsonNode = mapper.reader().readTree(jsonString);
            String version = jsonNode.get("_version").asText();
            if (!this.softwareRepository.existsByVersion(version)) {
                software = new Software();
                software.setVersion(version);
                ArrayNode releaseNotes = (ArrayNode) jsonNode.get("_releaseNotes");
                for (final JsonNode objNode : releaseNotes) {
                    software.getReleaseNotes().add(new ReleaseNote(software, objNode.get("_description").asText()));
                }
            } else {
                throw new BadRequestException("There is a software with specified version " + version);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Could not save the software.", e);
        }
        SetupFile dbFile = (SetupFile) dbFileStorageService.storeSetupFile(file);
        software.setSetupFile(dbFile);
        this.softwareRepository.save(software);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path(dbFile.getId())
                .toUriString();
        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @PostMapping("/uploadUserManualFile")
    public @ResponseBody
    UploadFileResponse uploadUserManualFile(@RequestParam("file") MultipartFile file) {
        UserManualFile dbFile = (UserManualFile) dbFileStorageService.storeUserManual(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/bugs/add", consumes = {"application/json"})
    public @ResponseBody
    Bug addNewBug(@RequestBody String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.reader().readTree(jsonString);
            String reporter = jsonNode.get("reporter").asText();
            String summary = jsonNode.get("summary").asText();
            String description = jsonNode.get("description").asText();
            String version = jsonNode.get("version").asText();
            Software software = this.softwareRepository.findByVersion(version);
            return this.bugRepository.save(new Bug(summary, description, software, reporter));
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Could not save the bug object.", e);
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/bugs/delete", consumes = {"application/json"})
    public @ResponseBody
    void deleteBug(@RequestBody String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.reader().readTree(jsonString);
            int id = jsonNode.get("id").asInt();
            if (this.bugRepository.existsById(id)) {
                this.bugRepository.deleteById(id);
            } else {
                throw new BadRequestException("There is no bug with id " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Could not delete the bug.", e);
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/bugs/edit", consumes = {"application/json"})
    public @ResponseBody
    void editBug(@RequestBody String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.reader().readTree(jsonString);
            int id = jsonNode.get("id").asInt();
            int statusValue = jsonNode.get("status").asInt();
            if (this.bugRepository.existsById(id)) {
                Bug bug = this.bugRepository.findById(id);
                bug.setStatus(BugStatus.getFromValue(statusValue));
                this.bugRepository.save(bug);
            } else {
                throw new BadRequestException("There is no bug with id " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("Could not edit the bug.", e);
        }
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/people/add", consumes = {"application/json"})
    public @ResponseBody
    Person addNewPerson(@RequestBody Person person) {
        this.personRepository.save(person);
        return person;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/links/add", consumes = {"application/json"})
    public @ResponseBody
    Link addNewLink(@RequestBody Link link) {
        this.linkRepository.save(link);
        return link;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/links/delete", consumes = {"application/json"})
    public @ResponseBody
    void removeLink(@RequestBody Link link) {
        this.linkRepository.delete(link);
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/links/edit", consumes = {"application/json"})
    public @ResponseBody
    void editLink(@RequestBody Link link) {
        Link existingLink = this.linkRepository.findById(link.getId());
        if (existingLink != null) {
            existingLink.setName(link.getName());
            existingLink.setUrl(link.getUrl());
            this.linkRepository.save(link);
        }
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
        AboutInfo infoToBeUpdated = null;
        if (this.aboutInfoRepository.findAll().isEmpty()) {
            infoToBeUpdated = new AboutInfo();
        } else {
            infoToBeUpdated = this.aboutInfoRepository.findAll().get(0);
        }
        infoToBeUpdated.setContent(aboutInfo.getContent());
        this.aboutInfoRepository.deleteAll();
        this.aboutInfoRepository.save(infoToBeUpdated);
        return aboutInfo;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/bugs/all")
    public @ResponseBody
    String getBugs() {
        ArrayNode jsonArrayOfBugs = new ObjectMapper().createArrayNode();
        List<Bug> listOfBugs = this.bugRepository.findAll();
        for (Bug bug : listOfBugs) {
            jsonArrayOfBugs.add(bug.toJson());
        }
        return jsonArrayOfBugs.toString();
    }


}
