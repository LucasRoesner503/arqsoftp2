package controller;



import com.arqsoft.project2.acmeUser.controllers.UserController;
import com.arqsoft.project2.acmeUser.model.User;
import com.arqsoft.project2.acmeUser.model.UserView;
import com.arqsoft.project2.acmeUser.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import utilities.TestDataInitializer;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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









