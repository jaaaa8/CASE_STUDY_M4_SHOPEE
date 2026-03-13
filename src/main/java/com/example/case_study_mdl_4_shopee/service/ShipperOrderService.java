package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.*;
import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import com.example.case_study_mdl_4_shopee.enums.TaskStatus;
import com.example.case_study_mdl_4_shopee.enums.TaskType;
import com.example.case_study_mdl_4_shopee.enums.TrackingStatus;
import com.example.case_study_mdl_4_shopee.repository.*;
import com.example.case_study_mdl_4_shopee.service.impl.IShipperOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ShipperOrderService implements IShipperOrderService {

    private final IShippingTaskRepository shippingTaskRepository;
    private final ISubOrdersRepository subOrdersRepository;
    private final IShipmentTrackingRepository shipmentTrackingRepository;
    private final IAccountRepository accountRepository;
    private final IWarehouseRepository warehouseRepository;

    /* ============================
       SHIPPER CONFIRM PICKUP
    ============================ */

    @Override
    public void confirmPickup(Long shipperId, Long subOrderId) {

        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found"));

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        if (subOrder.getStatus() != SubOrderStatus.SELLER_CONFIRMED) {
            throw new RuntimeException("Order not ready for pickup");
        }

        subOrder.setShipper(shipper);
        subOrder.setStatus(SubOrderStatus.SHIPPER_PICKED);

        subOrdersRepository.save(subOrder);

        ShipmentTracking tracking = ShipmentTracking.builder()
                .subOrder(subOrder)
                .warehouse(subOrder.getWarehouse())
                .status(TrackingStatus.SHIPPER_PICKED)
                .updatedBy(shipper)
                .note("Shipper picked up order")
                .build();

        shipmentTrackingRepository.save(tracking);
    }

    /* ============================
       ARRIVED WAREHOUSE
    ============================ */

    @Override
    public void confirmArrivedWarehouse(Long shipperId, Long subOrderId) {

        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found"));

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        if (!subOrder.getShipper().getAccountId().equals(shipperId)) {
            throw new RuntimeException("You are not assigned to this order");
        }

        subOrder.setStatus(SubOrderStatus.SHIPPED);

        subOrdersRepository.save(subOrder);

        ShipmentTracking tracking = ShipmentTracking.builder()
                .subOrder(subOrder)
                .warehouse(subOrder.getWarehouse())
                .status(TrackingStatus.RECEIVED_AT_WAREHOUSE)
                .updatedBy(shipper)
                .note("Order arrived warehouse")
                .build();

        shipmentTrackingRepository.save(tracking);
    }

    /* ============================
       CONFIRM DELIVERED
    ============================ */

    @Override
    public void confirmDelivered(Long shipperId, Long subOrderId) {

        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found"));

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        if (!subOrder.getShipper().getAccountId().equals(shipperId)) {
            throw new RuntimeException("You are not assigned to this order");
        }

        subOrder.setStatus(SubOrderStatus.DELIVERED);

        subOrdersRepository.save(subOrder);

        ShipmentTracking tracking = ShipmentTracking.builder()
                .subOrder(subOrder)
                .warehouse(subOrder.getWarehouse())
                .status(TrackingStatus.DELIVERED)
                .updatedBy(shipper)
                .note("Order delivered to customer")
                .build();

        shipmentTrackingRepository.save(tracking);
    }

    /* ============================
       GET SHIPPER TASK LIST
    ============================ */

    @Override
    public List<ShippingTask> getShipperTasks(Long shipperId) {


        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        return shippingTaskRepository.findByShipper(shipper);
    }

    /* ============================
       GET TASK DETAIL
    ============================ */

    @Override
    public List<SubOrders> getTaskDetail(Long taskId) {
        return subOrdersRepository.findSubOrdersByTaskId(taskId);
    }

    @Override
    @Transactional
    public void createTaskForSubOrder(SubOrders subOrder) {

        Account seller = subOrder.getSellerOrder();

        Location location = seller.getCity().getLocation();

        Warehouse warehouse = warehouseRepository
                .findByLocation(location)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No warehouse found"));

        Account shipper = accountRepository
                .findShipperByLocation(seller.getCity().getLocation())
                .orElseThrow(() -> new RuntimeException("Chưa có shipper cho thành phố này"));

        // lock task rows
        List<ShippingTask> tasks = shippingTaskRepository.findAvailableTasksForUpdate(warehouse, TaskType.PICKUP);

        ShippingTask task = null;

        // tìm task còn capacity
        for (ShippingTask t : tasks) {
            if (t.getSubOrders().size() < t.getCapacity()) {
                task = t;
                break;
            }
        }

        // nếu không có task phù hợp thì tạo mới
        if (task == null) {

            task = ShippingTask.builder()
                    .warehouse(warehouse)
                    .type(TaskType.PICKUP)
                    .status(TaskStatus.CREATED)
                    .taskDate(LocalDateTime.now())
                    .shipper(shipper)
                    .capacity(20)
                    .build();

            task = shippingTaskRepository.save(task);
        }

        // gán suborder vào task
        subOrder.setShippingTask(task);
        subOrder.setWarehouse(warehouse);

        subOrdersRepository.save(subOrder);
    }

}