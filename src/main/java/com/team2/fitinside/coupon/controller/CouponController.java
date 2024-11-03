package com.team2.fitinside.coupon.controller;

import com.team2.fitinside.coupon.dto.AvailableCouponResponseWrapperDto;
import com.team2.fitinside.coupon.dto.CouponResponseDto;
import com.team2.fitinside.coupon.dto.CouponResponseWrapperDto;
import com.team2.fitinside.coupon.dto.MyWelcomeCouponResponseWrapperDto;
import com.team2.fitinside.coupon.service.CouponService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@ApiResponses({
        @ApiResponse(responseCode = "403", description = "권한이 없는 사용자입니다."),
        @ApiResponse(responseCode = "500", description = "서버 에러")
})
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupons")
    @Operation(summary = "보유 쿠폰 목록 조회", description = "로그인 한 회원의 보유 쿠폰 전체 조회 (유효한 쿠폰만 조회 / 전체 쿠폰 조회)")
    @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponResponseWrapperDto.class)))
    public ResponseEntity<CouponResponseWrapperDto> findAllCoupons(
            @RequestParam(required = false, value = "page", defaultValue = "1") int page,
            @RequestParam(required = false, value = "includeInActiveCoupons", defaultValue = "false") boolean includeInActiveCoupons) {

        CouponResponseWrapperDto allCoupons = couponService.findAllCoupons(page, includeInActiveCoupons);
        return ResponseEntity.status(HttpStatus.OK).body(allCoupons);
    }

    @GetMapping("/coupons/{productId}")
    @Operation(summary = "적용 가능 쿠폰 목록 조회", description = "productId에 해당하는 상품에 적용 가능한 쿠폰 목록 조회")
    @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AvailableCouponResponseWrapperDto.class)))
    @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.")
    public ResponseEntity<AvailableCouponResponseWrapperDto> findAllAvailableCoupons(@PathVariable("productId") Long productId) {

        AvailableCouponResponseWrapperDto allAvailableCoupons = couponService.findAllAvailableCoupons(productId);
        return ResponseEntity.status(HttpStatus.OK).body(allAvailableCoupons);
    }

    @GetMapping("/coupon/code/{couponCode}")
    @Operation(summary = "쿠폰 검색", description = "쿠폰 다운로드를 위해 쿠폰 코드로 단일 검색")
    @ApiResponse(responseCode = "200", description = "쿠폰 정보 반환", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponResponseDto.class)))
    @ApiResponse(responseCode = "400", description = "쿠폰 정보가 유효하지 않습니다.")
    public ResponseEntity<CouponResponseDto> findCoupon(@PathVariable("couponCode") String couponCode) {

        CouponResponseDto coupon = couponService.findCoupon(couponCode);
        return ResponseEntity.status(HttpStatus.OK).body(coupon);
    }

    @GetMapping("/coupons/welcome")
    @Operation(summary = "웰컴 쿠폰 목록 조회", description = "웰컴 쿠폰 목록 조회 (이름에 “웰컴” 을 포함하는 쿠폰 조회)")
    @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponResponseWrapperDto.class)))
    public ResponseEntity<CouponResponseWrapperDto> findWelcomeCoupons() {

        CouponResponseWrapperDto allCoupons = couponService.findWelcomeCoupons();
        return ResponseEntity.status(HttpStatus.OK).body(allCoupons);
    }

    @GetMapping("/coupons/my-welcome")
    @Operation(summary = "보유한 웰컴 쿠폰 목록 조회", description = "보유한 웰컴 쿠폰 목록 조회 (보유한 쿠폰 중 이름에 “웰컴” 을 포함하는 쿠폰 조회)")
    @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = MyWelcomeCouponResponseWrapperDto.class)))
    public ResponseEntity<MyWelcomeCouponResponseWrapperDto> findMyWelcomeCoupons() {

        MyWelcomeCouponResponseWrapperDto myWelcomeCoupons = couponService.findMyWelcomeCoupons();
        return ResponseEntity.status(HttpStatus.OK).body(myWelcomeCoupons);
    }

    @PostMapping("/coupon")
    @Operation(summary = "쿠폰 다운로드", description = "쿠폰 코드를 입력하여 쿠폰 다운로드")
    @ApiResponse(responseCode = "201", description = "쿠폰이 다운로드되었습니다! couponMemberId: ")
    @ApiResponse(responseCode = "400", description = "쿠폰 정보가 유효하지 않습니다.")
    @ApiResponse(responseCode = "404", description = "해당하는 정보의 사용자를 찾을 수 없습니다.")
    @ApiResponse(responseCode = "409", description = "쿠폰 등록 이력이 존재합니다.")
    public ResponseEntity<String> enterCouponCode(@RequestBody String code) {

        Long savedCouponMemberId = couponService.enterCouponCode(code);
        return ResponseEntity.status(HttpStatus.CREATED).body("쿠폰이 다운로드되었습니다! couponMemberId: " + savedCouponMemberId);
    }

    @PostMapping("/coupon/{couponMemberId}")
    @Operation(summary = "쿠폰 사용", description = "상품에 쿠폰 사용")
    @ApiResponse(responseCode = "200", description = "쿠폰이 사용되었습니다!")
    @ApiResponse(responseCode = "400", description = "쿠폰 정보가 유효하지 않습니다.")
    public ResponseEntity<String> redeemCoupon(@PathVariable("couponMemberId") Long couponMemberId) {

        couponService.redeemCoupon(couponMemberId);
        return ResponseEntity.status(HttpStatus.OK).body("쿠폰이 사용되었습니다! couponMemberId: " + couponMemberId);
    }

    // 쿠폰을 적용한 주문 조회
    @GetMapping("/coupon/{couponId}/order")
    @Operation(summary = "쿠폰 사용 내역 조회", description = "쿠폰 사용 내역 (주문서) 조회")
    @ApiResponse(responseCode = "200", description = "쿠폰이 사용되었습니다!")
    @ApiResponse(responseCode = "404", description = "해당 쿠폰을 찾을 수 없습니다.")
    @ApiResponse(responseCode = "404", description = "주문을 찾을 수 없습니다.")
    public ResponseEntity<String> findOrder(@PathVariable("couponId") Long couponId) {

        Long orderId = couponService.findOrder(couponId);
        return ResponseEntity.status(HttpStatus.OK).body(Long.toString(orderId));
    }
}
