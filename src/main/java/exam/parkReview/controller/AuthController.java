package exam.parkReview.controller;

import exam.parkReview.dto.MemberLoginDto;
import exam.parkReview.dto.MemberSignupDto;
import exam.parkReview.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody MemberSignupDto requestDto) {

        log.info("dto username : {}", requestDto.getUsername());

        String username = authService.join(requestDto);
        return ResponseEntity.ok("회원가입 성공 - id : " + username);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginDto requestDto) {

        String token = authService.login(requestDto);
        return ResponseEntity.ok(token);
    }
}
