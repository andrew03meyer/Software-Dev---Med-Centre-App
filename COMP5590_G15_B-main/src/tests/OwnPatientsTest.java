package src.tests;

import org.junit.jupiter.api.Test;
import src.engine.DBManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class OwnPatientsTest {


    /**
     * Tests that all of andrew's patients show correctly
     */
    @Test
    public void testAndrewPatients() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewOwnPatients();
        ArrayList<String> row = new ArrayList<>(){{
            add("Amber");
            add("Smith");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row, set.get(0), "First row is valid");
    }

    /**
     * Tests that all of Peter Hugh's patients show correctly
     */
    @Test
    public void testPeterHughsPatients() {
        DBManager.login("ph1", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewOwnPatients();

        assertTrue(set.isEmpty(), "Peter Hughes has no patients");
    }

    /**
     * Tests that all of Callum's patients show correctly
     */
    @Test
    public void testCallumsPatients() {
        DBManager.login("callum", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewOwnPatients();
        ArrayList<String> row = new ArrayList<>(){{
            add("Thomas");
            add("Grace");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row, set.get(0), "First row is valid");
    }

    /**
     * Tests that all of John's patients show correctly
     */
    @Test
    public void testJohnsPatients() {
        DBManager.login("jd1", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewOwnPatients();
        ArrayList<String> row = new ArrayList<>(){{
            add("Andy");
            add("Mayor");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row, set.get(0), "First row is valid");
    }

    /**
     * Tests that all of Louis' patients show correctly
     */
    @Test
    public void testLouisPatients() {
        DBManager.login("lt1", "egg");
        ArrayList<ArrayList<String>> set = DBManager.viewOwnPatients();
        ArrayList<String> row = new ArrayList<>(){{
            add("Eep");
            add("Pope");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row, set.get(0), "First row is valid");
    }
}
