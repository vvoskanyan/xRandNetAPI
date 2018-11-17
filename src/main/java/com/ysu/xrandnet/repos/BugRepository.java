package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Bug;
import org.springframework.data.repository.CrudRepository;

public interface BugRepository extends CrudRepository<Bug, Integer> {
    Bug findById(int id);
}
