package com.ysu.xrandnet.repos;

import com.ysu.xrandnet.models.UserManualFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManualFileRepository extends JpaRepository<UserManualFile, String> {
}