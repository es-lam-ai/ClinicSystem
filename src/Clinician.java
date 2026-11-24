// Represents a doctor/clinician working at the clinic
public class Clinician {
    private int clinicianId;
    private String name;
    private String specialty;
    private String[] availableSlots;   // Small fixed schedule for this doctor
    private WaitingQueue waitingList;  // Custom FIFO queue for patients waiting for this doctor

    // Constructor with a default fixed daily schedule
    public Clinician(int clinicianId, String name, String specialty) {
        this.clinicianId = clinicianId;
        this.name = name;
        this.specialty = specialty;
        // Small, manageable fixed schedule
        this.availableSlots = new String[]{
                "09:00 AM", "10:00 AM", "11:00 AM", "01:00 PM", "02:00 PM"
        };
        this.waitingList = new WaitingQueue();
    }

    // Getters
    public int getClinicianId() {
        return clinicianId;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String[] getAvailableSlots() {
        return availableSlots;
    }

    public WaitingQueue getWaitingList() {
        return waitingList;
    }

    // Check if a chosen time exists in the doctor's daily schedule
    public boolean isValidTimeSlot(String time) {
        for (int i = 0; i < availableSlots.length; i++) {
            if (availableSlots[i].equalsIgnoreCase(time)) {
                return true;
            }
        }
        return false;
    }

    // Friendly display of doctor's details
    public void displayInfo() {
        System.out.println("Doctor ID: " + clinicianId +
                " | Dr. " + name +
                " | Specialty: " + specialty);
    }

    // Display the doctor's fixed daily time slots
    public void displaySchedule() {
        System.out.print("Daily Hours for Dr. " + name + ": ");
        for (int i = 0; i < availableSlots.length; i++) {
            System.out.print(availableSlots[i]);
            if (i < availableSlots.length - 1) {
                System.out.print(" | ");
            }
        }
        System.out.println();
    }

    // Show patients currently waiting for this doctor
    public void displayWaitingList() {
        System.out.println("\n--- Waiting List for Dr. " + name + " (" + specialty + ") ---");
        waitingList.displayQueue();
    }
}