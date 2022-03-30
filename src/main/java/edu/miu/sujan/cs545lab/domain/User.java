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
    @OneToMany(targetEntity = Post.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Post> posts;

    public User() {
    }

    public User(String name, List<Post> posts) {
        this.name = name;
        this.posts = posts;
    }
}
