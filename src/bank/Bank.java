package bank;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

class FeeCalculator { //TODO: create separate DB for this rules
	int calculateCreditFee(Account user, int creditAmount) {
		if (creditAmount < 1000) {
			throw new RuntimeException("Credit is too small, cannot calculate");
		}
		if (creditAmount > 100000) {
			return (int)(creditAmount * 0.05);
		}
		return (int)(creditAmount * 0.02);
	}
	
	int calculateTransactionFee(Account from, Account to, int amount) {
		if (amount < 10) {
			return 1;
		}
		if (amount > 100000) {
			return (int)(amount * 0.005);
		}
		return (int)(amount * 0.01);
	}
	
	int calculateDeposit(Account user, int amount) {
		if (amount < 1000) {
			throw new RuntimeException("Deposit amount is too low");
		}
		
		if (amount > 100000) {
			return (int)(amount * 0.03);
		}
		return (int)(amount * 0.01);
	}
}

public class Bank extends TransactionParticipant {
	static final private String BANKUSERNAME = "Bank";
	final static String accountsAmountsDBName = "accounts.txt";
	private static Bank instance;

	private CreditsDBManager creditsManager;
	private FeeCalculator feeCalc;
	private Map<String, Account> accounts;

	private Bank() {
		super(BANKUSERNAME);
		creditsManager = new CreditsDBManager();
		this.feeCalc = new FeeCalculator();
		this.accounts = getAllAccounts();
	}

	public static Bank getInstance() {
		if (instance == null) {
			instance = new Bank();
		}
		return instance;
	}

	Map<String, Account> getAllAccounts() { // TODO: create separate DB class
		Map<String, Account> accounts = new HashMap<>();

		try (BufferedReader br = new BufferedReader(new FileReader(accountsAmountsDBName))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(":");

				if (parts.length == 2) {
					String name = parts[0].trim();
					int balance = Integer.parseInt(parts[1].trim());

					Account newAcc = new Account(name, balance);
					accounts.put(name, newAcc);
				}
			}
		} catch (IOException e) {
			System.err.println("Помилка читання файлу: " + e.getMessage());
		} catch (NumberFormatException e) {
			System.err.println("Помилка формату числа: " + e.getMessage());
		}

		return accounts;
	}

	int displayCreditFee(Account account, int amount) {
		return feeCalc.calculateCreditFee(account, amount);
	}

	Credit makeCredit(Account account, int amount) {
		Credit credit = new Credit(account.name, amount, displayCreditFee(account, amount), LocalDate.now().plusDays(30));

		creditsManager.storeNewCredit(credit);

		return credit;
	}

	private boolean transactionValidate(TransactionParticipant from, TransactionParticipant to, int amount) {
		if (from.balance < amount) {
			return false;
		}
		return true;
	}

	boolean sendMoney(TransactionParticipant from, String receiverName, int amount) {
		Account to = accounts.get(receiverName);
		if (transactionValidate(from, to, amount) == false) {
			return false;
		}
		dbManager.storeNewTransaction(from, to, amount);

		from.updateBalance(amount, false);
		to.updateBalance(amount, true);

		return true;
	}

}
