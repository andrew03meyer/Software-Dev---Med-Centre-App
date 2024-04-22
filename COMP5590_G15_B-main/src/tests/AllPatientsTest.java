package src.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.engine.DBManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class AllPatientsTest {

    DBManager dbManager;

    @BeforeEach
    void setup() {
        dbManager = new DBManager();
    }


    /**
     * Tests that the first row is valid
     */
    @Test
    public void testRow0() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewAllPatients();
        ArrayList<String> row0 = new ArrayList<>(){{
            add("Amber");
            add("Smith");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row0, set.get(0), "First row is valid");
    }

    /**
     * Tests that the second row is valid
     */
    @Test
    public void testRow1() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewAllPatients();
        ArrayList<String> row0 = new ArrayList<>(){{
            add("Thomas");
            add("Grace");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row0, set.get(1), "Second row is valid");
    }

    /**
     * Tests that the third row is valid
     */
    @Test
    public void testRow2() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewAllPatients();
        ArrayList<String> row0 = new ArrayList<>(){{
            add("Scarlett");
            add("Johns");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row0, set.get(2), "Third row is valid");
    }

    /**
     * Tests that the fourth row is valid
     */
    @Test
    public void testRow3() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewAllPatients();
        ArrayList<String> row0 = new ArrayList<>(){{
            add("Eep");
            add("Pope");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row0, set.get(3), "Fourth row is valid");
    }

    /**
     * Tests that the fifth row is valid
     */
    @Test
    public void testRow4() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewAllPatients();
        ArrayList<String> row0 = new ArrayList<>(){{
            add("Jane");
            add("Doe");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row0, set.get(4), "Fifth row is valid");
    }

    /**
     * Tests that the last row is valid
     */
    @Test
    public void testRow5() {
        DBManager.login("andrew03meyer", "pass");
        ArrayList<ArrayList<String>> set = DBManager.viewAllPatients();
        ArrayList<String> row0 = new ArrayList<>(){{
            add("Andy");
            add("Mayor");
            add("0123245890");
            add("2, your house, your street, exampleton");
            add("bozo@skibidi.co.uk");
        }};

        assertEquals(row0, set.get(5), "Last row is valid");
    }
}
