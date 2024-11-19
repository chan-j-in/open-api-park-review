package exam.parkReview.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private String username;
    private int reviewCount;
    private double ratingAvg;
}
