package org.develcorp.services.account.services;

import org.develcorp.services.account.entities.Account;
import org.develcorp.services.account.entities.Report;

import java.net.UnknownHostException;
import java.util.List;

public interface AccountService {

    List<Account> listAllAccounts();

    Account getAccount(Long id);

    Account createAccount(Account account) throws UnknownHostException;

    Account updateAccount(Account account);

    Account deleteAccount(Long id);

    List<Account> findByCustomerId(Long customerId);

    List<Report> generateReport(Long customerId, String fromDate, String toDate);

    Account findByAccountId(Long accountId);
}
