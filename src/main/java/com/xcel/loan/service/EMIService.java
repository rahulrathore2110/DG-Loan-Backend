package com.xcel.loan.service;

import com.xcel.loan.model.EMI;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface EMIService {

    public String payEMI(Integer emiId);

    public String bounceEMI(Integer emiId);

    public EMI getEmiById(Integer emiId);

    public List<EMI> getAllEmi();

    public List<EMI> getSettledEMI();

    public List<EMI> getEmiByDate(LocalDate emiDate);

    public List<EMI> getEmiWithPenalty();

    public List<EMI> getAllPendingEmi();

    public List<EMI> getAllPendingEmiByDate(LocalDate emiDate);

    public List<EMI> getAllPayEmi();

    public List<EMI> getAllPayEmiByDate(LocalDate emiDate);

    public List<EMI> getAllBounceEmi();

    public List<EMI> getAllBounceEmiByDate(LocalDate emiDate);

    public List<EMI> getAllEmiWithLoanId(Integer lid);

    public List<EMI> getAllEmiWithNumber(Long number);


}
