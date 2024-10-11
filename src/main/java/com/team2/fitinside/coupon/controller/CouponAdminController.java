package com.team2.fitinside.coupon.controller;

import com.team2.fitinside.cart.dto.CartResponseWrapperDto;
import com.team2.fitinside.coupon.dto.CouponCreateRequestDto;
import com.team2.fitinside.coupon.dto.CouponMemberResponseWrapperDto;
import com.team2.fitinside.coupon.dto.CouponResponseWrapperDto;
import com.team2.fitinside.coupon.service.CouponAdminService;
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
@RequestMapping("/api/admin/coupons")
@ApiResponses({
        @ApiResponse(responseCode = "403", description = "권한이 없는 사용자입니다.", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "404", description = "해당하는 정보의 사용자를 찾을 수 없습니다.", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
})
public class CouponAdminController {

    private final CouponAdminService couponAdminService;

    @GetMapping
    @Operation(summary = "쿠폰 목록 조회", description = "쿠폰 목록 조회")
    @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponResponseWrapperDto.class)))
    public ResponseEntity<CouponResponseWrapperDto> findAllCoupons(
            @RequestParam(required = false, value = "page", defaultValue = "1") int page,
            @RequestParam(required = false, value = "includeInActiveCoupons", defaultValue = "false") boolean includeInActiveCoupons) {

        CouponResponseWrapperDto allCoupons = couponAdminService.findAllCoupons(page, includeInActiveCoupons);
        return ResponseEntity.status(HttpStatus.OK).body(allCoupons);
    }

    @GetMapping("/{couponId}")
    @Operation(summary = "특정 쿠폰 보유 회원 목록 조회", description = "특정 쿠폰 보유 회원 목록 조회")
    @ApiResponse(responseCode = "200", description = "쿠폰 보유 회원 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponMemberResponseWrapperDto.class)))
    public ResponseEntity<CouponMemberResponseWrapperDto> findCouponMembers(
            @PathVariable("couponId") Long couponId,
            @RequestParam(required = false, value = "page", defaultValue = "1") int page) {

        CouponMemberResponseWrapperDto allMembers = couponAdminService.findCouponMembers(page, couponId);
        return ResponseEntity.status(HttpStatus.OK).body(allMembers);
    }

    @PostMapping
    @Operation(summary = "쿠폰 추가", description = "쿠폰 추가")
    @ApiResponse(responseCode = "201", description = "쿠폰이 추가되었습니다!", content = @Content(mediaType = "application/json"))
    public ResponseEntity<String> createCoupon(@RequestBody CouponCreateRequestDto couponCreateRequestDto) {

        couponAdminService.createCoupon(couponCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("쿠폰이 추가되었습니다!");
    }

    @DeleteMapping("/{couponId}")
    @Operation(summary = "쿠폰 비활성화", description = "쿠폰 비활성화")
    @ApiResponse(responseCode = "200", description = "쿠폰이 비활성화 되었습니다!", content = @Content(mediaType = "application/json"))
    public ResponseEntity<String> deActiveCoupon(@PathVariable("couponId") Long couponId) {

        couponAdminService.deActiveCoupon(couponId);
        return ResponseEntity.status(HttpStatus.OK).body("쿠폰이 비활성화 되었습니다!");
    }
}