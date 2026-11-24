// Custom Linked List to store appointments (used for patient history and clinic records)
public class AppointmentLinkedList {

    // Inner Node class for the linked list
    private class Node {
        Appointment data;
        Node next;

        Node(Appointment data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node head;
    private int size;

    public AppointmentLinkedList() {
        this.head = null;
        this.size = 0;
    }

    // Add a new appointment to the end of the list
    public void addAppointment(Appointment app) {
        Node newNode = new Node(app);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    // Search for an appointment by its ID (used for rescheduling or canceling)
    public Appointment findById(int appointmentId) {
        Node current = head;
        while (current != null) {
            if (current.data.getAppointmentId() == appointmentId) {
                return current.data;
            }
            current = current.next;
        }
        return null; // Not found
    }

    // Check if a doctor already has an active (Upcoming) booking at a specific date and time
    public boolean isSlotBooked(int clinicianId, String date, String time) {
        Node current = head;
        while (current != null) {
            Appointment app = current.data;
            if (app.getClinicianId() == clinicianId &&
                    app.getDate().equalsIgnoreCase(date) &&
                    app.getTime().equalsIgnoreCase(time) &&
                    app.getStatus().equalsIgnoreCase("Upcoming")) {
                return true; // Conflict found
            }
            current = current.next;
        }
        return false; // Slot is free
    }

    // Custom Bubble Sort to sort appointments by date and time (no built-in sort used)
    public void sortByDate() {
        if (head == null || head.next == null) {
            return; // Already sorted
        }

        boolean swapped;
        do {
            swapped = false;
            Node current = head;
            while (current.next != null) {
                String currentKey = current.data.getDate() + " " + current.data.getTime();
                String nextKey = current.next.data.getDate() + " " + current.next.data.getTime();

                // Swap appointment data if out of order
                if (currentKey.compareTo(nextKey) > 0) {
                    Appointment temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
        } while (swapped);
    }

    // Display all appointments in the list
    public void displayAll() {
        if (head == null) {
            System.out.println("No appointments found yet.");
            return;
        }
        Node current = head;
        while (current != null) {
            current.data.displayInfo();
            current = current.next;
        }
    }

    // Report: Display appointments for a specific date
    public void displayByDate(String date) {
        boolean found = false;
        Node current = head;
        while (current != null) {
            if (current.data.getDate().equalsIgnoreCase(date)) {
                current.data.displayInfo();
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No appointments scheduled on " + date + ".");
        }
    }

    // Report: Display appointments for a specific doctor
    public void displayByClinician(int clinicianId) {
        boolean found = false;
        Node current = head;
        while (current != null) {
            if (current.data.getClinicianId() == clinicianId) {
                current.data.displayInfo();
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No appointments found for Doctor ID: " + clinicianId + ".");
        }
    }

    // Report: Display appointments by status (Upcoming, Completed, Canceled)
    public void displayByStatus(String status) {
        boolean found = false;
        Node current = head;
        while (current != null) {
            if (current.data.getStatus().equalsIgnoreCase(status)) {
                current.data.displayInfo();
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No " + status + " appointments found.");
        }
    }

    // Report: Show summary counts of Upcoming, Completed, and Canceled appointments
    public void displayStatusCounts() {
        int upcoming = 0, completed = 0, canceled = 0;
        Node current = head;

        while (current != null) {
            String status = current.data.getStatus();
            if (status.equalsIgnoreCase("Upcoming")) {
                upcoming++;
            } else if (status.equalsIgnoreCase("Completed")) {
                completed++;
            } else if (status.equalsIgnoreCase("Canceled")) {
                canceled++;
            }
            current = current.next;
        }

        System.out.println("\n--- Appointment Summary ---");
        System.out.println("Upcoming  : " + upcoming);
        System.out.println("Completed : " + completed);
        System.out.println("Canceled  : " + canceled);
        System.out.println("Total     : " + size);
    }

    public boolean isEmpty() {
        return head == null;
    }

    public int getSize() {
        return size;
    }
}