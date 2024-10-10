package com.team2.fitinside.member.controller;

import com.team2.fitinside.member.dto.ChangePasswordRequestDto;
import com.team2.fitinside.member.dto.MemberRequestDto;
import com.team2.fitinside.member.dto.MemberResponseDto;
import com.team2.fitinside.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class MemberController {

    private final MemberService memberService;

    // 유저 개인 정보 가져오기
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDto> getMyMemberInfo() {
        MemberResponseDto myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getUserName());
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }

    // 유저 이름 변경
    @PutMapping("/username")
    public ResponseEntity<MemberResponseDto> setMemberUserName(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberUserName(request.getUserName()));
    }

    // 유저 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<MemberResponseDto> setMemberPassword(@RequestBody ChangePasswordRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getExPassword(), request.getNewPassword()));
    }

    // 유저 전화번호 변경
    @PutMapping("/phone")
    public ResponseEntity<MemberResponseDto> setMemberPhone(@RequestBody MemberRequestDto request) {
        return ResponseEntity.ok(memberService.changeMemberPhone(request.getPhone()));
    }

    // 유저 탈퇴
    @DeleteMapping("/delete")
    public ResponseEntity<MemberResponseDto> deleteMember() {
        return ResponseEntity.ok(memberService.deleteMember());
    }

}