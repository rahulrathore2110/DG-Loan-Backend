package com.xcel.loan.repository;


import com.xcel.loan.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDao extends JpaRepository<Customer,Integer> {

    public List<Customer> findByNameContaining(String name);
    public Customer findByNumber(Long number);
    public Customer findByAadhar(Long aadhar);
    public Customer findByPancard(String pancard);



}
