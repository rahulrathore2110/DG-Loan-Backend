package com.xcel.loan.repository;

import com.xcel.loan.model.MainAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MainAccountDao extends JpaRepository<MainAccount, Integer> {
}
