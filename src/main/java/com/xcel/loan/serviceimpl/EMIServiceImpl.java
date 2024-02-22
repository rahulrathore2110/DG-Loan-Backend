package com.xcel.loan.serviceimpl;

import com.xcel.loan.exception.ResourceNotFoundException;
import com.xcel.loan.model.Customer;
import com.xcel.loan.model.EMI;
import com.xcel.loan.model.Loan;
import com.xcel.loan.model.Status;
import com.xcel.loan.repository.CustomerDao;
import com.xcel.loan.repository.EMIDao;
import com.xcel.loan.repository.LoanDao;
import com.xcel.loan.service.EMIService;
import com.xcel.loan.service.MainAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class EMIServiceImpl implements EMIService {
    @Autowired
    private EMIDao emiDao;

    @Autowired
    private LoanDao loanDao;

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private MainAccountService mainAccountService;

    @Override
    public String payEMI(Integer emiId) {
        EMI emi = this.emiDao.findById(emiId).orElseThrow(() -> new ResourceNotFoundException("No Emi Found With This Id : " + emiId));


        if (emi.getEmiStatus().equals(Status.PAY)) {
            throw new ResourceNotFoundException("EMI Is Already Paid.");
        }
        if (emi.getEmiStatus().equals(Status.SETTLED)) {
            throw new ResourceNotFoundException("EMI Is Already Settled.");
        }

        Loan loan =
                this.loanDao.findById(emi.getLoan().getLid()).orElseThrow(() -> new ResourceNotFoundException("No Loan Found"));
        loan.setNoOfEmiPaid(loan.getNoOfEmiPaid() + 1);
        loan.setNoOfEmiRemaining(loan.getNoOfEmiRemaining() - 1);

        emi.setEmiStatus(Status.PAY);
        mainAccountService.toReceiptsAc(emi.getEmiAmount(), "Receive against Emi Id : " + emiId + " And loan Id is : " + loan.getLid());
        this.loanDao.save(loan);
        this.emiDao.save(emi);

        return "EMI Pay successful";
    }

    @Override
    public String bounceEMI(Integer emiId) {
        EMI emi = this.emiDao.findById(emiId).orElseThrow(() -> new ResourceNotFoundException("No Emi Found With This Id : " + emiId));

        if (emi.getEmiDate().equals(LocalDate.now()) && emi.getEmiStatus().equals(Status.PENDING)) {

            Loan loan =
                    this.loanDao.findById(emi.getLoan().getLid()).orElseThrow(() -> new ResourceNotFoundException("No Loan Found"));


            int penalty = (int) (emi.getEmiAmount() * 0.05);
            loan.setNoOfEmiBounce(loan.getNoOfEmiBounce() + 1);
            loan.setPenaltyAccount(loan.getPenaltyAccount() + penalty);
            emi.setEmiStatus(Status.BOUNCE);
            emi.setEmiPenalty(emi.getEmiPenalty() + penalty);

            this.loanDao.save(loan);
            this.emiDao.save(emi);
            return "EMI Status Update Successfully";
        }

        return "You Can Bounce The EMI Only On DUe Date";
    }

    @Override
    public EMI getEmiById(Integer emiId) {
        return this.emiDao.findById(emiId).orElseThrow(() -> new ResourceNotFoundException("No Emi Found With This Id : " + emiId));
    }

    @Override
    public List<EMI> getAllEmi() {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }
        return emiList;
    }

    @Override
    public List<EMI> getSettledEMI() {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> settled = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiStatus().equals(Status.SETTLED)) {
                settled.add(e);
            }
        }

        if (settled.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        return settled;
    }

    @Override
    public List<EMI> getEmiByDate(LocalDate emiDate) {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> allEmi = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiDate().equals(emiDate)) {
                allEmi.add(e);
            }
        }

        if (allEmi.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found On This Date : " + emiDate);
        }

        return allEmi;
    }

    @Override
    public List<EMI> getEmiWithPenalty() {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> penaltyEMI = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiPenalty() != 0) {
                penaltyEMI.add(e);
            }
        }
        if (penaltyEMI.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }
        return penaltyEMI;
    }

    @Override
    public List<EMI> getAllPendingEmi() {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> pendingEMI = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiStatus().equals(Status.PENDING)) {
                pendingEMI.add(e);
            }
        }

        if (pendingEMI.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");

        }
        return pendingEMI;
    }

    @Override
    public List<EMI> getAllPendingEmiByDate(LocalDate emiDate) {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> pendingEMI = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiStatus().equals(Status.PENDING) && e.getEmiDate().equals(emiDate)) {
                pendingEMI.add(e);
            }
        }

        if (pendingEMI.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        return pendingEMI;
    }

    @Override
    public List<EMI> getAllPayEmi() {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> payEMI = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiStatus().equals(Status.PAY)) {
                payEMI.add(e);
            }
        }

        if (payEMI.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        return payEMI;
    }

    @Override
    public List<EMI> getAllPayEmiByDate(LocalDate emiDate) {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> payEMI = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiStatus().equals(Status.PAY) && e.getEmiDate().equals(emiDate)) {
                payEMI.add(e);
            }
        }

        if (payEMI.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        return payEMI;
    }

    @Override
    public List<EMI> getAllBounceEmi() {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> bounceEMI = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiStatus().equals(Status.BOUNCE)) {
                bounceEMI.add(e);
            }
        }

        if (bounceEMI.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        return bounceEMI;
    }

    @Override
    public List<EMI> getAllBounceEmiByDate(LocalDate emiDate) {
        List<EMI> emiList = this.emiDao.findAll();

        if (emiList.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        List<EMI> bounceEMI = new ArrayList<>();

        for (EMI e : emiList) {
            if (e.getEmiStatus().equals(Status.BOUNCE) && e.getEmiDate().equals(emiDate)) {
                bounceEMI.add(e);
            }
        }

        if (bounceEMI.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found At This Moment");
        }

        return bounceEMI;
    }

    @Override
    public List<EMI> getAllEmiWithLoanId(Integer lid) {
        Loan loan = this.loanDao.findById(lid).orElseThrow(() -> new ResourceNotFoundException("No Loan Found With This Id : " + lid));
        List<EMI> allEmi = loan.getAllEmi();

        return allEmi;
    }

    @Override
    public List<EMI> getAllEmiWithNumber(Long number) {
        Customer customer = this.customerDao.findByNumber(number);
        if (customer == null) {
            throw new ResourceNotFoundException("No Customer Found With This Number : " + number);
        }
        List<Loan> loans = customer.getAllLoans();

        if (loans.isEmpty()) {
            throw new ResourceNotFoundException("No Loan Found With This Number : " + number);
        }

        List<EMI> emis = new ArrayList<>();

        for (int i = 0; i < loans.size(); i++) {
            emis.addAll(loans.get(i).getAllEmi());
        }
        if (emis.isEmpty()) {
            throw new ResourceNotFoundException("No EMI Found With This Number : " + number);
        }


        return emis;
    }
}
