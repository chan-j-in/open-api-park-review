package exam.parkReview.dto.member;

import exam.parkReview.domain.entity.Member;
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

    public MemberDto(Member member) {
        this.username = member.getUsername();
        this.reviewCount = member.getReviewCount();
        this.ratingAvg = member.getRatingAvg();
    }
}
