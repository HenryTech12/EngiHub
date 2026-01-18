package org.app.engihub.mapper;

import org.app.engihub.dto.FileDTO;
import org.app.engihub.dto.UserDTO;
import org.app.engihub.model.FileModel;
import org.app.engihub.model.UserModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

public class FileMapper {
    @Autowired
    private ModelMapper mapper;

    public FileDTO convertToDTO(FileModel fileModel) {
        if(!Objects.isNull(fileModel))
            return mapper.map(fileModel,FileDTO.class);
        else
            return null;
    }

    public FileModel convertToModel(FileDTO fileDTO) {
        if(!Objects.isNull(fileDTO))
            return mapper.map(fileDTO,FileModel.class);
        else
            return null;
    }
}
