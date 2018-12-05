package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.DBFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ysu.xrandnet.models.File;

import java.util.List;

@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
    @Query(value = "SELECT file_name,id FROM xrandnet.files", nativeQuery = true)
    public List<File> findAllFileNameAndIds();
}