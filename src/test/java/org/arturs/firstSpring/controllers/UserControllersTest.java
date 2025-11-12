// package org.arturs.firstSpring.controllers;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.arturs.firstSpring.models.UserModel;
// import org.arturs.firstSpring.services.UserServices;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;

// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.when;
// import static org.mockito.Mockito.doNothing;
// import static org.mockito.Mockito.verify;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static
// org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static
// org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(UserControllers.class)
// class UserControllersTest {

// @Autowired
// private MockMvc mockMvc;

// @MockitoBean
// private UserServices userService;

// @Autowired
// private ObjectMapper objectMapper;

// private UserModel validUser;
// private UserModel invalidUser;

// @BeforeEach
// void setUp() {
// validUser = new UserModel();
// validUser.setName("testUser");
// validUser.setPassword("testPassword");

// invalidUser = new UserModel();
// // Invalid user with blank name and password
// invalidUser.setName("");
// invalidUser.setPassword("");
// }

// @Test
// void addUser_WithValidUser_ShouldReturnUserIdAndOkStatus() throws Exception {
// // Given
// Long expectedUserId = 1L;
// when(userService.findOrSaveUser(any(UserModel.class))).thenReturn(expectedUserId);

// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(validUser)))
// .andExpect(status().isOk())
// .andExpect(content().string(expectedUserId.toString()));
// }

// @Test
// void addUser_WithExistingUser_ShouldReturnExistingUserIdAndOkStatus() throws
// Exception {
// // Given
// Long existingUserId = 5L;
// when(userService.findOrSaveUser(any(UserModel.class))).thenReturn(existingUserId);

// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(validUser)))
// .andExpect(status().isOk())
// .andExpect(content().string(existingUserId.toString()));
// }

// @Test
// void addUser_WithInvalidUser_BlankName_ShouldReturnBadRequest() throws
// Exception {
// // Given
// UserModel userWithBlankName = new UserModel();
// userWithBlankName.setName(""); // Blank name should fail validation
// userWithBlankName.setPassword("validPassword");

// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(userWithBlankName)))
// .andExpect(status().isBadRequest());
// }

// @Test
// void addUser_WithInvalidUser_BlankPassword_ShouldReturnBadRequest() throws
// Exception {
// // Given
// UserModel userWithBlankPassword = new UserModel();
// userWithBlankPassword.setName("validName");
// userWithBlankPassword.setPassword(""); // Blank password should fail
// validation

// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(userWithBlankPassword)))
// .andExpect(status().isBadRequest());
// }

// @Test
// void addUser_WithNullUser_ShouldReturnBadRequest() throws Exception {
// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content("{}")) // Empty JSON object
// .andExpect(status().isBadRequest());
// }

// @Test
// void addUser_WithInvalidJson_ShouldReturnBadRequest() throws Exception {
// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content("invalid json"))
// .andExpect(status().isBadRequest());
// }

// @Test
// void addUser_WithMissingContentType_ShouldReturnUnsupportedMediaType() throws
// Exception {
// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .content(objectMapper.writeValueAsString(validUser)))
// .andExpect(status().isUnsupportedMediaType());
// }

// @Test
// void addUser_WithNullNameField_ShouldReturnBadRequest() throws Exception {
// // Given
// UserModel userWithNullName = new UserModel();
// userWithNullName.setName(null);
// userWithNullName.setPassword("validPassword");

// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(userWithNullName)))
// .andExpect(status().isBadRequest());
// }

// @Test
// void addUser_WithNullPasswordField_ShouldReturnBadRequest() throws Exception
// {
// // Given
// UserModel userWithNullPassword = new UserModel();
// userWithNullPassword.setName("validName");
// userWithNullPassword.setPassword(null);

// // When & Then
// mockMvc.perform(post("/api/v1/user")
// .contentType(MediaType.APPLICATION_JSON)
// .content(objectMapper.writeValueAsString(userWithNullPassword)))
// .andExpect(status().isBadRequest());
// }

// // Tests for deleteUser method
// @Test
// void deleteUser_WithValidUserId_ShouldReturnNoContentStatus() throws
// Exception {
// // Given
// Long userId = 1L;
// doNothing().when(userService).deleteUser(userId);

// // When & Then
// mockMvc.perform(delete("/api/v1/user/{userId}", userId))
// .andExpect(status().isNoContent());

// verify(userService).deleteUser(userId);
// }

// @Test
// void deleteUser_WithNonExistentUserId_ShouldReturnNoContentStatus() throws
// Exception {
// // Given
// Long nonExistentUserId = 999L;
// doNothing().when(userService).deleteUser(nonExistentUserId);

// // When & Then
// mockMvc.perform(delete("/api/v1/user/{userId}", nonExistentUserId))
// .andExpect(status().isNoContent());

// verify(userService).deleteUser(nonExistentUserId);
// }

// @Test
// void deleteUser_WithInvalidUserIdFormat_ShouldReturnBadRequest() throws
// Exception {
// // When & Then
// mockMvc.perform(delete("/api/v1/user/{userId}", "invalid"))
// .andExpect(status().isBadRequest());
// }

// @Test
// void deleteUser_WithNegativeUserId_ShouldCallServiceAndReturnNoContent()
// throws Exception {
// // Given
// Long negativeUserId = -1L;
// doNothing().when(userService).deleteUser(negativeUserId);

// // When & Then
// mockMvc.perform(delete("/api/v1/user/{userId}", negativeUserId))
// .andExpect(status().isNoContent());

// verify(userService).deleteUser(negativeUserId);
// }

// @Test
// void deleteUser_WithZeroUserId_ShouldCallServiceAndReturnNoContent() throws
// Exception {
// // Given
// Long zeroUserId = 0L;
// doNothing().when(userService).deleteUser(zeroUserId);

// // When & Then
// mockMvc.perform(delete("/api/v1/user/{userId}", zeroUserId))
// .andExpect(status().isNoContent());

// verify(userService).deleteUser(zeroUserId);
// }

// @Test
// void deleteUser_WithLargeUserId_ShouldCallServiceAndReturnNoContent() throws
// Exception {
// // Given
// Long largeUserId = Long.MAX_VALUE;
// doNothing().when(userService).deleteUser(largeUserId);

// // When & Then
// mockMvc.perform(delete("/api/v1/user/{userId}", largeUserId))
// .andExpect(status().isNoContent());

// verify(userService).deleteUser(largeUserId);
// }
// }