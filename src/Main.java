/*
 * CSE111 - Data Structures Project: Clinic Appointment System
 * Team Members (Name - ID):
 * 1. [Name 1] - [ID 1]
 * 2. [Name 2] - [ID 2]
 * 3. [Name 3] - [ID 3]
 * 4. [Name 4] - [ID 4]
 * 5. [Name 5] - [ID 5]
 */

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ClinicSystem clinic = new ClinicSystem();

    public static void main(String[] args) {
        // Load a few sample doctors and patients so you can test right away
        clinic.loadSampleData();

        System.out.println("==========================================");
        System.out.println("   Welcome to Alamein Care Clinic!");
        System.out.println("   How can we help you today?");
        System.out.println("==========================================");

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Please choose an option (1-5): ");

            switch (choice) {
                case 1:
                    patientMenu();
                    break;
                case 2:
                    doctorMenu();
                    break;
                case 3:
                    appointmentMenu();
                    break;
                case 4:
                    reportsMenu();
                    break;
                case 5:
                    System.out.println("\nThank you for visiting Alamein Care Clinic. Stay healthy!");
                    running = false;
                    break;
                default:
                    System.out.println("Oops! Please pick a number between 1 and 5.");
            }
        }
        scanner.close();
    }

    // --- Main Menu ---
    private static void printMainMenu() {
        System.out.println("\n================ MAIN MENU ================");
        System.out.println("1. Patient Services (Register, Search, Update)");
        System.out.println("2. Our Doctors & Waiting Lists");
        System.out.println("3. Appointments (Book, Reschedule, Cancel)");
        System.out.println("4. Clinic Reports & Schedules");
        System.out.println("5. Exit");
        System.out.println("===========================================");
    }

    // --- 1. Patient Services Sub-Menu ---
    private static void patientMenu() {
        System.out.println("\n--- Patient Services ---");
        System.out.println("1. Register a New Patient");
        System.out.println("2. Search Patient & View Visit History");
        System.out.println("3. Update Patient Contact Info");
        System.out.println("4. View All Registered Patients");
        System.out.println("5. Back to Main Menu");

        int choice = readInt("Choose an option: ");
        switch (choice) {
            case 1:
                int id = readInt("Enter new Patient ID (e.g., 104): ");
                System.out.print("Enter Patient Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter Phone / Contact Info: ");
                String contact = scanner.nextLine();
                clinic.addPatient(id, name, contact);
                break;
            case 2:
                int searchId = readInt("Enter Patient ID to search: ");
                Patient p = clinic.findPatient(searchId);
                if (p != null) {
                    p.displayInfo();
                    p.displayHistory();
                } else {
                    System.out.println("Sorry, we couldn't find a patient with ID " + searchId + ".");
                }
                break;
            case 3:
                int updateId = readInt("Enter Patient ID: ");
                System.out.print("Enter New Phone / Contact Info: ");
                String newContact = scanner.nextLine();
                clinic.updatePatientContact(updateId, newContact);
                break;
            case 4:
                clinic.displayAllPatients();
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // --- 2. Doctors & Waiting Lists Sub-Menu ---
    private static void doctorMenu() {
        System.out.println("\n--- Our Doctors & Waiting Lists ---");
        System.out.println("1. View All Doctors & Daily Hours");
        System.out.println("2. Add a New Doctor");
        System.out.println("3. Check a Doctor's Waiting List");
        System.out.println("4. Back to Main Menu");

        int choice = readInt("Choose an option: ");
        switch (choice) {
            case 1:
                clinic.displayAllClinicians();
                break;
            case 2:
                int docId = readInt("Enter new Doctor ID (e.g., 204): ");
                System.out.print("Enter Doctor Name (without Dr.): ");
                String docName = scanner.nextLine();
                System.out.print("Enter Specialty (e.g., Orthopedics): ");
                String specialty = scanner.nextLine();
                clinic.addClinician(docId, docName, specialty);
                break;
            case 3:
                int waitDocId = readInt("Enter Doctor ID: ");
                Clinician doc = clinic.findClinician(waitDocId);
                if (doc != null) {
                    doc.displayWaitingList();
                } else {
                    System.out.println("We couldn't find a doctor with ID " + waitDocId + ".");
                }
                break;
            case 4:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // --- 3. Appointments Sub-Menu ---
    private static void appointmentMenu() {
        System.out.println("\n--- Appointments ---");
        System.out.println("1. Book an Appointment");
        System.out.println("2. Reschedule an Appointment");
        System.out.println("3. Cancel an Appointment");
        System.out.println("4. Mark Appointment as Completed");
        System.out.println("5. Back to Main Menu");

        int choice = readInt("Choose an option: ");
        switch (choice) {
            case 1:
                int pId = readInt("Enter Patient ID (e.g., 101): ");
                int dId = readInt("Enter Doctor ID (e.g., 201): ");
                System.out.print("Enter Date (YYYY-MM-DD, e.g., 2025-12-15): ");
                String date = scanner.nextLine();
                System.out.print("Enter Time (e.g., 10:00 AM): ");
                String time = scanner.nextLine();
                clinic.bookAppointment(pId, dId, date, time);
                break;
            case 2:
                int resId = readInt("Enter Appointment ID to reschedule: ");
                System.out.print("Enter New Date (YYYY-MM-DD): ");
                String newDate = scanner.nextLine();
                System.out.print("Enter New Time (e.g., 01:00 PM): ");
                String newTime = scanner.nextLine();
                clinic.rescheduleAppointment(resId, newDate, newTime);
                break;
            case 3:
                int cancelId = readInt("Enter Appointment ID to cancel: ");
                clinic.cancelAppointment(cancelId);
                break;
            case 4:
                int compId = readInt("Enter Appointment ID to mark as Completed: ");
                clinic.completeAppointment(compId);
                break;
            case 5:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // --- 4. Reports Sub-Menu ---
    private static void reportsMenu() {
        System.out.println("\n--- Clinic Reports ---");
        System.out.println("1. View All Appointments (Sorted by Date)");
        System.out.println("2. View Appointments by Date");
        System.out.println("3. View Appointments by Doctor");
        System.out.println("4. View Appointments by Status (Upcoming/Completed/Canceled)");
        System.out.println("5. View Appointment Summary Counts");
        System.out.println("6. Back to Main Menu");

        int choice = readInt("Choose an option: ");
        switch (choice) {
            case 1:
                clinic.showAllAppointmentsSorted();
                break;
            case 2:
                System.out.print("Enter Date (YYYY-MM-DD): ");
                String date = scanner.nextLine();
                clinic.showReportByDate(date);
                break;
            case 3:
                int docId = readInt("Enter Doctor ID: ");
                clinic.showReportByClinician(docId);
                break;
            case 4:
                System.out.print("Enter Status (Upcoming, Completed, or Canceled): ");
                String status = scanner.nextLine();
                clinic.showReportByStatus(status);
                break;
            case 5:
                clinic.showSummaryCounts();
                break;
            case 6:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Helper method to safely read integers without crashing if the user types letters
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}