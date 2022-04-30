package com.devchats.devchats;

import static com.devchats.devchats.controller.UserController.convertUserEntityToDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devchats.devchats.controller.UserController;
import com.devchats.devchats.dto.UserDTO;
import com.devchats.devchats.model.Address;
import com.devchats.devchats.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class UserControllerTest {

  public static final String USER_ENDPOINT = "/api/v1/user";
  //  TODO: User Controller test
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserController userController;

  @Autowired
  private JacksonTester<User> json;

  User testUser;
  User testUser_two;
  List<User> userList;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    testUser = createUser();
    testUser.setId(1L);
    testUser.setAddress(createAddress());

    userList = new ArrayList<>();
    userList.add(testUser);
    userList.add(testUser_two);
    List<UserDTO> users = new ArrayList<>();
    for (User user : userList) {
      users.add(convertUserEntityToDTO(user));
    }

    given(userController.createUser(any())).willReturn(
        ResponseEntity.ok(convertUserEntityToDTO(testUser)));
    given(userController.getUserById(any())).willReturn(
        ResponseEntity.ok(convertUserEntityToDTO(testUser)));
    given(userController.getAllUsers()).willReturn((ResponseEntity<List<UserDTO>>) users);
    given(userController.updateUserById(any(), any())).willReturn(
        ResponseEntity.ok(convertUserEntityToDTO(testUser)));
    given(userController.deleteUserById(any())).willReturn(
        ResponseEntity.ok("User Successfully deleted"));


  }

  /**
   * Creates a user through API
   */
  @Test
  public void testCreateUser() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders
            .post(USER_ENDPOINT)
            .content(asJsonString(testUser))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(testUser.getFirstName()));
  }


  /**
   * testFindUserById
   */
  @Test
  public void testFindUserById() throws Exception {
    //Getting user by Id
    mockMvc.perform(MockMvcRequestBuilders
            .get(USER_ENDPOINT + "/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(
            status()
                .isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1));
  }

  @Test
  public void testDeleteUserById() throws Exception {
    User user = createUser();
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/user/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(
            status()
                .isOk())
        .andDo(print()).andReturn();

    MockHttpServletResponse response = mvcResult.getResponse();
    Assert.assertEquals("User Successfully deleted", response.getContentAsString());

  }

  @Test
  public void testUpdateUserById() throws Exception {
    //Posting a user
    mockMvc.perform(MockMvcRequestBuilders
            .put(USER_ENDPOINT + "/{id}", 1)
            .content(asJsonString(testUser))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(
            status().
                isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(testUser.getFirstName()));
  }

  //Gets all users
  @Test
  public void testGetAllUsers() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .get(USER_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(
            status()
                .isOk())
        .andReturn();

    String response = result.getResponse().getContentAsString();
    String[] split = response.split("},");
    Assert.assertEquals(2, split.length);

  }


  private User createUser() {
    return new User("lanreycole", "passwords", "test@yahoo.com",
        "f_name", "f_name");
  }

  private Address createAddress() {
    return new Address("2, Branthaven", "Ottawa", "ON", "CAN", "2342343");
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
