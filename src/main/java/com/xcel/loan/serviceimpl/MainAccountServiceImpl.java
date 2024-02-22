package com.xcel.loan.serviceimpl;

import com.xcel.loan.exception.ResourceNotFoundException;
import com.xcel.loan.model.MainAccount;
import com.xcel.loan.model.TransType;
import com.xcel.loan.model.TransactionHistory;
import com.xcel.loan.repository.MainAccountDao;
import com.xcel.loan.repository.TransactionHistoryDao;
import com.xcel.loan.service.MainAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MainAccountServiceImpl implements MainAccountService {
    @Autowired
    private MainAccountDao mainAccountDao;

    @Autowired
    private TransactionHistoryDao transactionHistoryDao;

    @Override
    public String initialiseAccount() {
        List<MainAccount> account = mainAccountDao.findAll();
        if (!account.isEmpty()) {
            throw new ResourceNotFoundException("Account is already activated");
        }

        MainAccount mainAccount = new MainAccount();
        mainAccount.setId(1);
        mainAccountDao.save(mainAccount);
        return "Account Active Successfully";
    }

    @Override
    public TransactionHistory toCapitalAc(Integer amount, String particular) {
        MainAccount mainAccount =
                this.mainAccountDao.findById(1).orElseThrow(() -> new ResourceNotFoundException(
                        "Please Activate your account before trigger any transaction related to " +
                                "account."));
        if (amount <= 0) {
            throw new ResourceNotFoundException("You can only initiate minimum 1 rupee transaction");
        }

        mainAccount.setMainAccount(mainAccount.getMainAccount() + amount);
        mainAccount.setCapital(mainAccount.getCapital() + amount);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setParticular(particular);
        transactionHistory.setTransType(TransType.CAPITAL);
        transactionHistory.setCreditAmount(amount);

        transactionHistoryDao.save(transactionHistory);
        mainAccountDao.save(mainAccount);

        return transactionHistory;
    }

    @Override
    public TransactionHistory fromDrawingAc(Integer amount, String particular) {
        MainAccount mainAccount =
                this.mainAccountDao.findById(1).orElseThrow(() -> new ResourceNotFoundException(
                        "Please Activate your account before trigger any transaction related to " +
                                "account."));
        if (mainAccount.getMainAccount() < amount) {
            throw new ResourceNotFoundException("Your capital balance is low to withdraw. you are" +
                    " trying to withdraw higher than your account balance");
        }
        mainAccount.setDrawing(mainAccount.getDrawing() + amount);
        mainAccount.setMainAccount(mainAccount.getMainAccount() - amount);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setParticular(particular);
        transactionHistory.setTransType(TransType.DRAWING);
        transactionHistory.setDebitAmount(amount);

        transactionHistoryDao.save(transactionHistory);
        mainAccountDao.save(mainAccount);

        return transactionHistory;
    }

    @Override
    public TransactionHistory toReceiptsAc(Integer amount, String particular) {
        MainAccount mainAccount =
                this.mainAccountDao.findById(1).orElseThrow(() -> new ResourceNotFoundException(
                        "Please Activate your account before trigger any transaction related to " +
                                "account."));

        mainAccount.setReceipts(mainAccount.getReceipts() + amount);
        mainAccount.setMainAccount(mainAccount.getMainAccount() + amount);
        mainAccount.setProfitLoss(mainAccount.getProfitLoss() + amount);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setParticular(particular);
        transactionHistory.setTransType(TransType.RECEIPT);
        transactionHistory.setCreditAmount(amount);

        transactionHistoryDao.save(transactionHistory);
        mainAccountDao.save(mainAccount);

        return transactionHistory;
    }

    @Override
    public TransactionHistory fromPaymentAc(Integer amount, String particular) {

        MainAccount mainAccount =
                this.mainAccountDao.findById(1).orElseThrow(() -> new ResourceNotFoundException(
                        "Please Activate your account before trigger any transaction related to " +
                                "account."));
        if (mainAccount.getMainAccount() < amount) {
            throw new ResourceNotFoundException("Your capital balance is low to do Payment. you " +
                    "are trying to Pay higher than your account balance");
        }
        mainAccount.setPayment(mainAccount.getPayment() + amount);
        mainAccount.setMainAccount(mainAccount.getMainAccount() - amount);
        mainAccount.setProfitLoss(mainAccount.getProfitLoss() - amount);

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionDate(LocalDateTime.now());
        transactionHistory.setParticular(particular);
        transactionHistory.setTransType(TransType.PAYMENT);
        transactionHistory.setDebitAmount(amount);

        transactionHistoryDao.save(transactionHistory);
        mainAccountDao.save(mainAccount);

        return transactionHistory;
    }

    @Override
    public MainAccount getAccountDetails() {
        MainAccount mainAccount =
                this.mainAccountDao.findById(1).orElseThrow(() -> new ResourceNotFoundException(
                        "Please Activate your account before trigger any transaction related to " +
                                "account."));

        return mainAccount;
    }

    @Override
    public MainAccount calculateProfit() {
        MainAccount mainAccount =
                this.mainAccountDao.findById(1).orElseThrow(() -> new ResourceNotFoundException(
                        "Please Activate your account before trigger any transaction related to " +
                                "account."));
        int profit =
                mainAccount.getReceipts() - mainAccount.getPayment();

        mainAccount.setProfitLoss(profit);
        mainAccountDao.save(mainAccount);

        return mainAccount;
    }
}
