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
    public static final Long reviewVersion = 1L;
    public static final String approvedStatus = "approved";
    public static final String pendingStatus = "pending";
    public static final String rejectedStatus = "rejected";
    public static final String reviewText = "Review text";
    public static final List<Vote> upVote = new ArrayList<Vote>(List.of(new Vote("upvote", 111L), new Vote("upvote", 112L), new Vote("upvote", 113L)));
    public static final List<Vote> downVote = new ArrayList<Vote>(List.of(new Vote("downvote", 114L), new Vote("downvote", 115L), new Vote("downvote", 116L)));
    public static final String report = "Review Report";

    // yy-mm-dd
    public static final LocalDate publishingDate = LocalDate.parse("2023-12-25"); // ho ho ho
    public static final String funFact = "im not funny";
    public static  Product product;
    public static  User user;
    public static  Rating rating;

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
    public static final List<Vote> supUpVote1 = new ArrayList<Vote>(List.of(new Vote("upvote", 111L), new Vote("upvote", 101L), new Vote("upvote", 102L)));

    // No upvote from original User
    public static final List<Vote> supUpVote2 = new ArrayList<Vote>(List.of(new Vote("upvote", 108L), new Vote("upvote", 105L), new Vote("upvote", 106L)));

    // No upvote from original user but 102L and 103L upvoted
    public static final List<Vote> supUpVote3 = new ArrayList<Vote>(List.of(new Vote("upvote", 108L), new Vote("upvote", 102L), new Vote("upvote", 103L)));

    // No downvotes from original User
    public static final List<Vote> supDownVote1 = new ArrayList<Vote>(List.of(new Vote("downvote", 129L), new Vote("downvote", 130L), new Vote("downvote", 131L)));

    // 103L and 104L have the same vote as the original user - downvote
    public static final List<Vote> supDownVote2 = new ArrayList<Vote>(List.of(new Vote("downvote", 111L), new Vote("downvote", 103L), new Vote("downvote", 104L)));

    // No upvote from original user neither related ones
    public static final List<Vote> supDownVote3 = new ArrayList<Vote>(List.of(new Vote("downvote", 127L), new Vote("downvote", 128L), new Vote("downvote", 129L)));


    public Product createSampleProduct(){
        return new Product(productId,sku,designation,description);
    }

    public Rating createSampleRating(){
        return new Rating(idRating,ratingVersion,rate);
    }

    public User createSampleUser(){
        return new User(originalUserId, originalUsername, originalPassword, originalFullname, originalNif, originalMorada);
    }

    public Review createSampleReview(){
        product = createSampleProduct();
        user = createSampleUser();
        rating = createSampleRating();
        return new Review(idReview,reviewVersion,approvedStatus,reviewText,upVote,downVote,report,publishingDate,funFact,product,rating,user);
    }

    public List<Review> createSampleReviews() {
        List<Review> reviews = new ArrayList<>();

        // Reviews which 101L and 102L have the same upvote as the original user
        for (int i = 0; i < 5; i++) {
            User user = new User(supUserId + i, supUsername + i, supPassword, supFullname, supNif, supMorada);
            Rating rating = new Rating(idRating + i, ratingVersion, rate);
            Product product = createSampleProduct(); // will use the same product for all reviews
            Review review = new Review(
                    idReview + i,
                    reviewVersion,
                    approvedStatus,
                    reviewText,
                    supUpVote1,
                    supDownVote1,
                    report,
                    publishingDate,
                    funFact,
                    product,
                    rating,
                    user
            );
            reviews.add(review);
        }

        // Reviews which 103L and 104L have the same downvote as the original user
        for (int i = 5; i < 10; i++) {
            User user = new User(supUserId + i, supUsername + i, supPassword, supFullname, supNif, supMorada);
            Rating rating = new Rating(idRating + i, ratingVersion, rate);
            Product product = createSampleProduct(); // will use the same product for all reviews
            Review review = new Review(
                    idReview + i,
                    reviewVersion,
                    approvedStatus,
                    reviewText,
                    supUpVote2,
                    supDownVote2,
                    report,
                    publishingDate,
                    funFact,
                    product,
                    rating,
                    user
            );
            reviews.add(review);
        }

        //Reviews to recommend - none voted by the original user
        for (int i = 10; i < 15; i++) {
            User user = new User(supUserId + i, supUsername + i, supPassword, supFullname, supNif, supMorada);
            Rating rating = new Rating(idRating + i, ratingVersion, rate);
            Product product = createSampleProduct(); // will use the same product for all reviews
            Review review = new Review(
                    idReview + i,
                    reviewVersion,
                    approvedStatus,
                    reviewText,
                    supUpVote3,
                    supDownVote3,
                    report,
                    publishingDate,
                    funFact,
                    product,
                    rating,
                    user
            );
            reviews.add(review);
        }

        return reviews;
    }


}
