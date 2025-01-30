package com.team2.fitinside.coupon.service;

import com.team2.fitinside.coupon.dto.CouponEmailRequestDto;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("쿠폰 이메일 서비스 단위 테스트")
class CouponEmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MimeMessage mimeMessage;

    @InjectMocks
    private CouponEmailService couponEmailService;

    @BeforeEach
    public void setUp() {
        given(javaMailSender.createMimeMessage()).willReturn(mimeMessage);
    }

    @Test
    @DisplayName("이메일 전송 성공 시 JavaMailSender 의 send() 메서드가 1회 호출된다.")
    public void sendEmail() throws Exception {

        //given
        CouponEmailRequestDto dto = new CouponEmailRequestDto(1L, "successEmail@test.com", "이메일 템플릿");

        //when
        couponEmailService.sendEmail(dto);

        // Then
        verify(javaMailSender).send(mimeMessage);
    }

    @Test
    @DisplayName("이메일 전송 시 유효하지 않은 이메일인 경우 INVALID_EMAIL_DATA 예외를 반환한다.")
    public void sendEmailInvalidEmailData() throws Exception {

        //given
        CouponEmailRequestDto dto = new CouponEmailRequestDto(1L, "failureEmail@test.com", "이메일 템플릿");


        doThrow(new RuntimeException("Mail sending failed"))
                .when(javaMailSender).send(mimeMessage);

        //when, then
        CustomException invalidEmailDataException = assertThrows(CustomException.class, () -> {
            couponEmailService.sendEmail(dto);
        });

        assertThat(invalidEmailDataException.getErrorCode()).isEqualTo(ErrorCode.INVALID_EMAIL_DATA);
    }

}