package org.app.engihub.repository;

import org.app.engihub.model.CommentModel;
import org.app.engihub.request.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentModel,Long> {
}
