package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.File;
import com.ysu.xrandnet.models.SetupFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetupFileRepository extends JpaRepository<SetupFile, String> {
    @Query(value = "SELECT file_name,files.id FROM xrandnet.files where dtype = 'SetupFile' order by created_at desc ", nativeQuery = true)
    public List<File> findAllFileNameAndIds();
}