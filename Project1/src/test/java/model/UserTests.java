package model;


import com.isep.acme.model.Review;
import com.isep.acme.model.Role;
import com.isep.acme.model.User;
import com.isep.acme.model.Vote;
import org.junit.jupiter.api.Test;
import utilities.TestDataInitializer;

import static org.junit.jupiter.api.Assertions.*;

public class UserTests {

    private final TestDataInitializer tdi = new TestDataInitializer();


    User sampleUser = tdi.createSampleUser();

    @Test
    void testCreateSampleUser() {


        // Act & Assert
        assertNotNull(sampleUser);
        assertNotNull(sampleUser.getUserId());
        assertNotNull(sampleUser.getUsername());
        assertNotNull(sampleUser.getPassword());
        assertNotNull(sampleUser.getFullName());
        assertNotNull(sampleUser.getAuthorities());
        assertNotNull(sampleUser.getNif());
        assertNotNull(sampleUser.getMorada());
    }

    @Test
    void testAddAuthority() {

        String authorityName = "Admin";

        // Act
        sampleUser.addAuthority(new Role(authorityName));

        // Assert
        assertTrue(sampleUser.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals(authorityName)));
    }

    @Test
    void testSetNif() {

        String newNif = "023456789";

        // Act
        sampleUser.setNif(newNif);

        // Assert
        assertEquals(newNif, sampleUser.getNif());
    }


}
