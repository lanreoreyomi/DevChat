package com.devchats.controller;

import com.devchats.exceptions.PostNotFoundException;
import com.devchats.model.AppUser;
import com.devchats.model.Comments;
import com.devchats.model.Post;
import com.devchats.service.PostServiceImpl;
import com.devchats.service.UserServiceImpl;
import com.devchats.util.AuthenticatedUser;
import java.util.List;
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

  UserServiceImpl userServiceImpl;
  public PostController(PostServiceImpl postServiceImpl, UserServiceImpl userServiceImpl) {
    this.postServiceImpl = postServiceImpl;
    this.userServiceImpl = userServiceImpl;
  }
  //TODO:Look into this. I expected post to return empty array for comments and likes
  @PostMapping("/create-post")
  public ResponseEntity<Post> createPost(@RequestBody String postRequest)
      throws PostNotFoundException {

    String username = AuthenticatedUser.getAuthenticatedUser().getName();
    Post post = null;

    if (!postRequest.trim().isEmpty() && postRequest.trim().length() <= 160) {
      AppUser user = userServiceImpl.findByUserName(username);
      post = new Post(postRequest.trim(), user);
      postServiceImpl.createPost(post);
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(post);
  }
  @GetMapping("{id}")
  public ResponseEntity<Post> getPostById(@PathVariable String id) throws PostNotFoundException {

    Post post = postServiceImpl.getPostById(Long.valueOf(id));
    return ResponseEntity.status(HttpStatus.OK)
        .body(post);

  }

  //TODO: Create get post by username

  @PostMapping("{id}/comment")
  public ResponseEntity<String> getPostById(@PathVariable String id, @RequestBody String comment)
      throws PostNotFoundException {

    Post post = postServiceImpl.getPostById(Long.valueOf(id));

    if (post != null) {
      Comments comments = new Comments();
      comments.setComment(comment);

      post.getComments().add(comments);

      postServiceImpl.createPost(post);

      return ResponseEntity.status(HttpStatus.CREATED)
          .body(post.getComments().stream().findFirst().get().getComment());
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body("Error saving post");
    }

  }
  @GetMapping()
  public ResponseEntity<List<Post>> getAllPosts(){

    if(postServiceImpl.getAllPosts().isEmpty()){
      return ResponseEntity.notFound().build();
    }else{
      return ResponseEntity.ok(postServiceImpl.getAllPosts());
    }
  }

  @DeleteMapping("{id}")
  public ResponseEntity<String> deletePostById(@PathVariable String id)
      throws PostNotFoundException {

    postServiceImpl.deletePostById(Long.valueOf(id));

    return ResponseEntity.status(HttpStatus.OK)
        .body("Post successfully deleted");


  }
}