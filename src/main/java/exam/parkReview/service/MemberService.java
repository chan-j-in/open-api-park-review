package exam.parkReview.service;

import exam.parkReview.domain.entity.Member;
import exam.parkReview.dto.MemberLoginDto;
import exam.parkReview.dto.MemberSignupDto;
import exam.parkReview.exception.AppException;
import exam.parkReview.exception.ErrorCode;
import exam.parkReview.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

}
