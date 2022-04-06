package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {}
