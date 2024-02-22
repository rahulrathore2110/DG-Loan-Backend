package com.xcel.loan.controller;

import com.xcel.loan.model.EMI;
import com.xcel.loan.service.EMIService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/emi")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EMIController {
    @Autowired
    private EMIService emiService;

    @PutMapping("/pay/{emiId}")
    public ResponseEntity<String> payEMIHandler(@Valid @PathVariable Integer emiId) {
        String createLoan = this.emiService.payEMI(emiId);
        return new ResponseEntity<>(createLoan, HttpStatus.ACCEPTED);
    }

    @PutMapping("/bounce/{emiId}")
    public ResponseEntity<String> bounceEMIHandler(@Valid @PathVariable Integer emiId) {
        String createLoan = this.emiService.bounceEMI(emiId);
        return new ResponseEntity<>(createLoan, HttpStatus.ACCEPTED);
    }

    @GetMapping("/getById/{emiId}")
    public ResponseEntity<EMI> getEMIByIdHandler(@Valid @PathVariable Integer emiId) {
        EMI createLoan = this.emiService.getEmiById(emiId);
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/allEmi")
    public ResponseEntity<List<EMI>> getAllEMIHandler() {
        List<EMI> createLoan = this.emiService.getAllEmi();
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/settled")
    public ResponseEntity<List<EMI>> getAllSettledEMIHandler() {
        List<EMI> createLoan = this.emiService.getSettledEMI();
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/allByDate/{emiDate}")
    public ResponseEntity<List<EMI>> getEMIByDateHandler(@PathVariable LocalDate emiDate) {
        List<EMI> createLoan = this.emiService.getEmiByDate(emiDate);
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/allWithPenalty")
    public ResponseEntity<List<EMI>> getEMIWithPenaltyHandler() {
        List<EMI> createLoan = this.emiService.getEmiWithPenalty();
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/allPending")
    public ResponseEntity<List<EMI>> getAllPendingEMIHandler() {
        List<EMI> createLoan = this.emiService.getAllPendingEmi();
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/pendingByDate/{emiDate}")
    public ResponseEntity<List<EMI>> getAllPendingEMIByDateHandler(@PathVariable LocalDate emiDate) {
        List<EMI> createLoan = this.emiService.getAllPendingEmiByDate(emiDate);
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/allPaid")
    public ResponseEntity<List<EMI>> getAllPayEMIHandler() {
        List<EMI> createLoan = this.emiService.getAllPayEmi();
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/paidByDate/{emiDate}")
    public ResponseEntity<List<EMI>> getAllPayEMIByDateHandler(@PathVariable LocalDate emiDate) {
        List<EMI> createLoan = this.emiService.getAllPayEmiByDate(emiDate);
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/allBounce")
    public ResponseEntity<List<EMI>> getAllBounceEMIHandler() {
        List<EMI> createLoan = this.emiService.getAllBounceEmi();
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/bounceByDate/{emiDate}")
    public ResponseEntity<List<EMI>> getAllBounceEMIByDateHandler(@PathVariable LocalDate emiDate) {
        List<EMI> createLoan = this.emiService.getAllBounceEmiByDate(emiDate);
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/loanId/{lid}")
    public ResponseEntity<List<EMI>> getAllEMIByIdHandler(@PathVariable Integer lid) {
        List<EMI> createLoan = this.emiService.getAllEmiWithLoanId(lid);
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }

    @GetMapping("/emiByNumber/{number}")
    public ResponseEntity<List<EMI>> getAllEMIByNumberHandler(@PathVariable Long number) {
        List<EMI> createLoan = this.emiService.getAllEmiWithNumber(number);
        return new ResponseEntity<>(createLoan, HttpStatus.OK);
    }
}
