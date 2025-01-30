package com.team2.fitinside.coupon.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.fitinside.coupon.dto.*;
import com.team2.fitinside.coupon.entity.Coupon;
import com.team2.fitinside.coupon.entity.CouponType;
import com.team2.fitinside.coupon.mapper.CouponMapper;
import com.team2.fitinside.coupon.service.CouponService;
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

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(CouponController.class)
@AutoConfigureMockMvc(addFilters = false) // 필터 제외 (JWT 검증 제외)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("쿠폰 회원 컨트롤러 단위 테스트")
class CouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CouponService couponService;

    private static final String URL = "/api/coupon";
    private static final String URLS = "/api/coupons";
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
    @DisplayName("쿠폰 목록 조회 시 유효한 쿠폰만 조회하는 경우 성공 시 상태코드 200을 반환하고 유효한 쿠폰만을 담은 리스트를 포함한 CouponResponseWrapperDto 를 바디에 포함한다.")
    void findAllActiveCoupons() throws Exception {
        //given
        boolean includeInActiveCoupons = false;

        CouponResponseDto dto1 = CouponMapper.INSTANCE.toCouponResponseDto(coupon1);
        CouponResponseWrapperDto expectedResponse = new CouponResponseWrapperDto(
                "쿠폰 목록 조회 완료했습니다!",
                List.of(dto1),
                1
        );

        given(couponService.findAllCoupons(PAGE, includeInActiveCoupons))
                .willReturn(expectedResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "?includeInActiveCoupons=" + includeInActiveCoupons));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse))); // 전체 응답 JSON 비교
    }

    @Test
    @Order(2)
    @DisplayName("쿠폰 목록 조회 시 전체 쿠폰 조회하는 경우 성공 시 상태코드 200을 반환하고 유효한 쿠폰, 유효하지 않은 쿠폰 모두를 담은 리스트를 포함한 CouponResponseWrapperDto 를 반환한다.")
    void findAllCoupons() throws Exception {
        //given
        boolean includeInActiveCoupons = true;

        CouponResponseDto dto1 = CouponMapper.INSTANCE.toCouponResponseDto(coupon1);
        CouponResponseDto dto2 = CouponMapper.INSTANCE.toCouponResponseDto(coupon2);

        given(couponService.findAllCoupons(PAGE, includeInActiveCoupons))
                .willReturn(new CouponResponseWrapperDto("쿠폰 목록 조회 완료했습니다!", List.of(dto1, dto2), 1));

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
    @DisplayName("쿠폰 목록 조회 시 권한이 없는 경우 상태코드 403을 반환하고 에러코드는 USER_NOT_AUTHORIZED 이다.")
    public void findAllCoupons403Exception() throws Exception {
        //given
        CustomException authorizedException = new CustomException(ErrorCode.USER_NOT_AUTHORIZED);
        given(couponService.findAllCoupons(PAGE, false)).willThrow(authorizedException);

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
    @DisplayName("적용 가능 쿠폰 목록 조회 성공 시 상태코드 200을 반환하고 해당 쿠폰들이 담긴 리스트를 포함한 AvailableCouponResponseWrapperDto 를 바디에 포함한다.")
    void findAllAvailableCoupons() throws Exception {
        //given
        Long productId = 1L;
        String message = "쿠폰 목록 조회 완료했습니다!";

        AvailableCouponResponseDto dto1 = CouponMapper.INSTANCE.toAvailableCouponResponseDto(coupon1);
        dto1.setCouponMemberId(1L);
        AvailableCouponResponseDto dto2 = CouponMapper.INSTANCE.toAvailableCouponResponseDto(coupon2);
        dto2.setCouponMemberId(2L);

        AvailableCouponResponseWrapperDto expectedResponse = new AvailableCouponResponseWrapperDto(
                message, List.of(dto1, dto2)
        );

        given(couponService.findAllAvailableCoupons(productId)).willReturn(expectedResponse);
        String expectedJson = objectMapper.writeValueAsString(expectedResponse);

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "/" + productId));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().json(expectedJson));
    }

    @Test
    @Order(5)
    @DisplayName("적용 가능 쿠폰 목록 조회 시 상품을 찾을 수 없는 경우 상태코드 404를 반환하고 에러코드는 PRODUCT_NOT_FOUND 이다.")
    void findAllAvailableCoupons404Exception() throws Exception {
        //given
        Long productId = 1L;
        CustomException productNotFoundException = new CustomException(ErrorCode.PRODUCT_NOT_FOUND);
        given(couponService.findAllAvailableCoupons(productId)).willThrow(productNotFoundException);

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "/" + productId));

        //then
        resultActions
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value(productNotFoundException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("해당 상품을 찾을 수 없습니다."));
    }

    @Test
    @Order(6)
    @DisplayName("쿠폰 검색 성공 시 상태코드 200와 해당 쿠폰의 정보들을 반환한다.")
    void findCoupon() throws Exception {
        //given
        String couponCode = coupon1.getCode();
        CouponResponseDto dto = CouponMapper.INSTANCE.toCouponResponseDto(coupon1);

        given(couponService.findCoupon(couponCode)).willReturn(dto);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/code/" + couponCode));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").value(dto.getId()))
                .andExpect(jsonPath("$.name").value(dto.getName()))
                .andExpect(jsonPath("$.code").value(dto.getCode()))
                .andExpect(jsonPath("$.type").value(dto.getType().toString()));
    }

    @Test
    @Order(7)
    @DisplayName("쿠폰 검색 시 쿠폰 정보가 유효하지 않은 경우 상태코드 400을 반환하고 에러코드는 INVALID_COUPON_DATA 이다.")
    void findCoupon400Exception() throws Exception {
        //given
        String couponCode = "ERROR1";
        CustomException invalidCouponDataException = new CustomException(ErrorCode.INVALID_COUPON_DATA);

        given(couponService.findCoupon(couponCode)).willThrow(invalidCouponDataException);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/code/" + couponCode));

        //then
        resultActions
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code").value(invalidCouponDataException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("쿠폰 정보가 유효하지 않습니다."));
    }

    @Test
    @Order(8)
    @DisplayName("웰컴 쿠폰 검색 성공 시 상태코드 200을 반환하고 해당 쿠폰을 담은 리스트를 포함한 CouponResponseWrapperDto 를 바디에 포함한다.")
    void findWelcomeCoupons() throws Exception {
        //given
        String message = "쿠폰 목록 조회 완료했습니다!";
        CouponResponseDto dto1 = CouponMapper.INSTANCE.toCouponResponseDto(coupon1);
        CouponResponseDto dto2 = CouponMapper.INSTANCE.toCouponResponseDto(coupon2);

        given(couponService.findWelcomeCoupons())
                .willReturn(new CouponResponseWrapperDto(message, List.of(dto1, dto2), 1));

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "/welcome"));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.coupons.size()").value(2));
    }

    @Test
    @Order(9)
    @DisplayName("보유한 웰컴 쿠폰 검색 성공 시 상태코드 200을 반환하고 해당 쿠폰들이 담긴 리스트를 포함한 MyWelcomeCouponResponseWrapperDto 를 바디에 포함한다.")
    void findMyWelcomeCoupons() throws Exception {
        //given
        String message = "쿠폰 목록 조회 완료했습니다!";
        CouponResponseDto dto1 = CouponMapper.INSTANCE.toCouponResponseDto(coupon1);
        CouponResponseDto dto2 = CouponMapper.INSTANCE.toCouponResponseDto(coupon2);

        given(couponService.findMyWelcomeCoupons())
                .willReturn(new MyWelcomeCouponResponseWrapperDto(message, List.of(dto1.getId(), dto2.getId())));

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS + "/my-welcome"));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value(message))
                .andExpect(jsonPath("$.couponIds.size()").value(2))
                .andExpect(jsonPath("$.couponIds[0]").value(dto1.getId()))
                .andExpect(jsonPath("$.couponIds[1]").value(dto2.getId()));
    }

    @Test
    @Order(10)
    @DisplayName("쿠폰 다운로드 성공 시 상태코드 201을 반환한다.")
    void enterCouponCode() throws Exception {
        //given
        Long couponMemberId = 1L;
        String couponCode = coupon1.getCode();
        String message = "쿠폰이 다운로드되었습니다! couponMemberId: " + couponMemberId;

        given(couponService.enterCouponCode(couponCode)).willReturn(couponMemberId);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .content(couponCode)
                .contentType(MediaType.TEXT_PLAIN));

        //then
        resultActions
                .andExpect(status().is(201))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(message));
    }

    @Test
    @Order(11)
    @DisplayName("쿠폰 다운로드 시 등록 이력이 존재한 경우 상태코드 409를 반환하고 에러코드는 DUPLICATE_COUPON 과 같다.")
    void enterCouponCode409Exception() throws Exception {
        //given
        String couponCode = "ERROR1";
        CustomException duplicateCouponException = new CustomException(ErrorCode.DUPLICATE_COUPON);
        String message = "쿠폰 등록 이력이 존재합니다.";

        given(couponService.enterCouponCode(couponCode)).willThrow(duplicateCouponException);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL)
                .content(couponCode)
                .contentType(MediaType.TEXT_PLAIN));

        //then
        resultActions
                .andExpect(status().is(409))
                .andExpect(jsonPath("$.code").value(duplicateCouponException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value(message));
    }

    @Test
    @Order(12)
    @DisplayName("쿠폰 사용 성공 시 상태코드 200을 반환한다.")
    void redeemCoupon() throws Exception {
        //given
        Long couponMemberId = 1L;
        String message = "쿠폰이 사용되었습니다! couponMemberId: " + couponMemberId;

        willDoNothing().given(couponService).redeemCoupon(couponMemberId);

        //when
        ResultActions resultActions = mockMvc.perform(post(URL + "/" + couponMemberId));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(message));
    }

    @Test
    @Order(13)
    @DisplayName("쿠폰 사용 내역 조회 성공 시 상태코드 200을 반환하고 해당 쿠폰이 적용된 주문의 id를 바디에 포함한다.")
    void findOrder() throws Exception {
        //given
        Long orderId = 1L;
        Long couponId = coupon1.getId();

        given(couponService.findOrder(couponId)).willReturn(orderId);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL + "/" + couponId + "/order"));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string(orderId.toString()));
    }
}