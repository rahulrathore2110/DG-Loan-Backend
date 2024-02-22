package com.xcel.loan.serviceimpl;

import com.xcel.loan.exception.ResourceNotFoundException;
import com.xcel.loan.model.*;
import com.xcel.loan.repository.CustomerDao;
import com.xcel.loan.repository.EMIDao;
import com.xcel.loan.repository.LoanDao;
import com.xcel.loan.service.LoanService;
import com.xcel.loan.service.MainAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanDao loanDao;
    @Autowired
    private CustomerDao customerDao;
    @Autowired
    private EMIDao emiDao;
    @Autowired
    private MainAccountService mainAccountService;


    /**
     * tenure 50days 10%, 75days 15%, 100days 20%
     */
    @Override
    public String createLoan(Loan loan, Integer cid) {
        Customer customer = this.customerDao.findById(cid).orElseThrow(() -> new ResourceNotFoundException("No customer find with this id : " + cid));

        int loanAmount = 0;

        if (loan.getTenure() == 50) {
            loanAmount = (int) (loan.getPrincipleAmount() + (loan.getPrincipleAmount() * 0.1));
            loan.setInterestRate(10);
        } else if (loan.getTenure() == 75) {
            loanAmount = (int) (loan.getPrincipleAmount() + (loan.getPrincipleAmount() * 0.15));
            loan.setInterestRate(15);
        } else if (loan.getTenure() == 100) {
            loanAmount = (int) (loan.getPrincipleAmount() + (loan.getPrincipleAmount() * 0.20));
            loan.setInterestRate(20);
        } else {
            throw new ResourceNotFoundException("Please choose tenure between 50, 75, 100 days.");
        }

        loan.setLoanDate(LocalDate.now());
        loan.setStatus(Status.PENDING);
        loan.setNoOfEmiRemaining(loan.getTenure());
        loan.setTotalLoanPayable(loanAmount);
        loan.setMonthlyEmi(loan.getTotalLoanPayable() / loan.getTenure());


        loan.setCustomer(customer);
        customer.getAllLoans().add(loan);

        this.loanDao.save(loan);

        return "Loan created successfully with Id : " + loan.getLid();
    }

    @Override
    public List<Loan> getAllLoans() {

        List<Loan> loanList = this.loanDao.findAll(Sort.by(Sort.Direction.DESC, "loanDate"));

        if (loanList.isEmpty()) {
            throw new ResourceNotFoundException("No Loan Found For This Moment.");
        }
        return loanList;
    }

    @Override
    public List<Loan> getRunningLoans() {
        List<Loan> loans = this.loanDao.findAll(Sort.by(Sort.Direction.DESC, "loanDate"));

        List<Loan> filterLoan = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStatus().equals(Status.RUNNING)) {
                filterLoan.add(loan);
            }

        }

        if (filterLoan.isEmpty()) {
            throw new ResourceNotFoundException("No Running Loan Found At This Moment");
        }
        return filterLoan;
    }

    @Override
    public List<Loan> getClosedLoans() {
        List<Loan> loans = this.loanDao.findAll(Sort.by(Sort.Direction.DESC, "loanDate"));

        List<Loan> filterLoan = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStatus().equals(Status.CLOSED) || loan.getStatus().equals(Status.SETTLED)) {
                filterLoan.add(loan);
            }

        }

        if (filterLoan.isEmpty()) {
            throw new ResourceNotFoundException("No Closed Loan Found At This Moment");
        }
        return filterLoan;
    }

    @Override
    public List<Loan> getApprovalLoans() {
        List<Loan> loans = this.loanDao.findAll(Sort.by(Sort.Direction.DESC, "loanDate"));

        List<Loan> filterLoan = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStatus().equals(Status.PENDING)) {
                filterLoan.add(loan);
            }

        }

        if (filterLoan.isEmpty()) {
            throw new ResourceNotFoundException("No Pending Approval Loan Found At This Moment");
        }
        return filterLoan;
    }

    @Override
    public List<Loan> getSettledLoans() {
        List<Loan> loans = this.loanDao.findAll(Sort.by(Sort.Direction.DESC, "loanDate"));

        List<Loan> filterLoan = new ArrayList<>();

        for (Loan loan : loans) {
            if (loan.getStatus().equals(Status.SETTLED)) {
                filterLoan.add(loan);
            }

        }

        if (filterLoan.isEmpty()) {
            throw new ResourceNotFoundException("No Settled Loan Found At This Moment");
        }
        return filterLoan;
    }

    @Override
    public String settleLoan(Integer lid, Integer amount) {
        Loan loan = this.loanDao.findById(lid).orElseThrow(() -> new ResourceNotFoundException("No Loan Found With This Id : " + lid));

        List<EMI> emis = loan.getAllEmi();
        if (!loan.getStatus().equals(Status.RUNNING)) {
            throw new ResourceNotFoundException("Only running loan have been able to settled");
        }

        for (EMI emi : emis) {
            emi.setEmiStatus(Status.SETTLED);
        }

        loan.setStatus(Status.SETTLED);
        loan.setAllEmi(emis);
        loan.setNoOfEmiRemaining(0);
        loan.setNoOfEmiPaid(loan.getTenure());

        System.out.println("Third1");

        this.loanDao.save(loan);
        System.out.println("Third2");

        TransactionHistory transactionHistory = mainAccountService.toReceiptsAc(amount, "Loan Settled against loanID : " + lid);
        System.out.println(transactionHistory);
        System.out.println("Four");

        return "Loan settled successfully with id : " + lid;
    }


    @Override
    public List<Loan> getLoanByNumber(Long number) {

        List<Loan> loans = this.loanDao.findAll();

        List<Loan> filterLoan = new ArrayList<>();

        for (Loan loan : loans) {
            if (Objects.equals(loan.getCustomer().getNumber(), number)) {
                filterLoan.add(loan);
            }

        }

        if (filterLoan.isEmpty()) {
            throw new ResourceNotFoundException("No Loan Found With This Number : " + number);
        }
        return filterLoan;
    }

    @Override
    public List<Loan> getLoanByAadhar(Long aadhar) {
        List<Loan> loans = this.loanDao.findAll();

        List<Loan> filterLoan = new ArrayList<>();

        for (Loan loan : loans) {
            if (Objects.equals(loan.getCustomer().getAadhar(), aadhar)) {
                filterLoan.add(loan);
            }
        }

        if (filterLoan.isEmpty()) {
            throw new ResourceNotFoundException("No Loan Found With This Aadhar Number : " + aadhar);
        }

        return filterLoan;
    }

    @Override
    public String approveLoan(Integer lid) {
        Loan loan = this.loanDao.findById(lid).orElseThrow(() -> new ResourceNotFoundException("No Loan Found With This Id : " + lid));
        if (!loan.getStatus().equals(Status.PENDING)) {
            throw new ResourceNotFoundException("You can approve only pending Loans. this loan is already approved");
        }
        int emiAmount = loan.getTotalLoanPayable() / loan.getTenure();
        List<EMI> emis = new ArrayList<>();

        for (int i = 0; i < loan.getTenure(); i++) {
            EMI emi = new EMI();
            emi.setEmiDate(loan.getLoanDate().plusDays(i));
            emi.setEmiAmount(emiAmount);
            emi.setEmiStatus(Status.PENDING);
            emi.setLoan(loan);

            emis.add(emi);
        }
        TransactionHistory transactionHistory = mainAccountService.fromPaymentAc(loan.getPrincipleAmount(), "Payment against approving a loan against loanId : " + lid);

        loan.setStatus(Status.RUNNING);

        loan.setAllEmi(emis);
        this.loanDao.save(loan);

        return "Loan Approved Successfully. You Can Check Loan Details With The Loan Id : " + lid;
    }
}
