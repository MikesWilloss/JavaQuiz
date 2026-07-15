import java.util.Scanner;

/**
 * Student.java
 * Represents an individual student with private attributes and public
 * accessors.
 */
class Student {
    private String name;
    private String id;
    private int age;
    private double grade;

    // Constructor
    public Student(String name, String id, int age, double grade) {
        this.name = name;
        this.id = id;
        this.age = age;
        this.grade = grade;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}

/**
 * StudentManagement.java
 * Manages the student database array and coordinates administrative choices.
 */
public class StudentManagement {

    // Private static variables to store the list and track student count
    private static Student[] students = new Student[100];
    private static int totalStudents = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=========================================");
        System.out.println("   STUDENT RECORD MANAGEMENT SYSTEM      ");
        System.out.println("=========================================");

        while (true) {
            System.out.println("\nAdministrative Options:");
            System.out.println("1. Add New Student");
            System.out.println("2. Update Student Information");
            System.out.println("3. View Student Details");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addNewStudent(scanner);
                    break;
                case "2":
                    updateStudentInfo(scanner);
                    break;
                case "3":
                    viewStudentDetails(scanner);
                    break;
                case "4":
                    System.out.println("\nExiting Student Record Management System. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Error: Invalid choice. Please enter a number between 1 and 4.");
                    break;
            }
        }
    }

    /**
     * Option 1: Prompts for information and adds a new student to the records.
     */
    private static void addNewStudent(Scanner scanner) {
        if (totalStudents >= students.length) {
            System.out.println("Error: The student database is full. Cannot add more records.");
            return;
        }

        System.out.print("\nEnter Student ID: ");
        String id = scanner.nextLine().trim();
        if (id.isEmpty()) {
            System.out.println("Error: Student ID cannot be empty.");
            return;
        }

        // Ensure ID uniqueness
        if (findStudentIndex(id) != -1) {
            System.out.println("Error: A student with ID " + id + " already exists.");
            return;
        }

        System.out.print("Enter Student Name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: Student Name cannot be empty.");
            return;
        }

        System.out.print("Enter Student Age: ");
        int age = parsePositiveInteger(scanner.nextLine());
        if (age <= 0) {
            System.out.println("Error: Age must be a valid positive integer.");
            return;
        }

        System.out.print("Enter Student Grade (GPA 0.0 - 4.0): ");
        double grade = parseDouble(scanner.nextLine());
        if (grade < 0.0 || grade > 4.0) {
            System.out.println("Error: Grade must be a decimal value between 0.0 and 4.0.");
            return;
        }

        // Add student to array
        students[totalStudents] = new Student(name, id, age, grade);
        totalStudents++;
        System.out.println("Success: Student record added successfully.");
    }

    /**
     * Option 2: Locates a student by ID and updates their name, age, and grade
     * details.
     */
    private static void updateStudentInfo(Scanner scanner) {
        System.out.print("\nEnter Student ID to update: ");
        String id = scanner.nextLine().trim();

        int index = findStudentIndex(id);
        if (index == -1) {
            System.out.println("Error: Student record with ID " + id + " was not found.");
            return;
        }

        Student student = students[index];
        System.out.println("Record found for " + student.getName());

        System.out.print("Enter New Name (or press Enter to keep '" + student.getName() + "'): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            student.setName(newName);
        }

        System.out.print("Enter New Age (or press Enter to keep " + student.getAge() + "): ");
        String ageInput = scanner.nextLine().trim();
        if (!ageInput.isEmpty()) {
            int newAge = parsePositiveInteger(ageInput);
            if (newAge <= 0) {
                System.out.println("Error: Invalid age. Update ignored for this field.");
            } else {
                student.setAge(newAge);
            }
        }

        System.out.print("Enter New Grade (or press Enter to keep " + student.getGrade() + "): ");
        String gradeInput = scanner.nextLine().trim();
        if (!gradeInput.isEmpty()) {
            double newGrade = parseDouble(gradeInput);
            if (newGrade < 0.0 || newGrade > 4.0) {
                System.out.println("Error: Invalid grade range. Update ignored for this field.");
            } else {
                student.setGrade(newGrade);
            }
        }

        System.out.println("Success: Student record updated successfully.");
    }

    /**
     * Option 3: Locates a student by ID and displays their complete stored details.
     */
    private static void viewStudentDetails(Scanner scanner) {
        System.out.print("\nEnter Student ID to view: ");
        String id = scanner.nextLine().trim();

        int index = findStudentIndex(id);
        if (index == -1) {
            System.out.println("Error: Student record with ID " + id + " was not found.");
            return;
        }

        Student student = students[index];
        System.out.println("\n-----------------------------------------");
        System.out.println("            STUDENT DETAILS              ");
        System.out.println("-----------------------------------------");
        System.out.println("ID:     " + student.getId());
        System.out.println("Name:   " + student.getName());
        System.out.println("Age:    " + student.getAge());
        System.out.println("Grade:  " + student.getGrade());
        System.out.println("-----------------------------------------");
    }

    /**
     * Helper method to perform a case-insensitive lookup of a student ID.
     * Returns the array index if found, otherwise returns -1.
     */
    private static int findStudentIndex(String id) {
        for (int i = 0; i < totalStudents; i++) {
            if (students[i].getId().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Utility method to safely parse integers and handle format exceptions.
     */
    private static int parsePositiveInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Utility method to safely parse doubles and handle format exceptions.
     */
    private static double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            return -1.0;
        }
    }
}