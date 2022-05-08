package org.develcorp.services.account.services;

import org.develcorp.services.account.entities.AccountType;
import org.develcorp.services.account.repository.AccountTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountTypeServiceImpl implements AccountTypeService {
    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Override
    public List<AccountType> listAllAccountTypes() {
        return accountTypeRepository.findAll();
    }

    @Override
    public AccountType getAccountType(Integer id) {
        return accountTypeRepository.findById(id).orElse(null);
    }

    @Override
    public AccountType createAccountType(AccountType accountType) {
        return accountTypeRepository.save(accountType);
    }

    @Override
    public AccountType updateAccountType(AccountType account) {
        AccountType accountDB = getAccountType(account.getAccountTypeId());

        if(null == accountDB){
            return null;
        }

        return accountTypeRepository.save(account);
    }
}
