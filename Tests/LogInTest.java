package Tests;
import Engine.DBManager;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;



public class LogInTest {

    @Test
    public void testLoginDoctorCorrect() {
        // this tests if an actual doctor is a doctor.
        String equalsDoc1 = DBManager.login("andrew03meyer", "pass");
        // if true, the test passes.
        assertTrue("Login should be successful", equalsDoc1.equals("doc"));
    }

    @Test
    public void testLoginPatientCorrect() {
        // this tests if an actual patient is a patient.
        String equalsPat1 = DBManager.login("as1", "pass");
        // if true, the test passes.
        assertTrue("Login should be successful", equalsPat1.equals("pat"));
    }

    @Test
    public void testLoginMixed() {
        // this tests if a doctor and 2 patients are all patients.
        String equalsDoc = DBManager.login("andrew03meyer", "pass");
        String equalsPat1 = DBManager.login("as1", "pass");
        String equalsPat2 = DBManager.login("lt1", "egg");
        // if doc is true and pat1 and pat2 is true, the test passes.
        assertTrue("Login should be successful", equalsDoc.equals("doc"));
        assertFalse("Login should be successful", equalsPat1.equals("doc"));
        assertTrue("Login should be successful", equalsPat2.equals("doc"));
    }

    @Test
    public void testLoginAllDocs() {
        // this tests if 5 actual doctors are all doctors.
        String equalsDoc = DBManager.login("andrew03meyer", "pass");
        String equalsDoc2 = DBManager.login("ps1", "pass");
        String equalsDoc3 = DBManager.login("ph1", "pass");
        String equalsDoc4 = DBManager.login("jd1", "pass");
        String equalsDoc5 = DBManager.login("lt1", "egg");
        // if true, the test passes.
        assertTrue("Login should be successful", equalsDoc.equals("doc"));
        assertTrue("Login should be successful", equalsDoc2.equals("doc"));
        assertTrue("Login should be successful", equalsDoc3.equals("doc"));
        assertTrue("Login should be successful", equalsDoc4.equals("doc"));
        assertTrue("Login should be successful", equalsDoc5.equals("doc"));
    }
}


