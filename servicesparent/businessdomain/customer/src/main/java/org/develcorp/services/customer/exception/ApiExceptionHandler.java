package org.develcorp.services.customer.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.develcorp.services.customer.common.ExceptionResponse;
import org.develcorp.services.customer.controller.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleUnknownHostException(Exception ex) {
        ExceptionResponse response = new ExceptionResponse("Error de conexion","erorr-404", ex.getMessage());
        return new ResponseEntity(response, HttpStatus.PARTIAL_CONTENT);
    }


    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ExceptionResponse> handleBusinessRuleException(BusinessRuleException ex) {
        ExceptionResponse response = new ExceptionResponse("Error de validacion", ex.getCode(), ex.getMessage());
        return new ResponseEntity(response, HttpStatus.PARTIAL_CONTENT);
    }

    public String formatMessage(BindingResult result){
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
