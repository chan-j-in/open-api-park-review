package exam.parkReview.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    private int reviewCount;
    private double ratingAvg;

    @Builder
    public Member(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
    }

    public void addReviewCount() {
        this.reviewCount++;
    }

    public void subtractReviewCount() {
        this.reviewCount--;
    }

    public void setRatingAvg() {

        if (reviews == null || reviews.isEmpty()) {
            this.ratingAvg = 0;
            return;
        }

        double totalRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();

        this.ratingAvg = totalRating/reviews.size();
    }


}
