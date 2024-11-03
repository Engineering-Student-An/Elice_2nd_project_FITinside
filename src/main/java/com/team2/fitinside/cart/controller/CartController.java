package com.team2.fitinside.cart.controller;

import com.team2.fitinside.cart.dto.CartCreateRequestDto;
import com.team2.fitinside.cart.dto.CartResponseWrapperDto;
import com.team2.fitinside.cart.dto.CartUpdateRequestDto;
import com.team2.fitinside.cart.service.CartService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
@ApiResponses({
        @ApiResponse(responseCode = "403", description = "권한이 없는 사용자입니다."),
        @ApiResponse(responseCode = "500", description = "서버 에러")
})
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    @Operation(summary = "로그인한 회원의 장바구니 조회", description = "장바구니 조회")
    @ApiResponse(responseCode = "200", description = "장바구니 조회 완료했습니다!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponseWrapperDto.class)))
    public ResponseEntity<CartResponseWrapperDto> findCart() {

        CartResponseWrapperDto carts = cartService.findAllCarts();
        return ResponseEntity.status(HttpStatus.OK).body(carts);
    }

    @PostMapping("/cart")
    @Operation(summary = "로그인한 회원의 장바구니 추가", description = "장바구니 추가")
    @ApiResponse(responseCode = "201", description = "장바구니가 추가되었습니다!")
    @ApiResponse(responseCode = "400", description = "상품 수량은 1개 이상 20개 이하여야 합니다.")
    @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.")
    @ApiResponse(responseCode = "404", description = "해당하는 정보의 사용자를 찾을 수 없습니다.")
    public ResponseEntity<String> createCart(@RequestBody CartCreateRequestDto dto) {

        Long createdCartId = cartService.createCart(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("장바구니가 추가되었습니다! cartId: " + createdCartId);
    }

    @PutMapping("/cart")
    @Operation(summary = "로그인한 회원의 장바구니 수정", description = "장바구니 수정")
    @ApiResponse(responseCode = "200", description = "장바구니가 수정되었습니다!")
    @ApiResponse(responseCode = "400", description = "상품 수량은 1개 이상 20개 이하여야 합니다.")
    @ApiResponse(responseCode = "404", description = "해당 상품을 찾을 수 없습니다.")
    @ApiResponse(responseCode = "404", description = "해당하는 정보의 사용자를 찾을 수 없습니다.")
    @ApiResponse(responseCode = "404", description = "해당 장바구니를 찾을 수 없습니다.")
    public ResponseEntity<String> updateCart(@RequestBody CartUpdateRequestDto dto) {

        Long updatedCartId = cartService.updateCart(dto);
        return ResponseEntity.status(HttpStatus.OK).body("장바구니가 수정되었습니다! cartId: " + updatedCartId);
    }

    @DeleteMapping("/cart/{productId}")
    @Operation(summary = "로그인한 회원의 장바구니 단건 삭제", description = "장바구니 삭제")
    @ApiResponse(responseCode = "200", description = "장바구니가 삭제되었습니다!")
    @ApiResponse(responseCode = "404", description = "해당 장바구니를 찾을 수 없습니다.")
    public ResponseEntity<String> deleteCart(@PathVariable("productId") Long productId) {

        Long deletedCartId = cartService.deleteCart(productId);
        return ResponseEntity.status(HttpStatus.OK).body("장바구니가 삭제되었습니다! cartId: " + deletedCartId);
    }

    @DeleteMapping("/carts")
    @Operation(summary = "로그인한 회원의 장바구니 초기화", description = "장바구니 초기화")
    @ApiResponse(responseCode = "200", description = "장바구니가 초기화되었습니다!")
    public ResponseEntity<String> clearCart() {

        cartService.clearCart();
        return ResponseEntity.status(HttpStatus.OK).body("장바구니가 초기화되었습니다!");
    }
}
