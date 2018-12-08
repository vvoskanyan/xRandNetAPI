package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.File;
import com.ysu.xrandnet.models.UserManualFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManualFileRepository extends JpaRepository<UserManualFile, String> {
    @Query(value = "SELECT file_name,files.id FROM xrandnet.files where dtype = 'UserManualFile' order by created_at desc limit 1", nativeQuery = true)
    public File findAllFileNameAndId();
}