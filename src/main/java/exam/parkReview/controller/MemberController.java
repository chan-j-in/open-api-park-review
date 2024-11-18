package exam.parkReview.controller;


import exam.parkReview.dto.MemberLoginDto;
import exam.parkReview.dto.MemberSignupDto;
import exam.parkReview.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody MemberSignupDto memberSignupDto) {

        memberService.signUp(memberSignupDto);
        return ResponseEntity.ok("회원가입 성공");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginDto memberLoginDto) {

        String token = memberService.login(memberLoginDto);
        return ResponseEntity.ok(token);
    }
}
