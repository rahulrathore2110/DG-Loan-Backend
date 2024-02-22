package com.xcel.loan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainAccount {
    @Id
    private int id;
    private int profitLoss;
    private int mainAccount;
    private int capital;
    private int drawing;
    private int payment;
    private int receipts;
}
