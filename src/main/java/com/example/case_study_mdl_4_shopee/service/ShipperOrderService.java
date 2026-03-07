package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.*;
import com.example.case_study_mdl_4_shopee.enums.SubOrderStatus;
import com.example.case_study_mdl_4_shopee.enums.TaskStatus;
import com.example.case_study_mdl_4_shopee.enums.TrackingStatus;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IShipmentTrackingRepository;
import com.example.case_study_mdl_4_shopee.repository.IShippingTaskRepository;
import com.example.case_study_mdl_4_shopee.repository.ISubOrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ShipperOrderService{

    private final IShippingTaskRepository shippingTaskRepository;
    private final ISubOrdersRepository subOrdersRepository;
    private final IShipmentTrackingRepository shipmentTrackingRepository;
    private final IAccountRepository accountRepository;

    /* ============================
       SHIPPER CONFIRM PICKUP
    ============================ */

    public void confirmPickup(Long shipperId, Long subOrderId) {

        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found"));

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        if(subOrder.getStatus() != SubOrderStatus.SELLER_CONFIRMED){
            throw new RuntimeException("Order not ready for pickup");
        }

        /* update suborder */

        subOrder.setShipper(shipper);
        subOrder.setStatus(SubOrderStatus.SHIPPER_PICKED);

        subOrdersRepository.save(subOrder);

        /* add tracking */

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

    public void confirmArrivedWarehouse(Long shipperId, Long subOrderId){

        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found"));

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

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

    public void confirmDelivered(Long shipperId, Long subOrderId){

        SubOrders subOrder = subOrdersRepository.findById(subOrderId)
                .orElseThrow(() -> new RuntimeException("SubOrder not found"));

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

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

    public List<ShippingTask> getShipperTasks(Long shipperId) {

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        return shippingTaskRepository.findByShipper(shipper);
    }

    public List<SubOrders> getTaskDetail(Long taskId) {

        return subOrdersRepository.findSubOrdersByTaskId(taskId);
    }

    public void createShippingTask(SubOrders subOrder){

        Warehouse warehouse = subOrder.getWarehouse();

        List<Account> shippers = accountRepository.findShipperByWarehouse(warehouse.getWarehouseId());

        Account shipper = shippers.get(0);

        ShippingTask task = ShippingTask.builder()
                .shipper(shipper)
                .warehouse(warehouse)
                .status(TaskStatus.CREATED)
                .build();

        shippingTaskRepository.save(task);

        subOrder.setShippingTask(task);

        subOrdersRepository.save(subOrder);
    }
}
