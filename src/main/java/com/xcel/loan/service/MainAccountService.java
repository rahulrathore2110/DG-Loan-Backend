package com.xcel.loan.service;

import com.xcel.loan.model.MainAccount;
import com.xcel.loan.model.TransactionHistory;
import org.springframework.stereotype.Service;

@Service
public interface MainAccountService {

    public String initialiseAccount();

    public TransactionHistory toCapitalAc(Integer amount, String particular);

    public TransactionHistory fromDrawingAc(Integer amount, String particular);

    public TransactionHistory toReceiptsAc(Integer amount, String particular);

    public TransactionHistory fromPaymentAc(Integer amount, String particular);

    public MainAccount getAccountDetails();

    public MainAccount calculateProfit();
}
