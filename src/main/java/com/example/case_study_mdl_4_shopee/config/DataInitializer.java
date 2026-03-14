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

            /* =========================
               CREATE PRODUCTS (DIVERSE DATA)
            ========================= */

            if (productRepository.count() <= 1) { // Kiểm tra nếu chỉ có 1 sản phẩm cũ thì tạo thêm

                // 1. Điện thoại iPhone 15 Pro Max
                Product p1 = new Product();
                p1.setName("iPhone 15 Pro Max 256GB");
                p1.setDescription("Chip A17 Pro mạnh mẽ, camera 48MP, khung viền Titan siêu bền.");
                p1.setPrice(29500000L);
                p1.setStock(20L);
                p1.setImageUrl("https://salt.tikicdn.com/cache/750x750/ts/product/6e/84/7b/6561e1b213b3429e2f4762c2f6d8955d.png");
                p1.setSeller(seller);
                productRepository.save(p1);

                // 2. Giày Sneaker Nike
                Product p2 = new Product();
                p2.setName("Giày Nike Air Jordan 1 Low");
                p2.setDescription("Thiết kế cổ điển, phong cách thể thao năng động, phù hợp mọi outfit.");
                p2.setPrice(3500000L);
                p2.setStock(15L);
                p2.setImageUrl("https://static.nike.com/a/images/t_PDP_1280_v1/f_auto,q_auto:eco/76a16377-66a9-4705-950c-e2f753549641/air-jordan-1-low-shoes-693S9k.png");
                p2.setSeller(seller);
                productRepository.save(p2);

                // 3. Tai nghe Bluetooth Sony
                Product p3 = new Product();
                p3.setName("Tai nghe Sony WH-1000XM5");
                p3.setDescription("Chống ồn chủ động đỉnh cao, âm thanh Hi-Res, pin lên đến 30 giờ.");
                p3.setPrice(6490000L);
                p3.setStock(10L);
                p3.setImageUrl("https://sony.scene7.com/is/image/sonyglobalsolutions/wh-1000xm5_b_primary-1?$categorypdpnav$&fmt=png-alpha");
                p3.setSeller(seller);
                productRepository.save(p3);

                // 4. Bàn phím cơ Akko
                Product p4 = new Product();
                p4.setName("Bàn phím cơ AKKO 3068B Multi-mode");
                p4.setDescription("Kết nối 3 chế độ, switch Akko Jelly Blue, keycap PBT bền bỉ.");
                p4.setPrice(1850000L);
                p4.setStock(30L);
                p4.setImageUrl("https://akkogear.com.vn/wp-content/uploads/2022/01/ban-phim-co-akko-3068b-plus-black-gold-01.jpg");
                p4.setSeller(seller);
                productRepository.save(p4);

                // 5. Chuột Logitech G Pro X
                Product p5 = new Product();
                p5.setName("Chuột Logitech G Pro X Superlight");
                p5.setDescription("Trọng lượng siêu nhẹ dưới 63g, cảm biến HERO 25K chính xác tuyệt đối.");
                p5.setPrice(3100000L);
                p5.setStock(25L);
                p5.setImageUrl("https://resource.logitechg.com/w_692,c_lpad,ar_4:3,q_auto,f_smart,dpr_1.0/content/dam/gaming/en/products/refreshed-g-pro-x-superlight/g-pro-x-superlight-white-gallery-1.png?v=1");
                p5.setSeller(seller);
                productRepository.save(p5);
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