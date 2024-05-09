package com.xcel.loan.service;

import com.xcel.loan.model.Customer;

import java.util.List;

public interface CustomerService {

    public Customer createCustomer(Customer customer);

    public String editCustomer(Customer customer, String remark);

    public String deleteCustomerById(Integer cid);

    public Customer getCustomerById(Integer cid);

    public Customer getCustomerByNumber(Long number);

    public Customer getCustomerByAadhar(Long aadhar);

    public Customer getCustomerByPancard(String pancard);

    public List<Customer> getCustomerByName(String name);

    public List<Customer> allCustomer();

}
