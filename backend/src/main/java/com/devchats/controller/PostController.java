package com.devchats.controller;

import com.devchats.exceptions.PostNotFoundException;
import com.devchats.model.AppUser;
import com.devchats.model.Comments;
import com.devchats.model.Post;
import com.devchats.service.PostServiceImpl;
import com.devchats.service.UserServiceImpl;
import com.devchats.util.AuthenticatedUser;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable String id) throws PostNotFoundException {

    Post post = postServiceImpl.getPostById(Long.valueOf(id));
    return ResponseEntity.status(HttpStatus.OK)
        .body(post);

  }

  //    select p.content, u.email from post p join users u on p.user_id = u.user_id;
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<Post>> GetPostsByUserId(@PathVariable String userId)
      throws PostNotFoundException {

    AppUser byUserName = userServiceImpl.findUserById(Long.valueOf(userId));

    List<Post> postsByUserId = postServiceImpl.getPostsByUserId(Long.valueOf(userId));

    return ResponseEntity.status(HttpStatus.OK)
        .body(postsByUserId);
  }

  // TODO: Use this SQL to retrieve posts by username
//  select p.content, u.email from post p join users u on p.user_id = u.user_id;
  @GetMapping("/user/{username}/posts")
  public ResponseEntity<List<Post>> GetPostsByUsername(@PathVariable String username)
      throws PostNotFoundException {
    AppUser byUserName = userServiceImpl.findByUserName(username);

    List<Post> postsByUserId = postServiceImpl.getPostsByUsername(byUserName.getUserName());

    return ResponseEntity.status(HttpStatus.OK)
        .body(postsByUserId);
  }

  @PostMapping("{id}/comment")
  public ResponseEntity<Comments> getCommentById(@PathVariable String id,
      @RequestBody String comment)
      throws PostNotFoundException {

    Post post = postServiceImpl.getPostById(Long.valueOf(id));

    if (post != null) {
      Comments comments = new Comments();
      comments.setComment(comment);

      post.getComments().add(comments);

      postServiceImpl.createPost(post);

      return ResponseEntity.status(HttpStatus.CREATED)
          .body(post.getComments().stream().findFirst().get());

    } else {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(null);
    }

  }

  @PutMapping("{postId}/comment/{commentId}")
  public ResponseEntity<Comments> updateComment(@PathVariable String postId,
      @PathVariable String commentId, @RequestBody String comment)
      throws PostNotFoundException {

    Post post = postServiceImpl.getPostById(Long.valueOf(postId));

    post.getComments().stream()
        .filter(e -> Objects.equals(e.getCommentId(), Long.valueOf(commentId))).findFirst().get()
        .setComment(comment);

    postServiceImpl.createPost(post);

    Comments comments = post.getComments().stream()
        .filter(e -> Objects.equals(e.getCommentId(), Long.valueOf(commentId))).findFirst().get();

    return ResponseEntity.status(HttpStatus.OK)
        .body(comments);
  }

  @GetMapping()
  public ResponseEntity<List<Post>> getAllPosts() {

    if (postServiceImpl.getAllPosts().isEmpty()) {
      return ResponseEntity.notFound().build();
    } else {
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