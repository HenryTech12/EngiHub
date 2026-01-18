package org.app.engihub.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.extern.slf4j.Slf4j;
import org.app.engihub.dto.FileDTO;
import org.app.engihub.mapper.FileMapper;
import org.app.engihub.model.FileModel;
import org.app.engihub.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Service
public class ResourceService {

    private final Cloudinary cloudinary;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private ResourceRepository resourceRepository;

    public ResourceService() {
        Dotenv dotenv = Dotenv.load();
        cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        System.out.println(cloudinary.config.cloudName);
    }

    public Map<String,Object> uploadFile(MultipartFile file, FileDTO fileDTO) {
        Map cloudinaryDetail = fetchCloudinary(file);
        System.out.println(cloudinaryDetail);
        FileModel fileModel = fileMapper.convertToModel(fileDTO);
        return Map.of("message","File uploaded successfully", "fileId",fileModel.getFileId());
    }

    public FileDTO fetchFileInfoByID(Long fileId) {
        return resourceRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File Not found"))
                .getFileDTO();
    }

    public Map<String,Object> deleteFileById(Long id) {
        resourceRepository.deleteById(id);
        log.info("file with id: {} deleted",id);
        return Map.of("message","File deleted successfully");
    }

    public Map fetchCloudinary(MultipartFile file) {

        Map params = ObjectUtils.asMap(
                "use_filename", true);
        try {
            Map uploadResult = cloudinary.uploader().upload(file, params);
            return uploadResult;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Page<FileDTO> fetchAllFiles(Pageable pageable) {
        return resourceRepository.findAll(pageable)
                .map(fileMapper::convertToDTO);
    }

}
