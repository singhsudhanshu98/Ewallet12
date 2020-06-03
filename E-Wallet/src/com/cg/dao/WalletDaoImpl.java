package com.cg.dao;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.cg.bean.Customer;

public class WalletDaoImpl implements WalletDao {

	private static Map<String, Customer> accounts = new HashMap<String, Customer>();
	private static Map<String, List<String>> transactions = new HashMap<String, List<String>>();
	private List<String> list;

	public WalletDaoImpl(Map<String, Customer> accounts, Map<String, List<String>> transactions, List<String> list) {
		super();
		WalletDaoImpl.accounts = accounts;
		WalletDaoImpl.transactions = transactions;
		this.list = list;
	}
	public WalletDaoImpl() {
		super();
	}
	@Override
	public boolean save(Customer customer) {
		accounts.put(customer.getMobileNo(), customer);
		return true;
	}
	@Override
	public Customer findOne(String mobileNo) {
		if (accounts.containsKey(mobileNo)) {
			return accounts.get(mobileNo);
		} else
			return null;
	}
	@Override
	public boolean saveTransactions(String mobileNo, String log) {
		if (transactions.get(mobileNo).add(log)) {
			return true;
		} else
			return false;
	}
	@Override
	public boolean createTransactionsList(String mobileNo, Customer customer) {
		list = new LinkedList<String>();
		list.add(new java.util.Date() + "\tAccount Created Sucessfully");
		transactions.put(mobileNo, list);
		return true;
	}
	@Override
	public List<String> getList(String mobileNo) {
		if (transactions.containsKey(mobileNo)) {
			return transactions.get(mobileNo);
		} else
			return null;
	}
}
