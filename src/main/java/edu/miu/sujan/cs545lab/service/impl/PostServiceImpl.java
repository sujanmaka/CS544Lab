package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.domain.Post;
import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.repository.PostRepository;
import edu.miu.sujan.cs545lab.service.PostService;
import edu.miu.sujan.cs545lab.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;

    private MapperUtils<PostDto> mapperToPostDto;
    private MapperUtils<Post> mapperToPost;

    @Autowired
    public void setPostRepository(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Autowired
    public void setMapperToPostDto(MapperUtils<PostDto> mapperToPostDto) {
        this.mapperToPostDto = mapperToPostDto;
    }

    @Autowired
    public void setMapperToPost(MapperUtils<Post> mapperToPost) {
        this.mapperToPost = mapperToPost;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PostDto> getPosts() {
        return (List<PostDto>) mapperToPostDto.mapList(postRepository.getPosts(), new PostDto());
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<PostDto> getPosts(FilterDto filterDto) {
        return (List<PostDto>) mapperToPostDto.mapList(postRepository.getPosts(filterDto), new PostDto());
    }

    @Override
    public PostDto createPost(PostDto post) {
        Post createdPost = postRepository.createPost((Post) mapperToPost.getMap(post, new Post()));
        return (PostDto) mapperToPostDto.getMap(createdPost, new PostDto());
    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.getPostById(id);
        if (post != null) {
            return (PostDto) mapperToPostDto.getMap(post, new PostDto());
        }
        return null;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deletePost(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto post) {
        Post updatedPost = postRepository.updatePost(id, (Post) mapperToPost.getMap(post, new Post()));
        if (updatedPost != null) {
            return (PostDto) mapperToPostDto.getMap(updatedPost, new PostDto());
        }
        return null;
    }
}
