package src.tests;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import src.engine.DBManager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static org.junit.Assert.*;


public class DatabaseTests {
    String regexPattern = "^(0[1-9]|1[0-2])-(19|20)\\d{2}$"; // Expression for MM-YYYY format

    @BeforeEach
    public void dbSetup(){
        DBManager.resetDb();
    }

    @Test //tests that the format of the date is correct
    public void testFormat() {
        List<List<String>> resultList = DBManager.bookingsQuery("03-2024");
        for (List<String> row : resultList) {
            String date = row.get(3);
            assertFalse("The date format is valid", isValidDateFormat(date));
        }
    }

    @Test //tests if the given date has any bookings
    public void testExistingDate() {
        List<List<String>> DateInData = DBManager.bookingsQuery("03-2024");
        assertTrue("This date has no bookings", DateInData.isEmpty());
    }

    //tests if the given date is in the correct format
    private boolean isValidDateFormat(String date) {
        Pattern pattern = Pattern.compile(regexPattern);
        return pattern.matcher(date).matches();
    }

    //checks if there is a null date in the bookingsQuery
    @Test(expected = NullPointerException.class)
    public void testDateIsNull() {
        // expects NullPointerException to be thrown when a null date is put into bookingsQuery.
        DBManager.bookingsQuery(null);
        // no more assertions needed, test passes if the line above throws a NullPointerException.
    }

    @Test // checks if january has 31 days
    public void testMaxDayLow() {
        assertEquals(31, DBManager.getMaxDay("01"));
    }

    @Test // checks if november has 30 days
    public void testMaxDayHigh() {
        assertEquals(30, DBManager.getMaxDay("11"));
    }

    @Test // checks if the month is invalid
    public void testInvalidMonthNegative() {
        assertEquals(-1, DBManager.getMaxDay("-01"));
    }

    @Test // checks february has 28 days
    public void testFebruaryNonLeapYear() {
        assertEquals(28, DBManager.getMaxDay("02"));
    }

    @Test // checks if -1 is returned for a month above 12
    public void testInvalidMonthAbove12() {
        assertEquals(-1, DBManager.getMaxDay("13"));
    }

    @Test // checks if the appropriate visit details are displayed for callum
    public void testViewBookingCallum() {
        DBManager.login("callum", "pass");
        ArrayList<ArrayList<String>> sampleTable = new ArrayList<>();

        assertEquals(sampleTable, DBManager.getVisitDetails());
    }

    @Test // checks if the appropriate visit details are displayed for john
    public void testViewBookingJohn() {
        DBManager.login("jd1", "pass");
        ArrayList<ArrayList<String>> sampleTable = new ArrayList<>();

        assertEquals(sampleTable, DBManager.getVisitDetails());
    }

    @Test // checks if the appropriate visit details are displayed for louis
    public void testViewBookingLouis() {
        DBManager.login("lt1", "egg");
        ArrayList<ArrayList<String>> sampleTable = new ArrayList<>();

        assertEquals(sampleTable, DBManager.getVisitDetails());
    }

    @Test
    public void testEnterBookingAndrew(){
        DBManager.login("andrew03meyer", "pass");
    }
}


