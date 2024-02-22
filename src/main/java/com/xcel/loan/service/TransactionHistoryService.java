package com.xcel.loan.service;

import com.xcel.loan.model.TransactionHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TransactionHistoryService {
    public List<TransactionHistory> getAllTransaction();

    public TransactionHistory getTransactionById(Integer id);
}
