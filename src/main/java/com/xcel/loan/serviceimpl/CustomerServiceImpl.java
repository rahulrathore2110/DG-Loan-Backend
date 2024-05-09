package com.xcel.loan.serviceimpl;

import com.xcel.loan.exception.ResourceNotFoundException;
import com.xcel.loan.model.Customer;
import com.xcel.loan.repository.CustomerDao;
import com.xcel.loan.repository.LoanDao;
import com.xcel.loan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private LoanDao loanDao;


    @Override
    public Customer createCustomer(Customer customer) {
        List<String> remark = new ArrayList<>();
        remark.add(LocalDateTime.now() + " : " + "New Customer Created");
        customer.setCreatedDate(LocalDateTime.now());
        customer.setLastUpdated(remark);
        Customer savedCustomer = this.customerDao.save(customer);
        return savedCustomer;
    }

    @Override
    public String editCustomer(Customer customer, String remark) {
        Customer findCustomer =
                this.customerDao.findById(customer.getCId()).orElseThrow(() -> new ResourceNotFoundException("User Not Found with id : " + customer.getCId()));

        findCustomer.setAlternateNumber(customer.getNumber());
        findCustomer.setAddress(customer.getAddress());
        findCustomer.setMonthlyIncome(customer.getMonthlyIncome());

        List<String> feedback = findCustomer.getLastUpdated();
        feedback.add(LocalDateTime.now() + " : " + remark);
        findCustomer.setLastUpdated(feedback);

        Customer updatedCustomer = this.customerDao.save(findCustomer);
        return "Your application updated successfully";
    }

    @Override
    public String deleteCustomerById(Integer cid) {
        Customer findCustomer =
                this.customerDao.findById(cid).orElseThrow(() -> new ResourceNotFoundException(
                        "User Not Found with id : " + cid));
        this.customerDao.deleteById(cid);

        return "Customer Deleted Successfully";
    }

    @Override
    public Customer getCustomerById(Integer cid) {

        Customer customer = this.customerDao.findById(cid).orElseThrow(() -> new ResourceNotFoundException("Not Customer Found With This Id : " + cid));

        return customer;
    }

    @Override
    public Customer getCustomerByNumber(Long number) {

        Customer customer = this.customerDao.findByNumber(number);

        if (customer == null) {
            throw new ResourceNotFoundException("No Customer Found With This Number : " + number);

        }
        return customer;
    }

    @Override
    public Customer getCustomerByAadhar(Long aadhar) {

        Customer customer = this.customerDao.findByAadhar(aadhar);
        if (customer == null) {
            throw new ResourceNotFoundException("No Customer Found With This Aadhar : " + aadhar);

        }
        return customer;
    }

    @Override
    public Customer getCustomerByPancard(String pancard) {
        Customer customer = this.customerDao.findByPancard(pancard);

        if (customer == null) {
            throw new ResourceNotFoundException("No Customer Found With This Pancard : " + pancard);

        }
        return customer;
    }


    @Override
    public List<Customer> getCustomerByName(String name) {
        List<Customer> customers = this.customerDao.findByNameContaining(name);

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("No Customer Found With This Name : " + name);

        }
        return customers;
    }


    @Override
    public List<Customer> allCustomer() {
        List<Customer> customers = this.customerDao.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        if (customers.isEmpty()) {
            throw new ResourceNotFoundException("No customer found");
        }
        return customers;
    }
}
