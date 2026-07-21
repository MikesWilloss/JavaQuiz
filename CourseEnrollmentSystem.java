import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * CourseEnrollmentSystem.java
 * University Course Enrollment and Grade Management System in Java.
 * Demonstrates static/instance methods, variables, and strict encapsulation.
 */

// ============================================================================
// 1. COURSE CLASS
// ============================================================================
class Course {
    private String courseCode;
    private String name;
    private int maxCapacity;
    private int currentEnrolledCount;

    // Static variable to track cumulative enrollments across ALL Course instances
    private static int totalEnrolledStudents = 0;

    public Course(String courseCode, String name, int maxCapacity) {
        this.courseCode = courseCode.toUpperCase().trim();
        this.name = name.trim();
        this.maxCapacity = maxCapacity;
        this.currentEnrolledCount = 0;
    }

    // Public Getters
    public String getCourseCode() {
        return courseCode;
    }

    public String getName() {
        return name;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public int getCurrentEnrolledCount() {
        return currentEnrolledCount;
    }

    /**
     * Instance method to increment seat counts for this course.
     * Also updates global static tracker.
     */
    public boolean incrementEnrollment() {
        if (currentEnrolledCount < maxCapacity) {
            currentEnrolledCount++;
            totalEnrolledStudents++; // Increment static global counter
            return true;
        }
        return false; // Course capacity full
    }

    /**
     * Static method retrieving overall system-wide enrollments.
     */
    public static int getTotalEnrolledStudents() {
        return totalEnrolledStudents;
    }

    @Override
    public String toString() {
        return "[" + courseCode + "] " + name + " (Enrolled: " + currentEnrolledCount + "/" + maxCapacity + ")";
    }
}

// ============================================================================
// 2. STUDENT CLASS
// ============================================================================
class Student {
    private String name;
    private String id;
    private List<Course> enrolledCourses;
    private Map<String, Double> grades; // Key: Course Code, Value: Grade (0-100)

    public Student(String name, String id) {
        this.name = name.trim();
        this.id = id.toUpperCase().trim();
        this.enrolledCourses = new ArrayList<>();
        this.grades = new HashMap<>();
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id.toUpperCase().trim();
    }

    public List<Course> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Map<String, Double> getGrades() {
        return grades;
    }

    /**
     * Instance method to enroll student in a course.
     */
    public boolean enrollInCourse(Course course) {
        if (isEnrolled(course)) {
            return false;
        }
        enrolledCourses.add(course);
        return true;
    }

    /**
     * Helper instance method checking enrollment status.
     */
    public boolean isEnrolled(Course course) {
        for (Course c : enrolledCourses) {
            if (c.getCourseCode().equalsIgnoreCase(course.getCourseCode())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Instance method assigning grade for a course.
     */
    public boolean assignGrade(Course course, double grade) {
        if (!isEnrolled(course)) {
            return false;
        }
        grades.put(course.getCourseCode(), grade);
        return true;
    }

    /**
     * Instance method calculating average grade across all courses.
     */
    public double calculateAverageGrade() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double g : grades.values()) {
            sum += g;
        }
        return sum / grades.size();
    }
}

// ============================================================================
// 3. COURSE MANAGEMENT CLASS
// ============================================================================
class CourseManagement {
    private static List<Course> courses = new ArrayList<>();
    private static List<Student> students = new ArrayList<>();
    private static Map<String, Double> overallGrades = new HashMap<>();

    public static boolean addCourse(String courseCode, String courseName, int maxCapacity) {
        if (findCourse(courseCode) != null) {
            return false; // Duplicate course code
        }
        Course newCourse = new Course(courseCode, courseName, maxCapacity);
        courses.add(newCourse);
        return true;
    }

    public static Course findCourse(String courseCode) {
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(courseCode.trim())) {
                return c;
            }
        }
        return null;
    }

    public static Student findOrCreateStudent(String name, String id) {
        Student existing = findStudentById(id);
        if (existing != null) {
            return existing;
        }
        Student newStudent = new Student(name, id);
        students.add(newStudent);
        return newStudent;
    }

