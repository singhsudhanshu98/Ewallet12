package com.cg.service;

import java.math.BigDecimal;
import java.util.List;

import com.cg.bean.Customer;
import com.cg.exception.InsufficientBalanceException;
import com.cg.exception.InvalidInputException;

public interface WalletService {
	public Customer createAccount(String name, String mobileno, BigDecimal amount) throws InvalidInputException;
	public Customer showBalance(String mobileno) throws InvalidInputException;


	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException;
	public Customer withdrawAmount(String mobileNo, BigDecimal amount)
			throws InvalidInputException, InsufficientBalanceException;
	public Customer transferMoney(String mobileNo1, String mobileNo2, BigDecimal amount);

}
