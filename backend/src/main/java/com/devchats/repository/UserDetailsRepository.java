package com.devchats.repository;

import com.devchats.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

  @Query("select c from UserDetails c where c.user.userId =:userId")
  UserDetails findUserDetailsByUserId(@Param("userId") Long userId);

}
