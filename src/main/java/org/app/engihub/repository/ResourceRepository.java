package org.app.engihub.repository;

import org.app.engihub.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<FileModel,Long> {
}
