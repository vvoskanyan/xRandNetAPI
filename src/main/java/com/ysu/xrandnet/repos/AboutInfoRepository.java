package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.AboutInfo;
import org.springframework.data.repository.CrudRepository;

public interface AboutInfoRepository extends CrudRepository<AboutInfo, Integer> {
    AboutInfo findById(int id);
}
