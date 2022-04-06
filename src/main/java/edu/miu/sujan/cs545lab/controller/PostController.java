package edu.miu.sujan.cs545lab.controller;

import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
  private PostServiceImpl postServiceImpl;

  @Autowired
  public void setPostService(PostServiceImpl postServiceImpl) {
    this.postServiceImpl = postServiceImpl;
  }

  @GetMapping
  public List<PostDto> getPosts() {
    return postServiceImpl.getPosts();
  }

  @GetMapping(headers = "API-VERSION=2")
  public List<PostDto> getPosts(FilterDto filter) {
    return postServiceImpl.getPosts(filter);
  }

  @GetMapping("/{id}")
  public PostDto getPostById(@PathVariable Long id) {
    return postServiceImpl.getPostById(id);
  }

  @PostMapping
  public ResponseEntity<PostDto> createPost(@RequestBody PostDto post) {
    return new ResponseEntity<>(postServiceImpl.createPost(post), HttpStatus.CREATED);
  }

  @PutMapping("{id}")
  public PostDto updatePost(@PathVariable Long id, @RequestBody PostDto post) {
    return postServiceImpl.updatePost(id, post);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deletePost(@PathVariable Long id) {
    postServiceImpl.deletePost(id);
  }
}
