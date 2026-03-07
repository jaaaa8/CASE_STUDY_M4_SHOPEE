package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.SubOrders;
import com.example.case_study_mdl_4_shopee.entity.Warehouse;
import com.example.case_study_mdl_4_shopee.repository.ISubOrdersRepository;
import com.example.case_study_mdl_4_shopee.repository.IWarehouseRepository;
import com.example.case_study_mdl_4_shopee.service.impl.ISellerOrderService;

import java.util.List;

public class SellerOrderService implements ISellerOrderService {

    private final ISubOrdersRepository subOrdersRepository;
    private final IWarehouseRepository warehouseRepository;

    public SellerOrderService(ISubOrdersRepository subOrdersRepository, IWarehouseRepository warehouseRepository) {
        this.subOrdersRepository = subOrdersRepository;
        this.warehouseRepository = warehouseRepository;
    }

    @Override
    public void confirmOrder(Long orderId) {

    }

    @Override
    public void rejectOrder(Long orderId) {

    }

    @Override
    public void shipOrder(Long orderId) {

    }

    @Override
    public void assignWarehouse(SubOrders subOrder) {
        String sellerLocation = String.valueOf(subOrder.getSellerOrder().getCity().getLocation());

        List<Warehouse> warehouses = warehouseRepository.findByLocation(sellerLocation);

        if(warehouses.isEmpty()){
            throw new RuntimeException("No warehouse found");
        }

        Warehouse warehouse = warehouses.get(0);

        subOrder.setWarehouse(warehouse);

        subOrdersRepository.save(subOrder);
    }
}
