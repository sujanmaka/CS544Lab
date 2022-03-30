package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.domain.User;
import edu.miu.sujan.cs545lab.dto.PostDto;
import edu.miu.sujan.cs545lab.dto.UserDto;
import edu.miu.sujan.cs545lab.exception.DataNotFoundException;
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
    public UserDto createUser(UserDto user) {
        User createdUser = userRepository.save((User) mapperToUser.getMap(user, new User()));
        return (UserDto) mapperToUserDto.getMap(createdUser, new UserDto());
    }

    @Override
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
}
