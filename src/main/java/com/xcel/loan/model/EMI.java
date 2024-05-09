package com.xcel.loan.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EMI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int emiId;
    private LocalDate emiDate;
    private int emiAmount;
    private int emiPenalty;
    private Status emiStatus;
    @ManyToOne
    @JsonManagedReference
    private Loan loan;
}
