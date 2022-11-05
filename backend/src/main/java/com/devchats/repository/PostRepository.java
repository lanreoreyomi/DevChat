package com.devchats.repository;

import com.devchats.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> getPostsByUsername(String username);
}
