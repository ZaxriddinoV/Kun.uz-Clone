package com.company.kunuz.Attach.service;

import com.company.kunuz.Attach.dto.AttachDTO;
import com.company.kunuz.Attach.entity.AttachEntity;
import com.company.kunuz.Attach.repository.AttachRepository;
import com.company.kunuz.ExceptionHandler.AppBadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {

    private String folderName = "attaches";
    @Autowired
    private AttachRepository attachRepository;

    public AttachDTO upload(MultipartFile file) {
        // attaches/2024/11/2
        String pathFolder = getYmDString(); // 2024/09/27
        String key = UUID.randomUUID().toString(); // dasdasd-dasdasda-asdasda-asdasd
        String extension = getExtension(file.getOriginalFilename()); // .jpg, .png, .mp4

        // create folder if not exists
        File folder = new File(folderName + "/" + pathFolder); // attaches/2024/09/27
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folderName + "/" + pathFolder + "/" + key + "." + extension);
            // attaches/2024/09/27/dasdasd-dasdasda-asdasda-asdasd.jpg
            Files.write(path, bytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        AttachEntity entity = new AttachEntity();
        entity.setId(key + "." + extension);
        entity.setPath(pathFolder);
        entity.setSize(file.getSize());
        entity.setOrigenName(file.getOriginalFilename());
        entity.setExtension(extension);
        entity.setVisible(true);
        attachRepository.save(entity);

        return toDTO(entity);
    }

    private String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);
        return year + "/" + month + "/" + day;
    }

    private String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    private AttachDTO toDTO(AttachEntity entity) {
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setOriginName(entity.getOrigenName());
        attachDTO.setSize(entity.getSize());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setCreatedData(entity.getCreatedDate());
        return attachDTO;
    }


    public ResponseEntity<Resource> open(String id) {
        AttachEntity entity = getEntity(id);
        String path = folderName + "/" + entity.getPath() + "/" + entity.getId();

        Path filePath = Paths.get(path).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource(filePath.toUri());
            if (!resource.exists()) {
                throw new RuntimeException("File not found: " + path);
            }
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public AttachEntity getEntity(String id) {
        Optional<AttachEntity> optional = attachRepository.findById(id);
        if (optional.isEmpty()) {
            throw new AppBadException("File not found");
        }
        return optional.get();
    }


}

