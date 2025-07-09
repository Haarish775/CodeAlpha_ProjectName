import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTracker {

    static ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n==== Student Grade Tracker ====");
            System.out.println("1. Add Student");
            System.out.println("2. Show Report");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();  // Clear buffer

            switch (choice) {
                case 1:
                    addStudent(sc);
                    break;
                case 2:
                    showReport();
                    break;
                case 3:
                    System.out.println("Exiting... Thank you!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 3);

        sc.close();
    }

    public static void addStudent(Scanner sc) {
        System.out.print("Enter student name: ");
        String name = sc.nextLine();
        System.out.print("Enter student grade (0-100): ");
        double grade = sc.nextDouble();
        students.add(new Student(name, grade));
        System.out.println("Student added successfully!");
    }

    public static void showReport() {
        if (students.isEmpty()) {
            System.out.println("No students to show.");
            return;
        }

        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        System.out.println("\n--- Student Report ---");
        for (Student s : students) {
            System.out.println("Name: " + s.name + ", Grade: " + s.grade);
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();

        System.out.printf("\nTotal Students: %d\n", students.size());
        System.out.printf("Average Grade: %.2f\n", average);
        System.out.printf("Highest Grade: %.2f (%s)\n", highest, topStudent);
        System.out.printf("Lowest Grade: %.2f (%s)\n", lowest, lowStudent);
    }
}