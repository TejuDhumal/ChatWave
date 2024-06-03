package com.axis.chatwave.service.impl;


import com.axis.chatwave.entity.Account;
import com.axis.chatwave.repository.AccountRepository;
import com.axis.chatwave.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {


    @Autowired
    public AccountRepository accountRepository;



    @Override
    public Account save(Account account) {
        Account account1 = accountRepository.save(account);
        return account1;
    }
}
