package com.team2.fitinside.coupon.service;

import com.team2.fitinside.config.SecurityUtil;
import com.team2.fitinside.coupon.dto.AvailableCouponResponseDto;
import com.team2.fitinside.coupon.dto.AvailableCouponResponseWrapperDto;
import com.team2.fitinside.coupon.dto.CouponResponseDto;
import com.team2.fitinside.coupon.dto.CouponResponseWrapperDto;
import com.team2.fitinside.coupon.entity.Coupon;
import com.team2.fitinside.coupon.entity.CouponMember;
import com.team2.fitinside.coupon.mapper.CouponMapper;
import com.team2.fitinside.coupon.repository.CouponMemberRepository;
import com.team2.fitinside.coupon.repository.CouponRepository;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.member.repository.MemberRepository;
import com.team2.fitinside.order.entity.OrderProduct;
import com.team2.fitinside.order.repository.OrderProductRepository;
import com.team2.fitinside.product.entity.Product;
import com.team2.fitinside.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponMemberRepository couponMemberRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final SecurityUtil securityUtil;

    // 보유 쿠폰 모두 조회
    public CouponResponseWrapperDto findAllCoupons(int page, boolean includeInActiveCoupons) {

        // 페이지 당 쿠폰 10개, 만료 일 기준 오름차순 정렬
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("Coupon_expiredAt").ascending());

        Long loginMemberId = getAuthenticatedMemberId();

        Page<CouponMember> couponMembers;
        if(includeInActiveCoupons) {     // 비활성화 쿠폰 포함
            couponMembers = couponMemberRepository.findByMember_Id(pageRequest, loginMemberId);
        } else {                        // 활성화 쿠폰만 조회
            couponMembers = couponMemberRepository.findByMember_IdAndCoupon_ActiveIsAndUsed(pageRequest, loginMemberId, true, false);
        }

        List<CouponResponseDto> dtos = new ArrayList<>();

        // coupon -> List<CouponResponseDto>
        for (CouponMember couponMember : couponMembers) {

            CouponResponseDto couponResponseDto = CouponMapper.INSTANCE.toCouponResponseDto(couponMember.getCoupon());
            couponResponseDto.setUsed(couponMember.isUsed());   // 사용 여부 설정
            if(couponMember.isUsed()) couponResponseDto.setActive(false);
            dtos.add(couponResponseDto);
        }

        // 총 페이지 수
        int totalPages = (couponMembers.getTotalPages()==0 ? 1 : couponMembers.getTotalPages());

        // 성공메시지 + List<CouponResponseDto> -> CouponResponseWrapperDto 반환
        return new CouponResponseWrapperDto("쿠폰 목록 조회 완료했습니다!", dtos, totalPages);
    }

    // 특정 상품에 적용 가능한 쿠폰 목록 조회
    public AvailableCouponResponseWrapperDto findAllAvailableCoupons(Long productId) {

        Long loginMemberId = getAuthenticatedMemberId();

        Product product = productRepository.findById(productId).orElseThrow(() -> new CustomException(ErrorCode.PRODUCT_NOT_FOUND));

        List<CouponMember> couponMembers = couponMemberRepository.findByMember_IdAndCoupon_Category_Id(loginMemberId, product.getCategory().getId());

        List<AvailableCouponResponseDto> dtos = new ArrayList<>();
        for (CouponMember couponMember : couponMembers) {

            // 쿠폰이 유효하지 않은 경우
            if(!couponMember.getCoupon().isActive()) continue;

            // 상품 가격이 최소 주문 금액보다 적은 경우
            if(couponMember.getCoupon().getMinValue() > product.getPrice()) continue;

            // 상품을 이미 사용한 경우
            if(couponMember.isUsed()) continue;

            AvailableCouponResponseDto availableCouponResponseDto = CouponMapper.INSTANCE.toAvailableCouponResponseDto(couponMember.getCoupon());
            availableCouponResponseDto.setCouponMemberId(couponMember.getId());
            dtos.add(availableCouponResponseDto);
        }

        // 성공메시지 + List<CouponResponseDto> -> CouponResponseWrapperDto 반환
        return new AvailableCouponResponseWrapperDto("쿠폰 목록 조회 완료했습니다!", dtos);
    }

    // 쿠폰 코드로 단일 쿠폰 조회
    public CouponResponseDto findCoupon(String couponCode) {

        // 권한 확인
        getAuthenticatedMemberId();

        Coupon foundCoupon = couponRepository.findByCode(couponCode).orElseThrow(() -> new CustomException(ErrorCode.INVALID_COUPON_DATA));

        return CouponMapper.INSTANCE.toCouponResponseDto(foundCoupon);
    }

    // 웰컴 쿠폰 목록 조회
    public CouponResponseWrapperDto findWelcomeCoupons() {

        List<Coupon> coupons = couponRepository.findByNameContains("웰컴");

        List<CouponResponseDto> dtos = new ArrayList<>();

        // coupon -> List<CouponResponseDto>
        for (Coupon coupon : coupons) {

            CouponResponseDto couponResponseDto = CouponMapper.INSTANCE.toCouponResponseDto(coupon);
            dtos.add(couponResponseDto);
        }

        return new CouponResponseWrapperDto("쿠폰 목록 조회 완료했습니다!", dtos, 1);
    }

    @Transactional
    public void enterCouponCode(String code) {

        Long loginMemberId = getAuthenticatedMemberId();

        // 이미 등록 이력이 있는 쿠폰 예외
        if(couponMemberRepository.existsByCoupon_Code(code)) {
            throw new CustomException(ErrorCode.DUPLICATE_COUPON);
        }

        Member foundMember = memberRepository.findById(loginMemberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Coupon foundCoupon = couponRepository.findByCode(code).orElseThrow(() -> new CustomException(ErrorCode.INVALID_COUPON_DATA));

        // 비활성화 된 쿠폰 입력 시 예외
        if(!foundCoupon.isActive()) {
            throw new CustomException(ErrorCode.INVALID_COUPON_DATA);
        }

        CouponMember couponMember = CouponMember.builder().used(false).build();

        couponMember.setCouponAndMember(foundCoupon, foundMember);

        couponMemberRepository.save(couponMember);
    }

    @Transactional
    public void redeemCoupon(Long couponMemberId) {

        CouponMember couponMember = couponMemberRepository.findById(couponMemberId).orElseThrow(() -> new CustomException(ErrorCode.INVALID_COUPON_DATA));

        // 이미 쿠폰을 사용했거나 쿠폰이 유효하지 않거나 기간이 만료된 경우 예외
        if(couponMember.isUsed() || !couponMember.getCoupon().isActive() || couponMember.getCoupon().getExpiredAt().isBefore(LocalDate.now())) {
            throw new CustomException(ErrorCode.INVALID_COUPON_DATA);
        }

        couponMember.useCoupon();
    }

    // 쿠폰이 적용된 주문 찾기
    public Long findOrder(Long couponId) {

        Long loginMemberId = getAuthenticatedMemberId();

        CouponMember foundCouponMember = couponMemberRepository.findByMember_IdAndCoupon_IdAndUsedIs(loginMemberId, couponId, true)
                .orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));

        OrderProduct foundOrderProduct = orderProductRepository.findByCouponMember_Id(foundCouponMember.getId()).orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        return foundOrderProduct.getOrder().getId();
    }

    private Long getAuthenticatedMemberId()  {
        try {
            return securityUtil.getCurrentMemberId();
        } catch (RuntimeException e) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED);
        }
    }
}
