package exam.parkReview.service;

import exam.parkReview.domain.entity.Member;
import exam.parkReview.domain.entity.Park;
import exam.parkReview.domain.entity.Review;
import exam.parkReview.dto.review.CreateReviewRequestDto;
import exam.parkReview.dto.review.ReviewResponseDto;
import exam.parkReview.dto.review.UpdateReviewRequestDto;
import exam.parkReview.exception.AppException;
import exam.parkReview.exception.ErrorCode;
import exam.parkReview.repository.MemberRepository;
import exam.parkReview.repository.ParkRepository;
import exam.parkReview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ParkRepository parkRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ReviewResponseDto createReview(Long parkNum, CreateReviewRequestDto createReviewRequestDto) {

        Park park = parkRepository.findByParkNum(parkNum)
                .orElseThrow(() -> new AppException(ErrorCode.PARK_NOT_FOUND, "올바르지 않은 공원 번호입니다."));

        Member member = getCurrentMember();

        if (reviewRepository.existsByParkAndMember(park, member)) {
            throw new AppException(ErrorCode.REVIEW_ALREADY_EXISTS, "이미 작성하신 리뷰가 있습니다.");
        }

        Review review = Review.builder()
                .content(createReviewRequestDto.getContent())
                .rating(createReviewRequestDto.getRating())
                .park(park)
                .member(member)
                .build();

        member.addReview(review);
        Review savedReview = reviewRepository.save(review);

        member.addReviewCount();
        member.setRatingAvg();

        return new ReviewResponseDto(
                parkNum,
                savedReview.getContent(),
                savedReview.getRating(),
                member.getUsername());
    }

    public List<ReviewResponseDto> getReviewsByPark(Long parkNum) {

        Park park = parkRepository.findByParkNum(parkNum)
                .orElseThrow(() -> new AppException(ErrorCode.PARK_NOT_FOUND, "올바르지 않은 공원 번호입니다."));

        List<Review> reviews = reviewRepository.findByPark(park);

        return reviews.stream()
                .map(review -> new ReviewResponseDto(review.getPark().getParkNum(), review.getContent(), review.getRating(), review.getMember().getUsername()))
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDto> getReviewsByMember() {

        Member member = getCurrentMember();

        List<Review> reviews = reviewRepository.findByMember(member);
        return reviews.stream()
                .map(review -> new ReviewResponseDto(review.getPark().getParkNum(), review.getContent(), review.getRating(), review.getMember().getUsername()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long parkNum) {

        Park park = parkRepository.findByParkNum(parkNum)
                .orElseThrow(() -> new AppException(ErrorCode.PARK_NOT_FOUND, "올바르지 않은 공원 번호입니다."));

        Member currentMember = getCurrentMember();

        Review review = reviewRepository.findByParkAndMember(park, currentMember)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND, "리뷰를 찾을 수 없습니다."));

        currentMember.removeReview(review);
        reviewRepository.delete(review);

        currentMember.subtractReviewCount();
        currentMember.setRatingAvg();
    }

    @Transactional
    public Review updateReview(Long parkNum, UpdateReviewRequestDto updateReviewRequestDto) {

        Park park = parkRepository.findByParkNum(parkNum)
                .orElseThrow(() -> new AppException(ErrorCode.PARK_NOT_FOUND, "올바르지 않은 공원 번호입니다."));

        Member currentMember = getCurrentMember();

        Review review = reviewRepository.findByParkAndMember(park, currentMember)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND, "작성된 리뷰가 없습니다."));

        review.update(updateReviewRequestDto.getContent(), updateReviewRequestDto.getRating());

        currentMember.setRatingAvg();

        return review;
    }

    private Member getCurrentMember() {
        // 현재 로그인된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AppException(ErrorCode.MEMBER_NOT_AUTHENTICATED, "사용자가 인증되지 않았습니다.");
        }

        String currentUsername = authentication.getName();

        // 현재 사용자 이름으로 회원 정보 조회
        return memberRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "사용자를 찾을 수 없습니다."));
    }

}