    public static Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id.trim())) {
                return s;
            }
        }
        return null;
    }

    public static String enrollStudent(Student student, Course course) {
        if (student == null || course == null) {
            return "Error: Invalid student or course record.";
        }

        if (student.isEnrolled(course)) {
            return "Error: Student " + student.getName() + " (" + student.getId()
                    + ") is ALREADY enrolled in " + course.getCourseCode() + ".";
        }

        if (course.getCurrentEnrolledCount() >= course.getMaxCapacity()) {
            return "Error: Enrollment failed! Course " + course.getCourseCode()
                    + " has reached maximum capacity (" + course.getMaxCapacity() + ").";
        }

        student.enrollInCourse(course);
        course.incrementEnrollment();
        return "SUCCESS: Enrolled " + student.getName() + " (" + student.getId() + ") in " + course.getCourseCode()
                + ".";
    }

    public static String assignGrade(Student student, Course course, double grade) {
        if (student == null || course == null) {
            return "Error: Invalid student or course record.";
        }

        if (grade < 0.0 || grade > 100.0) {
            return "Error: Grade must be between 0.0 and 100.0.";
        }

        if (!student.isEnrolled(course)) {
            return "Error: Student " + student.getName() + " is NOT enrolled in " + course.getCourseCode() + ".";
        }

        student.assignGrade(course, grade);
        calculateOverallGrade(student);
        return "SUCCESS: Grade " + String.format("%.2f", grade) + "% assigned to "
                + student.getName() + " for " + course.getCourseCode() + ".";
    }

    public static double calculateOverallGrade(Student student) {
        if (student == null)
            return 0.0;
        double avg = student.calculateAverageGrade();
        overallGrades.put(student.getId(), avg);
        return avg;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static Map<String, Double> getOverallGrades() {
        return overallGrades;
    }
}

// ============================================================================
// 4. MAIN ADMINISTRATOR INTERFACE
// ============================================================================
public class CourseEnrollmentSystem {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        seedInitialData();

