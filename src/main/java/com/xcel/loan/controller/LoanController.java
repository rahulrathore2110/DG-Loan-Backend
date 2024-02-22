package com.xcel.loan.controller;

import com.xcel.loan.model.Loan;
import com.xcel.loan.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LoanController {
    @Autowired
    private LoanService loanService;

    @PostMapping("/{cid}")
    public ResponseEntity<String> createLoanHandler(@Valid @RequestBody Loan loan, @PathVariable Integer cid) {
        String createLoan = this.loanService.createLoan(loan, cid);
        return new ResponseEntity<>(createLoan, HttpStatus.CREATED);
    }

    @GetMapping("/number/{number}")
    public ResponseEntity<List<Loan>> getLoanByNumberHandler(@PathVariable Long number) {
        List<Loan> allLoans = this.loanService.getLoanByNumber(number);
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @GetMapping("/aadhar/{aadhar}")
    public ResponseEntity<List<Loan>> getLoanByAadharHandler(@PathVariable Long aadhar) {
        List<Loan> allLoans = this.loanService.getLoanByAadhar(aadhar);
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Loan>> getAllLoanHandler() {
        List<Loan> allLoans = this.loanService.getAllLoans();
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @GetMapping("/running")
    public ResponseEntity<List<Loan>> getAllRunningLoanHandler() {
        List<Loan> allLoans = this.loanService.getRunningLoans();
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @GetMapping("/closed")
    public ResponseEntity<List<Loan>> getAllClosedLoanHandler() {
        List<Loan> allLoans = this.loanService.getClosedLoans();
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @GetMapping("/approval")
    public ResponseEntity<List<Loan>> getAllApprovalLoanHandler() {
        List<Loan> allLoans = this.loanService.getApprovalLoans();
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @GetMapping("/settled")
    public ResponseEntity<List<Loan>> getAllSettledLoanHandler() {
        List<Loan> allLoans = this.loanService.getSettledLoans();
        return new ResponseEntity<>(allLoans, HttpStatus.OK);
    }

    @PostMapping("/approve/{lid}")
    public ResponseEntity<String> approveLoanHandler(@PathVariable Integer lid) {
        String approveLoan = this.loanService.approveLoan(lid);
        return new ResponseEntity<>(approveLoan, HttpStatus.CREATED);
    }

    @PutMapping("/settle/{lid}/{amount}")
    public ResponseEntity<String> settleLoanHandler(@PathVariable Integer lid, @PathVariable Integer amount) {
        String approveLoan = this.loanService.settleLoan(lid, amount);
        return new ResponseEntity<>(approveLoan, HttpStatus.ACCEPTED);
    }


}
