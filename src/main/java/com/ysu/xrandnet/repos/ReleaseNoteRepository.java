package com.ysu.xrandnet.repos;


import com.ysu.xrandnet.models.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReleaseNoteRepository extends JpaRepository<ReleaseNote, Integer> {
    ReleaseNote findById(int id);
    List<ReleaseNote>
    findBySoftware_Version(String version);
}