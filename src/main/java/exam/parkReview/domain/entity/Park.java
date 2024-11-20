package exam.parkReview.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long parkNum;
    private String parkName;
    @Column(length = 1000)
    private String parkSummary;
    @Column(length = 1000)
    private String mainFacilities;
    private String guideMap;
    private String address;
    private String websiteUrl;

    @OneToMany(mappedBy = "park", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    private int reviewCount;
    private double ratingAvg;

    @Builder
    public Park(long parkNum, String parkName, String parkSummary, String mainFacilities, String guideMap, String address, String websiteUrl) {
        this.parkNum = parkNum;
        this.parkName = parkName;
        this.parkSummary = parkSummary;
        this.mainFacilities = mainFacilities;
        this.guideMap = guideMap;
        this.address = address;
        this.websiteUrl = websiteUrl;
        this.reviewCount = 0;
        this.ratingAvg = 0.0;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        this.reviewCount = this.reviews.size();
        this.ratingAvg = calculateRatingAvg();
    }

    public void removeReview(Review review) {
        this.reviews.remove(review);
        this.reviewCount = this.reviews.size();
        this.ratingAvg = calculateRatingAvg();
    }

    public void updateReview() {
        this.ratingAvg = calculateRatingAvg();
    }

    private double calculateRatingAvg() {
        if (this.reviews.isEmpty()) {
            return 0.0;
        }
        double totalRating = this.reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();
        return totalRating / this.reviews.size();
    }
}
