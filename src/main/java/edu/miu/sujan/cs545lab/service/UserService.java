package edu.miu.sujan.cs545lab.service;

import edu.miu.sujan.cs545lab.domain.User;
import edu.miu.sujan.cs545lab.dto.CommentDto;
import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.dto.UserDto;

import java.util.List;

public interface UserService {
  List<UserDto> getUsers(boolean userWithMoreThanOnePost);

  List<UserDto> getUsers(FilterDto filterDto);

  UserDto createUser(UserDto user);

  UserDto getUserById(Long id);

  List<PostDto> getPostsByUserId(Long id);

  CommentDto getComment(Long userId, Long postId, Long commentId);

  void deleteUser(Long id);

  User getUserByEmail(String email);
}
