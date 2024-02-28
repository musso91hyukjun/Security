package com.musso.pracSecurity.controller;

import com.musso.pracSecurity.model.Customer;
import com.musso.pracSecurity.repository.CustomerRepository;
import com.musso.pracSecurity.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
    private final CustomerService customerService;

    public LoginController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody Customer customer) {
        Customer saveCustomer = null;
        ResponseEntity response = null;
        try{
            saveCustomer = customerService.saveCustomer(customer);
            if(saveCustomer.getId() > 0 ) {
                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("success");
            }
        } catch(Exception e) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("fail");
        }
        return response;
    }
}
