package org.app.engihub.service;

import org.app.engihub.dto.ForumDTO;
import org.app.engihub.mapper.ForumMapper;
import org.app.engihub.model.ForumModel;
import org.app.engihub.repository.ForumRepository;
import org.app.engihub.request.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class ForumService {

    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private ForumRepository forumRepository;

    public Map<String,Object> createForum(@RequestBody ForumDTO forumDTO) {
        ForumModel forumModel = forumMapper.convertToModel(forumDTO);
        forumRepository.save(forumModel);
        return Map.of("message","Thread created successfully","threadId", forumModel.getId());
    }

    public Map<String,Object> updateComment(Comment comment, Long threadId) {
        ForumModel forumModel = forumRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Thread not found"));
        forumModel.setComments(comment.getComment());
        forumRepository.save(forumModel);
        return Map.of("message","Comment added successfully");
    }
}
