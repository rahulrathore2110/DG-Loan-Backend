package com.xcel.loan.repository;

import com.xcel.loan.model.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionHistoryDao extends JpaRepository<TransactionHistory, Integer> {
}
