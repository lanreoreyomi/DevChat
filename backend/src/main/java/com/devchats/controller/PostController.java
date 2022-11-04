package com.devchats.controller;

import com.devchats.exceptions.PostNotFoundException;
import com.devchats.model.Post;
import com.devchats.service.PostServiceImpl;
import com.devchats.util.AuthenticatedUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/post")
@Slf4j
public class PostController {

  PostServiceImpl postServiceImpl;

  public PostController(PostServiceImpl postServiceImpl) {
    this.postServiceImpl = postServiceImpl;
  }

  @PostMapping("/create-post")
  public ResponseEntity<String> createPost(@RequestBody String postRequest)
      throws PostNotFoundException {

    String username = AuthenticatedUser.getInstance().getName();

    if (!postRequest.trim().isEmpty() && postRequest.trim().length() <= 160) {

      try {

        Post post = new Post(postRequest.trim(), username);
        postServiceImpl.createPost(post);

      } catch (Exception e) {
        throw new PostNotFoundException("Error saving post");
      }
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body("Post successfully created");
  }

  @GetMapping("{id}")
  public ResponseEntity<String> getPostById(@PathVariable String id) throws PostNotFoundException {

      Post post = postServiceImpl.getPostById(Long.valueOf(id));
    return ResponseEntity.status(HttpStatus.OK)
        .body(post.getContent());

  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deletePostById(@PathVariable String id) throws PostNotFoundException {

    postServiceImpl.deletePostById(Long.valueOf(id));

    return ResponseEntity.status(HttpStatus.OK)
        .body("Post successfully deleted");


  }
}