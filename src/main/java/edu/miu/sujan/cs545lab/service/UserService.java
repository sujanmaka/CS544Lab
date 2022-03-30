package edu.miu.sujan.cs545lab.service;

import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(boolean userWithMoreThanOnePost);

    UserDto createUser(UserDto user);

    UserDto getUserById(Long id);

    List<PostDto> getPostsByUserId(Long id);

}
