package com.devchats.service;

import com.devchats.ServiceInterface.PostService;
import com.devchats.exceptions.PostNotFoundException;
import com.devchats.model.Post;
import com.devchats.repository.PostRepository;
import com.devchats.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

  private final PostRepository postRepo;
  private final UserRepository userRepo;

  public PostServiceImpl(PostRepository postRepo, UserRepository userRepo) {
    this.postRepo = postRepo;
    this.userRepo = userRepo;

  }

  @Override
  public Long createPost(Post post) {

    Post savedPost;
    log.info("Saving Post");

    try {

      savedPost = postRepo.save(post);

      if (savedPost.getPostId() <= 0) {
        log.error("Error creating post");
      }
    } catch (Exception ex) {

      throw new PostNotFoundException("Something went wrong while saving post");

    }
    return savedPost.getPostId();
  }

  @Override
  public Post getPostById(Long postId) {
    log.info("Retrieving post with id: " + postId);
    return postRepo.findById(postId)
        .orElseThrow(
            () -> new PostNotFoundException(String.format("Post with id %s not found", postId)));
  }

  @Override
  public List<Post> getAllPosts() {
    return Optional.of(postRepo.findAll()).orElseThrow(()->new PostNotFoundException("Error getting posts"));
  }

  @Override
  public void deletePostById(Long Id) {
    getPostById(Id);
    postRepo.deleteById(Id);
  }

  public List<Post> getPostsByUserId(Long id) {

    log.info("Retrieving post with post id: " + id);

    Optional<List<Post>> posts = Optional.of(postRepo.getPostsByUserId(id).orElseThrow(
        () -> new PostNotFoundException(String.format("Error getting posts with id: %s ", id))));

    return new ArrayList<>(posts.get());
  }

  @Override
  public  List<Post> getPostsByUsername(String username) {

    log.info("Retrieving post with post id: " + username);

    Optional<List<Post>> posts = Optional.of(postRepo.getPostByUsername(username).orElseThrow(
        () -> new PostNotFoundException(String.format("Error getting posts with username: %s ", username))));

    return new ArrayList<>(posts.get());
  }
}
