package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.engine.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static src.engine.DBManager.*;


public class LogAccessTest {

    DBManager dbManager;

    @BeforeEach
    void setup() {
        dbManager = new DBManager();
        resetDb();
    }

    @Test
    public void testLoginLogAccess() {
        login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = viewLogs();
        ArrayList<String> row = new ArrayList<>() {{
            add("1");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("1");
            add("login");
        }};
        assertEquals(row, set.get(0), "First row is valid");
    }

    @Test
    public void testViewAllLogs() {
        login("andrew03meyer", "pass");
        login("andrew03meyer", "pass");
        login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = viewLogs();
        assertEquals(3, set.size(), "All logs are shown");
    }

    @Test
    public void testViewPatientLogs() {
        login("andrew03meyer", "pass");
        viewAllPatients();
        ArrayList<ArrayList<String>> set = viewLogs();
        ArrayList<String> row1 = new ArrayList<>() {{
            add("1");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("1");
            add("login");
        }};

        ArrayList<String> row2 = new ArrayList<>() {{
            add("2");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("1");
            add("viewed all patients");
        }};

        assertEquals(row1, set.get(0), "First row is valid");
        assertEquals(row2, set.get(1), "Second row is valid");
    }

    @Test
    public void testCallumLoginLogAccess() {
        login("callum", "pass");
        ArrayList<ArrayList<String>> set = viewLogs();
        ArrayList<String> row = new ArrayList<>() {{
            add("1");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("4");
            add("login");
        }};
        assertEquals(row, set.get(0), "First row is valid");
    }

    @Test
    public void testViewOwnPatientsLogs() {
        login("andrew03meyer", "pass");
        viewOwnPatients();
        ArrayList<ArrayList<String>> set = viewLogs();
        ArrayList<String> row1 = new ArrayList<>() {{
            add("1");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("1");
            add("login");
        }};

        ArrayList<String> row2 = new ArrayList<>() {{
            add("2");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("1");
            add("viewed own patients");
        }};

        assertEquals(row1, set.get(0), "First row is valid");
        assertEquals(row2, set.get(1), "Second row is valid");
    }

    @Test
    public void testAssignNewDoctorLogs() {
        login("andrew03meyer", "pass");
        assignNewDoctor("Cal Sherv", "Amber Smith", "2021-05-01");
        ArrayList<ArrayList<String>> set = viewLogs();
        ArrayList<String> row1 = new ArrayList<>() {{
            add("1");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("1");
            add("login");
        }};

        ArrayList<String> row2 = new ArrayList<>() {{
            add("2");
            add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            add("1");
            add("assigned new doctor");
        }};

        assertEquals(row1, set.get(0), "First row is valid");
        assertEquals(row2, set.get(1), "Second row is valid");
    }

    @Test public void invalidLoginLogAccess() {
        login("andrew03meyer", "password");
        ArrayList<ArrayList<String>> set = viewLogs();

        assertEquals(0, set.size(), "Log is empty");
    }
}
