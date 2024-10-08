package com.team2.fitinside.order.entity;

import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.order.common.OrderStatus;
import com.team2.fitinside.order.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ORDERED;

    @Column(name = "total_price", nullable = false)
    @Builder.Default
    private int totalPrice = 0;

    @Column(name = "delivery_fee", nullable = false)
    private int deliveryFee;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "delivery_receiver", nullable = false)
    private String deliveryReceiver;

    @Column(name = "delivery_phone", nullable = false)
    private String deliveryPhone;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // 하나의 주문에 여러 상품이 있을 수 있음
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @Builder.Default // 빌더 패턴에서 기본값 유지
    private List<OrderProduct> orderProducts = new ArrayList<>();

    // 주문 상태 변경
    public void updateOrderStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    // 주문 취소
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCELLED;
    }

    // 주문 삭제
    public void deleteOrder() {
        this.isDeleted = true;
        // 연관된 주문 상품들도 같이 삭제
        for (OrderProduct product : orderProducts) {
            product.deleteOrderProduct();
        }
    }

    // 주문 상품 추가 및 총 가격 업데이트
    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this); // OrderProduct에도 해당 Order 설정 (양방향 관계)

        this.totalPrice += orderProduct.getOrderProductPrice() * orderProduct.getCount();
    }

    // 주문 수정
    public void updateDeliveryInfo(OrderRequestDto request) {
        this.deliveryAddress = request.getDeliveryAddress();
        this.deliveryReceiver = request.getDeliveryReceiver();
        this.deliveryPhone = request.getDeliveryPhone();
    }

    public void calculateDeliveryFee() {
        this.deliveryFee = (this.totalPrice >= 20000) ? 0 : 2500;
    }


}
