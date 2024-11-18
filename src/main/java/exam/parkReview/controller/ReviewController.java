package exam.parkReview.controller;

import exam.parkReview.domain.entity.Review;
import exam.parkReview.dto.CreateReviewDto;
import exam.parkReview.dto.ParkReviewListDto;
import exam.parkReview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/parks/{parkId}")
    public ResponseEntity<CreateReviewDto> createReview(@PathVariable("parkNum") Long parkNum, @RequestBody CreateReviewDto createReviewDto) {

        createReviewDto.setParkNum(parkNum);
        reviewService.createReview(createReviewDto);
        createReviewDto.setUsername("null");
        return ResponseEntity.ok(createReviewDto);
    }

    @GetMapping("/parks/{parkNum}")
    public ResponseEntity<List<ParkReviewListDto>> getReviewsByPark(@PathVariable("parkNum") Long parkNum) {

        List<ParkReviewListDto> reviews = reviewService.getReviewsByPark(parkNum);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/member")
    public ResponseEntity<List<ParkReviewListDto>> getReviewsByMember(Authentication authentication) {

        String username = authentication.getName();
        List<ParkReviewListDto> reviews = reviewService.getReviewsByMember(username);
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable("reviewId") Long reviewId) {

        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<ParkReviewListDto> updateReview(@PathVariable Long reviewId, @RequestBody CreateReviewDto updateReviewDto) {

        Review updatedReview = reviewService.updateReview(reviewId, updateReviewDto);

        ParkReviewListDto responseDto = new ParkReviewListDto(
                updatedReview.getPark().getParkNum(),
                updatedReview.getContent(),
                updatedReview.getRating(),
                updatedReview.getMember().getUsername()
        );

        return ResponseEntity.ok(responseDto);
    }

}
