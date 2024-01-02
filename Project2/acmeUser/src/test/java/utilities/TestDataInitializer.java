package utilities;

import com.arqsoft.project2.acmeUser.model.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class TestDataInitializer {


    public static  User user;

    // ORIGINAL USER DATA

    public static final String username = "user123";
    public static final Long userId = 1L;

    public static final Long originalUserId = 111L;
    public static final String originalUsername = "Username";
    public static final String originalPassword = "Password";
    public static final String originalFullname = "Justino Malaquias";
    public static final List<Role> authorities = new ArrayList<Role>(List.of(new Role("Admin")));
    public static final String originalNif = "123456789";
    public static final String originalMorada = "Rua da Boda Do Povo";

    // SUPPORTING USER DATA

    public static final Long supUserId = 100L;
    public static final String supUsername = "supporting username";
    public static final String supPassword = "supporting password";
    public static final String supFullname = "Maria Antonieta";
    public static final List<Role> supAuthorities = new ArrayList<Role>(List.of(new Role("Admin")));
    public static final String supNif = "987654321";
    public static final String supMorada = "Rua do Povo da Boda";

    public User createSampleUser(){
        return new User(originalUserId, originalUsername, originalPassword, originalFullname, originalNif, originalMorada);
    }

}
