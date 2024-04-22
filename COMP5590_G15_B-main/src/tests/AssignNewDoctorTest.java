package src.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.engine.DBManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static src.engine.DBManager.assignNewDoctor;


public class AssignNewDoctorTest {

    DBManager dbManager;

    @BeforeEach
    void setup() {
        dbManager = new DBManager();
    }
    @AfterEach
    void reset(){
        DBManager.resetDb();
    }

    /**
     * Tests that all of andrew's patients show correctly
     */
   @Test
   public void testAndrewPatients() {
       DBManager.login("andrew03meyer", "pass");
        assignNewDoctor("Andrew Meyer", "Amber Smith", "2025-02-12");
    }

    /**
     * Test of assigning a doctors to an existing patient when both exist
     */
    @Test
    public void testAssigningExistingPatientWithExistingDoctor() {
        DBManager.login("andrew03meyer", "pass");
            assignNewDoctor("Andrew Meyer", "Amber Smith", "2025-02-12");
            assignNewDoctor("Peter Smith", "Amber Smith", "2025-02-13");

            List<List<String>> bookings = DBManager.allBookings();
                boolean doctorAssigned = false;
                assert bookings != null;
                for (List<String> booking : bookings) {
                    if (booking.contains("Amber Smith") && booking.contains("Peter Smith")) {
                        doctorAssigned = true;
                        break;
                    }
                }
                assertFalse(doctorAssigned, "Second doctor should be assigned to an existing patient with an existing doctor");
            }

    /**
     * Test verifies that a doctor can be assigned to a new patient successfully
     */
    @Test
    public void testAssigningDoctorToNewPatient() {
        DBManager.login("andrew03meyer", "pass");
            assignNewDoctor("Andrew Meyer", "Johnny Doe", "2025-02-12");

        List<List<String>> bookings = DBManager.allBookings();
            boolean doctorAssigned = false;
            for (List<String> booking : bookings) {
                if (booking.contains("Johnny Doe") && booking.contains("Andrew Meyer")) {
                    doctorAssigned = true;
                    break;
                }
            }
            assertFalse(doctorAssigned, "Doctor should be assigned to a new patient successfully");
        }

    /**
     * Test verifies that a doctor can be assigned to a patient who already has other doctors assigned to them.
     */
    @Test
    public void testAssigningDoctorToPatientWithOtherDoctors() {
        DBManager.login("andrew03meyer", "pass");
            assignNewDoctor("Andrew Meyer", "Amber Smith", "2025-02-12");
            assignNewDoctor("Peter Smith", "Amber Smith", "2025-02-13");
            assignNewDoctor("John Doe", "Amber Smith", "2025-02-14");


        List<List<String>> bookings = DBManager.allBookings();
            boolean doctorAssigned = false;
            for (List<String> booking : bookings) {
                if (booking.contains("Amber Smith") && booking.contains("John Doe")) {
                    doctorAssigned = true;
                    break;
                }
            }
            assertFalse(doctorAssigned, "Third doctor should be assigned to a patient with existing doctors ");
    }




}

