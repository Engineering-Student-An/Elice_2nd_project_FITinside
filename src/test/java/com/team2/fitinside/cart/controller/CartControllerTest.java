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

    // 기본 장바구니 컨트롤러의 url
    private static final String URL = "/api/carts";

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("장바구니 목록 조회")
    public void findCart() throws Exception {

        //given (cartService.findAllCarts(): CartResponseWrapperDto 반환)
        given(cartService.findAllCarts())
                .willReturn(
                        new CartResponseWrapperDto("장바구니 조회 완료했습니다!",
                                List.of(new CartResponseDto(1L, 10), new CartResponseDto(2L,5)))
                );

        //when (/api/carts GET 요청 시)
        ResultActions resultActions = mockMvc.perform(get(URL));

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
    @DisplayName("장바구니 조회 - 403에러 (권한 없는 경우)")
    public void findCart403Exception() throws Exception {

        //given
        // cartService.findAllCarts() 호출 시 403 에러 던지게 설정
        CustomException authorizedException = new CustomException(ErrorCode.USER_NOT_AUTHORIZED);
        given(cartService.findAllCarts()).willThrow(authorizedException);

        //when
        ResultActions resultActions = mockMvc.perform(get(URL));

        //then
        resultActions
                .andExpect(status().is(403))
                .andExpect(jsonPath("$.code").value(authorizedException.getErrorCode().toString()))
                .andExpect(jsonPath("$.message").value("권한이 없는 사용자입니다."));
    }

    @Test
    @Order(3)
    @DisplayName("장바구니 생성")
    public void createCart() throws Exception {

        //given
        CartCreateRequestDto dto = new CartCreateRequestDto(1L, 10);
        given(cartService.createCart(dto)).willReturn(1L);

        //when (/api/carts POST 요청 시)
        ResultActions resultActions = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON) // Content-Type 설정 (@RequestBody를 사용하는 경우 JSON형식으로 전송해야 함)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(201))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))   // 응답의 형식이 text/plain;charset=UTF-8 임을 검증
                .andExpect(content().string("장바구니가 추가되었습니다! cartId: 1"));
    }

    @Test
    @Order(4)
    @DisplayName("장바구니 생성 - 400에러 (수량 범위 벗어난 경우)")
    public void createCart400Exception() throws Exception {

        //given
        CartCreateRequestDto dto = new CartCreateRequestDto(1L, 21);
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
    @DisplayName("장바구니 수정")
    public void updateCart() throws Exception {

        //given
        CartUpdateRequestDto dto = new CartUpdateRequestDto(1L, 20);
        given(cartService.updateCart(dto)).willReturn(1L);

        //when (/api/carts PUT 요청 시)
        ResultActions resultActions = mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))   // 응답의 형식이 text/plain;charset=UTF-8 임을 검증
                .andExpect(content().string("장바구니가 수정되었습니다! cartId: 1"));
    }

    @Test
    @Order(6)
    @DisplayName("장바구니 수정 - 404에러 (장바구니를 찾을 수 없는 경우)")
    public void updateCart404Error() throws Exception {

        //given
        CartUpdateRequestDto dto = new CartUpdateRequestDto(2L, 5);
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
    @DisplayName("장바구니 단건 삭제")
    public void deleteCart() throws Exception {

        //given
        Long productId = 1L;
        given(cartService.deleteCart(productId)).willReturn(1L);

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
    @DisplayName("장바구니 초기화")
    public void clearCart() throws Exception {

        //given
        willDoNothing().given(cartService).clearCart(); // cartService.clearCart() 호출 시 아무 행동도 하지 않도록 설정

        //when
        ResultActions resultActions = mockMvc.perform(delete(URL));

        //then
        resultActions
                .andExpect(status().is(200))
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("장바구니가 초기화되었습니다!"));
    }
}