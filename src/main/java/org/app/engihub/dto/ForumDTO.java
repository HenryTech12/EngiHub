package org.app.engihub.dto;

import lombok.Data;

import java.util.List;

@Data
public class ForumDTO {

    private String title;
    private String course;
    private List<String> tags;
    private String comments;
}
