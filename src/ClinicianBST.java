// Custom Binary Search Tree (BST) for fast clinician lookup by Doctor ID
public class ClinicianBST {

    // Inner Node class for the BST
    private class Node {
        Clinician data;
        Node left;
        Node right;

        Node(Clinician data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    public ClinicianBST() {
        this.root = null;
    }

    // Add a new doctor to the BST
    public boolean insert(Clinician clinician) {
        // Prevent duplicate Doctor IDs
        if (search(clinician.getClinicianId()) != null) {
            return false;
        }
        root = insertRec(root, clinician);
        return true;
    }

    // Recursive helper for insertion
    private Node insertRec(Node current, Clinician clinician) {
        if (current == null) {
            return new Node(clinician);
        }

        if (clinician.getClinicianId() < current.data.getClinicianId()) {
            current.left = insertRec(current.left, clinician);
        } else if (clinician.getClinicianId() > current.data.getClinicianId()) {
            current.right = insertRec(current.right, clinician);
        }

        return current;
    }

    // Search for a doctor by their ID
    public Clinician search(int clinicianId) {
        return searchRec(root, clinicianId);
    }

    // Recursive helper for searching
    private Clinician searchRec(Node current, int clinicianId) {
        if (current == null) {
            return null; // Doctor not found
        }

        if (clinicianId == current.data.getClinicianId()) {
            return current.data;
        } else if (clinicianId < current.data.getClinicianId()) {
            return searchRec(current.left, clinicianId);
        } else {
            return searchRec(current.right, clinicianId);
        }
    }

    // Display all doctors sorted by ID (In-Order Traversal)
    public void displayAll() {
        if (root == null) {
            System.out.println("No doctors are registered in the clinic yet.");
            return;
        }
        System.out.println("\n--- Our Clinic Doctors ---");
        inOrderRec(root);
    }

    // Recursive helper for in-order traversal
    private void inOrderRec(Node current) {
        if (current != null) {
            inOrderRec(current.left);
            current.data.displayInfo();
            current.data.displaySchedule();
            System.out.println("----------------------------------------");
            inOrderRec(current.right);
        }
    }

    public boolean isEmpty() {
        return root == null;
    }
}