package org.app.engihub.service;

import org.app.engihub.dto.ForumDTO;
import org.app.engihub.mapper.ForumMapper;
import org.app.engihub.model.CommentModel;
import org.app.engihub.model.ForumModel;
import org.app.engihub.repository.CommentRepository;
import org.app.engihub.repository.ForumRepository;
import org.app.engihub.request.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ForumService {

    @Autowired
    private ForumMapper forumMapper;
    @Autowired
    private ForumRepository forumRepository;
    @Autowired
    private CommentRepository commentRepository;

    public Map<String,Object> createForum(@RequestBody ForumDTO forumDTO) {
        ForumModel forumModel = forumMapper.convertToModel(forumDTO);
        forumRepository.save(forumModel);
        return Map.of("message","Thread created successfully","threadId", forumModel.getId());
    }

    public Map<String,Object> updateComment(Comment comment, Long threadId, String email) {
        ForumModel forumModel = forumRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Thread not found"));

        CommentModel commentModel = new CommentModel();
        commentModel.setComment(comment.getComment());
        commentModel.setDate(LocalDateTime.now());
        commentModel.setAuthor(email);

        forumModel.setCommentModel(commentModel);
        forumRepository.save(forumModel);
        commentRepository.save(commentModel);

        return Map.of("message","Comment added successfully");
    }

    public Map<String,Object> fetchThreadInfo(Long threadId) {
        ForumModel forumModel = forumRepository.findById(threadId)
                .orElseThrow(() -> new RuntimeException("Forum not found"));
        CommentModel commentModel = forumModel.getCommentModel();
        Map<String, Object> data = new HashMap<>();
        data.put("id", forumModel.getId());
        data.put("title",forumModel.getTitle());
        data.put("course",forumModel.getCourse());
        data.put("tags",forumModel.getTags());
        data.put("comments", Map.of(
                "id",commentModel.getCommentId(),
                "comment",commentModel.getComment(),
                "author",commentModel.getAuthor(),
                "date",commentModel.getDate()
        ));
        return data;
    }
}
