package com.devchats.service;

import com.devchats.ServiceInterface.CommentsService;
import com.devchats.model.Comments;
import com.devchats.repository.CommentsRepository;
import com.devchats.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentsServiceImpl implements CommentsService {

  CommentsRepository  commentsRepo;
  PostRepository postRepo;


  public CommentsServiceImpl(CommentsRepository commentsRepo, PostRepository postRepo) {
    this.commentsRepo = commentsRepo;
    this.postRepo = postRepo;
  }

  @Override
  public Long createComment(Comments comment) {
    return null;
  }

  @Override
  public Comments getPostById(Long commentId) {
    return null;
  }

  @Override
  public void updateCommentById(Long commentId) {

  }

  @Override
  public void deleteCommentById(Long commentId) {

  }
}
