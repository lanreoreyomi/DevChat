package com.devchats.devchats.repository;

import com.devchats.devchats.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

  @Query("select c from UserDetails c where c.user.id =:id")
  UserDetails findUserDetailsByUserId(@Param("id") Long id);

}
