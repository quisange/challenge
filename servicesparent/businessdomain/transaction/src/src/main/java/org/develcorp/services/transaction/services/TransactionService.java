package org.develcorp.services.transaction.services;

import org.develcorp.services.transaction.entities.Transaction;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    List<Transaction> listAllTransactions();

    Transaction getTransaction(Long id);

    Transaction openTransaction(Transaction transaction);

    Transaction createTransaction(Transaction transaction);

    Transaction updateTransaction(Transaction transaction);

    Transaction deleteTransaction(Long id);

    List<Transaction> findByAccountId(Long accountId);

    Transaction findByAccountIdLast(Long accountId);

    List<Transaction> findByAccountIdAndDateAndTransactionType(Long accountId, Date date, Transaction.TransactionType transactionType);

    List<Transaction> findByAccountIdAndDateBetween(Long accountId, Date fromDate, Date toDate);
}