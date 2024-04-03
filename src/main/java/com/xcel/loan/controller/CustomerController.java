package com.xcel.loan.controller;

import com.xcel.loan.model.Customer;
import com.xcel.loan.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/welcome")
    public String testHandler() {
        return "Your application working properly";
    }


    @PostMapping("/")
    public ResponseEntity<String> createCustomerHandler(@Valid @RequestBody Customer customer) {
        String createCustomer = this.customerService.createCustomer(customer);
        return new ResponseEntity<>(createCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/")
    public ResponseEntity<String> updateCustomerHandler(@Valid @RequestBody Customer customer,
                                                        @RequestParam String remark) {
        String updateCustomer = this.customerService.editCustomer(customer, remark);
        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/customerId/{id}")
    public ResponseEntity<String> deleteCustomerByIdHandler(@Valid @PathVariable Integer id) {
        String deleteCustomer = this.customerService.deleteCustomerById(id);
        return new ResponseEntity<>(deleteCustomer, HttpStatus.ACCEPTED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomerHandler() {
        List<Customer> updateCustomer = this.customerService.allCustomer();
        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<Customer> findCustomerByNumberHandler(@Valid @PathVariable Long number) {
        Customer updateCustomer = this.customerService.getCustomerByNumber(number);
        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }

    @GetMapping("/customerId/{id}")
    public ResponseEntity<Customer> findCustomerByIdHandler(@Valid @PathVariable Integer id) {
        Customer updateCustomer = this.customerService.getCustomerById(id);
        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }

    @GetMapping("/aadhar/{aid}")
    public ResponseEntity<Customer> findCustomerByAadharHandler(@Valid @PathVariable Long aid) {
        Customer updateCustomer = this.customerService.getCustomerByAadhar(aid);
        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }

    @GetMapping("/pancard/{pid}")
    public ResponseEntity<Customer> findCustomerByPancardHandler(@Valid @PathVariable String pid) {
        Customer updateCustomer = this.customerService.getCustomerByPancard(pid);
        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Customer>> findCustomerByNameHandler(@Valid @PathVariable String name) {
        List<Customer> updateCustomer = this.customerService.getCustomerByName(name);
        return new ResponseEntity<>(updateCustomer, HttpStatus.ACCEPTED);
    }
}
