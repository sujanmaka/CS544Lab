package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.Comment;
import edu.miu.sujan.cs545lab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
