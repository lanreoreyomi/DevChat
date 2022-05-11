package com.devchats.devchats.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

import com.devchats.devchats.model.Address;
import com.devchats.devchats.model.User;
import com.devchats.devchats.model.UserDetails;
import java.util.List;
import java.util.Optional;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

//DataJPATest is to test repositories.
// It spins up the database and handles all connections and configs
@DataJpaTest
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepo;
  @Autowired
  private UserDetailsRepository userDetailsRepo;
  @Autowired
  private AddressRepository addressRepo;


  @BeforeEach
  void setUp() {

  }

  @AfterEach
  void tearDown() {

    userRepo.deleteAll();

  }

  @Test
  public void checkIfUserIsSaved() {

    User user = createUser();
    user.setSalt("salt");
    user.setId(1L);

    User savedUser = userRepo.save(user);

    assertThat(savedUser).isNotNull();
    assertThat(savedUser.getId()).isGreaterThan(0);
  }

  @Test
  public void checkUpdateUserDetails() {

    User user = createUser();
    user.setSalt("salt");
    user.setId(1L);

    User savedUser = userRepo.save(user);

    assertThat(savedUser).isNotNull();
    assertThat(savedUser.getId()).isGreaterThan(0);
  }


  @Test
  public void checkIfAllUserIsReturned() {
    //when
    User user1 = createUser();
    user1.setSalt("salt");
    User user2 = createUser();
    user2.setSalt("salt");
    user2.setUsername("user2_username");

    userRepo.save(user1);
    userRepo.save(user2);

    //when
    List<User> expectedList = userRepo.findAll();
    //then
    assertThat(expectedList.size()).isEqualTo(2);
  }

  @Test
  public void checkIfUserByIdIsReturned() {
    //when
    User user1 = createUser();
    user1.setSalt("salt");
    User user2 = createUser();
    user2.setFirstName("User2");
    user2.setSalt("salt");
    user2.setUsername("user2_username");

    User save = userRepo.save(user1);
    User save1 = userRepo.save(user2);

    assertThat(save).isNotNull();
    assertThat(save1).isNotNull();

    Optional<User> expected = userRepo.findById(user2.getId());
    //then
    assertThat(expected).isPresent();
    assertThat(expected.get().getFirstName()).isEqualTo(user2.getFirstName());


  }

  @Test
  public void checkDeleteUserById() {
    //when
    //creating 2 users
    User user1 = createUser();
    user1.setSalt("salt");
    User user2 = createUser();
    user2.setFirstName("User2");
    user2.setSalt("salt");
    user2.setUsername("user2_username");

    User save = userRepo.save(user1);
    User save1 = userRepo.save(user2);

    assertThat(save).isNotNull();
    assertThat(save1).isNotNull();

    List<User> expectedList = userRepo.findAll();
    assertThat(expectedList.size()).isEqualTo(2);

    //when
    //Getting second user by ID
    userRepo.deleteById(user2.getId());
    //then
    List<User> expected = userRepo.findAll();
    assertThat(expected.size()).isEqualTo(1);
  }

  @Test
  public void checkUpdateUserById() {
    //when
    User user = createUser();
    user.setSalt("salt");

    //saving user
    User expected = userRepo.save(user);

    assertThat(expected).isNotNull();
    assertThat(expected.getFirstName()).isEqualTo(user.getFirstName());

    user.setFirstName("firstname_updated");

    //saving user
    User expected_update = userRepo.save(user);
    assertThat(expected_update).isNotNull();
    assertThat(expected_update.getFirstName()).isEqualTo(user.getFirstName());


  }

  @Test
  public void updateUserDetails() {

    User user1 = createUser();
    user1.setSalt("salt");
    User save = userRepo.save(user1);

    assertThat(save).isNotNull();
    assertThat(save.getFirstName()).isEqualTo(save.getFirstName());
    assertThat(save.getUserdetails()).isNull();

    UserDetails userDetails = createUserDetails();
    userDetails.setUser(save);

    save.setUserdetails(userDetails);

    userDetailsRepo.save(userDetails);

    User updatedUserDetails = userRepo.save(user1);

    assertThat(updatedUserDetails).isNotNull();
    assertThat(updatedUserDetails.getFirstName()).isEqualTo(save.getFirstName());
    assertThat(updatedUserDetails.getUserdetails()
        .getUser().getFirstName())
        .isEqualTo(save.getFirstName());


  }

  @Test
  public void updateUserAddress() {

    User user = createUser();
    user.setSalt("salt");
    User save = userRepo.save(user);

    assertThat(save).isNotNull();
    assertThat(save.getFirstName()).isEqualTo(save.getFirstName());
    assertThat(save.getAddress()).isNull();

    Address address = createAddress();
    address.setUser(save);

    save.setAddress(address);

    addressRepo.save(address);

    User updatedUserAddress = userRepo.save(user);

    assertThat(updatedUserAddress).isNotNull();
    assertThat(updatedUserAddress.getFirstName()).isEqualTo(save.getFirstName());
    assertThat(updatedUserAddress.getAddress()
        .getUser().getFirstName())
        .isEqualTo(save.getFirstName());


  }


//TODO: Check is email and username unique
  // @Test
  public void checkIfEmailExists() throws Exception {
    //when
    User user1 = createUser();
    user1.setSalt("salt");

    //saving user
    User savedUser1 = userRepo.save(user1);

    savedUser1.setAddress(null);

       userRepo.save(user1);
    assertThat(savedUser1).isNotNull();
    assertThat(savedUser1.getFirstName()).isEqualTo(user1.getFirstName());

    //saving user
    User user2 = createUser();
    user2.setSalt("salt");
    user2.setId(2L);
//    user2.setUsername("user2_username");

    assertThrows(ConstraintViolationException.class,
        ()->{

         userRepo.save(user2);
        });
  }


  private User createUser() {
    return new User("testfName", "testlname", "test@yahoo.com",
        "password", "l_name");

  }


  private Address createAddress() {
    return new Address("2, Branthaven", "Ottawa", "ON", "CAN", "2342343");
  }

  private UserDetails createUserDetails() {
    return new UserDetails("123-345-789", "Love cats and dogs",
        "Front End Developer", "Masters", "12/12/12");
  }

}