package services;


import com.arqsoft.project2.acmeUser.model.User;
import com.arqsoft.project2.acmeUser.model.UserView;
import com.arqsoft.project2.acmeUser.model.UserViewMapper;
import com.arqsoft.project2.acmeUser.repositories.UserRepository;
import com.arqsoft.project2.acmeUser.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import utilities.TestDataInitializer;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class UserServiceTests {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserViewMapper userViewMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private final TestDataInitializer tdi = new TestDataInitializer();

    private final User user = tdi.createSampleUser();

    @Test
    public void testLoadUserByUsername_UserFound() {
        when(userRepository.findByUsername(TestDataInitializer.username)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.loadUserByUsername(TestDataInitializer.username));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
    }

    @Test
    public void testGetUser_UserFound() {

        when(userRepository.getById(TestDataInitializer.userId)).thenReturn(user);

        UserView userView = new UserView(); // Assuming you have a UserView object
        when(userViewMapper.toUserView(user)).thenReturn(userView);

        UserView result = userService.getUser(TestDataInitializer.userId);

        assertNotNull(result);
        assertEquals(userView, result);
    }

    @Test
    public void testGetUserId_UserFound() {

        when(userRepository.findById(TestDataInitializer.userId)).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserId(TestDataInitializer.userId);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    public void testGetUserId_UserNotFound() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        Optional<User> result = userService.getUserId(userId);

        assertTrue(result.isEmpty());
    }



}
