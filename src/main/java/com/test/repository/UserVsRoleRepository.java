package com.test.repository;

import com.test.entity.UserVsRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVsRoleRepository extends JpaRepository<UserVsRole, Integer> {
}