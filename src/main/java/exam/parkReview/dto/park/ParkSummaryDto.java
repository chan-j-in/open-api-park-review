package exam.parkReview.dto.park;

import exam.parkReview.domain.entity.Park;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParkSummaryDto {

    private long parkNum;
    private String parkName;
    private String parkSummary;
    private String address;
    private int reviewCount;
    private double ratingAvg;

    public ParkSummaryDto(Park park) {
        this.parkNum = park.getParkNum();
        this.parkName = park.getParkName();
        this.parkSummary = park.getParkSummary();
        this.address = park.getAddress();
        this.reviewCount = park.getReviewCount();
        this.ratingAvg = park.getRatingAvg();
    }
}
