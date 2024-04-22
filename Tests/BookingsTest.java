package Tests;
import Engine.DBManager;
import org.junit.Test;
import java.util.List;
import java.util.regex.Pattern;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
// import static org.junit.Assert.assertEquals;

public class BookingsTest {
    String regexPattern = "^(0[1-9]|1[0-2])-(19|20)\\d{2}$"; // Expression for MM-YYYY format
    
    @Test
    public void testFormat() {
        List<List<String>> resultList = DBManager.bookingsQuery("03-2024");
        for (List<String> row : resultList) {
            String date = row.get(3);
            assertTrue("The date format is valid", isValidDateFormat(date));
        }
    }

    @Test
    public void testExistingDate() {
        List<List<String>> DateInData = DBManager.bookingsQuery("03-2024");
        assertFalse("This date has no bookings", DateInData.isEmpty());
    }
    
    private boolean isValidDateFormat(String date) {
        Pattern pattern = Pattern.compile(regexPattern);
        return pattern.matcher(date).matches();
    }

    @Test
    public void dateIsNull() {
        List<List<String>> resultList = DBManager.bookingsQuery(null);
        assertTrue("No results returned for empty date", resultList.isEmpty());
    }
}
