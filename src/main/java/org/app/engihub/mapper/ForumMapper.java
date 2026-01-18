package org.app.engihub.mapper;

import org.app.engihub.dto.FileDTO;
import org.app.engihub.dto.ForumDTO;
import org.app.engihub.model.FileModel;
import org.app.engihub.model.ForumModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ForumMapper {

    @Autowired
    private ModelMapper mapper;

    public ForumDTO convertToDTO(ForumModel forumModel) {
        if(!Objects.isNull(forumModel))
            return mapper.map(forumModel,ForumDTO.class);
        else
            return null;
    }

    public ForumModel convertToModel(ForumDTO forumDTO) {
        if(!Objects.isNull(forumDTO))
            return mapper.map(forumDTO,ForumModel.class);
        else
            return null;
    }
}
