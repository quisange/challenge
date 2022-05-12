package org.develcorp.services.transaction.repository;

import org.develcorp.services.transaction.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> findByAccountIdAndDateAndTransactionType
            (Long accountId, Date date, Transaction.TransactionType transactionType);

    List<Transaction> findByAccountIdAndDateBetween
            (Long accountId, Date fromDate, Date toDate);
}
