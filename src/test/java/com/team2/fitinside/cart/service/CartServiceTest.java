package com.team2.fitinside.cart.service;

import com.team2.fitinside.cart.dto.CartCreateRequestDto;
import com.team2.fitinside.cart.dto.CartResponseWrapperDto;
import com.team2.fitinside.cart.dto.CartUpdateRequestDto;
import com.team2.fitinside.cart.entity.Cart;
import com.team2.fitinside.cart.repository.CartRepository;
import com.team2.fitinside.config.SecurityUtil;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import com.team2.fitinside.member.entity.Authority;
import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.member.repository.MemberRepository;
import com.team2.fitinside.product.entity.Product;
import com.team2.fitinside.product.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("장바구니 서비스 단위 테스트")
class CartServiceTest {

    @Mock
    private CartRepository cartRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SecurityUtil securityUtil;

    @InjectMocks
    private CartService cartService;

    private Member loginMember;
    private Product product1;
    private Cart cart1;
    private Cart cart2;

    @BeforeEach
    void setUp() {
        // 테스트용 회원 객체 생성
        loginMember = createTestMember();

        // 테스트용 상품 생성
        product1 = createTestProduct(1L, "상품1", 10000, 10);
        Product product2 = createTestProduct(2L, "상품2", 20000, 200);

        // 테스트용 장바구니 생성
        cart1 = createTestCart(1L, 10, loginMember, product1);
        cart2 = createTestCart(2L, 20, loginMember, product2);
    }

    private Member createTestMember() {
        return Member.builder()
                .id(1L)
                .email("test@test.com")
                .password("password1234")
                .userName("회원1")
                .phone("010-1111-1111")
                .authority(Authority.ROLE_USER)
                .build();
    }

    private Product createTestProduct(Long id, String name, int price, int stock) {
        return Product.builder()
                .id(id)
                .productName(name)
                .price(price)
                .stock(stock)
                .build();
    }

    private Cart createTestCart(Long id, int quantity, Member member, Product product) {
        Cart cart = Cart.builder().id(id).quantity(quantity).build();
        cart.setUserAndProduct(member, product);
        return cart;
    }

    @Test
    @DisplayName("장바구니 목록 조회 성공 시 CartResponseWrapperDto를 반환한다.")
    public void findAllCarts() throws Exception {
        //given
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());
        given(cartRepository.findAllByMember_Id(loginMember.getId())).willReturn(List.of(cart1, cart2));

        //when
        CartResponseWrapperDto result = cartService.findAllCarts();

