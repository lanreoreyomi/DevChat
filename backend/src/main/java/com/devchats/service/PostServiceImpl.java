package com.devchats.service;

import com.devchats.ServiceInterface.PostService;
import com.devchats.exceptions.PostNotFoundException;
import com.devchats.model.Post;
import com.devchats.repository.PostRepository;
import com.devchats.util.AuthenticatedUser;
import java.util.List;
import javax.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Transactional
@Slf4j
public class PostServiceImpl implements PostService {

  private final PostRepository postRepo;



  public PostServiceImpl(PostRepository postRepo) {
    this.postRepo = postRepo;

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
    }
    catch (Exception ex) {

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
    return postRepo.findAll();
  }

  @Override
  public void deletePostById(Long Id) {
    getPostById(Id);
    postRepo.deleteById(Id);
  }

  public List<Post> getPostsByUsername(String username) {

    return postRepo.getPostsByUsername(username);
  }
}
