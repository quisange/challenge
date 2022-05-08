package org.develcorp.services.account.services;

import org.develcorp.services.account.entities.AccountType;

import java.util.List;

public interface AccountTypeService {

    List<AccountType> listAllAccountTypes();

    AccountType getAccountType(Integer id);

    AccountType createAccountType(AccountType accountType);

    AccountType updateAccountType(AccountType accountType);
}
