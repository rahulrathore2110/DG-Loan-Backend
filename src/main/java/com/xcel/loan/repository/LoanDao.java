package com.xcel.loan.repository;

import com.xcel.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LoanDao extends JpaRepository<Loan,Integer> {

}
