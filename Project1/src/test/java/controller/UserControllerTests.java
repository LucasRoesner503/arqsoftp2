package controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.isep.acme.controllers.UserController;
import com.isep.acme.model.Review;
import com.isep.acme.model.User;
import com.isep.acme.model.UserView;
import com.isep.acme.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import utilities.TestDataInitializer;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@EnableWebMvc
@WithMockUser
@ExtendWith(MockitoExtension.class)
public class UserControllerTests {

    @InjectMocks
    private UserController userController;

    @MockBean
    private UserService userService;

    private final TestDataInitializer tdi = new TestDataInitializer();
    private final User user = tdi.createSampleUser();

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetUser() throws Exception {
        // Define a sample user ID for testing
        Long userId = 1L;

        String jsonContent = "{'id':" + userId + ",'username':'" + user.getUsername() + "','fullname':'" + user.getFullName() + "'}";


        // Create a sample UserView
        UserView userView = new UserView(); // Replace with your actual UserView instance

        // Mock the service method to return the UserView when getUser is called
        when(userService.getUser(userId)).thenReturn(userView);

        // Perform the actual HTTP request to your controller
        mockMvc.perform(get("/admin/user/{userId}", userId)
                        .with(SecurityMockMvcRequestPostProcessors.jwt()))
                .andExpect(status().isOk());

    }

}









