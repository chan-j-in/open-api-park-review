package exam.parkReview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDto {

    private String content;
    private int rating;
    private Long parkNum;
    private String username;
}
