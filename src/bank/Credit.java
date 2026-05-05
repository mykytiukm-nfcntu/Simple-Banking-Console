package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

class CreditsDBManager {
	private static final String creditsFileName = "credits.txt";

	private void addCreditToFile(Credit credit, BufferedWriter bw) {
		try {
			String line = String.format("%s:%d:%d:%s",
				credit.username,
				credit.amount,
				credit.fee,
				credit.feeIncreaseDate.toString()
					);
			bw.write(line);
			bw.newLine();
		} catch(IOException e) {
			System.out.println("Failed writing credit to DB " + e);
		}
	}
		
	// Username:amount:fee:feeIncreaseDate
	Credit[] getUserCredits(Account account) {
		List<Credit> creditsList = new ArrayList<>();
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(creditsFileName))) {
			while ((line = br.readLine()) != null) {
				String[] data = line.split(":");

				if (data.length == 4) {
					String username = data[0];

					if (username.equals(account.name)) {
						int amount = Integer.parseInt(data[1]);
						int fee = Integer.parseInt(data[2]);
						LocalDate feeDate = LocalDate.parse(data[3]);
						creditsList.add(new Credit(account.name, amount, fee, feeDate));
					}
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading credits file: " + e.getMessage());
		}

		return creditsList.toArray(new Credit[0]);
	}

	void storeNewCredit(Credit credit) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(creditsFileName, true))) {
			addCreditToFile(credit, bw);
		} catch (IOException e) {
			System.err.println("Помилка запису у файл: " + e.getMessage());
		}
	}

	void updateCreditInfo() {
		List<Credit> creditsList = new ArrayList<>();
		String line;

		try (BufferedReader br = new BufferedReader(new FileReader(creditsFileName))) {
			while ((line = br.readLine()) != null) {
				String[] data = line.split(":");

				if (data.length == 4) {
					String username = data[0];

					int amount = Integer.parseInt(data[1]);
					int fee = Integer.parseInt(data[2]);
					LocalDate feeDate = LocalDate.parse(data[3]);

					LocalDate current = LocalDate.now();
					long days = feeDate.until(current, ChronoUnit.DAYS);

					if (days > 0) {
						fee *= 2;
						feeDate = current.plusDays(30);
					}

					creditsList.add(new Credit(username, amount, fee, feeDate));
				}
			}
		} catch (IOException e) {
			System.err.println("Error reading credits file: " + e.getMessage());
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(creditsFileName, false))) {
			for (Credit credit : creditsList) {
				addCreditToFile(credit, bw);
			}
		} catch (IOException e) {
			System.err.println("Помилка запису у файл: " + e.getMessage());
		}
	}
}

class CreditsManager {
	Account account;
	CreditsDBManager dbManager;

	CreditsManager(Account account) {
		this.account = account;
		this.dbManager = new CreditsDBManager();
	}

	Credit[] getCredits() {
		return dbManager.getUserCredits(account);
	}
}

public class Credit {
	String username;
	int amount;
	int fee;
	LocalDate feeIncreaseDate;

	public Credit(String username, int amount, int fee, LocalDate feeIncreaseDate) {
		this.username = username;
		this.amount = amount;
		this.fee = fee;
		this.feeIncreaseDate = feeIncreaseDate;
	}
}
