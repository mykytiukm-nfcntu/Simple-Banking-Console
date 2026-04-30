package bank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class AccountDBManager {
	static String fileStorageName = "users.txt";

	String readPassword(String username) {
		File file = new File(fileStorageName);

		if (!file.exists()) {
			System.out.println("Users file doesn't exist");
			String currentDir = System.getProperty("user.dir");
			System.out.println(currentDir);
			
			Path currentPath = Paths.get(""); 

	        try (Stream<Path> stream = Files.list(currentPath)) {
	            stream.forEach(path -> System.out.println(path.getFileName()));
	        } catch (IOException e) {
	            System.err.println("Помилка читання директорії: " + e.getMessage());
	        }
			return "";
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
				String pass = parts[1].trim();

				if (name.equals(username)) {
					return pass;
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("Файл не знайдено: " + e.getMessage());
		}

		return "";
	}

	void storeNewUser(Account newUser, String password) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileStorageName, true))) {
			writer.write(newUser.name + ":" + password);
			writer.newLine();
		} catch (IOException e) {
			System.err.println("Write error: " + e.getMessage());
		}

	}

	boolean userExists(String username) {
		try (BufferedReader reader = Files.newBufferedReader(Path.of(fileStorageName))) {
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.isBlank())
					continue;
				String[] parts = line.split(":");
				if (parts[0].equals(username)) {
					return true;
				}
			}
		} catch (IOException e) {
			return false;
		}

		return false;
	}
}