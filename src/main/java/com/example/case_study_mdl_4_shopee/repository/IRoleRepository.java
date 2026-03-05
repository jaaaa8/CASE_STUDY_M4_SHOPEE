package com.example.case_study_mdl_4_shopee.repository;


import com.example.case_study_mdl_4_shopee.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
        Role findByRoleName(String roleName);
}
