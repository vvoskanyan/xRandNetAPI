package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.AboutInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AboutInfoRepository extends JpaRepository<AboutInfo, Integer> {
    AboutInfo findById(int id);
}
