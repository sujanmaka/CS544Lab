package edu.miu.sujan.cs545lab.service;

import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.dto.PostDto;

import java.util.List;

public interface PostService {
    List<PostDto> getPosts();

    List<PostDto> getPosts(FilterDto filter);

    PostDto createPost(PostDto post);

    PostDto getPostById(Long id);

    void deletePost(Long id);

    PostDto updatePost(Long id, PostDto post);
}
