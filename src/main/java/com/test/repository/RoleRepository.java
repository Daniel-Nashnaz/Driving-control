package com.test.repository;

import com.test.entity.Role;
import com.test.entity.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {


    Optional<Role> findByRuleName(RoleName roleName);
}