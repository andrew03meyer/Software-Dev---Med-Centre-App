package src.tests;

import org.junit.Test;
import src.engine.DBManager;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class LoginTests {    

    @Test
    public void testLoginDoctorCorrect() {
        // this tests if an actual doctor is a doctor.
        Enum<DBManager.Person> equalsDoc1 = DBManager.login("andrew03meyer", "pass");
        // if true, the test passes.
        assertTrue("Login should be successful", equalsDoc1 == DBManager.Person.DOCTOR);
    }

    @Test
    public void testLoginDoctorIncorrect() {
        // this tests if an actual doctor is a doctor.
        Enum<DBManager.Person> equalsDoc1 = DBManager.login("andrew03meyer", "passes");
        Enum<DBManager.Person> equalsDoc2 = DBManager.login("andre03meyer", "pass");
        // if true, the test passes.
        assertFalse("Login should be unsuccessful", equalsDoc1 == DBManager.Person.DOCTOR);
        assertFalse("Login should be unsuccessful", equalsDoc2 == DBManager.Person.DOCTOR);
    }

    @Test
    public void testLoginPatientCorrect() {
        // this tests if an actual patient is a patient.
        Enum<DBManager.Person> equalsPat1 = DBManager.login("as1", "pass");
        // if true, the test passes.
        assertTrue("Login should be successful", equalsPat1 == DBManager.Person.PATIENT);
    }

    @Test
    public void testLoginPatientIncorrect() {
        // this tests if an actual patient is a patient.
        Enum<DBManager.Person> equalsPat1 = DBManager.login("as", "passes");
        Enum<DBManager.Person> equalsPat2 = DBManager.login("as1", "pas3s");
        // if true, the test passes.
        assertFalse("Login should be unsuccessful", equalsPat1 == DBManager.Person.PATIENT);
        assertFalse("Login should be unsuccessful", equalsPat2 == DBManager.Person.PATIENT);
    }

    @Test
    public void testLoginMixed() {
        // this tests if a doctor and 2 patients are all patients.
        Enum<DBManager.Person> equalsDoc = DBManager.login("andrew03meyer", "pass");
        Enum<DBManager.Person> equalsPat1 = DBManager.login("as1", "pass");
        Enum<DBManager.Person> equalsPat2 = DBManager.login("lt1", "egg");
        // if doc is true and pat1 and pat2 is true, the test passes.
        assertTrue("Login should be successful", equalsDoc == DBManager.Person.DOCTOR);
        assertFalse("Login should be unsuccessful", equalsPat1 == DBManager.Person.DOCTOR);
        assertTrue("Login should be successful", equalsPat2 == DBManager.Person.DOCTOR);
    }

    @Test
    public void testLoginAllDocs() {
        // this tests if 5 actual doctors are all doctors.
        Enum<DBManager.Person> equalsDoc = DBManager.login("andrew03meyer", "pass");
        Enum<DBManager.Person> equalsDoc2 = DBManager.login("ps1", "pass");
        Enum<DBManager.Person> equalsDoc3 = DBManager.login("ph1", "pass");
        Enum<DBManager.Person> equalsDoc4 = DBManager.login("jd1", "pass");
        Enum<DBManager.Person> equalsDoc5 = DBManager.login("lt1", "egg");
        // if true, the test passes.
        assertTrue("Login should be successful", equalsDoc == DBManager.Person.DOCTOR);
        assertTrue("Login should be successful", equalsDoc2 == DBManager.Person.DOCTOR);
        assertTrue("Login should be successful", equalsDoc3 == DBManager.Person.DOCTOR);
        assertTrue("Login should be successful", equalsDoc4 == DBManager.Person.DOCTOR);
        assertTrue("Login should be successful", equalsDoc5 == DBManager.Person.DOCTOR);
    }

    @Test(expected = NullPointerException.class)
    public void testUsernameIsNull() {
        // expects NullPointerException to be thrown when a null username is put into login.
        DBManager.login(null, "pass");
        // no more assertions needed, test passes if the line above throws a NullPointerException.
    }

    @Test(expected = NullPointerException.class)
    public void testPasswordIsNull() {
        // expects NullPointerException to be thrown when a null password is put into login.
        DBManager.login("andrew03meyer", null);
        // no more assertions needed, test passes if the line above throws a NullPointerException.
    }

    @Test(expected = NullPointerException.class)
    public void testUsernameAndPasswordIsNull() {
        // expects NullPointerException to be thrown when a null username and password is put into login.
        DBManager.login(null, null);
        // no more assertions needed, test passes if the line above throws a NullPointerException.
    }
}