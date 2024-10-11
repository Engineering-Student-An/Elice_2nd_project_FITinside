package com.team2.fitinside.order.controller;

import com.team2.fitinside.order.dto.OrderResponseDto;
import com.team2.fitinside.order.dto.OrderStatusUpdateRequestDto;
import com.team2.fitinside.order.service.OrderAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/orders")
@ApiResponses({
        @ApiResponse(responseCode = "403", description = "권한이 없습니다!", content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "서버 에러", content = @Content(mediaType = "application/json"))
})
public class OrderAdminController {

    private final OrderAdminService orderAdminService;

    @GetMapping
    @Operation(summary = "관리자의 전체 주문 조회", description = "전체 주문 조회")
    @ApiResponse(responseCode = "200", description = "전체 주문 조회 완료", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = OrderResponseDto.class))))
    public ResponseEntity<?> findAllOrdersByAdmin() {
        List<OrderResponseDto> response = orderAdminService.findAllOrdersByAdmin();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // 굳이 dto로 안 보여줘도 될 것 같은데.. 나중에 확인
    @PatchMapping("/{order_id}/status")
    @Operation(summary = "관리자의 주문 상태 수정", description = "주문 상태 수정")
    @ApiResponse(responseCode = "200", description = "주문 상태 수정 완료", content = @Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDto.class)))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 주문", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> updateStatusOrder(
            @PathVariable("order_id") Long orderId,
            @RequestBody OrderStatusUpdateRequestDto request) {

        OrderResponseDto response = orderAdminService.updateOrderStatus(orderId, request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{order_id}")
    @Operation(summary = "관리자의 주문 삭제", description = "주문 삭제")
    @ApiResponse(responseCode = "200", description = "주문 삭제 완료", content = @Content(mediaType = "application/json"))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 주문", content = @Content(mediaType = "application/json"))
    public ResponseEntity<?> deleteOrder(@PathVariable("order_id") Long orderId) {
        orderAdminService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("주문 삭제 완료. orderId: " + orderId);
    }

}