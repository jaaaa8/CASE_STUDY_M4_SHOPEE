package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.OrderItems;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import com.example.case_study_mdl_4_shopee.repository.IOrderItemsRepository;
import com.example.case_study_mdl_4_shopee.repository.ISubOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerOrderService {

    private final ISubOrdersRepository subOrdersRepository;
    private final IOrderItemsRepository orderItemsRepository;

    // ✅ 1) List subOrders của seller hiện tại
    public List<SubOrders> getMySubOrders() {
        Long sellerId = getCurrentSellerId(); // TODO: thay bằng logic login của nhóm bạn
        return subOrdersRepository.findBySellerId(sellerId);
    }

    // ✅ 2) Detail 1 subOrder + items
    public SubOrderDetailDto getSubOrderDetail(Long subOrderId) {
        Long sellerId = getCurrentSellerId();

        SubOrders subOrder = subOrdersRepository.findBySubOrderIdAndSellerId(subOrderId, sellerId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found or forbidden"));

        List<OrderItems> items = orderItemsRepository.findBySubOrderId(subOrderId);

        return new SubOrderDetailDto(subOrder, items);
    }

    // ✅ 3) Confirm: PENDING -> CONFIRMED
    public void confirmSubOrder(Long subOrderId) {
        Long sellerId = getCurrentSellerId();

        SubOrders so = subOrdersRepository.findBySubOrderIdAndSellerId(subOrderId, sellerId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found or forbidden"));

        if (so.getSubOrderStatus() != SubOrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING can be CONFIRMED");
        }

        so.setSubOrderStatus(SubOrderStatus.CONFIRMED);
        subOrdersRepository.save(so);
    }

    // ✅ 4) Update status (ví dụ: CONFIRMED -> SHIPPED -> COMPLETED ...)
    public void updateSubOrderStatus(Long subOrderId, String status) {
        Long sellerId = getCurrentSellerId();

        SubOrders so = subOrdersRepository.findBySubOrderIdAndSellerId(subOrderId, sellerId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found or forbidden"));

        SubOrderStatus newStatus;
        try {
            newStatus = SubOrderStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status: " + status);
        }

        // (tuỳ yêu cầu) check chuyển trạng thái hợp lệ
        // Mặc định: cho phép chuyển nếu không lùi trạng thái "ngu"
        // Bạn có thể đơn giản hóa nếu nhóm bạn không yêu cầu rule.
        so.setSubOrderStatus(newStatus);
        subOrdersRepository.save(so);
    }

    // ✅ 5) Cancel (hoặc reject): PENDING -> REJECTED / CANCELLED (tuỳ enum của bạn)
    public void cancelSubOrder(Long subOrderId) {
        Long sellerId = getCurrentSellerId();

        SubOrders so = subOrdersRepository.findBySubOrderIdAndSellerId(subOrderId, sellerId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found or forbidden"));

        if (so.getSubOrderStatus() != SubOrderStatus.PENDING) {
            throw new RuntimeException("Only PENDING can be CANCELLED/REJECTED");
        }

        // Nếu enum của bạn là REJECTED thì dùng REJECTED
        // Nếu enum của bạn là CANCELLED thì đổi lại ở đây
        if (hasStatus(SubOrderStatus.class, "REJECTED")) {
            so.setSubOrderStatus(SubOrderStatus.valueOf("REJECTED"));
        } else if (hasStatus(SubOrderStatus.class, "CANCELLED")) {
            so.setSubOrderStatus(SubOrderStatus.valueOf("CANCELLED"));
        } else {
            throw new RuntimeException("Enum SubOrderStatus missing REJECTED/CANCELLED");
        }

        subOrdersRepository.save(so);
    }

    // =========================
    // DTO trả về cho view detail
    // =========================
    public record SubOrderDetailDto(SubOrders subOrder, List<OrderItems> items) {}

    // =========================
    // Helper
    // =========================

    // TODO: bạn thay bằng lấy sellerId từ session/auth của dự án
    private Long getCurrentSellerId() {
        // Ví dụ tạm: return 1L;
        // Bạn phải thay bằng: session.getAttribute(...) hoặc SecurityContextHolder...
        throw new RuntimeException("Implement getCurrentSellerId() based on your login/session");
    }

    private static boolean hasStatus(Class<? extends Enum<?>> enumClass, String name) {
        for (Enum<?> e : enumClass.getEnumConstants()) {
            if (e.name().equals(name)) return true;
        }
        return false;
    }
}