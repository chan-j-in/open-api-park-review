package exam.parkReview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkReviewListDto {

    private Long parkNum;
    private String content;
    private int rating;
    private String username;
}
