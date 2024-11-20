package exam.parkReview.dto.member;

import exam.parkReview.domain.entity.Member;
import exam.parkReview.domain.entity.Review;
import exam.parkReview.dto.review.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDto {

    private String username;
    private int reviewCount;
    private double ratingAvg;
    private List<ReviewResponseDto> reviews;

    public MemberDetailDto(Member member) {
        this.username = member.getUsername();
        this.reviewCount = member.getReviewCount();
        this.ratingAvg = member.getRatingAvg();
        this.reviews = member.getReviews().stream()
                .map(review -> new ReviewResponseDto(
                        review.getPark().getParkNum(),
                        review.getContent(),
                        review.getRating(),
                        review.getMember().getUsername()
                ))
                .collect(Collectors.toList());
    }
}
