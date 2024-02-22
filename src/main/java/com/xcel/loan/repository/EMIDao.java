package com.xcel.loan.repository;

import com.xcel.loan.model.EMI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface EMIDao extends JpaRepository<EMI,Integer> {

}
