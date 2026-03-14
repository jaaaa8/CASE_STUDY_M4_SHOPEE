package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.dto.OrderAdminDto;
import com.example.case_study_mdl_4_shopee.dto.OrderDetailDto;
import com.example.case_study_mdl_4_shopee.dto.OrderItemDto;
import com.example.case_study_mdl_4_shopee.entity.OrderItems;
import com.example.case_study_mdl_4_shopee.entity.Orders;
import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.enums.OrderStatus;
import com.example.case_study_mdl_4_shopee.repository.IOrdersRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IAdminOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class AdminOrderService implements IAdminOrderService {

    @Autowired
    private IOrdersRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<OrderAdminDto> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map((Orders o) -> new  OrderAdminDto(
                        o.getOrdersId(),
                        o.getOrderCode(),
                        o.getCustomerOrder() != null ? o.getCustomerOrder().getUsername() : "N/A", // 3. String (Lấy tên thay vì Object Account)
                        o.getOrderStatus() != null ? o.getOrderStatus().name() : null,
                        o.getTotal(),
                        o.getCreatedAt(),
                        o.getSeller() != null ? o.getSeller().getUsername() : null
                ))
                .toList();
    }

    @Override
    public OrderDetailDto getOrderDetail(Long id) {

        Orders order = orderRepository.findById(id)
                .orElseThrow();

        OrderDetailDto dto = new OrderDetailDto();

        dto.setOrdersId(order.getOrdersId());
        dto.setOrderCode(order.getOrderCode());
        dto.setCustomerName(order.getCustomerOrder().getUsername());
        dto.setOrderStatus(order.getOrderStatus().name());
        dto.setTotal(order.getTotal());
        dto.setCreatedAt(order.getCreatedAt());

        List<OrderItemDto> items = new ArrayList<>();

        for (SubOrders sub : order.getSubOrders()) {

            for (OrderItems item : sub.getOrderItems()) {

                OrderItemDto itemDto = new OrderItemDto();

                itemDto.setProductName(item.getProduct().getName());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setPrice(item.getPrice());

                items.add(itemDto);
            }
        }

        dto.setItems(items);

        return dto;
    }


    @Override
    public Page<OrderAdminDto> searchOrders(
            String orderCode,
            String customer,
            OrderStatus status,
            Pageable pageable) {

        return orderRepository
                .searchOrders(orderCode, customer, status, pageable)
                .map(order -> {
                    // Map các trường cơ bản
                    OrderAdminDto dto = modelMapper.map(order, OrderAdminDto.class);

                    // Gán thủ công các trường bị lệch tên hoặc cần lấy từ Object liên quan
                    dto.setCustomerName(order.getCustomerOrder() != null ? order.getCustomerOrder().getUsername() : "N/A");
                    dto.setOrderStatus(order.getOrderStatus() != null ? order.getOrderStatus().name() : null);

                    // QUAN TRỌNG: Gán tên Shop/Seller ở đây
                    if (order.getSeller() != null) {
                        dto.setSellerName(order.getSeller().getUsername());
                    } else {
                        dto.setSellerName("Không có tên");
                    }

                    return dto;
                });
    }
    @Override
    public void updateStatus(Long id, OrderStatus status) {

        Orders order = orderRepository
                .findById(id)
                .orElseThrow();

        order.setOrderStatus(status);

        orderRepository.save(order);
    }

    @Override
    public void refundOrder(int orderId) {

    }

    @Override
    public void forceCancelOrder(int orderId) {

    }
}
