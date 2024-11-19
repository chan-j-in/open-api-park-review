package exam.parkReview.controller;

import exam.parkReview.dto.member.MemberDetailDto;
import exam.parkReview.dto.member.MemberDto;
import exam.parkReview.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    public ResponseEntity<List<MemberDto>> getMembers() {

        List<MemberDto> members = memberService.getMembers();
        return ResponseEntity.ok(members);
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetailDto> getMemberDetail(@PathVariable("memberId") Long memberId) {

        MemberDetailDto memberDetail = memberService.getMemberDetail(memberId);
        return ResponseEntity.ok(memberDetail);
    }
}
