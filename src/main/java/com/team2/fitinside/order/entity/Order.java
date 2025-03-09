package com.team2.fitinside.order.entity;

import com.team2.fitinside.member.entity.Member;
import com.team2.fitinside.order.dto.OrderRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE orders SET is_deleted = true WHERE order_id = ?")
@NoArgsConstructor
@AllArgsConstructor
@Getter @Builder
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

    @Column(name = "discounted_total_price")
    @Builder.Default
    private int discountedTotalPrice = 0;

    @Column(name = "delivery_fee", nullable = false)
    private int deliveryFee;

    // 배송 관련 정보
    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;

    @Column(name = "detailed_address")
    private String detailedAddress;

    @Column(name = "delivery_receiver", nullable = false)
    private String deliveryReceiver;

    @Column(name = "delivery_phone", nullable = false)
    private String deliveryPhone;

    @Column(name = "delivery_memo")
    private String deliveryMemo;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    // 하나의 주문에 여러 상품이 있을 수 있음
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

    // 주문 상품 추가 및 총 가격, 할인 가격 업데이트
    public void addOrderProduct(OrderProduct orderProduct) {
        this.orderProducts.add(orderProduct);
        orderProduct.setOrder(this); // OrderProduct에도 해당 Order 설정 (양방향 관계)

        this.totalPrice += orderProduct.getOrderProductPrice() * orderProduct.getCount();
        this.discountedTotalPrice += orderProduct.getDiscountedPrice();
    }

    // 주문 수정
    public void updateDeliveryInfo(OrderRequestDto request) {
        this.postalCode = request.getPostalCode();
        this.deliveryAddress = request.getDeliveryAddress();
        this.detailedAddress = request.getDetailedAddress();
        this.deliveryReceiver = request.getDeliveryReceiver();
        this.deliveryPhone = request.getDeliveryPhone();
        this.deliveryMemo = request.getDeliveryMemo();
    }

}
