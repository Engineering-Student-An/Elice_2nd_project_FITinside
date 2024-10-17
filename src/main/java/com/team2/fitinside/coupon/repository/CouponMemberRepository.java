package com.team2.fitinside.coupon.repository;

import com.team2.fitinside.coupon.entity.CouponMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponMemberRepository extends JpaRepository<CouponMember, Long> {

    Page<CouponMember> findByCoupon_Id(Pageable pageable, Long couponId);

    Page<CouponMember> findByMember_Id(Pageable pageable, Long memberId);

    Page<CouponMember> findByMember_IdAndCoupon_ActiveIsAndUsed(Pageable pageable, Long memberId, boolean active, boolean used);

    // 적용 가능한 CouponMember 리스트 반환 (카테고리 없는 엔티티도 반환)
    @Query("SELECT cm FROM CouponMember cm " +
            "WHERE cm.member.id = :memberId AND " +
            "(cm.coupon.category.id = :categoryId OR cm.coupon.category IS NULL)")
    List<CouponMember> findByMember_IdAndCoupon_Category_Id(@Param("memberId") Long memberId, @Param("categoryId") Long categoryId);

    boolean existsByCoupon_Code(String code);

    Optional<CouponMember> findByMember_IdAndCoupon_IdAndUsedIs(Long memberId, Long couponId, boolean used);

    boolean existsByMember_IdAndCoupon_Id(Long memberId, Long couponId);
}
