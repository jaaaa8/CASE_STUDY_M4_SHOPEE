package com.example.case_study_mdl_4_shopee.service;

import com.example.case_study_mdl_4_shopee.entity.Account;
import com.example.case_study_mdl_4_shopee.entity.AccountRole;
import com.example.case_study_mdl_4_shopee.entity.City;
import com.example.case_study_mdl_4_shopee.entity.Role;
import com.example.case_study_mdl_4_shopee.repository.IAccountRepository;
import com.example.case_study_mdl_4_shopee.repository.IAccountRoleRepository;
import com.example.case_study_mdl_4_shopee.repository.IRoleRepository;
import com.example.case_study_mdl_4_shopee.repository.IShippingTaskRepository;
import com.example.case_study_mdl_4_shopee.service.impl.IAdminShipperService;

import java.util.Optional;

public class AdminShipperService implements IAdminShipperService {
    private final IAccountRepository accountRepository;
    private final IRoleRepository roleRepository;
    private final IAccountRoleRepository accountRoleRepository;
    private final IShippingTaskRepository shippingTaskRepository;

    public AdminShipperService(IAccountRepository accountRepository, IRoleRepository roleRepository, IAccountRoleRepository accountRoleRepository, IShippingTaskRepository shippingTaskRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.accountRoleRepository = accountRoleRepository;
        this.shippingTaskRepository = shippingTaskRepository;
    }

    @Override
    public boolean addNewShipper(Account account) {

//        Optional<Account> existed = accountRepository.ex
//
//        if (existed.isPresent()) {
//            return false;
//        }
//
//        Role shipperRole = roleRepository.findByRoleName("ROLE_SHIPPER");
//
//        account.addRole(shipperRole);
//
//        accountRepository.save(account);
        return true;
    }

    @Override
    public boolean deleteShipper(Account shipper) {
        if(!shippingTaskRepository.existsActiveTaskByShipperId(shipper.getAccountId())) {
            return false;
        }
        AccountRole accountRole = accountRoleRepository
                .findActiveShipperRole(shipper)
                .orElseThrow(() -> new RuntimeException("Account is not shipper"));

        accountRole.setActive(false);

        accountRoleRepository.save(accountRole);
        return  true;
    }

    @Override
    public void confirmAtWareHouse(Long adminShipperId, Long subOrderId) {

    }

    @Override
    public void shipToAnotherWarehouse(Long shipperId, Long subOrderId) {

    }
}
