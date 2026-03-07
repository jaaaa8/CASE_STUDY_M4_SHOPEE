package com.example.case_study_mdl_4_shopee.config;

import com.example.case_study_mdl_4_shopee.entity.*;
import com.example.case_study_mdl_4_shopee.enums.StaffPosition;
import com.example.case_study_mdl_4_shopee.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            IAccountRepository accountRepository,
            IRoleRepository roleRepository,
            IWarehouseRepository warehouseRepository,
            IWarehouseStaffRepository warehouseStaffRepository,
            IProductRepository productRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            /* =========================
               CREATE ROLES
            ========================= */

            Role adminRole = createRole(roleRepository, "ROLE_ADMIN");
            Role customerRole = createRole(roleRepository, "ROLE_CUSTOMER");
            Role sellerRole = createRole(roleRepository, "ROLE_SELLER");
            Role shipperRole = createRole(roleRepository, "ROLE_SHIPPER");


            /* =========================
               CREATE ADMIN
            ========================= */

            Account admin = createAccount(
                    accountRepository,
                    passwordEncoder,
                    "admin",
                    "admin@gmail.com",
                    "0900000001"
            );

            admin.addRole(adminRole);
            admin.addRole(customerRole);
            admin.addRole(sellerRole);

            accountRepository.save(admin);


            /* =========================
               CREATE CUSTOMER
            ========================= */

            Account customer = createAccount(
                    accountRepository,
                    passwordEncoder,
                    "customer",
                    "customer@gmail.com",
                    "0900000002"
            );

            customer.addRole(customerRole);
            accountRepository.save(customer);


            /* =========================
               CREATE SELLER
            ========================= */

            Account seller = createAccount(
                    accountRepository,
                    passwordEncoder,
                    "seller",
                    "seller@gmail.com",
                    "0900000003"
            );

            seller.addRole(sellerRole);
            accountRepository.save(seller);


            /* =========================
               CREATE WAREHOUSE
            ========================= */

            Warehouse warehouse = warehouseRepository.findByName("Da Nang Warehouse")
                    .orElseGet(() -> {
                        Warehouse w = new Warehouse();
                        w.setName("Da Nang Warehouse");
                        w.setAddress("Da Nang");
                        return warehouseRepository.save(w);
                    });


            /* =========================
               CREATE ADMIN SHIPPER
            ========================= */


            Account adminShipper = createAccount(
                    accountRepository,
                    passwordEncoder,
                    "admin_shipper",
                    "admin_shipper@gmail.com",
                    "0900000004"
            );

            adminShipper.addRole(shipperRole);
            accountRepository.save(adminShipper);

            warehouseStaffRepository.findById(adminShipper.getAccountId())
                    .orElseGet(() -> {
                        WarehouseStaff staff = WarehouseStaff.builder()
                                .account(adminShipper)
                                .warehouse(warehouse)
                                .position(StaffPosition.ADMIN_SHIPPER)
                                .isActive(true)
                                .build();

                        return warehouseStaffRepository.save(staff);
                    });


            /* =========================
               CREATE SHIPPER
            ========================= */

            Account shipper = createAccount(
                    accountRepository,
                    passwordEncoder,
                    "shipper",
                    "shipper@gmail.com",
                    "0900000005"
            );

            shipper.addRole(shipperRole);
            accountRepository.save(shipper);

            warehouseStaffRepository.findById(shipper.getAccountId())
                    .orElseGet(() -> {
                        WarehouseStaff staff = WarehouseStaff.builder()
                                .account(shipper)
                                .warehouse(warehouse)
                                .position(StaffPosition.SHIPPER)
                                .isActive(true)
                                .build();

                        return warehouseStaffRepository.save(staff);
                    });


            /* =========================
               CREATE PRODUCT
            ========================= */

            if (productRepository.count() == 0) {

                Product product = new Product();
                product.setName("Laptop Gaming");
                product.setDescription("High performance gaming laptop");
                product.setPrice(20000000L);
                product.setStock(50L);
                product.setSeller(seller);

                productRepository.save(product);
            }

            System.out.println("===== DATA INITIALIZED =====");
        };
    }


    /* =========================
       HELPER METHODS
    ========================= */

    private Role createRole(IRoleRepository roleRepository, String roleName) {

        Role role = roleRepository.findByRoleName(roleName);

        if (role == null) {
            role = new Role();
            role.setRoleName(roleName);
            roleRepository.save(role);
        }

        return role;
    }


    private Account createAccount(IAccountRepository accountRepository,
                                  PasswordEncoder passwordEncoder,
                                  String username,
                                  String email,
                                  String phone) {

        return accountRepository.findByUsername(username)
                .orElseGet(() -> {

                    Account account = new Account();
                    account.setUsername(username);
                    account.setPassword(passwordEncoder.encode("123456"));
                    account.setEmail(email);
                    account.setPhone(phone);
                    account.setAddress("Da Nang");

                    return accountRepository.save(account);
                });
    }


}