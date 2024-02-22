package com.xcel.loan.serviceimpl;

import com.xcel.loan.exception.ResourceNotFoundException;
import com.xcel.loan.model.TransactionHistory;
import com.xcel.loan.repository.TransactionHistoryDao;
import com.xcel.loan.service.TransactionHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionHistoryServiceImpl implements TransactionHistoryService {

    @Autowired
    private TransactionHistoryDao transactionHistoryDao;

    @Override
    public List<TransactionHistory> getAllTransaction() {
//        List<TransactionHistory> transactionHistories = this.transactionHistoryDao.findAll();
        List<TransactionHistory> transactionHistories = this.transactionHistoryDao.findAll(Sort.by(Sort.Direction.DESC, "transactionDate"));

        if (transactionHistories.isEmpty()) {
            throw new ResourceNotFoundException("No Transaction found at this moment");
        }

        return transactionHistories;
    }

    @Override
    public TransactionHistory getTransactionById(Integer id) {
        TransactionHistory transactionHistory = this.transactionHistoryDao.findById(id).orElseThrow(() -> new ResourceNotFoundException("No transaction found with this id : " + id));
        return transactionHistory;
    }
}
