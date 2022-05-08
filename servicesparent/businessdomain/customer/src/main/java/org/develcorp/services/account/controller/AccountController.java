package org.develcorp.services.account.controller;

import org.develcorp.services.account.entities.Account;
import org.develcorp.services.account.entities.Report;
import org.develcorp.services.account.entities.RestResult;
import org.develcorp.services.account.exception.ApiExceptionHandler;
import org.develcorp.services.account.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.UnknownHostException;
import java.util.List;

@RestController
@RequestMapping(value = "/cuentas")
public class AccountController {

    @Autowired
    private AccountService accountService;

    private ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();


    @GetMapping (value = "/{id}")
    public ResponseEntity<Account> getAccount(@PathVariable("id") Long id){
        Account account = accountService.getAccount(id);

        if(account == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping
    public ResponseEntity<List<Account>> listAccounts(){
        List<Account> accounts = accountService.listAllAccounts();

        if(accounts == null || accounts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }


    @GetMapping(value = "/cliente/{customerId}")
    public ResponseEntity<List<Account>> ListAccountCustomer(@PathVariable("customerId") Long customerId){
        List<Account> accounts = accountService.findByCustomerId(customerId);

        if(accounts == null || accounts.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(accounts);
    }

    @PostMapping
    public ResponseEntity<RestResult> createAccount(@Valid @RequestBody Account account, BindingResult result) throws UnknownHostException {
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, apiExceptionHandler.formatMessage(result));
        }

        Account accountDB = accountService.createAccount (account);
        RestResult restResult = new RestResult();

        if(accountDB == null){
            restResult.setMessage("El codigo de cliente no existe");
            restResult.setObject(accountDB);
        }else{
            restResult.setMessage("Creacion exitosa");
            restResult.setObject(accountDB);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(restResult);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Account> updateAccount(@PathVariable("id") Long id, @RequestBody Account account){
        account.setAccountId(id);

        Account accountDB = accountService.updateAccount(account);
        return ResponseEntity.ok(accountDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Account> deleteAccount(@PathVariable("id") Long id){
        Account accountDelete = accountService.deleteAccount(id);
        return ResponseEntity.ok(accountDelete);
    }

    @GetMapping(value = "/reporte")
    public ResponseEntity<List<Report>> generateReport(@RequestParam(name = "cliente") Long accountId,
                                                 @RequestParam(name = "desde") String fromDate,
                                                 @RequestParam(name = "hasta") String toDate){
        return ResponseEntity.ok(accountService.generateReport(accountId, fromDate, toDate));
    }
}