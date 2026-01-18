package org.app.engihub.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class ForumModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String course;
    private List<String> tags;
    @OneToOne
    private CommentModel commentModel;
}
