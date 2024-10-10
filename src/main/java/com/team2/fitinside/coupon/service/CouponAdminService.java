package com.team2.fitinside.coupon.service;

import com.team2.fitinside.cart.dto.CartResponseDto;
import com.team2.fitinside.cart.mapper.CartMapper;
import com.team2.fitinside.category.entity.Category;
import com.team2.fitinside.category.repository.CategoryRepository;
import com.team2.fitinside.config.SecurityUtil;
import com.team2.fitinside.coupon.dto.*;
import com.team2.fitinside.coupon.entity.Coupon;
import com.team2.fitinside.coupon.entity.CouponMember;
import com.team2.fitinside.coupon.mapper.CouponMapper;
import com.team2.fitinside.coupon.repository.CouponMemberRepository;
import com.team2.fitinside.coupon.repository.CouponRepository;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.member.entity.Authority;
import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class CouponAdminService {

    private final CouponRepository couponRepository;
    private final CouponMemberRepository couponMemberRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    // 매일 쿠폰의 유효기간 확인 및 비활성화
    @Transactional
    @Scheduled(cron = "0 0 0 * * *")    // 매일 0시에 실행하게끔 스케쥴링
    public void deActiveCouponsByExpiredAt() {

        List<Coupon> expired = couponRepository.findByExpiredAtLessThanEqual(LocalDate.now());
        expired.forEach(Coupon::deActive);
    }

    // 쿠폰 목록 조회
    public CouponResponseWrapperDto findAllCoupons(int page, boolean includeInActiveCoupons) {

        // 관리자 권한 확인
        checkAdmin();

        // 페이지 당 쿠폰 10개, 만료 일 기준 오름차순 정렬
        PageRequest pageRequest = PageRequest.of(page - 1, 10, Sort.by("expiredAt").ascending());

        Page<Coupon> coupons;
        if(includeInActiveCoupons) {     // 비활성화 쿠폰 포함
            coupons = couponRepository.findAll(pageRequest);
        } else {                        // 활성화 쿠폰만 조회
            coupons = couponRepository.findByActiveIs(pageRequest, true);
        }

        List<CouponResponseDto> dtos = new ArrayList<>();

        // coupon -> List<CouponResponseDto>
        for (Coupon coupon : coupons) {
            CouponResponseDto couponResponseDto = CouponMapper.INSTANCE.toCouponResponseDto(coupon);
            dtos.add(couponResponseDto);
        }

        // 성공메시지 + List<CouponResponseDto> -> CouponResponseWrapperDto 반환
        return new CouponResponseWrapperDto("쿠폰 목록 조회 완료했습니다!", dtos);
    }

    // 쿠폰 보유한 회원 목록 조회
    public CouponMemberResponseWrapperDto findCouponMembers(int page, Long couponId) {

        checkAdmin();

        PageRequest pageRequest = PageRequest.of(page - 1, 10);

        Page<CouponMember> couponMembers = couponMemberRepository.findByCoupon_Id(pageRequest, couponId);

        List<CouponMemberResponseDto> dtos = new ArrayList<>();
        for (CouponMember couponMember : couponMembers) {
            dtos.add(CouponMapper.INSTANCE.toCouponMemberResponseDto(couponMember.getMember()));
        }

        // 성공메시지 + List<CouponMemberResponseDto> -> CouponMemberResponseWrapperDto 반환
        return new CouponMemberResponseWrapperDto("쿠폰 보유 회원 조회 완료했습니다!", dtos);
    }

    @Transactional
    public void createCoupon(CouponCreateRequestDto couponCreateRequestDto) {

        checkAdmin();

        // 할인 금액, 퍼센티지, 최소 주문금액, 유효기간 설정 확인
        if(couponCreateRequestDto.getValue() < 0 || couponCreateRequestDto.getPercentage() < 0 || couponCreateRequestDto.getPercentage() > 100 || couponCreateRequestDto.getMinValue() < 0 || couponCreateRequestDto.getExpiredAt().isBefore(LocalDate.now())) {
            throw new CustomException(ErrorCode.INVALID_COUPON_CREATE_DATA);
        }

        Coupon coupon = CouponMapper.INSTANCE.toEntity(couponCreateRequestDto, createCouponCode());

        // 카테고리 설정
        if(couponCreateRequestDto.getCategoryId() != 0) {
            Category category = categoryRepository.findById(couponCreateRequestDto.getCategoryId()).orElseThrow(() -> new CustomException(ErrorCode.CATEGORY_NOT_FOUND));
            coupon.setCategory(category);
        }

        couponRepository.save(coupon);
    }

    @Transactional
    public void deActiveCoupon(Long couponId) {

        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new CustomException(ErrorCode.COUPON_NOT_FOUND));
        coupon.deActive();
    }


    // 관리자 권한이 없는 경우 예외 던지는 메서드
    private void checkAdmin() {

        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        Member member = memberRepository.findById(currentMemberId).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if(member.getAuthority()!= Authority.ROLE_ADMIN) {
            throw new CustomException(ErrorCode.USER_NOT_AUTHORIZED);
        }
    }

    // 쿠폰 코드 랜덤 생성 메서드
    static String createCouponCode() {
        Random random = new Random();
        StringBuilder key = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0 : key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1 : key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }

        return key.toString();
    }
}
