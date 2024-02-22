package com.xcel.loan.controller;

import com.xcel.loan.model.TransactionHistory;
import com.xcel.loan.service.TransactionHistoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TransactionController {

    @Autowired
    private TransactionHistoryService transactionHistoryService;

    @GetMapping("/all")
    public ResponseEntity<List<TransactionHistory>> getAllTransactionHandler() {
        List<TransactionHistory> details = this.transactionHistoryService.getAllTransaction();
        return new ResponseEntity<>(details, HttpStatus.ACCEPTED);
    }

    @GetMapping("/byId/{id}")
    public ResponseEntity<TransactionHistory> getTransactionHandler(@Valid @PathVariable Integer id) {
        TransactionHistory details = this.transactionHistoryService.getTransactionById(id);
        return new ResponseEntity<>(details, HttpStatus.ACCEPTED);
    }
}
