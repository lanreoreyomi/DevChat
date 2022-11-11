package com.devchats.ServiceInterface;

import com.devchats.model.Comments;
import com.devchats.model.Post;
import java.util.List;

public interface CommentsService {

  public Long createComment(Comments comment);

  public Comments getPostById(Long commentId);

  public void updateCommentById(Long commentId);

  public void deleteCommentById(Long commentId);

}
