package com.team2.fitinside.coupon.controller;

import com.team2.fitinside.coupon.dto.CouponCreateRequestDto;
import com.team2.fitinside.coupon.dto.CouponEmailRequestDto;
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
@RequestMapping("/api/admin")
@ApiResponses({
        @ApiResponse(responseCode = "403", description = "권한이 없는 사용자입니다."),
        @ApiResponse(responseCode = "404", description = "해당하는 정보의 사용자를 찾을 수 없습니다."),
        @ApiResponse(responseCode = "500", description = "서버 에러")
})
public class CouponAdminController {

    private final CouponAdminService couponAdminService;

    @GetMapping("/coupons")
    @Operation(summary = "쿠폰 목록 조회", description = "존재하는 쿠폰 전체 조회 (유효한 쿠폰만 조회 / 전체 쿠폰 조회)")
    @ApiResponse(responseCode = "200", description = "쿠폰 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponResponseWrapperDto.class)))
    public ResponseEntity<CouponResponseWrapperDto> findAllCoupons(
            @RequestParam(required = false, value = "page", defaultValue = "1") int page,
            @RequestParam(required = false, value = "includeInActiveCoupons", defaultValue = "false") boolean includeInActiveCoupons) {

        CouponResponseWrapperDto allCoupons = couponAdminService.findAllCoupons(page, includeInActiveCoupons);
        return ResponseEntity.status(HttpStatus.OK).body(allCoupons);
    }

    @GetMapping("/coupons/{couponId}")
    @Operation(summary = "특정 쿠폰 보유 회원 목록 조회", description = "couponId에 해당하는 쿠폰을 보유한 회원 목록 조회")
    @ApiResponse(responseCode = "200", description = "쿠폰 보유 회원 목록 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CouponMemberResponseWrapperDto.class)))
    public ResponseEntity<CouponMemberResponseWrapperDto> findCouponMembers(
            @PathVariable("couponId") Long couponId,
            @RequestParam(required = false, value = "page", defaultValue = "1") int page) {

        CouponMemberResponseWrapperDto allMembers = couponAdminService.findCouponMembers(page, couponId);
        return ResponseEntity.status(HttpStatus.OK).body(allMembers);
    }

    @PostMapping("/coupon")
    @Operation(summary = "쿠폰 생성", description = "새로운 쿠폰 생성")
    @ApiResponse(responseCode = "201", description = "쿠폰이 생성되었습니다!")
    @ApiResponse(responseCode = "400", description = "쿠폰 생성 정보가 유효하지 않습니다.")
    @ApiResponse(responseCode = "404", description = "해당 카테고리를 찾을 수 없습니다.")
    public ResponseEntity<String> createCoupon(@RequestBody CouponCreateRequestDto couponCreateRequestDto) {

        Long createdCouponId = couponAdminService.createCoupon(couponCreateRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("쿠폰이 생성되었습니다! couponId: " + createdCouponId);
    }

    @DeleteMapping("/coupon/{couponId}")
    @Operation(summary = "쿠폰 비활성화", description = "쿠폰 비활성화")
    @ApiResponse(responseCode = "200", description = "쿠폰이 비활성화 되었습니다!")
    @ApiResponse(responseCode = "404", description = "해당 쿠폰을 찾을 수 없습니다.")
    public ResponseEntity<String> deActiveCoupon(@PathVariable("couponId") Long couponId) {

        Long deActivatedCouponId = couponAdminService.deActiveCoupon(couponId);
        return ResponseEntity.status(HttpStatus.OK).body("쿠폰이 비활성화 되었습니다! couponId: " + deActivatedCouponId);
    }

    @PostMapping("/coupon/email")
    @Operation(summary = "쿠폰 이메일 전송", description = "쿠폰 미보유 회원에게 쿠폰 이메일 전송")
    @ApiResponse(responseCode = "200", description = "쿠폰 이메일 전송을 완료했습니다!")
    @ApiResponse(responseCode = "400", description = "쿠폰 정보가 유효하지 않습니다.")
    @ApiResponse(responseCode = "400", description = "이메일 정보가 유효하지 않습니다.")
    @ApiResponse(responseCode = "404", description = "해당 쿠폰을 찾을 수 없습니다.")
    public ResponseEntity<String> sendCouponEmails(@RequestBody CouponEmailRequestDto couponEmailRequestDto) {

        String emailAddress = couponAdminService.sendEmail(couponEmailRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("쿠폰 이메일 전송을 완료했습니다! email: " + emailAddress);
    }

    @GetMapping("/coupons/{couponId}/members")
    @Operation(summary = "쿠폰 미보유 회원 조회", description = "couponId 에 해당하는 쿠폰을 미보유한 회원 목록 조회")
    @ApiResponse(responseCode = "200", description = "쿠폰 미보유 회원 목록을 조회했습니다!")
    @ApiResponse(responseCode = "404", description = "해당 쿠폰을 찾을 수 없습니다.")
    public ResponseEntity<CouponMemberResponseWrapperDto> findMembersWithOutCoupons(@PathVariable("couponId") Long couponId) {

        CouponMemberResponseWrapperDto dto = couponAdminService.findMembersWithOutCoupons(couponId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}
