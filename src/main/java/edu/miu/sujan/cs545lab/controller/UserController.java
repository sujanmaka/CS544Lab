package edu.miu.sujan.cs545lab.controller;

import edu.miu.sujan.cs545lab.dto.CommentDto;
import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.dto.UserDto;
import edu.miu.sujan.cs545lab.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
  private UserServiceImpl userServiceImpl;

  @Autowired
  public void setUserService(UserServiceImpl userServiceImpl) {
    this.userServiceImpl = userServiceImpl;
  }

  @GetMapping
  public List<UserDto> getUsers(@RequestParam(required = false) boolean userWithMoreThanOnePost) {
    return userServiceImpl.getUsers(userWithMoreThanOnePost);
  }

  @GetMapping("/v2")
  public List<UserDto> getUsersV2(FilterDto filterDto) {
    return userServiceImpl.getUsers(filterDto);
  }

  @GetMapping("/{id}")
  public UserDto getUserById(@PathVariable Long id) {
    return userServiceImpl.getUserById(id);
  }

  @GetMapping("/{id}/posts")
  public List<PostDto> getPostsByUserId(@PathVariable Long id) {
    return userServiceImpl.getPostsByUserId(id);
  }

  @GetMapping("/{userId}/posts/{postId}/comments/{commentId}")
  public CommentDto getComment(
      @PathVariable Long userId, @PathVariable Long postId, @PathVariable Long commentId) {
    return userServiceImpl.getComment(userId, postId, commentId);
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
    return new ResponseEntity<>(userServiceImpl.createUser(user), HttpStatus.CREATED);
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteUser(@PathVariable Long id) {
    userServiceImpl.deleteUser(id);
  }
}
