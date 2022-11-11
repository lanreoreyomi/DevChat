package com.devchats.repository;

import com.devchats.model.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  @Query(value = "select p from Post p where p.user.userName =:username")
  Optional<List<Post>> getPostByUsername(@Param("username") String username);

  @Query(value = "select p from Post p where p.user.userId =:userId")
  Optional<List<Post>> getPostsByUserId(@Param("userId") Long userId);

//  @Query(value = "delete from Comments c where c.commentId =:commentId")
//  void deleteCommentById(@Param("commentId") Long commentId);
}
