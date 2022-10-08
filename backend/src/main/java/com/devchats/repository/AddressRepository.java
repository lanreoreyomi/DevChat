package com.devchats.repository;

import com.devchats.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  @Query("select c from Address c where c.user.userId =:userId")
  Address findAddressByUserId(@Param("userId") Long userId);
}

