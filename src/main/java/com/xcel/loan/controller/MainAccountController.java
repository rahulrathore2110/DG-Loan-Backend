package com.xcel.loan.controller;


import com.xcel.loan.model.MainAccount;
import com.xcel.loan.model.TransactionHistory;
import com.xcel.loan.service.MainAccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mainAccount")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainAccountController {

    @Autowired
    private MainAccountService mainAccountService;

    @PostMapping("/active")
    public ResponseEntity<String> activeAccountHandler() {
        String activeAccount = this.mainAccountService.initialiseAccount();
        return new ResponseEntity<>(activeAccount, HttpStatus.CREATED);
    }

    @PutMapping("/capital")
    public ResponseEntity<TransactionHistory> CapitalHandler(@Valid @RequestParam Integer amount,
                                                             @RequestParam String particular) {
        TransactionHistory history = this.mainAccountService.toCapitalAc(amount, particular);
        return new ResponseEntity<>(history, HttpStatus.ACCEPTED);
    }

    @PutMapping("/drawing")
    public ResponseEntity<TransactionHistory> drawingHandler(@Valid @RequestParam Integer amount,
                                                             @RequestParam String particular) {
        TransactionHistory history = this.mainAccountService.fromDrawingAc(amount, particular);
        return new ResponseEntity<>(history, HttpStatus.ACCEPTED);
    }

    @PutMapping("/payment")
    public ResponseEntity<TransactionHistory> paymentHandler(@Valid @RequestParam Integer amount,
                                                             @RequestParam String particular) {
        TransactionHistory history = this.mainAccountService.fromPaymentAc(amount, particular);
        return new ResponseEntity<>(history, HttpStatus.ACCEPTED);
    }

    @PutMapping("/receipt")
    public ResponseEntity<TransactionHistory> receiptHandler(@Valid @RequestParam Integer amount,
                                                             @RequestParam String particular) {
        TransactionHistory history = this.mainAccountService.toReceiptsAc(amount, particular);
        return new ResponseEntity<>(history, HttpStatus.ACCEPTED);
    }

    @GetMapping("/accountDetails")
    public ResponseEntity<MainAccount> getAccountDetailsHandler() {
        MainAccount details = this.mainAccountService.getAccountDetails();
        return new ResponseEntity<>(details, HttpStatus.ACCEPTED);
    }

    @PutMapping("/calculateProfit")
    public ResponseEntity<MainAccount> calculateProfitHandler() {
        MainAccount details = this.mainAccountService.calculateProfit();
        return new ResponseEntity<>(details, HttpStatus.ACCEPTED);
    }


}
