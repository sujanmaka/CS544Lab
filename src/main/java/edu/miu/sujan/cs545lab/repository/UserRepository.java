package edu.miu.sujan.cs545lab.repository;

import edu.miu.sujan.cs545lab.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where size(u.posts) > 1")
    List<User> findUsersWithMoreThanOnePost();
}
