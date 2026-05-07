package bank;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class TransactionDBManager {
	final static String transactionsDBName = "transactions.txt";
	final static String accountsAmountsDBName = "accounts.txt";
	TransactionParticipant participant;

	TransactionDBManager(TransactionParticipant participant) {
		this.participant = participant;
	}

	void storeNewTransaction(TransactionParticipant from, TransactionParticipant to, int amount) {
		// Date from:<UserName> to:<UserName> amount:<amount>
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter logFormatter = DateTimeFormatter.ofPattern("'['yyyy-MM-dd HH:mm:ss']'");
		String date = now.format(logFormatter);

		String record = String.format("%s from:%s to:%s amount:%d\n", date, from.name, to.name, amount);

		Path path = Path.of(transactionsDBName);
		try {
			Files.writeString(path, record);
		} catch (Exception e) {
			System.err.println("Unable to store transaction " + e);
		}
	}

	int readCurrentAmount() {
		File file = new File(accountsAmountsDBName);

		if (!file.exists()) {
			throw new RuntimeException("File doesn't exist");
		}

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (line.isBlank()) {
					continue;
				}

				String[] parts = line.split(":");
				if (parts.length < 2) {
					continue;
				}

				String name = parts[0].trim();
				int amount = Integer.parseInt(parts[1].trim());

				if (name.equals(participant.name)) {
					return amount;
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("File " + accountsAmountsDBName + " not found: " + e.getMessage());
		}

		return 0;
	}

	void changeCurrentAmount(int newAmount) {
		Path path = Paths.get(accountsAmountsDBName);

		try {
			List<String> updatedLines = Files.lines(path).map(line -> {
				String[] parts = line.split(":");
				if (parts[0].equals(participant.name)) {
					return participant.name + ":" + newAmount;
				}
				return line;
			}).collect(Collectors.toList());

			Files.write(path, updatedLines);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	void initUserInDB() {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(accountsAmountsDBName, true))) {
			writer.write(this.participant.name + ":" + 0);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Write error: " + e.getMessage());
		}
	}
}

public abstract class TransactionParticipant {
	String name;
	int balance;
	TransactionDBManager dbManager; // TODO: move to Bank

	TransactionParticipant(String name) {
		this.name = name;
		dbManager = new TransactionDBManager(this);
		this.balance = this.dbManager.readCurrentAmount();
	}

	TransactionParticipant(String name, int amount) {
		this.name = name;
		dbManager = new TransactionDBManager(this);
		this.balance = amount;
	}
	
	void updateBalance(int transactionAmount, boolean isPlus) {
		if (isPlus == false && balance < transactionAmount) {
			throw new RuntimeException("Balance is not valid at this point");
		}

		if (isPlus) {
			balance += transactionAmount;
		} else {
			balance -= transactionAmount;
		}
		dbManager.changeCurrentAmount(balance);
	}

	void initUserInDB() {
		this.dbManager.initUserInDB();
	}
}
