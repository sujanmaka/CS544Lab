package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.aspect.annotation.ExecutionTime;
import edu.miu.sujan.cs545lab.domain.User;
import edu.miu.sujan.cs545lab.dto.CommentDto;
import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.dto.UserDto;
import edu.miu.sujan.cs545lab.exception.DataNotFoundException;
import edu.miu.sujan.cs545lab.exception.UserExistException;
import edu.miu.sujan.cs545lab.repository.UserRepository;
import edu.miu.sujan.cs545lab.service.UserService;
import edu.miu.sujan.cs545lab.util.MapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private MapperUtils<UserDto> mapperToUserDto;
  private MapperUtils<User> mapperToUser;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Autowired
  public void setMapperToUserDto(MapperUtils<UserDto> mapperToUserDto) {
    this.mapperToUserDto = mapperToUserDto;
  }

  @Autowired
  public void setMapperToUser(MapperUtils<User> mapperToUser) {
    this.mapperToUser = mapperToUser;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<UserDto> getUsers(boolean userWithMoreThanOnePost) {
    if (userWithMoreThanOnePost) {
      List<User> users = userRepository.findUsersWithMoreThanOnePost();
      return (List<UserDto>) mapperToUserDto.mapList(users, new UserDto());
    }
    return (List<UserDto>) mapperToUserDto.mapList(userRepository.findAll(), new UserDto());
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<UserDto> getUsers(FilterDto filterDto) {
    if (filterDto != null) {
      if (filterDto.getNumberOfPost() > 0) {
        List<User> users =
            userRepository.findUsersWithMoreThanGivenPost(filterDto.getNumberOfPost());
        return (List<UserDto>) mapperToUserDto.mapList(users, new UserDto());
      }
      if (filterDto.getTitle() != null) {
        List<User> users = userRepository.findUsersWithPostWithGivenTitle(filterDto.getTitle());
        return (List<UserDto>) mapperToUserDto.mapList(users, new UserDto());
      }
    }
    return getUsers(false);
  }

  @Override
  public UserDto createUser(UserDto user) {
    User existingUser = userRepository.findByEmail(user.getEmail());
    if (existingUser != null) {
      throw new UserExistException(
          String.format("User with email %s already exists.", user.getEmail()));
    }
    User createdUser = userRepository.save((User) mapperToUser.getMap(user, new User()));
    return (UserDto) mapperToUserDto.getMap(createdUser, new UserDto());
  }

  @Override
  @ExecutionTime
  public UserDto getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    return user.map(value -> (UserDto) mapperToUserDto.getMap(value, new UserDto())).orElse(null);
  }

  @Override
  public List<PostDto> getPostsByUserId(Long id) {
    UserDto user = getUserById(id);
    if (user == null) {
      throw new DataNotFoundException(String.format("User with id %d not found", id));
    }
    return user.getPosts();
  }

  @Override
  public CommentDto getComment(Long userId, Long postId, Long commentId) {
    List<PostDto> posts = getPostsByUserId(userId);
    Optional<PostDto> postDto = posts.stream().filter(p -> p.getId().equals(postId)).findFirst();
    if (postDto.isPresent()) {
      Optional<CommentDto> commentDto =
          postDto.get().getComments().stream().filter(c -> c.getId().equals(commentId)).findFirst();
      if (commentDto.isPresent()) {
        return commentDto.get();
      }
    }
    return null;
  }

  @Override
  public void deleteUser(Long id) {
    UserDto user = getUserById(id);
    if (user == null) {
      throw new DataNotFoundException(String.format("User with id %d not found", id));
    }
    userRepository.deleteById(id);
  }

  @Override
  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email);
  }
}
