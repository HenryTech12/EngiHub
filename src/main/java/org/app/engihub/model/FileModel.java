package org.app.engihub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import org.app.engihub.dto.FileDTO;

import java.time.LocalDateTime;

@Data
@Entity
public class FileModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;
    private FileDTO fileDTO;
    private String cloudinary_url;
    private String cloudinary_id;
    private String file_type;
    private LocalDateTime createdAt;
}
