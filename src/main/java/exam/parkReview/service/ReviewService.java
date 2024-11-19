package exam.parkReview.service;

import exam.parkReview.domain.entity.Member;
import exam.parkReview.domain.entity.Park;
import exam.parkReview.domain.entity.Review;
import exam.parkReview.dto.CreateReviewDto;
import exam.parkReview.dto.ParkReviewListDto;
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
    public void createReview(CreateReviewDto createReviewDto) {

        Park park = parkRepository.findByParkNum(createReviewDto.getParkNum())
                .orElseThrow(() -> new AppException(ErrorCode.PARK_NOT_FOUND, "올바르지 않은 공원 번호입니다."));

        Member member = getCurrentMember();

        Review review = Review.builder()
                .content(createReviewDto.getContent())
                .rating(createReviewDto.getRating())
                .park(park)
                .member(member)
                .build();

        reviewRepository.save(review);
    }

    public List<ParkReviewListDto> getReviewsByPark(Long parkNum) {

        Park park = parkRepository.findByParkNum(parkNum)
                .orElseThrow(() -> new AppException(ErrorCode.PARK_NOT_FOUND, "올바르지 않은 공원 번호입니다."));

        List<Review> reviews = reviewRepository.findByPark(park);

        return reviews.stream()
                .map(review -> new ParkReviewListDto(review.getPark().getParkNum(), review.getContent(), review.getRating(), review.getMember().getUsername()))
                .collect(Collectors.toList());
    }

    public List<ParkReviewListDto> getReviewsByMember(String username) {

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "회원을 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findByMember(member);
        return reviews.stream()
                .map(review -> new ParkReviewListDto(review.getPark().getParkNum(), review.getContent(), review.getRating(), review.getMember().getUsername()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteReview(Long reviewId) {

        Member currentMember = getCurrentMember();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND, "리뷰를 찾을 수 없습니다."));

        if (!review.getMember().equals(currentMember)) {
            throw new AppException(ErrorCode.UNAUTHORIZED, "본인의 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }

    @Transactional
    public Review updateReview(Long reviewId, CreateReviewDto updateReviewDto) {

        Member currentMember = getCurrentMember();

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new AppException(ErrorCode.REVIEW_NOT_FOUND, "리뷰를 찾을 수 없습니다."));

        if (!review.getMember().equals(currentMember)) {
            throw new AppException(ErrorCode.UNAUTHORIZED, "본인의 리뷰만 수정할 수 있습니다.");
        }

        review.update(updateReviewDto.getContent(), updateReviewDto.getRating());

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