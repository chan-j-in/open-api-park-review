package exam.parkReview.service;

import exam.parkReview.domain.entity.Member;
import exam.parkReview.domain.entity.Review;
import exam.parkReview.dto.member.MemberDetailDto;
import exam.parkReview.dto.member.MemberDto;
import exam.parkReview.dto.review.ReviewResponseDto;
import exam.parkReview.exception.AppException;
import exam.parkReview.exception.ErrorCode;
import exam.parkReview.repository.MemberRepository;
import exam.parkReview.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    public List<MemberDto> getMembers() {

        List<Member> members = memberRepository.findAll();

        return members.stream()
                .map(member -> new MemberDto(
                        member.getUsername(),
                        member.getReviewCount(),
                        member.getRatingAvg()
                ))
                .collect(Collectors.toList());
    }

    public MemberDetailDto getMemberDetail(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "회원을 찾을 수 없습니다."));

        List<Review> reviews = reviewRepository.findByMember(member);
        List<ReviewResponseDto> reviewResponseDtos = reviews.stream()
                .map(review -> new ReviewResponseDto(
                        review.getPark().getParkNum(),
                        review.getContent(),
                        review.getRating(),
                        review.getMember().getUsername()
                ))
                .collect(Collectors.toList());

        return new MemberDetailDto(
                member.getUsername(),
                member.getReviewCount(),
                member.getRatingAvg(),
                reviewResponseDtos);
    }

    public void deleteMember(Long memberId) {

        memberRepository.delete(memberRepository.findById(memberId)
                .orElseThrow(() -> new AppException(ErrorCode.MEMBER_NOT_FOUND, "회원을 찾을 수 없습니다.")));
    }
}
