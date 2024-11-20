package exam.parkReview.domain;

import exam.parkReview.domain.entity.Review;
import exam.parkReview.exception.AppException;
import exam.parkReview.exception.ErrorCode;
import lombok.Getter;

import java.util.List;

@Getter
public class ReviewStatistics {

    private int reviewCount;
    private double ratingAvg;

    public void incrementReviewCount() {
        this.reviewCount++;
    }

    public void decrementReviewCount() {
        this.reviewCount--;
    }

    public void calculateRatingAvg(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            throw new AppException(ErrorCode.REVIEW_NOT_FOUND, "등록된 리뷰가 없습니다.");
        }
        double totalRating = reviews.stream()
                .mapToDouble(Review::getRating)
                .sum();

        this.ratingAvg = totalRating / reviews.size();
    }
}
