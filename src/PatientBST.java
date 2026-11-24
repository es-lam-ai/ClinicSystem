// Custom Binary Search Tree (BST) for fast patient lookup by Patient ID
public class PatientBST {

    // Inner Node class for the BST
    private class Node {
        Patient data;
        Node left;
        Node right;

        Node(Patient data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public PatientBST() {
        this.root = null;
    }

    // Add a new patient to the BST
    public boolean insert(Patient patient) {
        // Prevent duplicate Patient IDs
        if (search(patient.getPatientId()) != null) {
            return false;
        }
        root = insertRec(root, patient);
        return true;
    }

    // Recursive helper for insertion
    private Node insertRec(Node current, Patient patient) {
        if (current == null) {
            return new Node(patient);
        }

        if (patient.getPatientId() < current.data.getPatientId()) {
            current.left = insertRec(current.left, patient);
        } else if (patient.getPatientId() > current.data.getPatientId()) {
            current.right = insertRec(current.right, patient);
        }

        return current;
    }

    // Search for a patient by their ID
    public Patient search(int patientId) {
        return searchRec(root, patientId);
    }

    // Recursive helper for searching
    private Patient searchRec(Node current, int patientId) {
        if (current == null) {
            return null; // Patient not found
        }

        if (patientId == current.data.getPatientId()) {
            return current.data;
        } else if (patientId < current.data.getPatientId()) {
            return searchRec(current.left, patientId);
        } else {
            return searchRec(current.right, patientId);
        }
    }

    // Display all patients sorted by ID (In-Order Traversal)
    public void displayAll() {
        if (root == null) {
            System.out.println("No patients are registered in the clinic yet.");
            return;
        }
        System.out.println("\n--- Registered Patients ---");
        inOrderRec(root);
    }

    // Recursive helper for in-order traversal
    private void inOrderRec(Node current) {
        if (current != null) {
            inOrderRec(current.left);
            current.data.displayInfo();
            inOrderRec(current.right);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
}