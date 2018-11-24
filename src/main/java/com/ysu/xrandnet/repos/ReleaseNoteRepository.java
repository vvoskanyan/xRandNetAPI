package com.ysu.xrandnet.repos;


import com.ysu.xrandnet.models.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseNoteRepository extends JpaRepository<ReleaseNote, Integer> {
    ReleaseNote findById(int id);
}