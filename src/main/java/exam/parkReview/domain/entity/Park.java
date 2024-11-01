package exam.parkReview.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Park {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long parkNum;
    private String parkName;
    @Column(length = 1000)
    private String parkSummary;
    @Column(length = 1000)
    private String mainFacilities;
    private String guideMap;
    private String address;
    private String websiteUrl;

    @Builder
    public Park(long parkNum, String parkName, String parkSummary, String mainFacilities, String guideMap, String address, String websiteUrl) {
        this.parkNum = parkNum;
        this.parkName = parkName;
        this.parkSummary = parkSummary;
        this.mainFacilities = mainFacilities;
        this.guideMap = guideMap;
        this.address = address;
        this.websiteUrl = websiteUrl;
    }
}
