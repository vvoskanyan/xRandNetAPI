package com.ysu.xrandnet.services;

import com.ysu.xrandnet.exceptions.FileStorageException;
import com.ysu.xrandnet.exceptions.MyFileNotFoundException;
import com.ysu.xrandnet.models.DBFile;
import com.ysu.xrandnet.models.SetupFile;
import com.ysu.xrandnet.models.UserManualFile;
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

    private final SetupFileRepository setupFileRepository;
    private final UserManualFileRepository userManualFileRepository;

    @Autowired
    public DBFileStorageService(SetupFileRepository setupFileRepository, UserManualFileRepository userManualFileRepository) {
        this.setupFileRepository = setupFileRepository;
        this.userManualFileRepository = userManualFileRepository;
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
                throw new FileStorageException("Unknown file type");
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

    public DBFile getUserManualFile(String fileId) {
        return userManualFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    public SetupFile getSetupFile(String fileId) {
        return setupFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

    public DBFile getFile(String fileId) {
        if (this.setupFileRepository.existsById(fileId)) {
            return setupFileRepository.findById(fileId)
                    .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
        } else if (this.userManualFileRepository.existsById(fileId)) {
            return userManualFileRepository.findById(fileId)
                    .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
        } else {
            throw new MyFileNotFoundException("File not found with id " + fileId);
        }
    }
}