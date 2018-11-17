package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.ReleaseNote;
import org.springframework.data.repository.CrudRepository;

public interface ReleaseNoteRepository extends CrudRepository<ReleaseNote, Integer> {
    ReleaseNote findById(int id);
}