package com.test.repository;

import com.test.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {



    List<Address> getAddressByUserID_Id(Integer userID);
}