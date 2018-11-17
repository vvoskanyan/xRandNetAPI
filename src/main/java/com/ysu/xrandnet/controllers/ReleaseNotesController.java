package com.ysu.xrandnet.controllers;

import com.ysu.xrandnet.models.ReleaseNote;
import com.ysu.xrandnet.repos.ReleaseNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/releaseNotes")
public class ReleaseNotesController {
    private final ReleaseNoteRepository releaseNoteRepository;

    @Autowired
    public ReleaseNotesController(ReleaseNoteRepository releaseNoteRepository) {
        this.releaseNoteRepository = releaseNoteRepository;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST, path = "/add", consumes = {"application/json"})
    public @ResponseBody
    ReleaseNote addNewReleaseNote(@RequestBody ReleaseNote releaseNote) {
        this.releaseNoteRepository.save(releaseNote);
        return releaseNote;
    }

    @ResponseStatus(code = HttpStatus.OK)
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<ReleaseNote> getReleaseNotes() {
        return this.releaseNoteRepository.findAll();
    }

}
