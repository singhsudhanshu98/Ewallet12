package com.cg.ui;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import com.cg.bean.Customer;
import com.cg.exception.InsufficientBalanceException;
import com.cg.exception.InvalidInputException;
import com.cg.service.WalletService;
import com.cg.service.WalletServiceImpl;

public class Client {
	WalletService service;
	public Client() {
		service = new WalletServiceImpl();
	}

	Scanner sc = new Scanner(System.in);

	public boolean validAmount(BigDecimal amount) {
		if (amount.compareTo(new BigDecimal(0)) > 0) {
			return true;
		} else {
			throw new InvalidInputException(" : Invalid Amount. Please enter a POSITIVE amount. Thanks. :) \n");
		}
	}

	public void displayMenu() {
		System.out.println("----------MyWallet-----------\n\n");

		System.out.println("1)Create Account");
		System.out.println("2)Balance Enquiry");
		System.out.println("3)Deposit Amount");
		System.out.println("4)Withdraw Funds");
		System.out.println("5)Transfer");
		System.out.println("6)Exit");

		System.out.println("\nChoose Operation:");

		int choice = sc.nextInt();

		switch (choice) {

		case 1:
			createAccount();
			break;

		case 2:
			balance();
			break;

		case 3:
			deposit();
			break;

		case 4:
			withdraw();
			break;
			
		case 5:
			transfer();
			break;

		case 6:
			System.out.println("Are you sure you want to exit? (yes/no)");
			String reply = sc.next();

			if (reply.trim().equalsIgnoreCase("yes")) {
				exitWallet();
			}
			break;

		default:
			System.out.println("Invalid Operation! Please try again.");
			break;
		}
	}

	public void createAccount() {
		try {
			System.out.println("\nEnter Credentials to Create an Account with MyWallet");

			System.out.println("\nEnter Your Name: ");
			String name = sc.next();

			System.out.println("Enter Your Phone Number:");
			String mobileNo = sc.next();

			System.out.println("\nEnter Amount to Deposit:  ");
			BigDecimal amount = sc.nextBigDecimal();

			service.createAccount(name, mobileNo, amount);
			System.out.println("\nDear " + name.toUpperCase()
					+ ", Your MyWallet Account has been SUCCESSFULLY created. Your MyWallet ID is your Phone Number : "
					+ mobileNo + "\n");
			System.out.println("\nBalance in A/C: " + amount + "\n");
		} catch (InputMismatchException e) {
			System.out.println("invalid input");
		} catch (InvalidInputException e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
		}
	}

	public void balance() {
		System.out.println("\nEnter Phone Number: ");
		String mobileNo = sc.next();

		try {
			Customer c = service.showBalance(mobileNo);

			System.out.println("Balance in Your Wallet : " + c.getWallet().getBalance() + "\n");
		} catch (InvalidInputException e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
		}
	}

	public void deposit() {

		System.out.println("\nEnter Phone Number: ");
		String mobileNo = sc.next();

		System.out.println("\nEnter Amount to Deposit:  ");
		BigDecimal amount = sc.nextBigDecimal();

		if (validAmount(amount)) {
			try {
				Customer c = service.depositAmount(mobileNo, amount);

				System.out.println("Amount of" + amount + " deposited Successfully to A/C linked to Phone Number : "
						+ " xxxxxx" + mobileNo.substring(6) + "\n");
				System.out.println("Balance in A/C: " + c.getWallet().getBalance() + "\n");

			} catch (InvalidInputException e) {
				// e.printStackTrace();
				System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
			}
		} else {
			System.out.println("Invalid Amount. Please enter a POSITIVE amount. Thanks. :)");
		}
	}

	public void withdraw() {

		System.out.println("\nEnter Phone Number: ");
		String mobileNo = sc.next();

		System.out.println("\nEnter Amount to Withdraw:  ");
		BigDecimal amount = sc.nextBigDecimal();

		if (validAmount(amount)) {
			try {
				Customer c = service.withdrawAmount(mobileNo, amount);

				System.out.println("Amount " + amount + " has been debited from A/C linked to Phone Number : "
						+ " xxxxxx" + mobileNo.substring(6) + "\n");
				System.out.println("Balance in A/C: " + c.getWallet().getBalance() + "\n");

			} catch (InvalidInputException e) {
				// e.printStackTrace();
				System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
			} catch (InsufficientBalanceException e) {
				// e.printStackTrace();
				System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
			}
		} else {
			System.out.println("Invalid Amount. Please enter a POSITIVE amount. Thanks. :)");
		}
	}


	public void exitWallet() {
		System.out.println("\n--------- Thank you for using MyWallet services. Have a nice day! :) ----------- \n");
		System.exit(0);
	}
	
	public void transfer() {
		System.out.println("\nEnter Phone Number you want transfer: ");
		String mobileNo1 = sc.next();
		
		System.out.println("\nEnter your phone Number");
		String mobileNo2 = sc.next();

		System.out.println("\nEnter Amount to Pay:  ");
		BigDecimal amount = sc.nextBigDecimal();

		if (validAmount(amount)) {
			try {
				Customer c = service.transferMoney(mobileNo1, mobileNo2, amount);

				System.out.println("Amount of" + amount + " deposited Successfully to A/C linked to Phone Number : "
						+ " xxxxxx" + mobileNo1.substring(6) + "\n");
				System.out.println("Balance in A/C: " + c.getWallet().getBalance() + "\n");

			} catch (InvalidInputException e) {
				// e.printStackTrace();
				System.out.println("Something went WRONG : Reason : " + e.getMessage() + "\n");
			} catch (Exception e) {
				// e.printStackTrace();
				System.out.println("Something went WRONG: Please Try Again After Some Time. Thanks.\n");
			}
		} else {
			System.out.println("Invalid Amount. Please enter a POSITIVE amount. Thanks. :)");
		}
	}

	public static void main(String[] args) {

		Client c = new Client();
		while (true)
			c.displayMenu();
	}
}
