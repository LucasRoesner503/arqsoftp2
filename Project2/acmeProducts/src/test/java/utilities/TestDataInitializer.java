package utilities;

import com.arqsoft.project2.acmeProducts.model.*;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@NoArgsConstructor
public class TestDataInitializer {

    // PRODUCT DATA
    public static final Long productId = 123L;
    public static final String sku = "SKU123456789";
    public static final String designation = "Sample designation";
    public static final String description = "Sample description";

    // REVIEW DATA
    public static final Long idReview = 100L;

    public static final String approvedStatus = "approved";
    public static final String pendingStatus = "pending";

    public static final String reviewText = "Review text";


    // yy-mm-dd
    public static final LocalDate publishingDate = LocalDate.parse("2023-12-25"); // ho ho ho
    public static final String funFact = "im not funny";
    public static  Product product;
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


    // RATING DATA
    public static final Long idRating = 1234L;
    public static final long ratingVersion = 2L;
    public static final Double rate = 2.2;

    // DIFFERENT UPVOTES AND DOWNVOTES - MORE 50% UPVOTES THAN ORIGINAL USER
    // 101L and 102L have the same vote as the original user - upvote

    public User createSampleUser(){
        return new User(originalUserId, originalUsername, originalPassword, originalFullname, originalNif, originalMorada);
    }

    public Product createSampleProduct(){
        return new Product(productId,sku,designation,description);
    }



}
