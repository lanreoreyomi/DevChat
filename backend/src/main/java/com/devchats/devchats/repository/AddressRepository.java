package com.devchats.devchats.repository;

import com.devchats.devchats.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  @Query("select c from Address c where c.user.id =:id")
  Address findAddressByUserId(@Param("id") Long id);
}

