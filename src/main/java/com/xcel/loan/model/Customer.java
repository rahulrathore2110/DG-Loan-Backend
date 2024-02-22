package com.xcel.loan.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CId;
    private LocalDateTime createdDate;
    private List<String> lastUpdated;
    private String name;
    private String email;
    private Long number;
    private Long alternateNumber;
    private String address;
    private Long aadhar;
    private String pancard;
    private String businessName;
    private String businessAddress;
    private int monthlyIncome;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Loan> allLoans;


}
