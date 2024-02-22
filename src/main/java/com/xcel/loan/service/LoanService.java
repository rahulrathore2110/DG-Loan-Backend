package com.xcel.loan.service;

import com.xcel.loan.model.Loan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanService {

    public String createLoan(Loan loan, Integer cid);

    public List<Loan> getAllLoans();

    public List<Loan> getRunningLoans();

    public List<Loan> getClosedLoans();

    public List<Loan> getApprovalLoans();

    public List<Loan> getSettledLoans();

    public String settleLoan(Integer lid, Integer amount);

    public List<Loan> getLoanByNumber(Long number);

    public List<Loan> getLoanByAadhar(Long aadhar);

    public String approveLoan(Integer lid);

}
