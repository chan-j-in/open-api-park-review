package exam.parkReview.dto.review;

import exam.parkReview.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDto {

    private Long parkNum;
    private String content;
    private int rating;
    private String username;

    public ReviewResponseDto(Review review) {
        this.parkNum = review.getPark().getParkNum();
        this.content = review.getContent();
        this.rating = review.getRating();
        this.username = review.getMember().getUsername();
    }
}
