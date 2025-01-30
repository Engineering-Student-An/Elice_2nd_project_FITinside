package com.team2.fitinside.coupon.service;

import com.team2.fitinside.category.entity.Category;
import com.team2.fitinside.category.repository.CategoryRepository;
import com.team2.fitinside.config.SecurityUtil;
import com.team2.fitinside.coupon.dto.AvailableCouponResponseWrapperDto;
import com.team2.fitinside.coupon.dto.CouponResponseDto;
import com.team2.fitinside.coupon.dto.CouponResponseWrapperDto;
import com.team2.fitinside.coupon.dto.MyWelcomeCouponResponseWrapperDto;
import com.team2.fitinside.coupon.entity.Coupon;
import com.team2.fitinside.coupon.entity.CouponMember;
import com.team2.fitinside.coupon.entity.CouponType;
import com.team2.fitinside.coupon.repository.CouponMemberRepository;
import com.team2.fitinside.coupon.repository.CouponRepository;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.member.entity.Authority;
import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.member.repository.MemberRepository;
import com.team2.fitinside.order.entity.Order;
import com.team2.fitinside.order.entity.OrderProduct;
import com.team2.fitinside.order.repository.OrderProductRepository;
import com.team2.fitinside.product.entity.Product;
import com.team2.fitinside.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("쿠폰 회원 서비스 단위 테스트")
class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponMemberRepository couponMemberRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderProductRepository orderProductRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private CouponService couponService;

    private Member loginMember;

    private Product product1;

    private Coupon activeCoupon1;
    private Coupon activeCoupon2;
    private Coupon activeCoupon3;
    private Coupon inActiveCoupon1;

    private CouponMember couponMember1;
    private CouponMember couponMember2;
    private CouponMember couponMember3;
    private CouponMember couponMember4;

    @BeforeEach
    void setUp() {

        // 테스트용 회원 객체 생성
        loginMember = Member.builder().id(1L).email("test@test.com").password("password1234").userName("관리자")
                .phone("010-1111-1111").authority(Authority.ROLE_USER).build();

        // 테스트용 카테고리 생성
        Category category1 = Category.builder().id(1L).name("카테고리1").build();

        // 테스트용 상품 생성
        product1 = Product.builder().id(1L).productName("상품1").price(10000).stock(10).build();
        product1.setCategory(category1);

        // 테스트용 쿠폰 객체 생성
        activeCoupon1 = Coupon.builder().id(1L).name("활성화 쿠폰 1 (가능)").code("AAAAAA").value(10000).minValue(0).active(true).type(CouponType.AMOUNT).expiredAt(LocalDate.now().plusDays(3)).build();
        activeCoupon2 = Coupon.builder().id(2L).name("활성화 쿠폰 2 (사용함)").code("BBBBBB").percentage(20).minValue(10000).active(true).type(CouponType.PERCENTAGE).build();
        activeCoupon3 = Coupon.builder().id(3L).name("활성화 쿠폰 3 (최소주문 금액 큼)").code("DDDDDD").value(5000).minValue(500000).active(true).type(CouponType.AMOUNT).expiredAt(LocalDate.now().minusDays(3)).build();
        inActiveCoupon1 = Coupon.builder().id(4L).name("비활성화 쿠폰 1").code("CCCCCC").value(5000).minValue(30000).active(false).type(CouponType.AMOUNT).build();


        // 테스트용 쿠폰-멤버 객체 생성
        couponMember1 = CouponMember.builder().id(1L).coupon(activeCoupon1).member(loginMember).used(false).build();
        couponMember2 = CouponMember.builder().id(2L).coupon(activeCoupon2).member(loginMember).used(true).build();
        couponMember3 = CouponMember.builder().id(3L).coupon(activeCoupon3).member(loginMember).used(false).build();
        couponMember4 = CouponMember.builder().id(4L).coupon(inActiveCoupon1).member(loginMember).used(false).build();
    }

    @Test
    @DisplayName("보유 쿠폰 목록 조회 시 유효한 쿠폰만 조회한 경우 유효한 쿠폰 리스트를 포함한 CouponResponseWrapperDto 를 반환한다.")
    public void findAllActiveCoupons() throws Exception {

        //given
        int page = 1;
        boolean includeInActiveCoupons = false;

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 활성화 쿠폰 (+ 미사용 쿠폰) 반환하게 설정
        given(couponMemberRepository.findByMemberIdAndCouponActiveAndUsed(
                eq(loginMember.getId()), eq(true), eq(false), any()))
                .willReturn(new PageImpl<>(List.of(couponMember1)));

        //when
        CouponResponseWrapperDto result = couponService.findAllCoupons(page, includeInActiveCoupons);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCoupons().size()).isEqualTo(1);
        assertThat(result.getCoupons().get(0).getName()).isEqualTo(activeCoupon1.getName());
        assertThat(result.getTotalPages()).isEqualTo(1);

    }

    @Test
    @DisplayName("보유 쿠폰 목록 조회 시 모든 쿠폰 조회하는 경우 유효한 쿠폰, 유효하지 않은 쿠폰 모두를 포함한 CouponResponseWrapperDto 를 반환한다.")
    public void findAllCoupons() throws Exception {

        //given
        int page = 1;
        boolean includeInActiveCoupons = true;

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 모든 쿠폰 반환하게 설정
        given(couponMemberRepository.findByMemberIdWithCouponsAndCategories(eq(loginMember.getId()), any()))
                .willReturn(new PageImpl<>(List.of(couponMember1, couponMember2, couponMember3, couponMember4)));

        //when
        CouponResponseWrapperDto result = couponService.findAllCoupons(page, includeInActiveCoupons);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCoupons().size()).isEqualTo(4);
        assertThat(result.getCoupons().get(0).getName()).isEqualTo(activeCoupon1.getName());
        assertThat(result.getCoupons().get(1).getName()).isEqualTo(activeCoupon2.getName());
        assertThat(result.getCoupons().get(2).getName()).isEqualTo(activeCoupon3.getName());
        assertThat(result.getCoupons().get(3).getName()).isEqualTo(inActiveCoupon1.getName());
        assertThat(result.getTotalPages()).isEqualTo(1);

    }

    @Test
    @DisplayName("보유 쿠폰 목록 조회 시 해당 쿠폰이 없는 경우 사이즈가 0인 리스트를 포함한 CouponResponseWrapperDto 를 반환한다.")
    public void findAllCouponsWhenCouponsEmpty() throws Exception {

        //given
        int page = 1;
        boolean includeInActiveCoupons = true;

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 빈 리스트 반환하게 설정
        given(couponMemberRepository.findByMemberIdWithCouponsAndCategories(eq(loginMember.getId()), any())).willReturn(
                new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        //when
        CouponResponseWrapperDto result = couponService.findAllCoupons(page, includeInActiveCoupons);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCoupons().size()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(1);

    }

    @Test
    @DisplayName("특정 상품에 적용 가능한 쿠폰 목록 조회 성공 시 " +
            "비활성화, 사용한 쿠폰, 최소주문금액이 상품보다 적은 쿠폰들은 제외된 적용 가능한 쿠폰만 포함된 AvailableCouponResponseWrapperDto 를 반환한다.")
    public void findAllAvailableCoupons() throws Exception {

        //given
        Long productId = 1L;

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 테스트용 상품 반환하게 설정
        given(productRepository.findById(productId)).willReturn(Optional.of(product1));

        // 카테고리에 맞는 쿠폰 리스트 반환하게 설정
        given(couponMemberRepository.findByMember_IdAndCoupon_Category_Id(eq(loginMember.getId()),eq(1L)))
                .willReturn(List.of(couponMember1, couponMember2, couponMember3, couponMember4));

        //when
        AvailableCouponResponseWrapperDto result = couponService.findAllAvailableCoupons(productId);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        // 비활성화, 사용한 쿠폰, 최소주문금액이 상품보다 적은 쿠폰들은 제외 => 1개 반환 검증
        assertThat(result.getCoupons().size()).isEqualTo(1);
        assertThat(result.getCoupons().get(0).getName()).isEqualTo(activeCoupon1.getName());
    }

    @Test
    @DisplayName("쿠폰 코드로 단일 쿠폰 조회 성공 시 해당 쿠폰을 포함한 CouponResponseDto 를 반환한다.")
    public void findCoupon() throws Exception {

        //given
        String couponCode = activeCoupon1.getCode();

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 테스트용 쿠폰 반환하게 설정
        given(couponRepository.findByCode(couponCode)).willReturn(Optional.of(activeCoupon1));

        //when
        CouponResponseDto result = couponService.findCoupon(couponCode);

        //then
        assertThat(result.getId()).isEqualTo(activeCoupon1.getId());
        assertThat(result.getName()).isEqualTo(activeCoupon1.getName());
        assertThat(result.getCode()).isEqualTo(activeCoupon1.getCode());
    }

    @Test
    @DisplayName("웰컴 쿠폰 목록 조회 성공 시 이름에 웰컴이 포함된 쿠폰 리스트를 포함한 CouponResponseWrapperDto 를 반환한다.")
    public void findWelcomeCoupons() throws Exception {

        //given
        // 테스트용 웰컴 쿠폰 리스트 반환하게 설정
        given(couponRepository.findByNameContains("웰컴")).willReturn(
                List.of(Coupon.builder().id(5L).name("웰컴1").code("111111").value(10000).minValue(0).active(true).type(CouponType.AMOUNT).build(),
                        Coupon.builder().id(6L).name("웰컴2").code("222222").percentage(20).minValue(0).active(true).type(CouponType.PERCENTAGE).build()));

        //when
        CouponResponseWrapperDto result = couponService.findWelcomeCoupons();

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCoupons().size()).isEqualTo(2);
        assertThat(result.getCoupons().get(0).getName()).isEqualTo("웰컴1");
        assertThat(result.getCoupons().get(1).getName()).isEqualTo("웰컴2");
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("보유한 웰컴 쿠폰 목록 조회 성공 시 이름에 웰컴이 포함된 쿠폰 리스트를 포함한 MyWelcomeCouponResponseWrapperDto 를 반환한다.")
    public void findMyWelcomeCoupons() throws Exception {

        //given
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 테스트용 웰컴 쿠폰 생성
        Coupon welcomeCoupon = Coupon.builder().id(5L).name("웰컴1").code("111111").value(10000).minValue(0).active(true).type(CouponType.AMOUNT).build();

        // 테스트용 쿠폰-멤버 생성
        CouponMember welcomeCouponMember = CouponMember.builder().id(5L).coupon(welcomeCoupon).member(loginMember).used(false).build();

        // 테스트용 웰컴 쿠폰 리스트 반환하게 설정
        given(couponMemberRepository.findByMember_IdAndCoupon_Name_Contains(loginMember.getId(), "웰컴")).willReturn(
                List.of(welcomeCouponMember));

        //when
        MyWelcomeCouponResponseWrapperDto result = couponService.findMyWelcomeCoupons();

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCouponIds().size()).isEqualTo(1);
        assertThat(result.getCouponIds().get(0)).isEqualTo(welcomeCoupon.getId());

    }

    @Test
    @DisplayName("쿠폰 다운로드 성공 시 해당 쿠폰의 id를 반환한다.")
    public void enterCouponCode() throws Exception {

        //given
        String couponCode = "EEEEEE";
        Coupon coupon = Coupon.builder().id(5L).name("다운로드 예시 쿠폰").code("EEEEEE").value(10000).minValue(0).active(true).type(CouponType.AMOUNT).build();

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());
        given(memberRepository.findById(loginMember.getId())).willReturn(Optional.of(loginMember));
        given(couponRepository.findByCode(couponCode)).willReturn(Optional.of(coupon));

        // 등록 이력 false 반환하게 설정
        given(couponMemberRepository.existsByCoupon_CodeAndMember_Id(couponCode, loginMember.getId())).willReturn(false);

        // 쿠폰 멤버 저장 시 테스트용 쿠폰멤버 객체 반환하게 설정
        given(couponMemberRepository.save(any()))
                .willReturn(CouponMember.builder().id(5L).coupon(coupon).member(loginMember).used(false).build());

        //when
        Long result = couponService.enterCouponCode(couponCode);

        //then
        assertEquals(result, 5L);

    }

    @Test
    @DisplayName("쿠폰 다운로드 시 등록 이력 존재하는 경우 DUPLICATE_COUPON 예외를 반환한다.")
    public void enterCouponCode409Exception() throws Exception {

        //given
        String couponCode = "AAAAAA";

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 등록 이력 true 반환하게 설정
        given(couponMemberRepository.existsByCoupon_CodeAndMember_Id(couponCode, loginMember.getId())).willReturn(true);

        // when, then
        CustomException duplicateCouponException = assertThrows(CustomException.class, () -> {
            couponService.enterCouponCode(couponCode);
        });
        assertThat(duplicateCouponException.getErrorCode()).isEqualTo(ErrorCode.DUPLICATE_COUPON);

    }

    @Test
    @DisplayName("쿠폰 다운로드 시 비활성화 된 쿠폰 입력하는 경우 INVALID_COUPON_DATA 예외를 반환한다.")
    public void enterCouponCode400Exception() throws Exception {

        //given
        String couponCode = "CCCCCC";

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());
        given(memberRepository.findById(loginMember.getId())).willReturn(Optional.of(loginMember));
        given(couponRepository.findByCode(couponCode)).willReturn(Optional.of(inActiveCoupon1));

        // 등록 이력 false 반환하게 설정
        given(couponMemberRepository.existsByCoupon_CodeAndMember_Id(couponCode, loginMember.getId())).willReturn(false);

        // when, then
        CustomException invalidCouponDataException = assertThrows(CustomException.class, () -> {
            couponService.enterCouponCode(couponCode);
        });
        assertThat(invalidCouponDataException.getErrorCode()).isEqualTo(ErrorCode.INVALID_COUPON_DATA);

    }

    @Test
    @DisplayName("쿠폰 적용 성공 시 해당 쿠폰멤버 객체에서 useCoupon() 메서드가 1회 실행된다.")
    public void redeemCoupon() throws Exception {

        //given
        Long couponMemberId = 1L;
        CouponMember spyCouponMember = spy(couponMember1);
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // spy 쿠폰멤버 객체 반환하게 설정
        given(couponMemberRepository.findById(couponMemberId)).willReturn(Optional.of(spyCouponMember));


        // when
        couponService.redeemCoupon(couponMemberId);

        // then
        // useCoupon() 메서드가 1번 실행되었는지 검증
        verify(spyCouponMember, times(1)).useCoupon();
    }

    @Test
    @DisplayName("쿠폰 적용 시 쿠폰을 이미 사용한 경우이거나 쿠폰이 비활성화 된 경우이거나 기간 만료된 경우 CustomException 예외를 반환한다.")
    public void redeemCoupon400ExceptionFirstCase() throws Exception {

        //given
        Long usedCouponMemberId = 2L;
        Long InActiveCouponMemberId = 4L;
        Long expiredCouponMemberId = 3L;
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 쿠폰멤버 객체 반환하게 설정
        given(couponMemberRepository.findById(usedCouponMemberId)).willReturn(Optional.of(couponMember2));
        given(couponMemberRepository.findById(InActiveCouponMemberId)).willReturn(Optional.of(couponMember4));
        given(couponMemberRepository.findById(expiredCouponMemberId)).willReturn(Optional.of(couponMember3));

        // when, then
        assertThrows(CustomException.class, () -> couponService.redeemCoupon(usedCouponMemberId));
        assertThrows(CustomException.class, () -> couponService.redeemCoupon(InActiveCouponMemberId));
        assertThrows(CustomException.class, () -> couponService.redeemCoupon(expiredCouponMemberId));
    }

    @Test
    @DisplayName("쿠폰 적용된 주문 찾기 성공 시 해당 쿠폰이 적용된 주문의 id를 반환한다.")
    public void findOrder() throws Exception {

        //given
        Long couponId = 2L;

        // 테스트용 order 객체 생성
        Order order = Order.builder().id(200L).build();

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 쿠폰멤버 반환하게 설정
        given(couponMemberRepository.findByMember_IdAndCoupon_IdAndUsedIs(eq(loginMember.getId()), eq(couponId), eq(true)))
                .willReturn(Optional.of(couponMember2));


        // 주문 반환하게 설정
        given(orderProductRepository.findByCouponMember_Id(2L))
                .willReturn(Optional.of(OrderProduct.builder().id(120L).order(order).build()));

        // when
        Long result = couponService.findOrder(couponId);

        // then
        assertEquals(result, order.getId());
    }

    @Test
    @DisplayName("쿠폰 관련 메서드 실행 시 로그인 하지 않은 경우 USER_NOT_AUTHORIZED 예외를 반환한다.")
    public void getAuthenticatedMemberIdFail() throws Exception {

        //given
        given(securityUtil.getCurrentMemberId()).willThrow(new RuntimeException());

        //when, then
        CustomException userNotAuthorizedException = assertThrows(CustomException.class,
                () -> couponService.findOrder(1L));
        assertThat(userNotAuthorizedException.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_AUTHORIZED);

    }
}