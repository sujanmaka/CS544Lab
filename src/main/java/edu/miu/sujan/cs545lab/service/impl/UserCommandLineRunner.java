package edu.miu.sujan.cs545lab.service.impl;

import edu.miu.sujan.cs545lab.domain.Comment;
import edu.miu.sujan.cs545lab.domain.Post;
import edu.miu.sujan.cs545lab.domain.Role;
import edu.miu.sujan.cs545lab.domain.User;
import edu.miu.sujan.cs545lab.enums.RoleType;
import edu.miu.sujan.cs545lab.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

@Component
@Transactional
public class UserCommandLineRunner implements CommandLineRunner {

  private UserRepository userRepository;

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    List<User> users = userRepository.findAll();
    if (users.isEmpty()) {
      setData();
    }
  }

  private void setData() {

    Comment comment1 = new Comment("This is my comment");
    Comment comment2 = new Comment("This is their comment");
    Comment comment3 = new Comment("This is our comment");
    Post post1 = new Post("ABC", "This is abc", "Sujan");
    post1.setComments(Arrays.asList(comment1, comment2, comment3));
    Post post2 = new Post("BAA", "This is baa", "Jim");
    post2.setComments(List.of(comment1));
    Post post3 = new Post("EDB", "This is edb", " James");
    Post post4 = new Post("XYZ", "This is xyz", "Jenny");

    Role role1 = new Role(RoleType.USER);
    Role role2 = new Role(RoleType.ADMIN);

    User user1 = new User("Sujan Maka", Arrays.asList(post1, post2));
    user1.setRoles(Arrays.asList(role1, role2));
    user1.setEmail("smaka@miu.edu");
    user1.setPassword("$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2"); // 123
    User user2 = new User("James Pin", Arrays.asList(post3, post4));
    user2.setRoles(List.of(role1));
    user2.setEmail("james@miu.edu");
    user2.setPassword("$2a$12$IKEQb00u5QpZMx4v5zMweu.3wrq0pS7XLCHO4yHZ.BW/yvWu1feo2"); // 123
    userRepository.save(user1);
    userRepository.save(user2);
  }
}
