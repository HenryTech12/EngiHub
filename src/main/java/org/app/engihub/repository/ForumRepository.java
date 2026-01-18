package org.app.engihub.repository;

import org.app.engihub.model.ForumModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForumRepository extends JpaRepository<ForumModel,Long> {
}
