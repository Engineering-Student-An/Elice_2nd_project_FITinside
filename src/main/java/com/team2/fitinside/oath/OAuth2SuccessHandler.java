package com.team2.fitinside.oath;

import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.jwt.TokenProvider;
import com.team2.fitinside.oath.util.RefreshTokenCookieUtil;
import com.team2.fitinside.member.repository.MemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;


@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;
    private final RefreshTokenCookieUtil refreshTokenCookieUtil;
//    private static final String URI = "http://localhost:3000/tokenCheck";
    private static final String URI = "https://fit-inside.netlify.app/tokenCheck";
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal(); // 인증된 사용자의 정보를 가져옴
        Member member = memberRepository.findByEmail((String) oAuth2User.getAttributes().get("email"))
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)); // 사용자 정보 조회

        // accessToken, refreshToken 발급
        String accessToken = tokenProvider.generateAccessToken(authentication);

        String refreshToken = tokenProvider.generateRefreshToken(authentication);
        refreshTokenCookieUtil.saveRefreshToken(member.getId(), refreshToken); // 리프레시 토큰 저장
        refreshTokenCookieUtil.addRefreshTokenToCookie(request, response, refreshToken); // 리프레시 토큰을 쿠키에 추가


        // 토큰 전달을 위한 redirect
        String redirectUrl = UriComponentsBuilder.fromUriString(URI)
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        response.sendRedirect(redirectUrl);

    }


}
