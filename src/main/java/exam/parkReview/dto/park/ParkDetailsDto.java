package exam.parkReview.dto.park;

import exam.parkReview.domain.entity.Park;
import exam.parkReview.domain.entity.Review;
import exam.parkReview.dto.review.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkDetailsDto {

    private long parkNum;
    private String parkName;
    private String parkSummary;
    private String mainFacilities;
    private String guideMap;
    private String address;
    private String websiteUrl;
    private int reviewCount;
    private double ratingAvg;
    private List<ReviewResponseDto> reviews;

    public ParkDetailsDto(Park park) {
        this.parkNum = park.getParkNum();
        this.parkName = park.getParkName();
        this.parkSummary = park.getParkSummary();
        this.mainFacilities = park.getMainFacilities();
        this.guideMap = park.getGuideMap();
        this.address = park.getAddress();
        this.websiteUrl = park.getWebsiteUrl();
        this.reviewCount = park.getReviewCount();
        this.ratingAvg = park.getRatingAvg();
        this.reviews = park.getReviews().stream()
                .map(review -> new ReviewResponseDto(
                        review.getPark().getParkNum(),
                        review.getContent(),
                        review.getRating(),
                        review.getMember().getUsername()
                ))
                .collect(Collectors.toList());
    }
}
