package edu.miu.sujan.cs545lab.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  private String name;
  private String email;
  private String password;

  @OneToMany(targetEntity = Post.class, cascade = CascadeType.ALL)
  @JoinColumn(name = "user_id")
  private List<Post> posts;

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
  @JoinTable
  private List<Role> roles;

  public User() {}

  public User(String name, List<Post> posts) {
    this.name = name;
    this.posts = posts;
  }
}
