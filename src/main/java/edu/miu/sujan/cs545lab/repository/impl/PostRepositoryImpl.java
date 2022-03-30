package edu.miu.sujan.cs545lab.repository.impl;

import edu.miu.sujan.cs545lab.domain.Post;
import edu.miu.sujan.cs545lab.domain.PostV2;
import edu.miu.sujan.cs545lab.dto.FilterDto;
import edu.miu.sujan.cs545lab.exception.DataNotFoundException;
import edu.miu.sujan.cs545lab.repository.PostRepository;
import edu.miu.sujan.cs545lab.util.CustomNullAwareBeanUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PostRepositoryImpl implements PostRepository {

    private static List<Post> posts;
    private static List<PostV2> postsV2;

    static {
        posts = new ArrayList<>();
        posts.add(new Post("Hello World", "Basics of Java", "Sujan Maka"));
        posts.add(new Post("Compro", "Intro  to MS Program", "Ryan Dj"));

        postsV2 = new ArrayList<>();
        postsV2.add(new PostV2("Hello World", "Basics of Java", "Sujan Maka"));
        postsV2.add(new PostV2("Compro", "Intro  to MS Program", "Ryan Dj"));
    }

    @Override
    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public List<PostV2> getPosts(FilterDto filterDto) {
        if (filterDto != null && filterDto.getAuthor() != null) {
            return postsV2.stream().filter(post -> post.getAuthor().equalsIgnoreCase(filterDto.getAuthor())).collect(Collectors.toList());
        }
        return postsV2;
    }

    @Override
    public Post createPost(Post post) {
        Post createdPost = new Post(post.getTitle(), post.getContent(), post.getAuthor());
        posts.add(createdPost);
        return createdPost;
    }

    @Override
    public Post getPostById(long id) {
        return posts.stream().filter(post -> post.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void deletePost(long id) {
        Post post = getPostById(id);
        if (post == null) {
            throw new DataNotFoundException(String.format("Post with id %d not found", id));
        }
        posts.remove(post);
    }

    @Override
    public Post updatePost(long id, Post post) {
        Post currentPost = getPostById(id);
        if (currentPost == null) {
            throw new DataNotFoundException(String.format("Post with id %d not found", id));
        }
        post.setId(id);
        CustomNullAwareBeanUtils.myCopyProperties(post, currentPost);
        return currentPost;
    }
}
