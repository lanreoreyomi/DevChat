package com.devchats.devchats.repository;


import static org.assertj.core.api.Assertions.assertThat;

import com.devchats.devchats.model.User;
import java.util.List;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

//DataJPATest is to test repositories.
// It spins up the database and handles all connections and configs
@DataJpaTest
@RunWith(SpringRunner.class)
public class UserRepositoryTest {

  @Autowired
  private UserRepository userRepo;

  @Autowired
  private TestEntityManager entityManager;


  @BeforeEach
  void setUp() {

  }

  @AfterEach
  void tearDown() {

  }

  @Test
  public void checkIfUserIsSaved() {

    User user = createUser();
    user.setSalt("salt");

    User persistUser = entityManager.persistAndFlush(user);
    User savedUser = userRepo.save(persistUser);

    persistAndFlush();

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

    User persist = entityManager.persistAndFlush(user1);
    User persist2 = entityManager.persistAndFlush(user2);

    userRepo.save(persist);
    userRepo.save(persist2);

    persistAndFlush();
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

    User persist = entityManager.persistAndFlush(user1);
    User persist2 = entityManager.persistAndFlush(user2);

    User save = userRepo.save(persist);
    User save1 = userRepo.save(persist2);

    assertThat(save).isNotNull();
    assertThat(save1).isNotNull();

    persistAndFlush();

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

    //saving 2 users
    User persist = entityManager.persistAndFlush(user1);
    User persist2 = entityManager.persistAndFlush(user2);

    User save = userRepo.save(persist);
    User save1 = userRepo.save(persist2);

    persistAndFlush();

    assertThat(save).isNotNull();
    assertThat(save1).isNotNull();

    persistAndFlush();

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
    User persist = entityManager.persistAndFlush(user);
    User expected = userRepo.save(persist);

    assertThat(expected).isNotNull();
    assertThat(expected.getFirstName()).isEqualTo(user.getFirstName());

    user.setFirstName("firstname_updated");

    //saving user
    User persist_update = entityManager.persistAndFlush(user);
    User expected_update = userRepo.save(persist_update);
    persistAndFlush();
    assertThat(expected_update).isNotNull();
    assertThat(expected_update.getFirstName()).isEqualTo(user.getFirstName());


  }


  //TODo: Test check if email exists
//  @Test
  public void checkIfEmailExists() throws Exception {
    //when
    User user = createUser();
    user.setSalt("salt");

    //saving user
    User persist = entityManager.persistAndFlush(user);
    User expected = userRepo.save(persist);

    assertThat(expected).isNotNull();
    assertThat(expected.getFirstName()).isEqualTo(user.getFirstName());
    //saving user
    User persist_update = entityManager.persistAndFlush(user);
    User expected_update = userRepo.save(persist_update);

    assertThat(expected_update).isNotNull();
    assertThat(expected_update.getFirstName()).isEqualTo(user.getFirstName());

    User user2 = createUser();
    user2.setSalt("salt");

    //saving user
    User persist2 = entityManager.persistAndFlush(user2);
    User expected2 = userRepo.save(persist2);

//    //saving user
//    User persist = entityManager.persistAndFlush(user);
//    User expected = userRepo.save(persist);

  }


  private User createUser() {
    return new User("testfName", "testlname", "test@yahoo.com",
        "password", "l_name");
  }


  public void persistAndFlush() {
    // forces synchronization to DB
    entityManager.flush();
    // clears persistence context
    // all entities are now detached and can be fetched again
    entityManager.clear();
  }
}