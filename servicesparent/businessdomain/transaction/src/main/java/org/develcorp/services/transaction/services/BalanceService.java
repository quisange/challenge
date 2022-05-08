package org.develcorp.services.transaction.services;

import org.develcorp.services.transaction.entities.Balance;

public interface BalanceService {

    Balance getBalance(Long id);

    Balance createBalance(Balance balance);

    Balance updateBalance(Balance balance);
    Balance findByAccountId(Long accountId);
}