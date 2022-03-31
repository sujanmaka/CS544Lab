package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAuthor(String author);
    List<Post> findAllByTitle(String title);
}
