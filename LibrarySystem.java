import java.util.Scanner;

/**
 * LibrarySystem.java
 * An interactive, console-driven Library Management System tracking books,
 * quantities, borrowing transactions, and returns with strict input validation.
 */
public class LibrarySystem {

    // Blueprint representing a single book in the library system
    static class Book {
        String title;
        String author;
        int quantity;

        Book(String title, String author, int quantity) {
            this.title = title;
            this.author = author;
            this.quantity = quantity;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Fixed-size array representing library inventory capacity
        Book[] library = new Book[100];
        int bookCount = 0;

        System.out.println("=========================================");
        System.out.println("    WELCOME TO THE JAVA LIBRARY SYSTEM   ");
        System.out.println("=========================================");

        while (true) {
            System.out.println("\nPlease choose an operation:");
            System.out.println("1. Add Books");
            System.out.println("2. Borrow Books");
            System.out.println("3. Return Books");
            System.out.println("4. Exit");
            System.out.print("Enter your choice (1-4): ");

            String choiceInput = scanner.nextLine().trim();

            switch (choiceInput) {
                case "1":
                    // --- OPTION 1: ADD BOOKS ---
                    System.out.print("\nEnter book title: ");
                    String addTitle = scanner.nextLine().trim();
                    if (addTitle.isEmpty()) {
                        System.out.println("Error: Title cannot be left blank.");
                        break;
                    }

                    System.out.print("Enter book author: ");
                    String addAuthor = scanner.nextLine().trim();
                    if (addAuthor.isEmpty()) {
                        System.out.println("Error: Author cannot be left blank.");
                        break;
                    }

                    System.out.print("Enter quantity to add: ");
                    int addQty = parsePositiveInteger(scanner.nextLine());
                    if (addQty <= 0) {
                        System.out.println("Error: Quantity must be a valid positive number.");
                        break;
                    }

                    // Check if the book already exists in our system array
                    int existingIndex = findBookIndex(library, bookCount, addTitle);
                    if (existingIndex != -1) {
                        library[existingIndex].quantity += addQty;
                        System.out.println("Success: Updated existing book inventory. New total: "
                                + library[existingIndex].quantity);
                    } else {
                        // Add as a new entry if the inventory isn't full
                        if (bookCount < library.length) {
                            library[bookCount] = new Book(addTitle, addAuthor, addQty);
                            bookCount++;
                            System.out.println("Success: New book added to the library system.");
                        } else {
                            System.out.println("Error: System storage full. Cannot track more unique book profiles.");
                        }
                    }
                    break;

                case "2":
                    // --- OPTION 2: BORROW BOOKS ---
                    System.out.print("\nEnter the title of the book to borrow: ");
                    String borrowTitle = scanner.nextLine().trim();

                    int borrowIndex = findBookIndex(library, bookCount, borrowTitle);
                    if (borrowIndex == -1) {
                        System.out.println("Error: That book does not belong to this library system.");
                        break;
                    }

                    System.out.print("Enter the number of books to borrow: ");
                    int borrowQty = parsePositiveInteger(scanner.nextLine());
                    if (borrowQty <= 0) {
                        System.out.println("Error: Borrow amount must be a valid positive number.");
                        break;
                    }

                    // Check availability constraints
                    if (library[borrowIndex].quantity >= borrowQty) {
                        library[borrowIndex].quantity -= borrowQty;
                        System.out.println("Success: You checked out " + borrowQty + " copies of '"
                                + library[borrowIndex].title + "'.");
                        System.out.println("Remaining copies available: " + library[borrowIndex].quantity);
                    } else {
                        System.out.println("Error: Transaction rejected. Only " + library[borrowIndex].quantity
                                + " copies are currently available.");
                    }
                    break;

                case "3":
                    // --- OPTION 3: RETURN BOOKS ---
                    System.out.print("\nEnter the title of the book to return: ");
                    String returnTitle = scanner.nextLine().trim();

                    int returnIndex = findBookIndex(library, bookCount, returnTitle);
                    if (returnIndex == -1) {
                        System.out.println(
                                "Error: Processing rejected. This book does not belong to our catalog records.");
                        break;
                    }

                    System.out.print("Enter the number of books to return: ");
                    int returnQty = parsePositiveInteger(scanner.nextLine());
                    if (returnQty <= 0) {
                        System.out.println("Error: Return amount must be a valid positive number.");
                        break;
                    }

                    // Update stock counts safely
                    library[returnIndex].quantity += returnQty;
                    System.out.println("Success: Returned " + returnQty + " copies of '" + library[returnIndex].title
                            + "' smoothly.");
                    System.out.println("New inventory available: " + library[returnIndex].quantity);
                    break;

                case "4":
                    // --- OPTION 4: EXIT PROGRAM ---
                    System.out.println("\n=========================================");
                    System.out.println(" Thank you for using the Library System! ");
                    System.out.println("=========================================");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Error: Invalid choice option. Please type a selection from 1 to 4.");
                    break;
            }
        }
    }

    /**
     * Helper method performing case-insensitive lookups across the inventory array.
     */
    private static int findBookIndex(Book[] library, int totalCount, String title) {
        for (int i = 0; i < totalCount; i++) {
            if (library[i].title.equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1; // Not found
    }

    /**
     * Robust validation parsing helper to trap invalid format entries safely.
     */
    private static int parsePositiveInteger(String input) {
        try {
            return Integer.parseInt(input.trim());
        } catch (NumberFormatException e) {
            return -1; // Explicit error indicator to caller logic
        }
    }
}