package com.team2.fitinside.member.controller;

import com.team2.fitinside.member.dto.MemberRequestDto;
import com.team2.fitinside.member.dto.MemberResponseDto;
import com.team2.fitinside.member.dto.TokenDto;
import com.team2.fitinside.member.oath.util.RefreshTokenCookieUtil;
import com.team2.fitinside.member.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenCookieUtil refreshTokenCookieUtil;

    // 회원가입
    @PostMapping("")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody MemberRequestDto requestDto,
            HttpServletResponse response,
            HttpServletRequest request) {
        TokenDto tokenDto = authService.login(request, response, requestDto);
        return ResponseEntity.ok(tokenDto);
    }
}