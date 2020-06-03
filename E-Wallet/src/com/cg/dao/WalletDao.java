package com.cg.dao;

import java.util.List;

import com.cg.bean.Customer;

public interface WalletDao {
	public boolean save(Customer customer);
	public Customer findOne(String mobileNo);
	public boolean createTransactionsList(String mobileNo, Customer customer);
	public boolean saveTransactions(String mobileNo, String log);
	public List<String> getList(String mobileNo);
}
