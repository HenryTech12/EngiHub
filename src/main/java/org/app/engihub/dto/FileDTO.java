package org.app.engihub.dto;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class FileDTO {

    private Long id;
    private String title;
    private String description;
    private String uploadedBy;
    private String course;
    private String[] tags;
    private String uploadDate;
}
