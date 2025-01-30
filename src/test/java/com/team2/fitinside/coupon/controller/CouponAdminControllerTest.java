package com.team2.fitinside.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.fitinside.coupon.dto.*;
import com.team2.fitinside.coupon.entity.Coupon;
import com.team2.fitinside.coupon.entity.CouponType;
import com.team2.fitinside.coupon.mapper.CouponMapper;
import com.team2.fitinside.coupon.service.CouponAdminService;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CouponAdminController.class)
@AutoConfigureMockMvc(addFilters = false) // 필터 제외 (JWT 검증 제외)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("쿠폰 관리자 컨트롤러 단위 테스트")
class CouponAdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponAdminService couponAdminService;

    private static final String URL = "/api/admin/coupon";
    private static final String URLS = "/api/admin/coupons";

    private static final int PAGE = 1;

    @Autowired
    private ObjectMapper objectMapper;

    private Coupon coupon1;
    private Coupon coupon2;

    @BeforeEach
    void setUp() {
        // 테스트용 쿠폰 생성
        coupon1 = createTestCoupon(1L, "coupon1", "AAAAAA", CouponType.AMOUNT, 10000, 0, LocalDate.of(2024, 10, 27), true);
        coupon2 = createTestCoupon(2L, "coupon2", "BBBBBB", CouponType.PERCENTAGE, 20, 30000, LocalDate.of(2024, 10, 20), false);
    }

    private Coupon createTestCoupon(Long id, String name, String code, CouponType type, int value, int minValue, LocalDate expiredAt, boolean active) {
        return Coupon.builder()
                .id(id)
                .name(name)
                .code(code)
                .type(type)
                .value(value)
                .minValue(minValue)
                .expiredAt(expiredAt)
                .active(active)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("쿠폰 목록 조회 시 유효한 쿠폰만 조회하는 경우 성공 시 상태코드 200을 반환하며 유효한 쿠폰먼 포함된 CouponResponseWrapperDto 를 바디에 포함한다.")
    void findAllActiveCoupons() throws Exception {
        //given
        boolean includeInActiveCoupons = false;

        CouponResponseDto dto1 = CouponMapper.INSTANCE.toCouponResponseDto(coupon1);
        CouponResponseWrapperDto expectedResponse = new CouponResponseWrapperDto(
                "쿠폰 목록 조회 완료했습니다!",
                List.of(dto1),
                1
        );

        given(couponAdminService.findAllCoupons(PAGE, includeInActiveCoupons)).willReturn(expectedResponse);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "?includeInActiveCoupons=" + includeInActiveCoupons));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }

    @Test
    @Order(2)
    @DisplayName("쿠폰 목록 조회 시 전체 쿠폰 조회하는 경우 성공 시 상태코드 200을 반환하며 모든 쿠폰이 포함된 CouponResponseWrapperDto 를 바디에 포함한다.")
    void findAllCoupons() throws Exception {
        //given
        boolean includeInActiveCoupons = true;

        CouponResponseDto dto1 = CouponMapper.INSTANCE.toCouponResponseDto(coupon1);
        CouponResponseDto dto2 = CouponMapper.INSTANCE.toCouponResponseDto(coupon2);

        given(couponAdminService.findAllCoupons(PAGE, includeInActiveCoupons))
                .willReturn(
                        new CouponResponseWrapperDto("쿠폰 목록 조회 완료했습니다!", List.of(dto1, dto2), 1)
                );

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "?includeInActiveCoupons=" + includeInActiveCoupons));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value("쿠폰 목록 조회 완료했습니다!"))
                .andExpect(jsonPath("$.coupons.length()").value(2))
                .andExpect(jsonPath("$.coupons[0].active").value(dto1.isActive()))
                .andExpect(jsonPath("$.coupons[1].active").value(dto2.isActive()));
    }

    @Test
    @Order(3)
    @DisplayName("쿠폰 목록 조회 시 권한이 없는 경우 상태코드 403을 반환하고 에러코드는 USER_NOT_AUTHORIZED 와 같다.")
    public void findAllCoupons403Exception() throws Exception {
        //given
        CustomException authorizedException = new CustomException(ErrorCode.USER_NOT_AUTHORIZED);
        given(couponAdminService.findAllCoupons(PAGE, false)).willThrow(authorizedException);

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS));

        //then
        resultActions
                .andExpect(status().is(403))
                .andExpect(jsonPath("$.code").value(authorizedException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("권한이 없는 사용자입니다."));
    }

    @Test
    @Order(4)
    @DisplayName("특정 쿠폰 보유 회원 목록 조회 성공 시 상태코드 200을 반환하며 해당 회원들의 이름과 이메일이 포함된 CouponMemberResponseWrapperDto 를 바디에 포함한다.")
    void findCouponMembers() throws Exception {
        //given
        Long couponId = coupon1.getId();
        String message = "쿠폰 보유 회원 목록 조회 완료했습니다!";
        CouponMemberResponseDto dto1 = createCouponMemberResponseDto("email1@test.com", "user1");
        CouponMemberResponseDto dto2 = createCouponMemberResponseDto("email2@test.com", "user2");

        given(couponAdminService.findCouponMembers(PAGE, couponId))
                .willReturn(new CouponMemberResponseWrapperDto(message, List.of(dto1, dto2), 1));

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "/" + couponId));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.members.length()").value(2))
                .andExpect(jsonPath("$.members[0].email").value(dto1.getEmail()))
                .andExpect(jsonPath("$.members[0].userName").value(dto1.getUserName()));
    }

    private CouponMemberResponseDto createCouponMemberResponseDto(String email, String userName) {
        return CouponMemberResponseDto.builder().email(email).userName(userName).build();
    }

    @Test
    @Order(5)
    @DisplayName("쿠폰 생성 성공 시 상태코드 201을 반환한다.")
    void createCoupon() throws Exception {
        //given
        CouponCreateRequestDto dto = CouponCreateRequestDto.builder()
                .name("coupon3")
                .type(CouponType.AMOUNT)
                .value(5000)
                .minValue(70000)
                .expiredAt(LocalDate.of(2025, 1, 1))
                .categoryId(0L)
                .build();

        given(couponAdminService.createCoupon(dto)).willReturn(3L);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(201))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("쿠폰이 생성되었습니다! couponId: 3"));
    }

    @Test
    @Order(6)
    @DisplayName("쿠폰 생성 시 쿠폰 생성 정보가 유효하지 않는 경우 상태코드 400을 반환하고 에러코드는 INVALID_COUPON_CREATE_DATA 와 같다.")
    void createCoupon400Exception() throws Exception {
        //given
        CustomException invalidCouponException = new CustomException(ErrorCode.INVALID_COUPON_CREATE_DATA);
        CouponCreateRequestDto dto = createCouponCreateRequestDto();

        given(couponAdminService.createCoupon(any())).willThrow(invalidCouponException);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code").value(invalidCouponException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("쿠폰 생성 정보가 유효하지 않습니다."));
    }

    private CouponCreateRequestDto createCouponCreateRequestDto() {
        return CouponCreateRequestDto.builder()
                .name("coupon3")
                .type(CouponType.AMOUNT)
                .value(5000)
                .minValue(70000)
                .expiredAt(LocalDate.of(2025, 1, 1))
                .categoryId(0L)
                .build();
    }

    @Test
    @Order(7)
    @DisplayName("쿠폰 비활성화 성공 시 상태코드 200을 반환한다.")
    void deActiveCoupon() throws Exception {
        //given
        Long couponId = coupon1.getId();
        given(couponAdminService.deActiveCoupon(couponId)).willReturn(couponId);

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "/" + couponId));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("쿠폰이 비활성화 되었습니다! couponId: " + couponId));
    }

    @Test
    @Order(8)
    @DisplayName("쿠폰 비활성화 시 쿠폰을 찾을 수 없는 경우 상태코드 404를 반환하고 에러코드는 COUPON_NOT_FOUND 와 같다.")
    void deActiveCoupon404Exception() throws Exception {
        //given
        Long couponId = coupon1.getId();
        CustomException couponNotFoundException = new CustomException(ErrorCode.COUPON_NOT_FOUND);
        given(couponAdminService.deActiveCoupon(couponId)).willThrow(couponNotFoundException);

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "/" + couponId));

        //then
        resultActions
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value(couponNotFoundException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("해당 쿠폰을 찾을 수 없습니다."));
    }

    @Test
    @Order(9)
    @DisplayName("쿠폰 이메일 전송 성공 시 상태코드 200을 반환한다.")
    void sendCouponEmails() throws Exception {
        //given
        Long couponId = coupon1.getId();
        String address = "test1@test.com";
        String template = "email template";
        CouponEmailRequestDto dto = new CouponEmailRequestDto(couponId, address, template);
        given(couponAdminService.sendEmail(dto)).willReturn(address);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("쿠폰 이메일 전송을 완료했습니다! email: " + address));
    }

    @Test
    @Order(10)
    @DisplayName("쿠폰 이메일 전송 시 이메일 정보가 유효하지 않은 경우 상태코드 400을 반환하고 에러코드는 INVALID_EMAIL_DATA 와 같다.")
    void sendCouponEmails400Exception() throws Exception {
        //given
        Long couponId = coupon1.getId();
        String address = "test1@test.com";
        String template = "email template";
        CustomException invalidEmailException = new CustomException(ErrorCode.INVALID_EMAIL_DATA);
        CouponEmailRequestDto dto = new CouponEmailRequestDto(couponId, address, template);
        given(couponAdminService.sendEmail(dto)).willThrow(invalidEmailException);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/email")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code").value(invalidEmailException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("이메일 정보가 유효하지 않습니다."));
    }

    @Test
    @Order(11)
    @DisplayName("특정 쿠폰 미보유 회원 조회 성공 시 상태코드 200을 반환하고 해당 회원들의 이메일과 이름이 담긴 리스트를 포함한 CouponMemberResponseWrapperDto 를 바디에 포함한다.")
    void findMembersWithOutCoupons() throws Exception {
        //given
        Long couponId = 3L;
        CouponMemberResponseDto dto1 = createCouponMemberResponseDto("email1@test.com", "user1");
        CouponMemberResponseDto dto2 = createCouponMemberResponseDto("email2@test.com", "user2");

        String message = "쿠폰 미보유 회원 목록을 조회했습니다!";

        given(couponAdminService.findMembersWithOutCoupons(couponId))
                .willReturn(new CouponMemberResponseWrapperDto(message, List.of(dto1, dto2), 1));

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "/" + couponId + "/members"));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.members.length()").value(2))
                .andExpect(jsonPath("$.members[0].email").value(dto1.getEmail()))
                .andExpect(jsonPath("$.members[0].userName").value(dto1.getUserName()));
    }
}