// Main controller class that manages patients, doctors, bookings, waiting lists, and reports
public class ClinicSystem {
    private PatientBST patients;
    private ClinicianBST clinicians;
    private AppointmentLinkedList allAppointments;
    private int nextAppointmentId; // Auto-generates unique appointment IDs

    public ClinicSystem() {
        this.patients = new PatientBST();
        this.clinicians = new ClinicianBST();
        this.allAppointments = new AppointmentLinkedList();
        this.nextAppointmentId = 1001;
    }

    // --- 1. Patient Management ---

    public void addPatient(int id, String name, String contact) {
        Patient newPatient = new Patient(id, name, contact);
        if (patients.insert(newPatient)) {
            System.out.println("Welcome, " + name + "! Your patient profile (ID: " + id + ") is ready.");
        } else {
            System.out.println("Sorry, a patient with ID " + id + " already exists.");
        }
    }

    public Patient findPatient(int id) {
        return patients.search(id);
    }

    public void updatePatientContact(int id, String newContact) {
        Patient patient = patients.search(id);
        if (patient == null) {
            System.out.println("We couldn't find a patient with ID " + id + ".");
            return;
        }
        patient.setContactInfo(newContact);
        System.out.println("Updated! " + patient.getName() + "'s contact info is now: " + newContact);
    }

    public void displayAllPatients() {
        patients.displayAll();
    }

    // --- 2. Clinician Management ---

    public void addClinician(int id, String name, String specialty) {
        Clinician newDoc = new Clinician(id, name, specialty);
        if (clinicians.insert(newDoc)) {
            System.out.println("Dr. " + name + " (" + specialty + ") has been added to our clinic.");
        } else {
            System.out.println("Sorry, a doctor with ID " + id + " already exists.");
        }
    }

    public Clinician findClinician(int id) {
        return clinicians.search(id);
    }

    public void displayAllClinicians() {
        clinicians.displayAll();
    }

    // --- 3. Booking, Rescheduling & Canceling ---

    // Returns true if booked immediately, false if slot was taken (and patient was added to waiting list)
    public void bookAppointment(int patientId, int clinicianId, String date, String time) {
        Patient patient = patients.search(patientId);
        if (patient == null) {
            System.out.println("We couldn't find your patient profile (ID: " + patientId + "). Please register first.");
            return;
        }

        Clinician doctor = clinicians.search(clinicianId);
        if (doctor == null) {
            System.out.println("We couldn't find a doctor with ID " + clinicianId + ".");
            return;
        }

        // Check if the requested time is part of the doctor's daily hours
        if (!doctor.isValidTimeSlot(time)) {
            System.out.println("Sorry, " + time + " is outside Dr. " + doctor.getName() + "'s working hours.");
            doctor.displaySchedule();
            return;
        }

        // Check for time conflicts
        if (allAppointments.isSlotBooked(clinicianId, date, time)) {
            System.out.println("Sorry, Dr. " + doctor.getName() + " is already booked on " + date + " at " + time + ".");
            doctor.getWaitingList().enqueue(patient);
            System.out.println("No worries! " + patient.getName() + " has been added to Dr. " +
                    doctor.getName() + "'s waiting list. We will let you know as soon as a slot opens up.");
            return;
        }

        // Confirm booking and add to both clinic records and patient history
        Appointment newApp = new Appointment(nextAppointmentId++, patientId, clinicianId, date, time);
        allAppointments.addAppointment(newApp);
        patient.addAppointmentToHistory(newApp);

        System.out.println("Booking confirmed! Here are your details:");
        newApp.displayInfo();
    }

    // Cancel an appointment and offer the freed slot to the next waiting patient
    public void cancelAppointment(int appointmentId) {
        Appointment app = allAppointments.findById(appointmentId);
        if (app == null) {
            System.out.println("We couldn't find Appointment #" + appointmentId + ".");
            return;
        }

        if (!app.getStatus().equalsIgnoreCase("Upcoming")) {
            System.out.println("Appointment #" + appointmentId + " is already " + app.getStatus() + ".");
            return;
        }

        // Mark as canceled
        app.setStatus("Canceled");
        System.out.println("Appointment #" + appointmentId + " has been canceled.");

        // Check if anyone is waiting for this doctor and offer them the open slot
        offerSlotToWaitingPatient(app.getClinicianId(), app.getDate(), app.getTime());
    }

