package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.Software;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareRepository extends JpaRepository<Software, Integer> {
    Software findByVersion(String version);

    @Query(value = "SELECT version FROM xrandnet.software", nativeQuery = true)
    List<String> getAllVersions();
}
