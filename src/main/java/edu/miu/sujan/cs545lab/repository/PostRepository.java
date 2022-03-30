package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.Post;
import edu.miu.sujan.cs545lab.domain.PostV2;
import edu.miu.sujan.cs545lab.dto.FilterDto;

import java.util.List;

public interface PostRepository {
    List<Post> getPosts();

    List<PostV2> getPosts(FilterDto filterDto);

    Post createPost(Post post);

    Post getPostById(long id);

    void deletePost(long id);

    Post updatePost(long id, Post post);
}
