package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.SetupFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SetupFileRepository extends JpaRepository<SetupFile, String> {
}