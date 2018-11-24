package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Bug;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BugRepository extends JpaRepository<Bug, Integer> {
    Bug findById(int id);
}
