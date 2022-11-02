package com.devchats.controller;

import static com.devchats.controller.AppUserController.convertAddressEntityToDTO;
import static com.devchats.controller.AppUserController.convertUserDetailsEntityToDTO;
import static com.devchats.controller.AppUserController.convertUserEntityToDTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devchats.dto.UserDTO;
import com.devchats.model.Address;
import com.devchats.model.AppUser;
import com.devchats.model.UserDetails;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@SpringBootTest
@AutoConfigureMockMvc
 public class UserControllerTest {

  public static final String USER_ENDPOINT = "/api/v1/uxcxcser";

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private AppUserController appUserController;


  AppUser user_one;
  AppUser user_two;

  UserDetails userDetails;

  Address address;

  @BeforeEach
  public void setup() {
     mockMvc = MockMvcBuilders.standaloneSetup(appUserController).build();
    user_one = createUser();
    user_one.setUserId(1L);
     user_one.setAddress(createAddress());

    user_two = createUser();
    user_two.setUserId(2L);
    user_two.setUserName("user2_username");
     user_two.setAddress(createAddress());

    List<UserDTO> userList = new ArrayList<>();
    userList.add(convertUserEntityToDTO(user_one));
    userList.add(convertUserEntityToDTO(user_two));


    userDetails = createUserDetails();
    userDetails.setId(1L);

    address = createAddress();
    address.setAddressId(1L);

    when(appUserController.createUser(any())).thenReturn(
        ResponseEntity.ok(user_one.getUserId()));

    when(appUserController.getUserById(any())).thenReturn(
        ResponseEntity.ok(convertUserEntityToDTO(user_one)));

    when(appUserController.getAllUsers()).thenReturn(ResponseEntity.ok(userList));

    when(appUserController.updateUserById(any(), any())).thenReturn(
        ResponseEntity.ok(convertUserEntityToDTO(user_one)));

    when(appUserController.deleteUserById(any())).thenReturn(
        ResponseEntity.ok("User Successfully deleted"));

    when(appUserController.updateUserDetails(any(), any())).thenReturn(
        ResponseEntity.ok(convertUserDetailsEntityToDTO(userDetails)));

    when(appUserController.updateUserAddress(any(), any())).thenReturn(ResponseEntity.ok(convertAddressEntityToDTO(address)));


  }

  /**
   * Creates a user through API
   */
  @Test
  public void testCreateUser() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders
            .post(USER_ENDPOINT)
            .content(asJsonString(user_one))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user_one.getFirstName()));

  }



  /**
   * testFindUserById
   */
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
    AppUser user = createUser();
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
            .delete("/api/v1/user/{id}", 1)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(
            status()
                .isOk())
        .andDo(print()).andReturn();

    MockHttpServletResponse response = mvcResult.getResponse();
    Assertions.assertThat("User Successfully deleted").isEqualTo(response.getContentAsString());

  }

  @Test
  public void testUpdateUserById() throws Exception {
    //Posting a user
    mockMvc.perform(MockMvcRequestBuilders
            .put(USER_ENDPOINT + "/{id}", 1)
            .content(asJsonString(user_one))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(
            status().
                isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value(user_one.getFirstName()));

  }

  //Gets all users
  @Test
  public void testGetAllUsers() throws Exception {
    MvcResult result = mockMvc.perform(MockMvcRequestBuilders
            .get(USER_ENDPOINT)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(
            status()
                .isOk())
        .andReturn();

    String response = result.getResponse().getContentAsString();
    String[] split = response.split("null},");

    System.out.println("response = " + response);
    Assertions.assertThat(2).isEqualTo(split.length);

  }

public void updateUserDetails() throws Exception {
  //Posting a user
  mockMvc.perform(MockMvcRequestBuilders
          .put(USER_ENDPOINT + "/{id}/userdetails", 1L)
          .content(asJsonString(userDetails))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)).andDo(print())
      .andExpect(
          status().

              isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(userDetails.getId()));

}


public void updateUserAddress() throws Exception {
  //Posting a user
  mockMvc.perform(MockMvcRequestBuilders
          .put(USER_ENDPOINT + "/{id}/userdetails", 1L)
          .content(asJsonString(address))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON)).andDo(print())
      .andExpect(
          status().

              isOk())
      .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(address.getAddressId()));

}


  private AppUser createUser() {
    return new AppUser("lanreycole", "passwords", "test@yahoo.com",
        "f_name", "f_name");
  }

  private Address createAddress() {
    return new Address("2, Branthaven", "Ottawa", "ON", "CAN", "2342343");
  }

  private UserDetails createUserDetails() {
    return new UserDetails("123-345-789", "Love cats and dogs",
        "Front End Developer", "Masters", LocalDate.of(2020, 1, 8));
  }

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
