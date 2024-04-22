package src.tests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import src.engine.DBManager;
import java.util.ArrayList;


public class PastVisitsTests {

    DBManager dbManager;

    @BeforeEach
    void setup() {
        dbManager = new DBManager();
    }

    // valid input, all fields are filled
    @Test
    void validEnterPastVisitDetailsTest() {
        try {
            DBManager.enterPastVisitDetails("Amber Smith", "2023-05-25", "Follow-up", "None");
            assertTrue(true, "correctly entered past visit details");
        } catch (Exception e) {
            fail("Method threw an exception: " + e.getMessage());
        }
        DBManager.resetDb();
    }
    // invalid input, patient name is empty
    @Test
    void emptyNameEnterPastVisitDetailsTest() {
        try {
            DBManager.enterPastVisitDetails("", "2023-05-26", "check-up", "None");
            fail("Method did not throw an exception");
        } catch (Exception SQLException) {
            assertTrue(true, "correctly threw an exception");
        }
    }

    // invalid input, only first name is entered
    @Test
    void noSurnamePastVisitDetailsTest() {
        try {
            DBManager.enterPastVisitDetails("Amber", "2023-05-27", "check-up", "None");
            fail("Method did not throw an exception");
        } catch (Exception SQLException) {
            assertTrue(true, "correctly threw an exception");
        }
        DBManager.resetDb();
    }

    @Test
    void editPastVisitDetailsPrescriptionsTest() {
        DBManager.enterPastVisitDetails("John Doe", "2023-05-25", "Follow-up", "Old Prescription");
        DBManager.login("andrew03meyer", "pass");
        DBManager.updateVisitDetails("1", "New Comments", "New Prescription");

        ArrayList<ArrayList<String>> visitDetails = DBManager.getVisitDetails();
        boolean FoundDetails = false;
        for (ArrayList<String> visitDetail : visitDetails) {
            String comments = visitDetail.get(4);
            String prescription = visitDetail.get(5);
            if ("New Comments".equals(comments) && "New Prescription".equals(prescription)) {
                FoundDetails = true;
            }
        }
        assertTrue(FoundDetails, "Updated details found in visit details");
        DBManager.resetDb();
    }

    @Test
    void viewPastVisitDetailsAndPrescriptionsTest() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> visitDetails = DBManager.getVisitDetails();

        assertNotNull(visitDetails, "No past visit details found");

        if (!visitDetails.isEmpty()) {
            ArrayList<String> latestVisit = visitDetails.get(0);
            assertEquals("Amber smith", latestVisit.get(2) + " " + latestVisit.get(3), "Patient name mismatch");
            assertEquals("2022-02-10", latestVisit.get(1), "Visit date mismatch");
            assertEquals("e", latestVisit.get(4), "Comments mismatch");
            assertEquals("x", latestVisit.get(5), "Prescription mismatch");
        }
    }
}
