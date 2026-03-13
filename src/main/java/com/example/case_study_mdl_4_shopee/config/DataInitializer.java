package com.example.case_study_mdl_4_shopee.config;

import com.example.case_study_mdl_4_shopee.entity.*;
import com.example.case_study_mdl_4_shopee.enums.StaffPosition;
import com.example.case_study_mdl_4_shopee.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(
            IAccountRepository accountRepository,
            IRoleRepository roleRepository,
            IWarehouseRepository warehouseRepository,
            IWarehouseStaffRepository warehouseStaffRepository,
            IProductRepository productRepository,
            PasswordEncoder passwordEncoder,
            ILocationRepository locationRepository,
            ICityRepository cityRepository) {

        return args -> {

            /* =========================
               CREATE ROLES
            ========================= */

            Role adminRole = createRole(roleRepository, "ROLE_ADMIN");
            Role customerRole = createRole(roleRepository, "ROLE_CUSTOMER");
            Role sellerRole = createRole(roleRepository, "ROLE_SELLER");
            Role shipperRole = createRole(roleRepository, "ROLE_SHIPPER");


            /* =========================
               CREATE LOCATION
            ========================= */

            Location north = createLocation(locationRepository, "North");
            Location mid = createLocation(locationRepository, "Mid");
            Location south = createLocation(locationRepository, "South");


            /* =========================
               CREATE CITIES
            ========================= */

            if(cityRepository.count() == 0){

                List<City> cities = new ArrayList<>();

                // NORTH
                cities.add(new City(null,"Hà Nội",north));
                cities.add(new City(null,"Hải Phòng",north));
                cities.add(new City(null,"Quảng Ninh",north));
                cities.add(new City(null,"Bắc Ninh",north));
                cities.add(new City(null,"Bắc Giang",north));
                cities.add(new City(null,"Hải Dương",north));
                cities.add(new City(null,"Hưng Yên",north));
                cities.add(new City(null,"Thái Bình",north));
                cities.add(new City(null,"Nam Định",north));
                cities.add(new City(null,"Ninh Bình",north));
                cities.add(new City(null,"Hà Nam",north));
                cities.add(new City(null,"Phú Thọ",north));
                cities.add(new City(null,"Vĩnh Phúc",north));
                cities.add(new City(null,"Lào Cai",north));
                cities.add(new City(null,"Yên Bái",north));
                cities.add(new City(null,"Tuyên Quang",north));
                cities.add(new City(null,"Hà Giang",north));
                cities.add(new City(null,"Cao Bằng",north));
                cities.add(new City(null,"Bắc Kạn",north));
                cities.add(new City(null,"Lạng Sơn",north));
                cities.add(new City(null,"Thái Nguyên",north));
                cities.add(new City(null,"Sơn La",north));
                cities.add(new City(null,"Điện Biên",north));
                cities.add(new City(null,"Lai Châu",north));
                cities.add(new City(null,"Hòa Bình",north));

                // MID
                cities.add(new City(null,"Thanh Hóa",mid));
                cities.add(new City(null,"Nghệ An",mid));
                cities.add(new City(null,"Hà Tĩnh",mid));
                cities.add(new City(null,"Quảng Bình",mid));
                cities.add(new City(null,"Quảng Trị",mid));
                cities.add(new City(null,"Thừa Thiên Huế",mid));
                cities.add(new City(null,"Đà Nẵng",mid));
                cities.add(new City(null,"Quảng Nam",mid));
                cities.add(new City(null,"Quảng Ngãi",mid));
                cities.add(new City(null,"Bình Định",mid));
                cities.add(new City(null,"Phú Yên",mid));
                cities.add(new City(null,"Khánh Hòa",mid));
                cities.add(new City(null,"Ninh Thuận",mid));
                cities.add(new City(null,"Bình Thuận",mid));
                cities.add(new City(null,"Kon Tum",mid));
                cities.add(new City(null,"Gia Lai",mid));
                cities.add(new City(null,"Đắk Lắk",mid));
                cities.add(new City(null,"Đắk Nông",mid));
                cities.add(new City(null,"Lâm Đồng",mid));

                // SOUTH
                cities.add(new City(null,"TP Hồ Chí Minh",south));
                cities.add(new City(null,"Bình Dương",south));
                cities.add(new City(null,"Đồng Nai",south));
                cities.add(new City(null,"Bà Rịa Vũng Tàu",south));
                cities.add(new City(null,"Tây Ninh",south));
                cities.add(new City(null,"Bình Phước",south));

                cities.add(new City(null,"Long An",south));
                cities.add(new City(null,"Tiền Giang",south));
                cities.add(new City(null,"Bến Tre",south));
                cities.add(new City(null,"Trà Vinh",south));
                cities.add(new City(null,"Vĩnh Long",south));
                cities.add(new City(null,"Đồng Tháp",south));
                cities.add(new City(null,"An Giang",south));
                cities.add(new City(null,"Kiên Giang",south));
                cities.add(new City(null,"Hậu Giang",south));
                cities.add(new City(null,"Sóc Trăng",south));
                cities.add(new City(null,"Bạc Liêu",south));
                cities.add(new City(null,"Cà Mau",south));
                cities.add(new City(null,"Cần Thơ",south));

                cityRepository.saveAll(cities);
            }

            /* =========================
               CREATE ADMIN
            ========================= */

            Account admin = createAccount(
                    accountRepository,
                    cityRepository,
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
                    cityRepository,
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
                    cityRepository,
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
                        w.setLocation(mid);
                        return warehouseRepository.save(w);
                    });


            /* =========================
               CREATE ADMIN SHIPPER
            ========================= */


            Account adminShipper = createAccount(
                    accountRepository,
                    cityRepository,
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
                    cityRepository,
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
                                  ICityRepository cityRepository,
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

                    City city = cityRepository.findByName("Đà Nẵng");

                    if(city == null){
                        throw new RuntimeException("City not found");
                    }

                    account.setCity(city);

                    return accountRepository.save(account);
                });
    }

    private Location createLocation(ILocationRepository locationRepository, String name) {

        Location location = locationRepository.findByName(name);

        if (location == null) {

            location = new Location();
            location.setName(name);

            locationRepository.save(location);
        }

        return location;
    }

}