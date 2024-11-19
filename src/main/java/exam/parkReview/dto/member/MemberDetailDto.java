package exam.parkReview.dto.member;

import exam.parkReview.domain.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDetailDto {

    private String username;
    private int reviewCount;
    private double ratingAvg;
    private List<Review> reviews;
}
