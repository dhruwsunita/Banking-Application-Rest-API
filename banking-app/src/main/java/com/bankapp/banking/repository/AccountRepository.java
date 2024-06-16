package com.bankapp.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankapp.banking.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