        boolean running = true;
        while (running) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleAddCourse();
                    break;
                case "2":
                    handleEnrollStudent();
                    break;
                case "3":
                    handleAssignGrade();
                    break;
                case "4":
                    handleCalculateOverallGrade();
                    break;
                case "5":
                    handleDisplaySystemSummary();
                    break;
                case "6":
                    running = false;
                    System.out.println("\nExiting System. Goodbye!");
                    break;
                default:
                    System.out.println("\n[!] Invalid option. Please enter a choice between 1 and 6.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n==================================================");
        System.out.println("   UNIVERSITY COURSE & GRADE MANAGEMENT SYSTEM   ");
        System.out.println("==================================================");
        System.out.println("1. Add a New Course");
        System.out.println("2. Enroll Student in Course");
        System.out.println("3. Assign Grade to Student");
        System.out.println("4. Calculate Student Overall Grade");
        System.out.println("5. View System Summary & Total Enrollments");
        System.out.println("6. Exit");
        System.out.println("==================================================");
        System.out.print("Select an option (1-6): ");
    }

    private static void handleAddCourse() {
        System.out.println("\n--- [1] Add New Course ---");
        System.out.print("Enter Course Code (e.g., CS1102): ");
        String code = scanner.nextLine();

        System.out.print("Enter Course Name: ");
        String name = scanner.nextLine();

        int capacity = readIntInput("Enter Maximum Student Capacity: ");

        if (capacity <= 0) {
            System.out.println("[!] Error: Capacity must be greater than 0.");
            return;
        }

        boolean added = CourseManagement.addCourse(code, name, capacity);
        if (added) {
            System.out.println("[✓] SUCCESS: Course " + code.toUpperCase() + " added successfully.");
        } else {
            System.out.println("[!] Error: Course code " + code.toUpperCase() + " already exists.");
        }
    }

    private static void handleEnrollStudent() {
        System.out.println("\n--- [2] Enroll Student in Course ---");
        if (CourseManagement.getCourses().isEmpty()) {
            System.out.println("[!] No courses available. Please add a course first.");
            return;
        }

        System.out.print("Enter Student ID (e.g., S101): ");
        String id = scanner.nextLine();

        System.out.print("Enter Student Full Name: ");
        String name = scanner.nextLine();

        Student student = CourseManagement.findOrCreateStudent(name, id);

        System.out.print("Enter Course Code to Enroll: ");
        String courseCode = scanner.nextLine();

        Course course = CourseManagement.findCourse(courseCode);
        if (course == null) {
            System.out.println("[!] Error: Course '" + courseCode + "' not found.");
            return;
        }

        String result = CourseManagement.enrollStudent(student, course);
        System.out.println(result.startsWith("SUCCESS") ? "[✓] " + result : "[!] " + result);
    }

    private static void handleAssignGrade() {
        System.out.println("\n--- [3] Assign Grade to Student ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        Student student = CourseManagement.findStudentById(id);
        if (student == null) {
            System.out.println("[!] Error: Student ID '" + id + "' not found.");
            return;
        }

        System.out.print("Enter Course Code: ");
        String courseCode = scanner.nextLine();

        Course course = CourseManagement.findCourse(courseCode);
        if (course == null) {
            System.out.println("[!] Error: Course '" + courseCode + "' not found.");
            return;
        }

        double grade = readDoubleInput("Enter Grade (0.0 to 100.0): ");
        String result = CourseManagement.assignGrade(student, course, grade);
        System.out.println(result.startsWith("SUCCESS") ? "[✓] " + result : "[!] " + result);
    }

    private static void handleCalculateOverallGrade() {
        System.out.println("\n--- [4] Calculate Student Overall Grade ---");
        System.out.print("Enter Student ID: ");
        String id = scanner.nextLine();

        Student student = CourseManagement.findStudentById(id);
        if (student == null) {
            System.out.println("[!] Error: Student not found.");
            return;
        }

        System.out.println("\nStudent: " + student.getName() + " (ID: " + student.getId() + ")");
        System.out.println("Course Breakdown:");
        if (student.getEnrolledCourses().isEmpty()) {
            System.out.println("  - No enrolled courses.");
        } else {
            for (Course c : student.getEnrolledCourses()) {
                Double g = student.getGrades().get(c.getCourseCode());
                String gradeStr = (g != null) ? String.format("%.2f%%", g) : "Not Graded";
                System.out.println("  * " + c.getCourseCode() + " (" + c.getName() + "): " + gradeStr);
            }
        }

        double overall = CourseManagement.calculateOverallGrade(student);
        System.out.printf("--> Cumulative Average Grade: %.2f%%\n", overall);
    }

    private static void handleDisplaySystemSummary() {
        System.out.println("\n--- [5] System Summary & Global Enrollment Tracker ---");
        System.out.println("Global Enrolled Seats (Static Tracker): " + Course.getTotalEnrolledStudents());
        System.out.println("\nCourse Offerings:");
        if (CourseManagement.getCourses().isEmpty()) {
            System.out.println("  No courses listed.");
        } else {
            for (Course c : CourseManagement.getCourses()) {
                System.out.println("  - " + c);
            }
        }

        System.out.println("\nRegistered Students (" + CourseManagement.getStudents().size() + "):");
        if (CourseManagement.getStudents().isEmpty()) {
            System.out.println("  No registered students.");
        } else {
            for (Student s : CourseManagement.getStudents()) {
                double avg = CourseManagement.calculateOverallGrade(s);
                System.out.printf("  - %s (ID: %s) | Courses: %d | Avg Grade: %.2f%%\n",
                        s.getName(), s.getId(), s.getEnrolledCourses().size(), avg);
            }
        }
    }

    private static void seedInitialData() {
        // Initial courses with restricted capacity for verification testing
        CourseManagement.addCourse("CS1101", "Programming Fundamentals", 1); // Capacity: 1
        CourseManagement.addCourse("CS1102", "Programming 1 in Java", 30);
    }

    private static int readIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Input error: Please enter a valid integer.");
            }
        }
    }

    private static double readDoubleInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("[!] Input error: Please enter a valid decimal number.");
            }
        }
    }
}