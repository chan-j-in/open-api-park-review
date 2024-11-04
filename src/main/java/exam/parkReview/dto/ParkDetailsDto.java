package exam.parkReview.dto;

import exam.parkReview.domain.entity.Park;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public ParkDetailsDto(Park park) {
        this.parkNum = park.getParkNum();
        this.parkName = park.getParkName();
        this.parkSummary = park.getParkSummary();
        this.mainFacilities = park.getMainFacilities();
        this.guideMap = park.getGuideMap();
        this.address = park.getAddress();
        this.websiteUrl = park.getWebsiteUrl();
    }
}
