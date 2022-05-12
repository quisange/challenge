package org.develcorp.services.transaction.services;

import org.develcorp.services.transaction.entities.Balance;
import org.develcorp.services.transaction.entities.Transaction;
import org.develcorp.services.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceService balanceService;

    @Value("${dailyDebitLimit}")
    private BigDecimal max;

    @Override
    public List<Transaction> listAllTransactions() {
        return this.transactionRepository.findAll();
    }

    @Override
    public Transaction getTransaction(Long id) {
        return this.transactionRepository.findById(id).orElse(null);
    }

    public Transaction openTransaction(Transaction transaction) {
        Balance balance = balanceService.findByAccountId(transaction.getAccountId());

        balance = new Balance();
        balance.setAccountId(transaction.getAccountId());
        balance.setActualBalance(transaction.getValue());
        balance.setModifiedAt(new Timestamp(new Date().getTime()));
        balanceService.createBalance(balance);

        transaction.setBalance(balance.getActualBalance());
        transaction.setStatus(true);
        transaction.setMessage("Apertura de " + transaction.getValue());
        return this.transactionRepository.save(transaction);
        }

    @Override
    public Transaction createTransaction(Transaction transaction) {
        Balance balance = balanceService.findByAccountId(transaction.getAccountId());



        if (balance == null){
            return null;
        }else{
            switch (transaction.getTransactionType()) {
                case Deposito:
                    balance.setActualBalance(balance.getActualBalance().add(transaction.getValue()));

                    transaction.setBalance(balance.getActualBalance());
                    transaction.setStatus(true);
                    transaction.setMessage("Deposito de " + transaction.getValue());
                    break;
                case Retiro:
                    List<Transaction> transactions = findByAccountIdAndDateAndTransactionType(transaction.getAccountId(), new Date(), Transaction.TransactionType.Retiro);

                    BigDecimal total = new BigDecimal(0);

                    for (Transaction objeto : transactions) {
                        if (objeto.isStatus()) {
                            total = total.add(objeto.getValue().abs());
                        }
                    }

                    if (total.add(transaction.getValue()).compareTo(max) > 0) {
                        transaction.setBalance(balance.getActualBalance());
                        transaction.setStatus(false);
                        transaction.setMessage("Ha superado su limite diario de " + max + " dolares.");
                    } else {
                        if (balance.getActualBalance().compareTo(transaction.getValue()) < 0) {
                            transaction.setBalance(balance.getActualBalance());
                            transaction.setStatus(false);
                            transaction.setMessage("Saldo no disponible");
                        } else {
                            balance.setActualBalance(balance.getActualBalance().subtract(transaction.getValue()));

                            transaction.setBalance(balance.getActualBalance());
                            transaction.setStatus(true);
                            transaction.setMessage("Retiro de " + transaction.getValue());
                        }
                    break;
                }
            }
        }

        balanceService.updateBalance(balance);
        return this.transactionRepository.save(transaction);
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        Transaction transactionDB = getTransaction(transaction.getTransactionId());
        if (transactionDB == null) {
            return null;
        }
        return this.transactionRepository.save(transaction);
    }

    @Override
    public Transaction deleteTransaction(Long id) {
        Transaction transactionDB = getTransaction(id);
        if (transactionDB == null) {
            return null;
        }
        transactionDB.setStatus(false);
        return this.transactionRepository.save(transactionDB);
    }

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    @Override
    public Transaction findByAccountIdLast(Long accountId) {
        List<Transaction> transactions = findByAccountId(accountId);

        Comparator<Transaction> compareById =
                (Transaction o1, Transaction o2) -> o1.getTransactionId().compareTo( o2.getTransactionId() );

        Collections.sort(transactions, compareById);

        if (transactions.isEmpty()){
            return null;
        }
        return transactions.get(transactions.size() - 1);
    }

    @Override
    public List<Transaction> findByAccountIdAndDateAndTransactionType(Long accountId, Date date, Transaction.TransactionType transactionType) {
        return transactionRepository.findByAccountIdAndDateAndTransactionType(accountId, date, transactionType);
    }

    @Override
    public List<Transaction> findByAccountIdAndDateBetween(Long accountId, Date fromDate, Date toDate) {
        return transactionRepository.findByAccountIdAndDateBetween(accountId, fromDate, toDate);
    }
}