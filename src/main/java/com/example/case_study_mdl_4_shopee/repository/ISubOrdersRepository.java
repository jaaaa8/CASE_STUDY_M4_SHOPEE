package com.example.case_study_mdl_4_shopee.repository;

import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubOrdersRepository extends JpaRepository<SubOrders, Long> {
    @Query("""
       SELECT s
       FROM SubOrders s
       WHERE s.shippingTask.taskId = :taskId
       """)
    List<SubOrders> findSubOrdersByTaskId(@Param("taskId") Long taskId);

    // 1. Lấy danh sách đơn hàng của một Seller cụ thể
    List<SubOrders> findAllBySellerOrder_AccountId(Long sellerId);

    // 2. Tìm một đơn hàng cụ thể của Seller (để bảo mật, tránh Seller xem đơn của người khác)
    Optional<SubOrders> findBySubOrderIdAndSellerOrder_AccountId(Long subOrderId, Long sellerId);

    // 3. Lọc đơn hàng theo trạng thái (Ví dụ: Seller muốn xem tất cả đơn PENDING)
    List<SubOrders> findAllBySellerOrder_AccountIdAndStatus(Long sellerId, SubOrderStatus status);

    // 4. Tìm các SubOrders thuộc về một đơn hàng tổng (Orders)
    List<SubOrders> findAllByOrder_OrdersId(Long ordersId);
}
