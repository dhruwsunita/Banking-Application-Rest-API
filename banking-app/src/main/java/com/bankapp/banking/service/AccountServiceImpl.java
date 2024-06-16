package com.bankapp.banking.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bankapp.banking.dto.AccountDto;
import com.bankapp.banking.entity.Account;
import com.bankapp.banking.mapper.AccountMapper;
import com.bankapp.banking.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	private AccountRepository accountRepository;

	public AccountServiceImpl(AccountRepository accountRepository) {
		this.accountRepository = accountRepository;
	}


	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account = AccountMapper.mapToAccount(accountDto);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public AccountDto getAccountById(Long id) {
		Account account = accountRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exists"));
		return AccountMapper.mapToAccountDto(account);
	}


	@Override
	public AccountDto deposit(Long id, double amount) {
		Account account = accountRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exists"));
		
		double total = account.getBalance() + amount;
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		
		return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public AccountDto withdraw(Long id, double amount) {
		Account account = accountRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exists"));
		
		if(account.getBalance() < amount) {
			throw new RuntimeException("Insufficient amount");	
		}
		
		double total = account.getBalance() - amount;
		account.setBalance(total);
		Account savedAccount = accountRepository.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public List<AccountDto> getAllAccounts() {
	   List<Account> accounts =  accountRepository.findAll();
	   return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account))
	    .collect(Collectors.toList());
	}


	@Override
	public void deleteAccount(Long id) {
		Account account = accountRepository
				.findById(id)
				.orElseThrow(() -> new RuntimeException("Account does not exists"));
		
		accountRepository.deleteById(id);
		
	}

}
