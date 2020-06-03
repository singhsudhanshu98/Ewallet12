package com.cg.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.cg.bean.Customer;
import com.cg.bean.Wallet;
import com.cg.exception.InsufficientBalanceException;
import com.cg.exception.InvalidInputException;
import com.cg.dao.WalletDao;
import com.cg.dao.WalletDaoImpl;

public class WalletServiceImpl implements WalletService {
	WalletDao dao;

	public WalletServiceImpl() {
		dao = new WalletDaoImpl();
	}

	public WalletServiceImpl(Map<String, Customer> data) {
		super();
	}

	public boolean isValidName(String name) {
		if (((name != null) && name.matches("[A-Z][a-z]+"))) {
			return true;
		} else {
			throw new InvalidInputException(" : Name cannot be NULL (or) INVALID Name.");
		}
	}

	public boolean isValidNumber(String mobileNo) {
		if ((mobileNo != null) && (mobileNo.matches("[4-9][0-9]{9}"))) {
			return true;
		} else {
			throw new InvalidInputException(" : MobileNo cannot be NULL (or) INVALID Number.");
		}
	}

	public boolean validAmount(BigDecimal amount) {
		if (amount != null && amount.compareTo(new BigDecimal(0)) > 0) {
			return true;
		} else {
			throw new InvalidInputException(" : Amount cannot be NEGATIVE.");
		}
	}

	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		if (isValidName(name) && isValidNumber(mobileNo) && validAmount(amount)) {
			if (dao.findOne(mobileNo) != null) {
				throw new InvalidInputException(
						" : Account linked to the Entered Number ALREADY EXISTS: Please try again.\n");
			} else {
				Wallet w = new Wallet(amount);
				Customer c = new Customer(name, mobileNo, w);
				boolean flag = dao.createTransactionsList(mobileNo, c);
				boolean flag1 = dao.save(c);
				if (flag && flag1) {
					return c;
				} else {
					throw new InvalidInputException(" : Try at a later time. Thank you.\n");
				}
			}
		} else {
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer showBalance(String mobileNo) throws InvalidInputException {
		if (isValidNumber(mobileNo)) {
			if (dao.findOne(mobileNo) == null) {
				throw new InvalidInputException(
						" : A/C with Entered Phone Number Does NOT EXIST: Please try again. \n");
			} else {
				String log = new java.util.Date() + "\tViewed Balance.";
				dao.saveTransactions(mobileNo, log);
				return dao.findOne(mobileNo);
			}
		} else {
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		if (isValidNumber(mobileNo) && validAmount(amount)) {
			if (dao.findOne(mobileNo) != null) {
				BigDecimal b = dao.findOne(mobileNo).getWallet().getBalance();
				b = b.add(amount);
				dao.findOne(mobileNo).getWallet().setBalance(b);
				String log = new java.util.Date() + "\tAmount of" + amount + " Credited to A/C. Balance in A/C : " + b;
				dao.saveTransactions(mobileNo, log);
				return dao.findOne(mobileNo);
			} else {
				throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
			}
		} else {
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount)
			throws InvalidInputException, InsufficientBalanceException {
		if (isValidNumber(mobileNo) && validAmount(amount)) {
			if (dao.findOne(mobileNo) != null) {
				BigDecimal b = dao.findOne(mobileNo).getWallet().getBalance();
				int i = b.compareTo(amount);
				if (i >= 0) {
					b = b.subtract(amount);
					dao.findOne(mobileNo).getWallet().setBalance(b);
					String log = new java.util.Date() + "\tAmount of" + amount + " Debited from A/C. Balance in A/C : "
							+ b;
					dao.saveTransactions(mobileNo, log);
					return dao.findOne(mobileNo);
				} else {
					throw new InsufficientBalanceException(
							" : Insufficient Balance : Please Check Balance and Try Again. Thanks.\n");
				}
			} else {
				throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
			}
		} else {
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

	@Override
	public Customer transferMoney(String mobileNo1, String mobileNo2, BigDecimal amount) {

		if (isValidNumber(mobileNo1) && validAmount(amount)) {
			if (dao.findOne(mobileNo1) != null) {
				BigDecimal b = dao.findOne(mobileNo1).getWallet().getBalance();
				b = b.add(amount);
				dao.findOne(mobileNo1).getWallet().setBalance(b);
				BigDecimal c = dao.findOne(mobileNo2).getWallet().getBalance();
				c = c.subtract(amount);
				dao.findOne(mobileNo2).getWallet().setBalance(c);
				String log = new java.util.Date() + "\tAmount of" + amount + " Credited to A/C. Balance in A/C : " + b;
				dao.saveTransactions(mobileNo1, log);
				return dao.findOne(mobileNo1);
			} else {
				throw new InvalidInputException(" : A/C with Entered Phone Number Does NOT EXIST : \n");
			}
		} else {
			throw new InvalidInputException(" : INVALID Input : ");
		}
	}

}
