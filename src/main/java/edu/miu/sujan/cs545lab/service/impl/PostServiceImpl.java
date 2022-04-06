package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.domain.Post;
import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.exception.DataNotFoundException;
import edu.miu.sujan.cs545lab.repository.PostRepository;
import edu.miu.sujan.cs545lab.service.PostService;
import edu.miu.sujan.cs545lab.util.CustomNullAwareBeanUtils;
import edu.miu.sujan.cs545lab.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    return (List<PostDto>) mapperToPostDto.mapList(postRepository.findAll(), new PostDto());
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<PostDto> getPosts(FilterDto filterDto) {
    if (filterDto != null) {
      if (filterDto.getAuthor() != null) {
        return (List<PostDto>)
            mapperToPostDto.mapList(
                postRepository.findAllByAuthor(filterDto.getAuthor()), new PostDto());
      }
      if (filterDto.getTitle() != null) {
        return (List<PostDto>)
            mapperToPostDto.mapList(
                postRepository.findAllByTitle(filterDto.getTitle()), new PostDto());
      }
    }
    return getPosts();
  }

  @Override
  public PostDto createPost(PostDto post) {
    Post createdPost = postRepository.save((Post) mapperToPost.getMap(post, new Post()));
    return (PostDto) mapperToPostDto.getMap(createdPost, new PostDto());
  }

  @Override
  public PostDto getPostById(Long id) {
    Optional<Post> post = postRepository.findById(id);
    return post.map(value -> (PostDto) mapperToPostDto.getMap(value, new PostDto())).orElse(null);
  }

  @Override
  public void deletePost(Long id) {
    postRepository.delete((Post) mapperToPost.getMap(getPostById(id), new Post()));
  }

  @Override
  public PostDto updatePost(Long id, PostDto post) {
    PostDto currentPost = getPostById(id);
    if (currentPost == null) {
      throw new DataNotFoundException(String.format("Post with id %d not found", id));
    }
    CustomNullAwareBeanUtils.myCopyProperties(post, currentPost);
    postRepository.save((Post) mapperToPost.getMap(currentPost, new Post()));
    post.setId(currentPost.getId());
    return post;
  }
}
