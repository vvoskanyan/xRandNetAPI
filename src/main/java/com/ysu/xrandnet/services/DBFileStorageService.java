package com.ysu.xrandnet.services;

import com.ysu.xrandnet.exceptions.FileStorageException;
import com.ysu.xrandnet.exceptions.MyFileNotFoundException;
import com.ysu.xrandnet.models.DBFile;
import com.ysu.xrandnet.models.SetupFile;
import com.ysu.xrandnet.models.UserManualFile;
import com.ysu.xrandnet.repos.DBFileRepository;
import com.ysu.xrandnet.repos.SetupFileRepository;
import com.ysu.xrandnet.repos.UserManualFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class DBFileStorageService {

    private final DBFileRepository dbFileRepository;
    private final SetupFileRepository setupFileRepository;
    private final UserManualFileRepository userManualFileRepository;

    @Autowired
    public DBFileStorageService(DBFileRepository dbFileRepository, SetupFileRepository setupFileRepository, UserManualFileRepository userManualFileRepository) {
        this.dbFileRepository = dbFileRepository;
        this.setupFileRepository = setupFileRepository;
        this.userManualFileRepository = userManualFileRepository;
    }

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());

            return (DBFile) this.dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile storeFile(MultipartFile file, JpaRepository repository) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            if (repository instanceof SetupFileRepository) {
                SetupFile dbFile = new SetupFile(fileName, file.getContentType(), file.getBytes());
                return this.setupFileRepository.save(dbFile);

            } else if (repository instanceof UserManualFileRepository) {
                UserManualFile dbFile = new UserManualFile(fileName, file.getContentType(), file.getBytes());
                this.userManualFileRepository.deleteAll();
                return this.userManualFileRepository.save(dbFile);
            } else {
                DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes());
                return this.dbFileRepository.save(dbFile);
            }

        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile storeUserManual(MultipartFile userManualFile) {
        return this.storeFile(userManualFile, this.userManualFileRepository);
    }

    public DBFile storeSetupFile(MultipartFile setupFile) {
        return this.storeFile(setupFile, this.setupFileRepository);

    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }
}