// Represents a single clinic appointment
public class Appointment {
    private int appointmentId;
    private int patientId;
    private int clinicianId;
    private String date;   // Format: YYYY-MM-DD (helps with sorting later)
    private String time;   // Example: "10:00 AM"
    private String status; // "Upcoming", "Completed", or "Canceled"

    // Constructor to create a new appointment
    public Appointment(int appointmentId, int patientId, int clinicianId, String date, String time) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.date = date;
        this.time = time;
        this.status = "Upcoming"; // Every new booking starts as Upcoming
    }

    // Getters
    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getClinicianId() {
        return clinicianId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }

    // Setters (used when rescheduling or updating status)
    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Friendly display format for patients and staff
    public void displayInfo() {
        System.out.println("Appointment #" + appointmentId +
                " | Date: " + date + " at " + time +
                " | Patient ID: " + patientId +
                " | Doctor ID: " + clinicianId +
                " | Status: [" + status + "]");
    }
}