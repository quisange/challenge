package org.develcorp.services.account.services;

import org.assertj.core.api.Assertions;
import org.develcorp.services.account.entities.Account;
import org.develcorp.services.account.entities.AccountType;
import org.develcorp.services.account.services.AccountService;
import org.develcorp.services.account.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.List;

@SpringBootTest
public class AccountServiceMockTest {

    @Autowired
    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    public void whenFindAll_thenReturnListAccounts() {

        Account account = Account.builder()
                .accountId(987654L)
                .accountType(AccountType.builder()
                        .accountTypeId(1)
                        .description("Ahorro")
                        .build())
                .initialBalance(new BigDecimal(100))
                .build();

        try {
            accountService.createAccount(account);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        List<Account> founds = accountService.listAllAccounts();

        Assertions.assertThat(founds.size()).isEqualTo(1);
    }
}
