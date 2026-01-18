package org.app.engihub.controller;

import org.app.engihub.dto.FileDTO;
import org.app.engihub.pagination.ResourcePageRequest;
import org.app.engihub.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private ResourcePageRequest resourcePageRequest;

    @PostMapping("/files/upload")
    public ResponseEntity<Map<String,Object>> createResource(@RequestBody FileDTO fileDTO, MultipartFile file) {
        return new ResponseEntity<Map<String, Object>>(resourceService.uploadFile(file,fileDTO), HttpStatus.CREATED);
    }

    @GetMapping("/files/{fileId}")
    public ResponseEntity<FileDTO> fetchFileInfoByID(@PathVariable Long fileId) {
        return new ResponseEntity<>(resourceService.fetchFileInfoByID(fileId),HttpStatus.OK);
    }

    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<Map<String,Object>> DeleteFileInfoByID(@PathVariable Long fileId) {
        return new ResponseEntity<>(resourceService.deleteFileById(fileId),HttpStatus.OK);
    }

    @GetMapping("/files")
    public ResponseEntity<Page<FileDTO>> getAllResource(int pageNo, int pageSize, String sortBy) {
        return new ResponseEntity<>(resourceService.fetchAllFiles(resourcePageRequest.pageRequest(pageNo,pageSize,sortBy)),HttpStatus.OK);
    }
}
