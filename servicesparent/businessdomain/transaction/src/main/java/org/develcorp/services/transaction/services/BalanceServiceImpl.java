package org.develcorp.services.transaction.services;

import org.develcorp.services.transaction.entities.Balance;
import org.develcorp.services.transaction.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    private BalanceRepository balanceRepository;


    @Override
    public Balance getBalance(Long id) {
        return this.balanceRepository.findById(id).orElse(null);
    }

    @Override
    public Balance createBalance(Balance balance) {
        return balanceRepository.save(balance);
    }

    @Override
    public Balance updateBalance(Balance balance) {
        Balance balanceDB = getBalance(balance.getId());
        if (balanceDB == null) {
            return null;
        }
        return this.balanceRepository.save(balance);
    }

    @Override
    public Balance findByAccountId(Long accountId) {
        return balanceRepository.findByAccountId(accountId);
    }
}