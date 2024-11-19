package exam.parkReview.controller;

import exam.parkReview.domain.entity.Review;
import exam.parkReview.dto.CreateReviewRequestDto;
import exam.parkReview.dto.ReviewResponseDto;
import exam.parkReview.dto.UpdateReviewRequestDto;
import exam.parkReview.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/parks/{parkNum}")
    public ResponseEntity<ReviewResponseDto> createReview(@PathVariable("parkNum") Long parkNum, @Valid @RequestBody CreateReviewRequestDto createReviewRequestDto) {

        ReviewResponseDto createReviewResponseDto = reviewService.createReview(parkNum, createReviewRequestDto);
        return ResponseEntity.ok(createReviewResponseDto);
    }

    @GetMapping("/parks/{parkNum}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByPark(@PathVariable("parkNum") Long parkNum) {

        List<ReviewResponseDto> reviews = reviewService.getReviewsByPark(parkNum);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/member")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByMember() {

        List<ReviewResponseDto> reviews = reviewService.getReviewsByMember();
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/parks/{parkNum}")
    public ResponseEntity<Void> deleteReview(@PathVariable("parkNum") Long parkNum) {

        reviewService.deleteReview(parkNum);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/parks/{parkNum}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable("parkNum") Long parkNum, @Valid @RequestBody UpdateReviewRequestDto updateReviewRequestDto) {

        Review updatedReview = reviewService.updateReview(parkNum, updateReviewRequestDto);

        ReviewResponseDto responseDto = new ReviewResponseDto(
                updatedReview.getPark().getParkNum(),
                updatedReview.getContent(),
                updatedReview.getRating(),
                updatedReview.getMember().getUsername()
        );

        return ResponseEntity.ok(responseDto);
    }

}
