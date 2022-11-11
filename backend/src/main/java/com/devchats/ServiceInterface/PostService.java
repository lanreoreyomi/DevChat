package com.devchats.ServiceInterface;

import com.devchats.model.Post;
import java.util.List;
import java.util.Optional;

public interface PostService {

  public Long createPost(Post post);

  public Post getPostById(Long postId);

  public List<Post> getPostsByUserId(Long postId);
  public List<Post> getPostsByUsername(String username);

  public List<Post> getAllPosts();

  public void deletePostById(Long Id);

}
