package exam.parkReview.dto.park;

import exam.parkReview.domain.entity.Park;
import exam.parkReview.dto.review.ReviewResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private List<ReviewResponseDto> reviews = new ArrayList<>();

    public ParkDetailsDto(Park park) {
        this.parkNum = park.getParkNum();
        this.parkName = park.getParkName();
        this.parkSummary = park.getParkSummary();
        this.mainFacilities = park.getMainFacilities();
        this.guideMap = park.getGuideMap();
        this.address = park.getAddress();
        this.websiteUrl = park.getWebsiteUrl();
        this.reviews = reviews;
    }
}
