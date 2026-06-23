import java.util.Scanner;

/**
 * QuizGame.java
 * A simple console-based quiz game implementing input validation,
 * conditional routing, and percentage score calculation.
 */
public class QuizGame {

    public static void main(String[] args) {
        // Initialize Scanner for capturing user keyboard input
        Scanner scanner = new Scanner(System.in);

        // Track the number of correct answers
        int correctAnswersCount = 0;
        int totalQuestions = 5;

        System.out.println("=========================================");
        System.out.println("      WELCOME TO THE JAVA QUIZ GAME      ");
        System.out.println("=========================================");
        System.out.println("Instructions: Type A, B, C, or D to answer.\n");

        // --- QUESTION 1 ---
        System.out.println("Question 1: Which data type is used to store a single character in Java?");
        System.out.println("A) String\nB) char\nC) int\nD) float");
        char answer1 = getValidInput(scanner);
        // Using switch-case to evaluate the user's answer
        switch (answer1) {
            case 'B':
                System.out.println("Correct!\n");
                correctAnswersCount++;
                break;
            default:
                System.out.println("Incorrect. The correct answer was B.\n");
                break;
        }

        // --- QUESTION 2 ---
        System.out.println("Question 2: What is the default value of a boolean variable in Java?");
        System.out.println("A) true\nB) null\nC) false\nD) 0");
        char answer2 = getValidInput(scanner);
        switch (answer2) {
            case 'C':
                System.out.println("Correct!\n");
                correctAnswersCount++;
                break;
            default:
                System.out.println("Incorrect. The correct answer was C.\n");
                break;
        }

        // --- QUESTION 3 ---
        System.out.println("Question 3: Which operator is used for a logical 'AND' operation in Java?");
        System.out.println("A) &\nB) &&\nC) ||\nD) !");
        char answer3 = getValidInput(scanner);
        switch (answer3) {
            case 'B':
                System.out.println("Correct!\n");
                correctAnswersCount++;
                break;
            default:
                System.out.println("Incorrect. The correct answer was B.\n");
                break;
        }

        // --- QUESTION 4 ---
        System.out.println("Question 4: Which of these is NOT a valid conditional statement type in Java?");
        System.out.println("A) if-else\nB) switch-case\nC) ternary\nD) loop-else");
        char answer4 = getValidInput(scanner);
        switch (answer4) {
            case 'D':
                System.out.println("Correct!\n");
                correctAnswersCount++;
                break;
            default:
                System.out.println("Incorrect. The correct answer was D.\n");
                break;
        }

        // --- QUESTION 5 ---
        System.out.println("Question 5: What is operator precedence?");
        System.out.println(
                "A) The order in which operators are evaluated\nB) A way to name variables\nC) A type of loop\nD) Memory management");
        char answer5 = getValidInput(scanner);
        switch (answer5) {
            case 'A':
                System.out.println("Correct!\n");
                correctAnswersCount++;
                break;
            default:
                System.out.println("Incorrect. The correct answer was A.\n");
                break;
        }

        // --- SCORE COMPUTATION AND OUTPUT ---
        // Calculate percentage using type casting to avoid integer division truncation
        double finalPercentage = ((double) correctAnswersCount / totalQuestions) * 100;

        System.out.println("=========================================");
        System.out.println("               QUIZ RESULTS              ");
        System.out.println("=========================================");
        System.out.println("Total Correct Answers: " + correctAnswersCount + " out of " + totalQuestions);
        System.out.printf("Final Score: %.1f%%\n", finalPercentage);
        System.out.println("=========================================");

        // Close scanner resource to prevent memory leaks
        scanner.close();
    }

    /**
     * Helper method to handle input validation.
     * Ensures the user can only progress if they type A, B, C, or D.
     */
    private static char getValidInput(Scanner scanner) {
        char inputChar = ' ';
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Your answer: ");
            String userInput = scanner.nextLine().trim();

            // Check if input is empty to avoid StringIndexOutOfBoundsException
            if (userInput.isEmpty()) {
                System.out.println("Input cannot be empty. Please enter A, B, C, or D.");
                continue;
            }

            // Convert to uppercase so inputs like 'a' automatically work as 'A'
            inputChar = Character.toUpperCase(userInput.charAt(0));

            // Using 'if' statements to validate against allowed constraints
            if (inputChar == 'A' || inputChar == 'B' || inputChar == 'C' || inputChar == 'D') {
                isValid = true;
            } else {
                System.out.println("Invalid choice! Please enter a valid letter (A, B, C, or D).");
            }
        }
        return inputChar;
    }
}