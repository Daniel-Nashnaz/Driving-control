package com.test.repository;

import com.test.entity.UserVsAdmin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserVsAdminRepository extends JpaRepository<UserVsAdmin, Integer> {
}