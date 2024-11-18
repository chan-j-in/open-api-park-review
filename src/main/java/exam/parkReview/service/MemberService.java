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

    @Transactional
    public void signUp(MemberSignupDto memberSignupDto) {

        if (memberRepository.existsByUsername(memberSignupDto.getUsername())) {
            throw new AppException(ErrorCode.DUPLICATE_MEMBER, "중복된 ID 입니다.");
        }

        if(memberSignupDto.getPassword().equals(memberSignupDto.getPasswordCheck())) {
            throw new AppException(ErrorCode.PASSWORD_DO_NOT_MATCH, "두 비밀번호가 일치하지 않습니다.");
        }

        Member member = Member.builder()
                .username(memberSignupDto.getUsername())
                .password(memberSignupDto.getPassword())
                .build();
        memberRepository.save(member);
    }

    public String login(MemberLoginDto memberLoginDto) {

        Member member = memberRepository.findByUsername(memberLoginDto.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.INVALID_CREDENTIALS, "아이디 또는 비밀번호를 확인해주세요."));

        if (member.getPassword().equals(memberLoginDto.getPassword())) {
            throw new AppException(ErrorCode.INVALID_CREDENTIALS, "아이디 또는 비밀번호를 확인해주세요.");
        }

        return "token";
    }

}
