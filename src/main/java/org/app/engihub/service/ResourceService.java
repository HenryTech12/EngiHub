package org.app.engihub.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.app.engihub.dto.FileDTO;
import org.app.engihub.mapper.FileMapper;
import org.app.engihub.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ResourceService {

    private final Cloudinary cloudinary;
    @Autowired
    private FileMapper fileMapper;
    public ResourceService() {
        Dotenv dotenv = Dotenv.load();
        cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        System.out.println(cloudinary.config.cloudName);
    }

    public FileDTO uploadFile(MultipartFile file, FileDTO fileDTO) {
        Map cloudinaryDetail = fetchCloudinary(file);
        System.out.println(cloudinaryDetail);
        FileModel fileModel = fileMapper.convertToModel(fileDTO);
        return new FileDTO();
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

}
