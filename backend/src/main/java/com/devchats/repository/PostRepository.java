package com.devchats.repository;

import com.devchats.model.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

   @Query(value = "select c from Post c where c.user.userName =:username")
    List<Post> getPostsByUsername( @Param("username") String username);
}
