// Represents a patient registered in the clinic
public class Patient {
    private int patientId;
    private String name;
    private String contactInfo;
    private AppointmentLinkedList appointmentHistory; // Custom linked list for visit history

    // Constructor to register a new patient
    public Patient(int patientId, String name, String contactInfo) {
        this.patientId = patientId;
        this.name = name;
        this.contactInfo = contactInfo;
        this.appointmentHistory = new AppointmentLinkedList();
    }

    // Getters
    public int getPatientId() {
        return patientId;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public AppointmentLinkedList getAppointmentHistory() {
        return appointmentHistory;
    }

    // Setter to update contact details
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    // Add a booking to this patient's personal visit history
    public void addAppointmentToHistory(Appointment app) {
        appointmentHistory.addAppointment(app);
    }

    // Friendly display of patient profile
    public void displayInfo() {
        System.out.println("Patient ID: " + patientId +
                " | Name: " + name +
                " | Contact: " + contactInfo);
    }

    // Display patient's full appointment history
    public void displayHistory() {
        System.out.println("\n--- Visit History for " + name + " (ID: " + patientId + ") ---");
        appointmentHistory.displayAll();
    }
}