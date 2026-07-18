package com.balneamdp.repository;

import com.balneamdp.models.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {
    List<Comments> findBySeaSideResortId(Long id);

}
