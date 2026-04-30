# Banking System 🏦

A simple Java banking application that allows user registration, account management, and transactions. This project uses text files as a lightweight database.

## 🚀 How to Load the Project into Eclipse

Follow these steps to import and run the project on your local machine:

### 1. Clone the Repository
Open your terminal or Git Bash and run:
```bash
git clone https://github.com
```
*(Replace the URL with your actual GitHub repository link.)*

### 2. Import into Eclipse IDE
Since this is a standard Java project, you should import it as follows:

1.  Open **Eclipse IDE**.
2.  Go to **File** -> **New** -> **Java Project**.
3.  In the **Project name** field, enter: `BankingSystem`.
4.  In the **Contents** section:
    *   Uncheck **Use default location**.
    *   Click **Browse...** and select the folder you just cloned from GitHub.
5.  Click **Finish**.

### 3. Running the Application
1.  In the **Package Explorer** window, expand the `src/bank` package.
2.  Locate the `Programm.java` file.
3.  Right-click on the file.
4.  Select **Run As** -> **Java Application**.

## 🛠 Features & Technologies
*   **Java 8+** (Compatible with Java 11, 17, and 21).
*   **File I/O**: Data is persisted in `accounts.txt` and `users.txt`.
*   **Singleton Pattern**: Implemented in `KeyboardScanner` for efficient resource management.
*   **Modular Structure**: Clean separation between logic (`bank` package) and utilities (`utils` package).

## ⚠️ Troubleshooting
*   **Module Errors**: If you see errors related to `module-info.java` and you are using an older version of Java, you can safely delete that file.
*   **File Not Found**: Ensure you run the application from the project root so the program can locate the `.txt` database files.
*   **Keyboard Input**: If the console doesn't seem to respond, make sure you have clicked inside the Eclipse Console tab before typing.