package com.team2.fitinside.cart.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.fitinside.cart.dto.CartCreateRequestDto;
import com.team2.fitinside.cart.dto.CartResponseDto;
import com.team2.fitinside.cart.dto.CartResponseWrapperDto;
import com.team2.fitinside.cart.dto.CartUpdateRequestDto;
import com.team2.fitinside.cart.service.CartService;
import com.team2.fitinside.global.exception.CustomException;
import com.team2.fitinside.global.exception.ErrorCode;
import org.junit.jupiter.api.*;

import static org.mockito.BDDMockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CartController.class)
@AutoConfigureMockMvc(addFilters = false) // 필터 제외 (JWT 검증 제외)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("장바구니 컨트롤러 단위 테스트")
class CartControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    @Autowired
    private ObjectMapper objectMapper;

    // 기본 장바구니 컨트롤러의 URL
    private static final String URL = "/api/cart";
    private static final String URLS = "/api/carts";

    @BeforeEach
    void setUp() {
        // 공통적으로 사용할 수 있는 설정을 추가할 수 있습니다.
    }

    private CartCreateRequestDto createCartCreateRequestDto(Long productId, int quantity) {
        return new CartCreateRequestDto(productId, quantity);
    }

    private CartUpdateRequestDto createCartUpdateRequestDto(Long productId, int quantity) {
        return new CartUpdateRequestDto(productId, quantity);
    }

    private void mockCartServiceFindAllCarts() {
        given(cartService.findAllCarts()).willReturn(
                new CartResponseWrapperDto("장바구니 조회 완료했습니다!",
                        List.of(new CartResponseDto(1L, 10), new CartResponseDto(2L, 5)))
        );
    }

    private void mockCartServiceCreateCart(CartCreateRequestDto dto, Long cartId) {
        given(cartService.createCart(dto)).willReturn(cartId);
    }

    private void mockCartServiceUpdateCart(CartUpdateRequestDto dto, Long cartId) {
        given(cartService.updateCart(dto)).willReturn(cartId);
    }

    private void mockCartServiceDeleteCart(Long productId, Long cartId) {
        given(cartService.deleteCart(productId)).willReturn(cartId);
    }

    @Test
    @Order(1)
    @DisplayName("장바구니 목록 조회 성공 시 상태코드 200을 반환하고 해당 목록들이 담긴 리스트를 포함한 CartResponseWrapperDto 를 바디에 포함한다.")
    public void findCart() throws Exception {
        //given
        mockCartServiceFindAllCarts();

        //when (/api/carts GET 요청 시)
        ResultActions resultActions = mockMvc.perform(get(URLS));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.message").value("장바구니 조회 완료했습니다!"))
                .andExpect(jsonPath("$.carts.length()").value(2))
                .andExpect(jsonPath("$.carts[0].productId").value(1L))
                .andExpect(jsonPath("$.carts[0].quantity").value(10));
    }

    @Test
    @Order(2)
    @DisplayName("장바구니 목록 조회 시 권한이 없다면 상태코드 403을 반환하고 에러코드는 USER_NOT_AUTHORIZED 와 같다.")
    public void findCart403Exception() throws Exception {
        //given
        CustomException authorizedException = new CustomException(ErrorCode.USER_NOT_AUTHORIZED);
        given(cartService.findAllCarts()).willThrow(authorizedException);

        //when
        ResultActions resultActions = mockMvc.perform(get(URLS));

        //then
        resultActions
                .andExpect(status().is(403))
                .andExpect(jsonPath("$.code").value(authorizedException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("권한이 없는 사용자입니다."));
    }

    @Test
    @Order(3)
    @DisplayName("장바구니 생성 성공 시 상태코드 201을 반환한다.")
    public void createCart() throws Exception {
        //given
        CartCreateRequestDto dto = createCartCreateRequestDto(1L, 10);
        mockCartServiceCreateCart(dto, 1L);

        //when (/api/carts POST 요청 시)
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(201))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("장바구니가 추가되었습니다! cartId: 1"));
    }

    @Test
    @Order(4)
    @DisplayName("장바구니 생성 시 상품의 수량 범위를 벗어난 경우 상태코드 400을 반환하고 에러코드는 CART_OUT_OF_RANGE 와 같다.")
    public void createCart400Exception() throws Exception {
        //given
        CartCreateRequestDto dto = createCartCreateRequestDto(1L, 21);
        CustomException badRequestException = new CustomException(ErrorCode.CART_OUT_OF_RANGE);
        given(cartService.createCart(dto)).willThrow(badRequestException);

        //when (/api/carts POST 요청 시)
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.code").value(badRequestException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("상품 수량은 1개 이상 20개 이하여야 합니다."));
    }

    @Test
    @Order(5)
    @DisplayName("장바구니 수정 성공 시 상태코드 200을 반환한다.")
    public void updateCart() throws Exception {
        //given
        CartUpdateRequestDto dto = createCartUpdateRequestDto(1L, 20);
        mockCartServiceUpdateCart(dto, 1L);

        //when (/api/carts PUT 요청 시)
        ResultActions resultActions = mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("장바구니가 수정되었습니다! cartId: 1"));
    }

    @Test
    @Order(6)
    @DisplayName("장바구니 수정 시 해당 장바구니를 찾을 수 없는 경우 상태코드 404를 반환하고 에러코드는 CART_NOT_FOUND 와 같다.")
    public void updateCart404Error() throws Exception {
        //given
        CartUpdateRequestDto dto = createCartUpdateRequestDto(2L, 5);
        CustomException notFoundException = new CustomException(ErrorCode.CART_NOT_FOUND);
        given(cartService.updateCart(dto)).willThrow(notFoundException);

        //when
        ResultActions resultActions = mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.code").value(notFoundException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("해당 장바구니를 찾을 수 없습니다."));
    }

    @Test
    @Order(7)
    @DisplayName("장바구니 단건 삭제 성공 시 상태코드 200을 반환한다.")
    public void deleteCart() throws Exception {
        //given
        Long productId = 1L;
        mockCartServiceDeleteCart(productId, 1L);

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL + "/" + productId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("장바구니가 삭제되었습니다! cartId: 1"));
    }

    @Test
    @Order(8)
    @DisplayName("장바구니 초기화 성공 시 상태코드 200을 반환한다.")
    public void clearCart() throws Exception {
        //given
        willDoNothing().given(cartService).clearCart();

        //when
        ResultActions resultActions = mockMvc.perform(delete(URLS));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("장바구니가 초기화되었습니다!"));
    }
}
