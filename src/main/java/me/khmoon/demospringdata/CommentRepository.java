package me.khmoon.demospringdata;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

public interface CommentRepository extends MyRepository<Comment, Long> {

  @Query(value = "select c from Comment As c", nativeQuery = true)
  List<Comment> findByTitleContains(String keyword);
}
