package utils;

import java.util.Scanner;

public class KeyboardScanner {
	private static KeyboardScanner instance;
	
	private Scanner scanner;
	
	private KeyboardScanner() {
		scanner = new Scanner(System.in);
	};
	
	public static KeyboardScanner getInstance() {
		if (instance == null) {
			instance = new KeyboardScanner();
		}
		return instance;
	}
	
	public String getNextLine() {
		String newLine;
		int iterations = 10;
		do {
			newLine = this.scanner.nextLine();
		} while (newLine.isEmpty() == true && iterations-- > 0);
		
		if (iterations <= 0) {
			throw new RuntimeException("Couldn't read from keyboard");
		}
		return newLine;
	}
	
	public void close() {
		this.scanner.close();
	}
}