    // Reschedule an upcoming appointment to a new date and time
    public void rescheduleAppointment(int appointmentId, String newDate, String newTime) {
        Appointment app = allAppointments.findById(appointmentId);
        if (app == null) {
            System.out.println("We couldn't find Appointment #" + appointmentId + ".");
            return;
        }

        if (!app.getStatus().equalsIgnoreCase("Upcoming")) {
            System.out.println("Only upcoming appointments can be rescheduled.");
            return;
        }

        Clinician doctor = clinicians.search(app.getClinicianId());
        if (!doctor.isValidTimeSlot(newTime)) {
            System.out.println("Sorry, " + newTime + " is outside Dr. " + doctor.getName() + "'s working hours.");
            doctor.displaySchedule();
            return;
        }

        if (allAppointments.isSlotBooked(app.getClinicianId(), newDate, newTime)) {
            System.out.println("Sorry, Dr. " + doctor.getName() + " is already booked on " + newDate + " at " + newTime + ".");
            return;
        }

        // Save old slot details before updating
        String oldDate = app.getDate();
        String oldTime = app.getTime();

        // Update appointment
        app.setDate(newDate);
        app.setTime(newTime);
        System.out.println("Your appointment has been rescheduled! New details:");
        app.displayInfo();

        // Offer the freed old slot to the next patient on the waiting list
        offerSlotToWaitingPatient(app.getClinicianId(), oldDate, oldTime);
    }

    // Mark an upcoming appointment as Completed after the patient's visit
    public void completeAppointment(int appointmentId) {
        Appointment app = allAppointments.findById(appointmentId);
        if (app == null) {
            System.out.println("We couldn't find Appointment #" + appointmentId + ".");
            return;
        }

        if (!app.getStatus().equalsIgnoreCase("Upcoming")) {
            System.out.println("Appointment #" + appointmentId + " is already marked as " + app.getStatus() + ".");
            return;
        }

        app.setStatus("Completed");
        System.out.println("Appointment #" + appointmentId + " is now marked as Completed. Wishing the patient good health!");
    }

    // Helper: Automatically offers a freed slot to the first patient in the doctor's waiting queue
    private void offerSlotToWaitingPatient(int clinicianId, String date, String time) {
        Clinician doctor = clinicians.search(clinicianId);
        if (doctor != null && !doctor.getWaitingList().isEmpty()) {
            Patient nextPatient = doctor.getWaitingList().dequeue();
            Appointment autoBooked = new Appointment(nextAppointmentId++, nextPatient.getPatientId(), clinicianId, date, time);
            allAppointments.addAppointment(autoBooked);
            nextPatient.addAppointmentToHistory(autoBooked);

            System.out.println("\n>>> Good news for the waiting list! <<<");
            System.out.println("The open slot (" + date + " at " + time + ") with Dr. " + doctor.getName() +
                    " has been offered and booked for " + nextPatient.getName() + " (ID: " + nextPatient.getPatientId() + ").");
            autoBooked.displayInfo();
        }
    }

    // --- 4. Reports & Sorted Schedules ---

    public void showAllAppointmentsSorted() {
        System.out.println("\n--- All Clinic Appointments (Sorted by Date & Time) ---");
        allAppointments.sortByDate();
        allAppointments.displayAll();
    }

    public void showReportByDate(String date) {
        System.out.println("\n--- Appointments on " + date + " ---");
        allAppointments.sortByDate();
        allAppointments.displayByDate(date);
    }

    public void showReportByClinician(int clinicianId) {
        Clinician doc = clinicians.search(clinicianId);
        if (doc == null) {
            System.out.println("We couldn't find a doctor with ID " + clinicianId + ".");
            return;
        }
        System.out.println("\n--- Appointments for Dr. " + doc.getName() + " ---");
        allAppointments.sortByDate();
        allAppointments.displayByClinician(clinicianId);
    }

    public void showReportByStatus(String status) {
        System.out.println("\n--- " + status + " Appointments ---");
        allAppointments.sortByDate();
        allAppointments.displayByStatus(status);
    }

    public void showSummaryCounts() {
        allAppointments.displayStatusCounts();
    }

    // --- 5. Sample Data for Quick Testing ---

    public void loadSampleData() {
        // Sample Doctors
        clinicians.insert(new Clinician(201, "Ahmed Hassan", "Cardiology"));
        clinicians.insert(new Clinician(202, "Mona El-Sayed", "Pediatrics"));
        clinicians.insert(new Clinician(203, "Khaled Omar", "Dermatology"));

        // Sample Patients
        patients.insert(new Patient(101, "Omar Ali", "01012345678"));
        patients.insert(new Patient(102, "Sara Mahmoud", "01198765432"));
        patients.insert(new Patient(103, "Youssef Adel", "01255544433"));
    }
}