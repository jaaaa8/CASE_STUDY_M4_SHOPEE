package com.example.case_study_mdl_4_shopee.service.impl;

import com.example.case_study_mdl_4_shopee.entity.Account;

public interface IAdminShipperService {
    boolean addNewShipper(Account shipper);
    boolean deleteShipper(Account shipper);
    void confirmAtWareHouse(Long adminShipperId, Long subOrderId);
    void shipToAnotherWarehouse(Long shipperId, Long subOrderId);
}
