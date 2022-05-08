package org.develcorp.services.account.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.develcorp.services.account.entities.AccountType;
import org.develcorp.services.account.entities.ErrorMessage;
import org.develcorp.services.account.services.AccountTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/tipoCuentas")
public class AccountTypeController {

    @Autowired
    private AccountTypeService accountTypeService;

    @GetMapping
    public ResponseEntity<List<AccountType>> listAccountTypes(){
        List<AccountType> accountTypes = accountTypeService.listAllAccountTypes();
        return ResponseEntity.ok(accountTypes);
    }

    @GetMapping (value = "/{id}")
    public ResponseEntity<AccountType> getAccountType(@PathVariable("id") Integer id){
        return ResponseEntity.ok(accountTypeService.getAccountType(id));
    }

    @PostMapping
    public ResponseEntity<AccountType> createAccountType(@Valid @RequestBody AccountType accountType, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }

        AccountType accountTypeDB = accountTypeService.createAccountType (accountType);
        return ResponseEntity.status(HttpStatus.CREATED).body(accountTypeDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountType> updateAccountType(@PathVariable("id") Integer id, @RequestBody AccountType accountType){
        accountType.setAccountTypeId(id);

        AccountType accountTypeDB = accountTypeService.updateAccountType(accountType);
        return ResponseEntity.ok(accountTypeDB);
    }

    private String formatMessage(BindingResult result){
        List<Map<String, String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String, String> error = new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());

        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors)
                .build();

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";
        try{
            jsonString = mapper.writeValueAsString(errorMessage);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }
}