package com.devchats.controller;

import com.devchats.exceptions.PostNotFoundException;
import com.devchats.model.Comments;
import com.devchats.model.Post;
import com.devchats.service.CommentsServiceImpl;
import com.devchats.service.PostServiceImpl;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/comment")
@Slf4j
public class CommentController {

  PostServiceImpl postServiceImpl;
  CommentsServiceImpl commentServiceImpl;

  public CommentController(PostServiceImpl postServiceImpl,
      CommentsServiceImpl commentServiceImpl) {
    this.postServiceImpl = postServiceImpl;
    this.commentServiceImpl = commentServiceImpl;
  }

  //Add comments
  @PostMapping("/post/{id}")
  public ResponseEntity<Comments> getCommentById(@PathVariable String id,
      @RequestBody String comment)
      throws PostNotFoundException {

    Post post = postServiceImpl.getPostById(Long.valueOf(id));

    if (post != null) {

      Comments comments = new Comments();
      comments.setComment(comment);

      post.getComments().add(comments);

      log.info("adding comment for post");
      postServiceImpl.createPost(post);

      return ResponseEntity.status(HttpStatus.CREATED)
          .body(post.getComments().stream().findFirst().get());

    } else {
      log.error("Error adding comment");
      return ResponseEntity.status(HttpStatus.BAD_REQUEST)
          .body(null);
    }

  }

  @DeleteMapping("{commentId}/post/{postId}")
  public ResponseEntity<String> deleteCommentById(@PathVariable String postId,
      @PathVariable String commentId)
      throws PostNotFoundException {
    Post postById = postServiceImpl.getPostById(Long.valueOf(postId));

    if (postById != null) {
      Comments comment = postById.getComments().stream()
          .filter(c -> Objects.equals(c.getCommentId(), Long.valueOf(commentId))).findFirst().get();
      postServiceImpl.deleteCommentById(comment.getCommentId());
      log.info("Comment successfully deleted");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body("Comment successfully deleted");
  }


  //Update comment
  @PutMapping("{commentId}/post/{postId}")
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
}
