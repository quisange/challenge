package org.develcorp.services.transaction.controller;

import org.develcorp.services.transaction.entities.RestResult;
import org.develcorp.services.transaction.entities.Transaction;
import org.develcorp.services.transaction.exception.ApiExceptionHandler;
import org.develcorp.services.transaction.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/movimientos")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    private ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler();

    @GetMapping (value = "/{id}")
    public ResponseEntity<Transaction> getTransaction(@RequestParam(name = "id", required = true) Long id){
        Transaction transaction = transactionService.getTransaction(id);

        if(transaction == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> listTransactions(Long accountId){
        List<Transaction> transactions;

        if (null == accountId){
            transactions = transactionService.listAllTransactions();
        } else {
            transactions = transactionService.findByAccountId(accountId);
        }

        if(transactions == null || transactions.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping(value = "/reporte")
    public ResponseEntity<List<Transaction>> reportList(@RequestParam(name = "cliente") Long accountId,
                                                        @RequestParam(name = "desde") String fromDate,
                                                        @RequestParam(name = "hasta") String toDate){
        List<Transaction> transactions;

        Date from;
        Date to;

        try {
            from = new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
            to = new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
        } catch (ParseException e) {
            throw new UnsupportedOperationException(e);
        }

        System.out.println("DESDE: " + from);
        System.out.println("HASTA: " + to);
        System.out.println("ACCO: " + accountId);
        transactions = transactionService.findByAccountIdAndDateBetween(accountId, from, to);

        if(transactions == null || transactions.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transactions);
    }

    @GetMapping (path = "/account")
    public ResponseEntity<Transaction> getLastTransactionByAccount(@RequestParam(name = "accountId", required = true) Long accoutId){
        Transaction transaction = transactionService.findByAccountIdLast(accoutId);

        if(transaction == null){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(transaction);
    }

    @PostMapping(value = "/apertura")
    public ResponseEntity<Transaction> openTransaction(@Valid @RequestBody Transaction transaction, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, apiExceptionHandler.formatMessage(result));
        }

        Transaction transactionDB = transactionService.openTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDB);
    }

    @PostMapping
    public ResponseEntity<RestResult> createTransaction(@Valid @RequestBody Transaction transaction, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, apiExceptionHandler.formatMessage(result));
        }

        Transaction transactionDB = transactionService.createTransaction (transaction);
        RestResult restResult = new RestResult();

        if(transactionDB == null){
            restResult.setMessage("El codigo de cuenta no existe");
            restResult.setObject(transactionDB);
        }else{
            restResult.setMessage("Creacion exitosa");
            restResult.setObject(transactionDB);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(restResult);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Transaction> updateTransaction(@PathVariable("id") Long id, @RequestBody Transaction transaction){
        transaction.setTransactionId(id);

        Transaction transactionDB = transactionService.updateTransaction(transaction);
        return ResponseEntity.ok(transactionDB);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable("id") Long id){
        Transaction transactionDelete = transactionService.deleteTransaction(id);
        return ResponseEntity.ok(transactionDelete);
    }
}