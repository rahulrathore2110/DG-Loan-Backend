package com.xcel.loan.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int lid;
    private LocalDate loanDate;
    private int principleAmount;
    private int totalLoanPayable;
    private int interestRate;
    private int monthlyEmi;
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<EMI> allEmi;
    private int tenure;
    private int noOfEmiPaid;
    private int noOfEmiRemaining;
    private int noOfEmiBounce;
    private String loanManager;
    private Status status;
    private int penaltyAccount;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customerId")
    private Customer customer;
}
