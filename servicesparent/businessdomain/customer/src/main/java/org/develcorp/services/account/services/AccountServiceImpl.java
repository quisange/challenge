package org.develcorp.services.account.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.develcorp.services.account.entities.Account;
import org.develcorp.services.account.entities.Report;
import org.develcorp.services.account.entities.Transaction;
import org.develcorp.services.account.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.TcpClient;

import java.math.BigDecimal;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    private final WebClient.Builder webClientBuilder;

    AccountServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    TcpClient tcpClient = TcpClient
            .create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    private <T> List<T> getTransactions(long accountId) {
        List<T> transactions = new ArrayList<>();

        try {
            WebClient client = webClientBuilder.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                    .baseUrl("http://businessdomain-transaction")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://businessdomain-transaction"))
                    .build();
            List<Object> block = client.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                            .path("/movimientos")
                            .queryParam("accountId", accountId)
                            .build())
                    .retrieve().bodyToFlux(Object.class).collectList().block();
            transactions = (List<T>) block;
        } catch (Exception e) {
            return transactions;
        }
        return transactions;
    }

    private <T> List<T> getTransactions(long accountId, String fromDate, String toDate) {
        List<T> transactions = new ArrayList<>();

        try {
            WebClient client = webClientBuilder.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                    .baseUrl("http://businessdomain-transaction")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://businessdomain-transaction"))
                    .build();
            List<Object> block = client.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                            .path("/movimientos/reporte")
                            .queryParam("cliente", accountId)
                            .queryParam("desde", fromDate)
                            .queryParam("hasta", toDate)
                            .build())
                    .retrieve().bodyToFlux(Object.class).collectList().block();
            transactions = (List<T>) block;
        } catch (Exception e) {
            return transactions;
        }
        return transactions;
    }
    private String getCustomerIdentification(long customerId) {
        String identification = null;
            WebClient client = webClientBuilder.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                    .baseUrl("http://businessdomain-customer")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .defaultUriVariables(Collections.singletonMap("url", "http://businessdomain-customer"))
                    .build();
            JsonNode block = client.method(HttpMethod.GET).uri("/clientes/" + customerId)
                    .retrieve().bodyToMono(JsonNode.class).block();

            if(block != null){
                identification = block.get("name").asText();
            }

        return identification;
    }

    private void createFirstTransaction(Account account) {
        Transaction transaction = new Transaction();
        transaction.setAccountId(account.getAccountId());
        transaction.setTransactionType(Transaction.TransactionType.Apertura);
        transaction.setValue(account.getInitialBalance());
        transaction.setDate(new Date());

        webClientBuilder.clientConnector(new ReactorClientHttpConnector(HttpClient.from(tcpClient)))
                .baseUrl("http://businessdomain-transaction")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://businessdomain-transaction"))
                .build();

        webClientBuilder.build()
                .post()
                .uri("/movimientos/apertura")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON )
                .body(BodyInserters.fromValue(transaction))
                .exchange()
                .block();
    }

    @Override
    public List<Account> listAllAccounts() {
        return this.accountRepository.findAll();
    }

    @Override
    public Account getAccount(Long id) {
        Account account = accountRepository.findById(id).orElse(null);

        if(account == null){
            return null;
        }

        account.setTransactions(getTransactions(account.getAccountId()));
        return account;
    }

    @Override
    public Account createAccount(Account account) throws UnknownHostException {
        Account accountDB = accountRepository.findByAccountId(account.getAccountId());
        if (accountDB != null){
            return  accountDB;
        }

        account.setStatus(true);
        accountDB = accountRepository.save(account);
        createFirstTransaction(account);
        return accountDB;
    }

    @Override
    public Account updateAccount(Account account) {
        Account accountDB = getAccount(account.getAccountId());
        if (accountDB == null) {
            return null;
        }
        return this.accountRepository.save(account);
    }

    @Override
    public Account deleteAccount(Long id) {
        Account accountDB = getAccount(id);
        if (accountDB == null) {
            return null;
        }
        accountDB.setStatus(false);
        return this.accountRepository.save(accountDB);
    }

    @Override
    public List<Report> generateReport(Long customerId, String fromDate, String toDate) {
        System.out.println("INGREESA AL METODO");
        List<Report> reports = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();

        Report report;
        String nameCustomer;
        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        if(accounts.isEmpty()){
            return null;
        }

        nameCustomer = getCustomerIdentification(customerId);

        for (Account account : accounts) {
            List<Transaction>transactions = mapper.convertValue(getTransactions(account.getAccountId(), fromDate, toDate), new TypeReference<List<Transaction>>() {});

            if(transactions != null){
                for (Transaction transaction: transactions) {
                    if (transaction != null) {
                        report = new Report();
                        report.setCustomer(nameCustomer);
                        report.setAccountNumber(account.getAccountId());
                        report.setAccountType(account.getAccountType().getDescription());
                        report.setStatus(account.isStatus());
                        report.setDate(transaction.getDate());
                        report.setInitialBalance(account.getInitialBalance());
                        if(account.getAccountType().getAccountTypeId() == 1){
                            report.setTransactionValue(transaction.getValue().multiply(new BigDecimal(-1)));
                        }else{
                        report.setTransactionValue(transaction.getValue());
                        }
                        report.setTransactionBalance(transaction.getBalance());

                        reports.add(report);
                    }
                }
            }
        }
        return reports;
    }

    @Override
    public List<Account> findByCustomerId(Long customerId) {
        return accountRepository.findByCustomerId(customerId);
    }

    @Override
    public Account findByAccountId(Long accountId) {
        return accountRepository.findByAccountId(accountId);
    }
}