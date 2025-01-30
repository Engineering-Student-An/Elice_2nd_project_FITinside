package com.team2.fitinside.coupon.service;

import com.team2.fitinside.category.entity.Category;
import com.team2.fitinside.category.repository.CategoryRepository;
import com.team2.fitinside.config.SecurityUtil;
import com.team2.fitinside.coupon.dto.CouponCreateRequestDto;
import com.team2.fitinside.coupon.dto.CouponEmailRequestDto;
import com.team2.fitinside.coupon.dto.CouponMemberResponseWrapperDto;
import com.team2.fitinside.coupon.dto.CouponResponseWrapperDto;
import com.team2.fitinside.coupon.entity.Coupon;
import com.team2.fitinside.coupon.entity.CouponMember;
import com.team2.fitinside.coupon.entity.CouponType;
import com.team2.fitinside.coupon.mapper.CouponMapper;
import com.team2.fitinside.coupon.repository.CouponMemberRepository;
import com.team2.fitinside.coupon.repository.CouponRepository;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.member.entity.Authority;
import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("쿠폰 관리자 서비스 단위 테스트")
class CouponAdminServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private CouponMemberRepository couponMemberRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SecurityUtil securityUtil;

    @Mock
    private CouponEmailService couponEmailService;

    @InjectMocks
    private CouponAdminService couponAdminService;

    private Member adminMember;
    private Member userMember;

    private Coupon activeCoupon1;
    private Coupon activeCoupon2;
    private Coupon inActiveCoupon1;

    private CouponMember couponMember1;

    @BeforeEach
    void setUp() {
        adminMember = createTestMember(1L, "test@test.com", "관리자", Authority.ROLE_ADMIN);
        userMember = createTestMember(2L, "test22@test.com", "회원", Authority.ROLE_USER);

        activeCoupon1 = createTestCoupon(1L, "활성화 쿠폰 1", "AAAAAA", 10000, 0, true);
        activeCoupon2 = createTestCoupon(2L, "활성화 쿠폰 2", "BBBBBB", 0, 20, true);
        inActiveCoupon1 = createTestCoupon(3L, "비활성화 쿠폰 1", "CCCCCC", 5000, 30000, false);

        couponMember1 = CouponMember.builder().id(1L).coupon(activeCoupon1).member(userMember).used(false).build();
    }

    private Member createTestMember(Long id, String email, String userName, Authority authority) {
        return Member.builder()
                .id(id)
                .email(email)
                .password("password1234")
                .userName(userName)
                .phone("010-1111-1111")
                .authority(authority)
                .build();
    }

    private Coupon createTestCoupon(Long id, String name, String code, int value, int minValue, boolean active) {
        return Coupon.builder()
                .id(id)
                .name(name)
                .code(code)
                .value(value)
                .minValue(minValue)
                .active(active)
                .type(CouponType.AMOUNT)
                .build();
    }

    @Test
    @DisplayName("유효기간이 지난 쿠폰 비활성화 성공 시 해당 쿠폰 객체의 비활성화 메서드 (=deActive())가 1번 실행된다.")
    public void testDeActiveCouponsByExpiredAt() {
        //given
        Coupon spyCoupon1 = spy(createTestCoupon(4L, "Expired Coupon", "EXPIRED", 0, 0, true));
        Coupon spyCoupon2 = spy(createTestCoupon(5L, "Expired Coupon 2", "EXPIRED2", 0, 0, true));

        given(couponRepository.findByExpiredAtLessThanEqual(LocalDate.now())).willReturn(List.of(spyCoupon1, spyCoupon2));

        //when
        couponAdminService.deActiveCouponsByExpiredAt();

        //then
        verify(couponRepository, times(1)).findByExpiredAtLessThanEqual(LocalDate.now());
        verify(spyCoupon1, times(1)).deActive();
        verify(spyCoupon2, times(1)).deActive();
    }

    @Test
    @DisplayName("쿠폰 목록 조회 시 유효한 쿠폰만 조회하는 경우 유효하지 않은 쿠폰은 조회되지 않는다.")
    public void findAllActiveCoupons() throws Exception {
        //given
        setUpAdminMember();

        int page = 1;
        boolean includeInActiveCoupons = false;

        given(couponRepository.findByActiveIs(any(), eq(true)))
                .willReturn(new PageImpl<>(List.of(activeCoupon1, activeCoupon2)));

        //when
        CouponResponseWrapperDto result = couponAdminService.findAllCoupons(page, includeInActiveCoupons);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCoupons().size()).isEqualTo(2);
        assertThat(result.getCoupons().get(0).getName()).isEqualTo(activeCoupon1.getName());
        assertThat(result.getCoupons().get(1).getName()).isEqualTo(activeCoupon2.getName());
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("쿠폰 목록 조회 시 모든 쿠폰 조회하는 경우 유효한 쿠폰, 유효하지 않은 쿠폰 모두 조회된다.")
    public void findAllCoupons() throws Exception {
        //given
        setUpAdminMember();

        int page = 1;
        boolean includeInActiveCoupons = true;

        given(couponRepository.findAll((Pageable) any()))
                .willReturn(new PageImpl<>(List.of(activeCoupon1, activeCoupon2, inActiveCoupon1)));

        //when
        CouponResponseWrapperDto result = couponAdminService.findAllCoupons(page, includeInActiveCoupons);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCoupons().size()).isEqualTo(3);
        assertThat(result.getCoupons().get(0).getName()).isEqualTo(activeCoupon1.getName());
        assertThat(result.getCoupons().get(1).getName()).isEqualTo(activeCoupon2.getName());
        assertThat(result.getCoupons().get(2).getName()).isEqualTo(inActiveCoupon1.getName());
        assertThat(result.getCoupons().get(2).isActive()).isEqualTo(inActiveCoupon1.isActive());
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("쿠폰 목록 조회 시 쿠폰이 없는 경우 사이즈가 0인 회원 리스트를 포함한 CouponResponseWrapperDto 를 반환한다.")
    public void findAllCouponsWhenCouponsEmpty() throws Exception {
        //given
        setUpAdminMember();

        int page = 1;
        boolean includeInActiveCoupons = true;

        given(couponRepository.findAll((Pageable) any()))
                .willReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        //when
        CouponResponseWrapperDto result = couponAdminService.findAllCoupons(page, includeInActiveCoupons);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 목록 조회 완료했습니다!");
        assertThat(result.getCoupons().size()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("특정 쿠폰 보유한 회원 목록 조회 성공 시 쿠폰을 보유한 회원만 포함한 CouponMemberResponseWrapperDto 를 반환한다.")
    public void findCouponMembers() throws Exception {
        //given
        setUpAdminMember();

        int page = 1;
        Long couponId = activeCoupon1.getId();

        given(couponMemberRepository.findByCoupon_Id(eq(couponId), any()))
                .willReturn(new PageImpl<>(List.of(couponMember1)));

        //when
        CouponMemberResponseWrapperDto result = couponAdminService.findCouponMembers(page, couponId);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 보유 회원 조회 완료했습니다!");
        assertThat(result.getMembers().size()).isEqualTo(1);
        assertThat(result.getMembers().get(0)).isEqualTo(CouponMapper.INSTANCE.toCouponMemberResponseDto(userMember));
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("쿠폰 보유한 회원 목록 조회 시 보유한 회원이 없는 경우 사이즈가 0인 회원 리스트를 포함한 CouponMemberResponseWrapperDto 를 반환한다.")
    public void findCouponMembersWhenMembersEmpty() throws Exception {
        //given
        setUpAdminMember();

        int page = 1;
        Long couponId = activeCoupon1.getId();

        given(couponMemberRepository.findByCoupon_Id(eq(couponId), any()))
                .willReturn(new PageImpl<>(List.of(), PageRequest.of(0, 10), 0));

        //when
        CouponMemberResponseWrapperDto result = couponAdminService.findCouponMembers(page, couponId);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 보유 회원 조회 완료했습니다!");
        assertThat(result.getMembers().size()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(1);
    }

    @Test
    @DisplayName("쿠폰 생성 시 카테고리 존재하는 경우 생성된 쿠폰 객체에서 setCategory() 메서드가 1번 실행된다.")
    public void createCoupon() throws Exception {
        //given
        setUpAdminMember();

        CouponCreateRequestDto dto = CouponCreateRequestDto.builder()
                .name("새로운 쿠폰").type(CouponType.AMOUNT).value(7000)
                .minValue(7000).expiredAt(LocalDate.now().plusDays(3)).categoryId(1L).build();

        Category category1 = Category.builder().id(1L).name("카테고리1").build();
        Coupon createdCoupon = Coupon.builder().id(4L).build();

        given(categoryRepository.findById(dto.getCategoryId())).willReturn(Optional.of(category1));
        given(couponRepository.save(any())).willReturn(createdCoupon);

        //when
        Long result = couponAdminService.createCoupon(dto);

        //then
        assertThat(result).isEqualTo(4L);

        // ArgumentCaptor 사용하여 save 메서드에서 전달된 Coupon 객체를 캡쳐
        ArgumentCaptor<Coupon> couponCaptor = ArgumentCaptor.forClass(Coupon.class);
        verify(couponRepository).save(couponCaptor.capture());

        // 캡쳐한 Coupon 객체의 setCategory가 호출되었는지 검증
        Coupon capturedCoupon = couponCaptor.getValue();
        assertNotNull(capturedCoupon);
        assertEquals(category1, capturedCoupon.getCategory()); // setCategory가 호출되었는지 검증
    }

    @Test
    @DisplayName("쿠폰 생성 사 모든 카테고리로 생성하는 경우 생성된 쿠폰 객체에서 setCategory() 메서드가 실행되지 않는다.")
    public void createCouponAllCategories() throws Exception {
        //given
        setUpAdminMember();

        CouponCreateRequestDto dto = CouponCreateRequestDto.builder()
                .name("새로운 쿠폰").type(CouponType.AMOUNT).value(7000)
                .minValue(7000).expiredAt(LocalDate.now().plusDays(3)).categoryId(0L).build();

        Coupon createdCoupon = Coupon.builder().id(4L).build();

        given(couponRepository.save(any())).willReturn(createdCoupon);

        //when
        Long result = couponAdminService.createCoupon(dto);

        //then
        assertThat(result).isEqualTo(4L);

        // ArgumentCaptor 사용하여 save 메서드에서 전달된 Coupon 객체를 캡쳐
        ArgumentCaptor<Coupon> couponCaptor = ArgumentCaptor.forClass(Coupon.class);
        verify(couponRepository).save(couponCaptor.capture());

        // 캡쳐한 Coupon 객체의 setCategory가 호출 안되었는지 검증
        Coupon capturedCoupon = couponCaptor.getValue();
        assertNotNull(capturedCoupon);
        assertNull(capturedCoupon.getCategory()); // setCategory가 호출되었는지 검증
    }

    @Test
    @DisplayName("쿠폰 생성 시 쿠폰 생성 정보가 유효하지 않은 경우 CustomException 예외를 반환한다.")
    public void createCouponWithInvalidData() throws Exception {
        //given
        setUpAdminMember();

        // 유효하지 않은 쿠폰 생성 dto들
        CouponCreateRequestDto dto1 = createInvalidCouponDto(-7000, 0, 7000);
        CouponCreateRequestDto dto2 = createInvalidCouponDto(0, -10, 10000);
        CouponCreateRequestDto dto3 = createInvalidCouponDto(0, 200, 0);
        CouponCreateRequestDto dto4 = createInvalidCouponDto(7000, 0, -7000);
        CouponCreateRequestDto dto5 = createExpiredCouponDto();

        //when, then
        assertThrows(CustomException.class, () -> couponAdminService.createCoupon(dto1));
        assertThrows(CustomException.class, () -> couponAdminService.createCoupon(dto2));
        assertThrows(CustomException.class, () -> couponAdminService.createCoupon(dto3));
        assertThrows(CustomException.class, () -> couponAdminService.createCoupon(dto4));
        assertThrows(CustomException.class, () -> couponAdminService.createCoupon(dto5));
    }

    private CouponCreateRequestDto createInvalidCouponDto(int value, int percentage, int minValue) {
        return CouponCreateRequestDto.builder()
                .name("새로운 쿠폰").type(CouponType.AMOUNT).value(value).percentage(percentage)
                .minValue(minValue).expiredAt(LocalDate.now().plusDays(3)).categoryId(1L).build();
    }

    private CouponCreateRequestDto createExpiredCouponDto() {
        return CouponCreateRequestDto.builder()
                .name("새로운 쿠폰").type(CouponType.AMOUNT).value(7000)
                .minValue(7000).expiredAt(LocalDate.now().minusDays(3)).categoryId(1L).build();
    }

    @Test
    @DisplayName("쿠폰 비활성화 성공 시 해당 쿠폰의 id를 반환하고 deActive() 메서드가 1회 실행된다.")
    public void deActiveCoupon() throws Exception {
        //given
        setUpAdminMember();

        Long couponId = activeCoupon1.getId();
        Coupon spyCoupon = spy(activeCoupon1);
        given(couponRepository.findById(couponId)).willReturn(Optional.of(spyCoupon));

        //when
        Long result = couponAdminService.deActiveCoupon(couponId);

        //then
        assertThat(result).isEqualTo(couponId);
        verify(spyCoupon, times(1)).deActive();
    }

    @Test
    @DisplayName("쿠폰 비활성화 시 쿠폰을 찾을 수 없는 경우 COUPON_NOT_FOUND 예외를 반환한다.")
    public void deActiveCouponWhenCouponNotFound() throws Exception {
        //given
        setUpAdminMember();

        Long couponId = 4L;
        given(couponRepository.findById(couponId)).willReturn(Optional.empty());

        //when, then
        CustomException couponNotFoundException = assertThrows(CustomException.class, () -> couponAdminService.deActiveCoupon(couponId));
        assertThat(couponNotFoundException.getErrorCode()).isEqualTo(ErrorCode.COUPON_NOT_FOUND);
    }

    @Test
    @DisplayName("쿠폰 이메일 전송 성공 시 이메일 주소가 회원의 이메일 주소와 동일하며 sendEmail() 메서드가 1회 호출된다.")
    public void sendEmail() throws Exception {
        //given
        setUpAdminMember();

        Long couponId = activeCoupon1.getId();
        given(couponRepository.findById(couponId)).willReturn(Optional.of(activeCoupon1));
        willDoNothing().given(couponEmailService).sendEmail(any());

        CouponEmailRequestDto dto = new CouponEmailRequestDto(couponId, userMember.getEmail(), "이메일 템플릿");

        //when
        String emailAddress = couponAdminService.sendEmail(dto);

        //then
        assertThat(emailAddress).isEqualTo(userMember.getEmail());
        verify(couponEmailService, times(1)).sendEmail(dto);
    }

    @Test
    @DisplayName("쿠폰 이메일 전송 시 비활성화된 쿠폰인 경우 INVALID_COUPON_DATA 예외를 반환한다.")
    public void sendEmailWhenCouponIsInActive() throws Exception {
        //given
        setUpAdminMember();

        Long couponId = inActiveCoupon1.getId();
        given(couponRepository.findById(couponId)).willReturn(Optional.of(inActiveCoupon1));

        CouponEmailRequestDto dto = new CouponEmailRequestDto(couponId, userMember.getEmail(), "이메일 템플릿");

        //when, then
        CustomException invalidCouponDataException = assertThrows(CustomException.class, () -> couponAdminService.sendEmail(dto));
        assertThat(invalidCouponDataException.getErrorCode()).isEqualTo(ErrorCode.INVALID_COUPON_DATA);
    }

    @Test
    @DisplayName("쿠폰 미보유 회원 목록 조회 성공 시 쿠폰을 보유하지 않은 회원 리스트를 포함한 CouponMemberResponseWrapperDto 를 반환한다.")
    public void findMembersWithOutCoupons() throws Exception {
        //given
        setUpAdminMember();

        Long couponId = activeCoupon1.getId();
        given(couponRepository.findById(couponId)).willReturn(Optional.of(activeCoupon1));

        given(couponMemberRepository.findCouponMembersWithoutCoupons(couponId)).willReturn(List.of(adminMember));

        //when
        CouponMemberResponseWrapperDto result = couponAdminService.findMembersWithOutCoupons(couponId);

        //then
        assertThat(result.getMessage()).isEqualTo("쿠폰 미보유 회원 목록을 조회했습니다!");
        assertThat(result.getMembers().size()).isEqualTo(1);
        assertThat(result.getMembers().get(0).getUserName()).isEqualTo(adminMember.getUserName());
        assertThat(result.getMembers().get(0).getEmail()).isEqualTo(adminMember.getEmail());
    }

    @Test
    @DisplayName("관리자 권한이 필요한 메서드 실행 시 권한이 없는 경우 USER_NOT_AUTHORIZED 예외를 반환한다.")
    public void checkAdminFail() throws Exception {
        //given
        given(securityUtil.getCurrentMemberId()).willReturn(userMember.getId());
        given(memberRepository.findById(userMember.getId())).willReturn(Optional.of(userMember));

        //when, then
        CustomException userNotAuthorizedException = assertThrows(CustomException.class, () -> couponAdminService.deActiveCoupon(1L));
        assertThat(userNotAuthorizedException.getErrorCode()).isEqualTo(ErrorCode.USER_NOT_AUTHORIZED);
    }

    private void setUpAdminMember() {
        given(securityUtil.getCurrentMemberId()).willReturn(adminMember.getId());
        given(memberRepository.findById(adminMember.getId())).willReturn(Optional.of(adminMember));
    }
}
