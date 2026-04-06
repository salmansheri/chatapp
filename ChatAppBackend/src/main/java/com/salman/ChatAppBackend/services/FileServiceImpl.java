package com.salman.ChatAppBackend.services;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salman.ChatAppBackend.exceptions.ApiException;
import com.salman.ChatAppBackend.interfaces.FileService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import static java.io.File.separator;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileServiceImpl implements FileService{

//     application:
//   file:
//     uploads:
//       media-output:
//         path: ./uploads
    @Value("application.file.uploads.media-output.path")
    private String fileUploadPath; 

    @Override
    public String saveFile(@NonNull MultipartFile file, @NonNull String senderId) {
        final String fileUploadSubPath = fileUploadPath + "users" + separator +  senderId; 

        return uploadFile(file, fileUploadSubPath); 
       
    }

    private String uploadFile(MultipartFile file, String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        
        File targetFolder = new File(finalUploadPath);

        if (!targetFolder.exists()) {
            boolean folderCreated = targetFolder.mkdirs(); 

            if (!folderCreated) {
                log.warn("Failed to create the target folder, {}", targetFolder);
                return null;
            }
        }

        final String fileExtension = getFileExtension(file.getOriginalFilename()); 

        String targetFilePath = finalUploadPath + separator + System.currentTimeMillis() + fileExtension; 

        Path targetPath = Paths.get(targetFilePath); 

        try {
            Files.write(targetPath, file.getBytes()); 
            log.info("File saved to {}", targetPath);
            return targetFilePath; 

        } catch(IOException ex) {
            log.error("File was not saved", ex);
            throw new ApiException(ex.getMessage()); 
        }

       
        
    }

    private String getFileExtension(String originalFilename) {
       if (originalFilename == null || originalFilename.isEmpty()) {
        return ""; 
       }

       int lastDotIndex = originalFilename.lastIndexOf('.'); 

       if (lastDotIndex == -1) {
        return ""; 
       }

       return originalFilename.substring(lastDotIndex + 1).toLowerCase(); 
    }
    
    
}