        //then
        assertThat(result.getMessage()).isEqualTo("장바구니 조회 완료했습니다!");
        assertThat(result.getCarts().size()).isEqualTo(2);
        assertThat(result.getCarts().get(0).getProductId()).isEqualTo(1L);
        assertThat(result.getCarts().get(0).getQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("장바구니 목록 조회 시 권한이 없는 경우 CustomException에 해당하는 예외를 반환한다.")
    public void findAllCarts403Exception() throws Exception {
        //given
        // securityUtil.getCurrentMemberId()가 RuntimeException을 반환한 경우
        given(securityUtil.getCurrentMemberId()).willThrow(new RuntimeException());

        //when, then
        // cartService.findAllCarts() 실행했을 때 예외를 반환하는지 검증
        assertThrows(CustomException.class,
                () -> cartService.findAllCarts());
    }

    @Test
    @DisplayName("장바구니 생성 성공 시 저장된 장바구니의 id를 반환한다.")
    public void createCart() throws Exception {

        //given
        // 테스트용 장바구니 생성 요청 dto
        CartCreateRequestDto dto = new CartCreateRequestDto(product1.getId(), 5);

        // productRepository.findById() 호출 시 생성해 둔 상품 객체 반환하게 설정
        given(productRepository.findById(product1.getId())).willReturn(Optional.ofNullable(product1));

        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // memberRepository.findById() 호출 시 생성해 둔 Member 객체 반환하게 설정
        given(memberRepository.findById(loginMember.getId())).willReturn(Optional.ofNullable(loginMember));

        // cartRepository.save() 호출 시 id:3인 장바구니 객체 반환하게 설정
        given(cartRepository.save(any())).willReturn(Cart.builder().id(3L).build());

        //when
        Long savedCartId = cartService.createCart(dto);

        //then
        assertThat(savedCartId).isEqualTo(3L);      // 새로운 장바구니 id를 반환하는지 검증
    }

    @Test
    @DisplayName("장바구니 생성 시 동일한 장바구니가 존재하는 경우 기존의 장바구니를 수정한다.")
    public void createCartWhenCartExists() throws Exception {

        //given
        // 테스트용 장바구니 생성 요청 dto
        CartCreateRequestDto dto = new CartCreateRequestDto(product1.getId(), 5);

        // cartRepository.existsCartByMember_IdAndProduct_Id()가 true 반환하게 설정
        given(cartRepository.existsCartByMember_IdAndProduct_Id(loginMember.getId(), product1.getId())).willReturn(true);

        given(cartRepository.findByMember_IdAndProduct_Id(loginMember.getId(), product1.getId())).willReturn(Optional.ofNullable(cart1));

        given(productRepository.findById(product1.getId())).willReturn(Optional.ofNullable(product1));
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        //when
        Long savedCartId = cartService.createCart(dto);

        //then
        assertThat(savedCartId).isEqualTo(1L);          // 기존의 cart1의 id를 반환하는지 검증
        assertThat(cart1.getQuantity()).isEqualTo(5);   // 기존의 cart1의 수량이 바뀌었는지 검증
    }

    @Test
    @DisplayName("장바구니 생성 시 상품 수량 범위를 벗어나면 CART_OUT_OF_RANGE 예외를 반환한다.")
    public void createCart400ExceptionCartOutOfRange() throws Exception {

        //given
        CartCreateRequestDto dto1 = new CartCreateRequestDto(product1.getId(), 21);
        CartCreateRequestDto dto2 = new CartCreateRequestDto(product1.getId(), 0);
        given(productRepository.findById(product1.getId())).willReturn(Optional.ofNullable(product1));


        //when, then
        // 수량이 20개 초과일 때 CART_OUT_OF_RANGE를 던지는지 검증
        CustomException exceptionQuantityOverRange = assertThrows(CustomException.class, () ->
                cartService.createCart(dto1));
        assertThat(exceptionQuantityOverRange.getErrorCode()).isEqualTo(ErrorCode.CART_OUT_OF_RANGE);

        // 수량이 1개 미만일 때 CART_OUT_OF_RANGE를 던지는지 검증
        CustomException exceptionQuantityUnderRange = assertThrows(CustomException.class, () ->
                cartService.createCart(dto2));
        assertThat(exceptionQuantityUnderRange.getErrorCode()).isEqualTo(ErrorCode.CART_OUT_OF_RANGE);
    }

    @Test
    @DisplayName("장바구니 생성 시 상품의 재고 수량을 초과하는 경우 OUT_OF_STOCK 예외를 반환한다.")
    public void createCart400ExceptionOutOfStock() throws Exception {

        //given
        CartCreateRequestDto dto = new CartCreateRequestDto(product1.getId(), 11);
        given(productRepository.findById(product1.getId())).willReturn(Optional.ofNullable(product1));

        //when, then
        // 수량이 상품 재고 (10개) 초과일 때 OUT_OF_STOCK을 던지는지 검증
        CustomException exceptionQuantityOutOfStock = assertThrows(CustomException.class, () ->
                cartService.createCart(dto));
        assertThat(exceptionQuantityOutOfStock.getErrorCode()).isEqualTo(ErrorCode.OUT_OF_STOCK);
    }

    @Test
    @DisplayName("장바구니 수정 성공 시 수정된 장바구니의 id를 반환한다.")
    public void updateCart() throws Exception {
        //given
        CartUpdateRequestDto dto = new CartUpdateRequestDto(product1.getId(), 7);

        // Cart 객체를 spy로 설정
        Cart spyCart = spy(cart1);
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());
        given(cartRepository.findByMember_IdAndProduct_Id(loginMember.getId(), product1.getId())).willReturn(Optional.of(spyCart));

        //when
        Long updatedCartId = cartService.updateCart(dto);

        //then
        assertThat(updatedCartId).isEqualTo(1L);
        assertThat(spyCart.getQuantity()).isEqualTo(7);   // 장바구니의 수량이 변경되었는지 검증
        verify(spyCart, times(1)).updateQuantity(anyInt());     // 엔티티의 수량 변경 메서드가 실행되었는지 검증
    }

    @Test
    @DisplayName("장바구니 수정 시 기존 수량과 동일한 경우 해당 장바구니 객체에서 수량 변경 메서드 (=updateQuantity()) 가 호출되지 않는다.")
    public void updateCartSameQuantity() throws Exception {
        //given
        // 현재 장바구니의 수량과 동일한 요청 생성
        CartUpdateRequestDto dto = new CartUpdateRequestDto(product1.getId(), 10);

        // Cart 객체를 spy로 설정
        Cart spyCart = spy(cart1);
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());
        given(cartRepository.findByMember_IdAndProduct_Id(loginMember.getId(), product1.getId())).willReturn(Optional.of(spyCart));

        //when
        Long updatedCartId = cartService.updateCart(dto);

        //then
        assertThat(updatedCartId).isEqualTo(1L);
        verify(spyCart, never()).updateQuantity(anyInt()); // spyCart.updateQuantity()가 호출되지 않았음을 검증
    }

    @Test
    @DisplayName("장바구니 단일 삭제 성공 시 삭제된 장바구니의 id를 반환한다.")
    public void deleteCart() throws Exception {
        //given
        Long productId = product1.getId();  // deleteCart 메서드에 넘길 productId 파라미터
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());
        given(cartRepository.findByMember_IdAndProduct_Id(loginMember.getId(), productId)).willReturn(Optional.of(cart1));
        willDoNothing().given(cartRepository).delete(any());    // cartRepository.delete()시 아무것도 하지 않게 설정

        //when
        Long deletedCartId = cartService.deleteCart(productId);

        //then
        assertThat(deletedCartId).isEqualTo(1L);
        verify(cartRepository, times(1)).delete(cart1); // delete(cart1)이 실행되었는지 확인
    }

    @Test
    @DisplayName("장바구니 초기화 성공 시 조회된 장바구니 List를 포함하여 deleteAll() 메서드가 1번 실행된다.")
    public void clearCart() throws Exception {
        //given
        given(securityUtil.getCurrentMemberId()).willReturn(loginMember.getId());

        // 로그인한 회원의 장바구니 조회 시 cart1, cart2 반환하게 설정
        given(cartRepository.findAllByMember_Id(loginMember.getId())).willReturn(List.of(cart1, cart2));
        willDoNothing().given(cartRepository).deleteAll(List.of(cart1, cart2));

        //when
        cartService.clearCart();

        //then
        verify(cartRepository, times(1)).deleteAll(List.of(cart1, cart2));
    }
}
