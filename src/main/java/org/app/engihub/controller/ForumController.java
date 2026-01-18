package org.app.engihub.controller;

import org.app.engihub.dto.ForumDTO;
import org.app.engihub.request.Comment;
import org.app.engihub.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
public class ForumController {

    @Autowired
    private ForumService forumService;

    @PostMapping("/forum/threads")
    public ResponseEntity<Map<String,Object>> createForum(@RequestBody ForumDTO forumDTO) {
        return new ResponseEntity<Map<String, Object>>(forumService.createForum(forumDTO), HttpStatus.CREATED);
    }

    @PostMapping("/forum/threads/{threadId}/comments")
    public ResponseEntity<Map<String,Object>> updateComment(@RequestBody Comment comment, @PathVariable Long threadId, Principal principal) {
        return new ResponseEntity<>(forumService.updateComment(comment,threadId,principal.getName()),HttpStatus.OK);
    }

    @GetMapping("/forum/threads/{threadId}")
    public ResponseEntity<Map<String,Object>> fetchForum(@PathVariable Long threadId) {
        return new ResponseEntity<>(forumService.fetchThreadInfo(threadId),HttpStatus.OK);
    }
}
