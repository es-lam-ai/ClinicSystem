// Custom FIFO Queue for patients waiting for an open appointment slot
public class WaitingQueue {

    // Inner Node class for the queue
    private class Node {
        Patient data;
        Node next;

        Node(Patient data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; // First patient in line
    private Node rear;  // Last patient in line
    private int size;

    public WaitingQueue() {
        this.front = null;
        this.rear = null;
        this.size = 0;
    }

    // Add a patient to the end of the waiting list
    public void enqueue(Patient patient) {
        // Prevent adding the same patient twice to the same waiting list
        if (contains(patient.getPatientId())) {
            System.out.println(patient.getName() + " is already on this waiting list.");
            return;
        }

        Node newNode = new Node(patient);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // Remove and return the first patient in line when a slot opens up
    public Patient dequeue() {
        if (isEmpty()) {
            return null;
        }
        Patient nextPatient = front.data;
        front = front.next;

        // If the queue is now empty, reset rear as well
        if (front == null) {
            rear = null;
        }
        size--;
        return nextPatient;
    }

    // Look at the next patient in line without removing them
    public Patient peek() {
        if (isEmpty()) {
            return null;
        }
        return front.data;
    }

    // Check if a patient is already waiting in this queue
    public boolean contains(int patientId) {
        Node current = front;
        while (current != null) {
            if (current.data.getPatientId() == patientId) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public boolean isEmpty() {
        return front == null;
    }

    public int getSize() {
        return size;
    }

    // Friendly display of everyone currently waiting
    public void displayQueue() {
        if (isEmpty()) {
            System.out.println("No patients are currently waiting.");
            return;
        }

        Node current = front;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.data.getName() +
                    " (ID: " + current.data.getPatientId() +
                    " | Contact: " + current.data.getContactInfo() + ")");
            current = current.next;
            position++;
        }
    }
}