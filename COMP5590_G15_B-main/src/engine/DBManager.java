package src.engine;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class DBManager {
    private static String docId;
    private Connection connection;
    private Statement statement;

    /**
     * Enum to store the type of person logging in
     */
    public enum Person {
        DOCTOR, PATIENT, NONE
    }

    //---------------------------------------//
    //		  Functionality Methods			 //
    //---------------------------------------//

    /**
     * Method to check the bookings table is valid (checks the dates on bookings are in the future)
     * if not, move them to past visits with null comments and prescription attributes
     * run on program execution
     */
    public static void validateBookings() {
        DBManager db = new DBManager();
        db.testConnection();

        String query = "SELECT BID, DID, PID, Date FROM gpManagementSystem . bookings AS bks WHERE bks.date < current_date();";

        try {
            // Items to transfer
            ResultSet resultSet = db.statement.executeQuery(query);
            while (resultSet.next()) {
                ArrayList<String> values = new ArrayList<>();
                values.add(resultSet.getString(1)); // BID
                values.add(resultSet.getString(2)); // DID
                values.add(resultSet.getString(3)); // PID
                values.add(resultSet.getString(4)); // Date

                // Transfer to past_visits
                String insertQuery = "INSERT INTO gpManagementSystem.past_visits(Date, DID, PID) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = db.connection.prepareStatement(insertQuery);
                insertStatement.setString(1, values.get(3));
                insertStatement.setInt(2, Integer.parseInt(values.get(1)));
                insertStatement.setInt(3, Integer.parseInt(values.get(2)));
                insertStatement.executeUpdate();
                insertStatement.close();

                // Remove from bookings
                String deleteQuery = "DELETE FROM gpManagementSystem.bookings WHERE BID = ?";
                PreparedStatement deleteStatement = db.connection.prepareStatement(deleteQuery);
                deleteStatement.setInt(1, Integer.parseInt(values.get(0)));
                deleteStatement.executeUpdate();
                deleteStatement.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        db.closeConnection();
    }


    /**
     * A method used in LogInUI that checks whether the user's login details match the database
     *
     * @param username the username that was inputted in the UI
     * @param password the password that was inputted in the UI
     * @return 'doc', 'pat' or 'fail' depending on if the details match a doctor, patient or none
     */
    public static Enum<DBManager.Person> login(String username, String password) {
        // Error checking
        if (username == null || password == null) {
            throw new NullPointerException("Username or password is null");
        }

        // Check if the username and password are correct
        DBManager db = new DBManager();
        db.testConnection();

        String query = "select * from gpManagementSystem . doctors where username = ? and password = SHA(?)";
        try (PreparedStatement loginStatementDoc = db.connection.prepareStatement(query)){
            //Login query
            loginStatementDoc.setString(1, username);
            loginStatementDoc.setString(2, password);
            ResultSet resultSet = loginStatementDoc.executeQuery();


            // Successful login
            if (resultSet.next()) {
                //Set attbribute docId - used for other methods
                docId = resultSet.getString(1);

                //Close connections
                loginStatementDoc.close();
                resultSet.close();
                db.closeConnection();

                //Log the access
                logAccess("login");

                return Person.DOCTOR;
            } else {
                //Close connections
                loginStatementDoc.close();
                resultSet.close();
            }
        }
        catch (SQLException e) {
            System.err.println("Can't login: " + e.getMessage());
        }


        // Patient login query
        query = "select * from gpManagementSystem . patients where username = ? and password = SHA(?)";

        //Login query
        try(PreparedStatement loginStatementPat = db.connection.prepareStatement(query)){
            loginStatementPat.setString(1, username);
            loginStatementPat.setString(2, password);
            ResultSet resultSetPat = loginStatementPat.executeQuery();

            // Successful login
            if (resultSetPat.next()) {
                //Close connections
                loginStatementPat.close();
                resultSetPat.close();
                db.closeConnection();

                return Person.PATIENT;
            } else {
                //Close connections
                loginStatementPat.close();
                resultSetPat.close();
            }
        } catch (SQLException e) {
            System.err.println("Can't login: " + e.getMessage());
        }

        db.closeConnection();
        return Person.NONE;
    }


    /**
     * Runs a query that gets all future bookings in a given month and year
     *
     * @param date the date that was inputted (in MM-yyyy format)
     * @return a list of the data found with the query or null if nothing was found
     */
    public static List<List<String>> bookingsQuery(String date) {
        String[] dateSplit = new String[2];
        dateSplit[0] = "0";
        dateSplit[1] = "0";

        //Date error checking
        if(date.contains("-")){
            dateSplit = date.split("-");
        }   
        else{
            return null;
        }
        
        //Get max day + error checking
        int maxDay = getMaxDay(dateSplit[0]);
        if(maxDay == -1){
            return null;
        }

		DBManager db = new DBManager();
		db.testConnection();

		// Execute query
		String query = "SELECT doc.F_Name, doc.L_Name, pat.F_Name, pat.L_Name, book.Date FROM gpManagementSystem.bookings AS book JOIN gpManagementSystem.doctors AS doc ON doc.DID = book.DID JOIN gpManagementSystem.patients AS pat ON pat.pid = book.pid WHERE (book.Date >= ?) && book.date <= ? AND doc.DID = ?;";

		try(PreparedStatement select = db.connection.prepareStatement(query)) {
            
            select.setString(1, dateSplit[1] + "-" + dateSplit[0] + '-' + "01");
            select.setString(2, dateSplit[1] + "-" + dateSplit[0] + '-' + maxDay);
            select.setString(3, docId);

			// If success
			ResultSet resultSet = select.executeQuery();
			List<List<String>> bookings = new ArrayList<>();

			// Store results into an arraylist
			int count = 0;
			while (resultSet.next()) {
				bookings.add(new ArrayList<>());
				String docFirstName = resultSet.getString(1).substring(0,1).toUpperCase() + resultSet.getString(1).substring(1);
                bookings.get(count).add(docFirstName);
                String docLastName = resultSet.getString(2).substring(0,1).toUpperCase() + resultSet.getString(2).substring(1);
				bookings.get(count).add(docLastName);
                String patFirstName = resultSet.getString(3).substring(0,1).toUpperCase() + resultSet.getString(3).substring(1);
				bookings.get(count).add(patFirstName);
                String patLastName = resultSet.getString(4).substring(0,1).toUpperCase() + resultSet.getString(4).substring(1);
				bookings.get(count).add(patLastName);
				bookings.get(count).add(resultSet.getString(5)); // DATE
				count++;
			}

			// Return result
			db.closeConnection();

            //Log the access
            logAccess("view bookings");

			return bookings;

		} catch (SQLException e) {
			// Error with query or database, or nothing was found
			System.err.println("Can't search for bookings: " + e.getMessage());
			db.closeConnection();
			return null;
		}
    }

    /**
     * Method to get all bookings for a specific doctor
     * @return 2D List of results from the query
     */
    public static List<List<String>> allBookings(){
        //Variables
        List<List<String>> bookings = new ArrayList<>();
        String query = "SELECT doc.F_Name, doc.L_Name, pat.F_Name, pat.L_Name, book.Date FROM gpManagementSystem.bookings AS book JOIN gpManagementSystem.doctors AS doc ON doc.DID = book.DID JOIN gpManagementSystem.patients AS pat ON pat.pid = book.pid WHERE book.did = ?";
        
        //Database setup
        DBManager db = new DBManager();
        db.testConnection();

        try(PreparedStatement selectAll = db.connection.prepareStatement(query)){
            selectAll.setString(1, docId);
            
			// If success
			ResultSet resultSet = selectAll.executeQuery();

			// Store results into an arraylist
			int count = 0;
			while (resultSet.next()) {
				bookings.add(new ArrayList<>());
				String docFirstName = resultSet.getString(1).substring(0,1).toUpperCase() + resultSet.getString(1).substring(1);
                bookings.get(count).add(docFirstName);
                String docLastName = resultSet.getString(2).substring(0,1).toUpperCase() + resultSet.getString(2).substring(1);
				bookings.get(count).add(docLastName);
                String patFirstName = resultSet.getString(3).substring(0,1).toUpperCase() + resultSet.getString(3).substring(1);
				bookings.get(count).add(patFirstName);
                String patLastName = resultSet.getString(4).substring(0,1).toUpperCase() + resultSet.getString(4).substring(1);
				bookings.get(count).add(patLastName);
				bookings.get(count).add(resultSet.getString(5)); // DATE
				count++;
			}
        }
        catch(SQLException e){
            e.printStackTrace();
            return null;
        }
        return bookings;
    }


    /**
     * Gets past visits, from that past_visit table
     *
     * @return 2D array of BID, F_Name, L_Name, Comments, Prescriptions given a doctor ID
     * doctorID stored on login
     */
    public static ArrayList<ArrayList<String>> getVisitDetails() {
        ArrayList<ArrayList<String>> queryResults = new ArrayList<>();
        String query = "SELECT VID, Date, pts.F_name, pts.L_Name, Comments, Prescriptions FROM gpManagementSystem . past_visits AS PV JOIN gpManagementSystem . patients AS pts ON pts.PID = PV.PID WHERE PV.DID = '" + docId + "';";

        DBManager db = new DBManager();

        try {
            db.testConnection();
            ResultSet results = db.statement.executeQuery(query);

            int count = 0;
            while (results.next()) {
                queryResults.add(new ArrayList<>());
                queryResults.get(count).add(results.getString(1)); // VID
                String fName = results.getString(2).substring(0,1).toUpperCase() + results.getString(2).substring(1);
                queryResults.get(count).add(fName); // Pat F_Name
                String lName = results.getString(3).substring(0,1).toUpperCase() + results.getString(3).substring(1);
                queryResults.get(count).add(lName); // Pat L_Name
                queryResults.get(count).add(results.getString(4)); // Date
                queryResults.get(count).add(results.getString(5)); // Comments
                queryResults.get(count).add(results.getString(6)); // Prescriptions
                count++;
            }

            //Log the access
            logAccess("view past visit details");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.closeConnection();

        return queryResults;
    }


    /**
     * Edits a pre-existing, past visit based on visit ID
     * @params vid, comments, and prescriptions
     */
    public static void updateVisitDetails(String vid, String comments, String prescriptions) {
        String query = "UPDATE gpManagementSystem.past_visits AS bks SET comments = ?, prescriptions = ? WHERE VID = ?;";

        DBManager db = new DBManager();
        db.testConnection();

        try(PreparedStatement update = db.connection.prepareStatement(query)) {
            update.setString(1, comments);
            update.setString(2, prescriptions);
            update.setString(3, vid);
            update.executeUpdate();

            //Log the access
            logAccess("updated past visit details");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        db.closeConnection();
    }


    /**
     * Enter new past visit details
     * Inserts details into past_visits and removes it from bookings (only if entry is found in bookings)
     * @param patients full name (provided by dropdown), date, comments, prescription
     */
    public static void enterPastVisitDetails(String pat_name, String date, String comments, String prescription) {
        String query;

        DBManager db = new DBManager();
        db.testConnection();


        //Gets PID from full name
        String[] name = nameStringToArray(pat_name);

        //Get PID
        String PID = getPID(name);
		//Error checking
		if(PID.equals("")){
			return;
		}

        //check item exists
        Boolean exists = false;
        query = "SELECT * FROM gpManagementSystem.bookings WHERE PID = ? AND date = ?;";
        try (PreparedStatement deleteStatement = db.connection.prepareStatement(query)) {
            deleteStatement.setString(1, PID);
            deleteStatement.setString(2, date);
            ResultSet result = deleteStatement.executeQuery();

            if (result.next()) {
                exists = true;
            } else {
                System.out.println("item does not exist");
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }


        if (exists) {
            //insert data into past_visits
            query = "INSERT INTO gpManagementSystem.past_visits(date, did, pid, comments, prescriptions) VALUES (?, ?, ?, ?, ?);";

            try (PreparedStatement ins = db.connection.prepareStatement(query)){
                ins.setString(1, date);
                ins.setString(2, docId);
                ins.setString(3, PID);
                ins.setString(4, comments);
                ins.setString(5, prescription);
                ins.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();;
            }


            //Delete data from Bookings
            String deleteQuery = "DELETE FROM gpManagementSystem.bookings WHERE PID = ? AND date = ?;";
            try (PreparedStatement deleteStatement = db.connection.prepareStatement(deleteQuery)) {
                deleteStatement.setString(1, PID);
                deleteStatement.setString(2, date);
                deleteStatement.executeUpdate();
                
                //Log the access
                logAccess("entered visit info for a booking");

            } catch (SQLException e) {
                e.printStackTrace();;
            }
        }

        db.closeConnection();
    }

    /**
     * View all patients' (in the database) information
     * @return ArrayList<ArrayList<String>> Each row and their respective information
     */

     public static ArrayList<ArrayList<String>> viewAllPatients(){
        ArrayList<ArrayList<String>> patients = new ArrayList<>();

        //DB Setup
        DBManager db = new DBManager();
        String query = "SELECT f_name, l_name, p_number, address, email FROM gpManagementSystem . patients;";
        
        try {
            db.testConnection();
            ResultSet results = db.statement.executeQuery(query);

            int count = 0;
            while (results.next()) {
                patients.add(new ArrayList<>());
                String patFirstName = results.getString(1).substring(0,1).toUpperCase() + results.getString(1).substring(1);
				patients.get(count).add(patFirstName);
                String patLastName = results.getString(2).substring(0,1).toUpperCase() + results.getString(2).substring(1);
				patients.get(count).add(patLastName);
                patients.get(count).add(results.getString(3)); // p_number
                patients.get(count).add(results.getString(4)); // address
                patients.get(count).add(results.getString(5)); // email
                count++;
            }

            //Log the access
            logAccess("viewed all patients");

        } catch (SQLException e) {
            System.err.println(e);
        }

        db.closeConnection();


        return patients;
     }

    /**
     * View a specific doctor's patients' information (determined by who is logged in)
     * @return ArrayList<ArrayList<String>> Each row and their respective information
     */
     public static ArrayList<ArrayList<String>> viewOwnPatients(){
        ArrayList<ArrayList<String>> patients = new ArrayList<>();

        //DB Setup
        DBManager db = new DBManager();
        String query = "SELECT DISTINCT p.f_name, p.l_name, p.p_number, p.address, p.email FROM gpManagementSystem.patients AS p JOIN gpManagementSystem.bookings AS b ON b.PID = p.PID JOIN gpManagementSystem.doctors AS d ON d.DID = b.DID WHERE d.did = ?;";
        db.testConnection();

        try (PreparedStatement sel = db.connection.prepareStatement(query)){
            sel.setInt(1, Integer.parseInt(docId));
            ResultSet results = sel.executeQuery();

            int count = 0;
            while (results.next()) {
                patients.add(new ArrayList<>());
                String patFirstName = results.getString(1).substring(0,1).toUpperCase() + results.getString(1).substring(1);
				patients.get(count).add(patFirstName);
                String patLastName = results.getString(2).substring(0,1).toUpperCase() + results.getString(2).substring(1);
				patients.get(count).add(patLastName);
                patients.get(count).add(results.getString(3)); // p_number
                patients.get(count).add(results.getString(4)); // address
                patients.get(count).add(results.getString(5)); // email
                count++;
            }

            //Log the access
            logAccess("viewed own patients");

        } catch (SQLException e) {
            System.err.println(e);
        }

        db.closeConnection();


        return patients;
     }

     /**
      * Changes the current doctor, for that booking, to the new one
      * @param doctor_name - new doctor for that booking
      * @param patient_name - the patient for that booking
      * @param date - the date for that booking
      */
     public static void assignNewDoctor(String doctor_name, String patient_name, String date){
        
        //Name manipulations
        String[] doc_name = nameStringToArray(doctor_name);
        String[] pat_name = nameStringToArray(patient_name);

        //Get the doctor ID
        String DID = getDID(doc_name);
        //Get patient ID
        String PID = getPID(pat_name);

        //If both PID and DID are set
        if(!DID.equals("") && !PID.equals("")){
            //Update to new doctor ID
            DBManager db = new DBManager();
            db.testConnection();
            String query = "UPDATE gpManagementSystem . bookings SET did = ? WHERE pid = ? AND date = ?";

            try(PreparedStatement udDID= db.connection.prepareStatement(query)){
                udDID.setInt(1, Integer.parseInt(DID));
                udDID.setInt(2, Integer.parseInt(PID));
                udDID.setString(3, date);

                udDID.executeUpdate();

                //Log the access
                logAccess("assigned new doctor");
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Logs actions from a user in a table
     * Method called when the user accesses any functionality
     * @param item
     */
    public static void logAccess(String item){

        //DB Setup
        DBManager db = new DBManager();
        String query = "INSERT INTO gpManagementSystem.access_log (time, DID, Action) VALUES (CURRENT_TIMESTAMP(), ?, ?);";
        db.testConnection();

        try (PreparedStatement log = db.connection.prepareStatement(query)){
            log.setInt(1, Integer.parseInt(docId));
            log.setString(2, item);
            log.executeUpdate();
        } 
        catch (SQLException e) {
            System.err.println(e);
        }

        db.closeConnection();
    }

    /**
     * Prints all the access logs
     */
    public static ArrayList<ArrayList<String>> viewLogs(){
        ArrayList<ArrayList<String>> logs = new ArrayList<>();

        //DB Setup
        DBManager db = new DBManager();
        String query = "SELECT * FROM gpManagementSystem . access_log;";
        
        try {
            db.testConnection();
            ResultSet results = db.statement.executeQuery(query);

            int count = 0;
            while (results.next()) {
                logs.add(new ArrayList<>());
                logs.get(count).add(results.getString(1)); // LID
                logs.get(count).add(results.getString(2)); // Time
                logs.get(count).add(results.getString(3)); // DID
                logs.get(count).add(results.getString(4)); // action
                count++;
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        db.closeConnection();


        return logs;
     }


	//---------------------------------------//
	//				Helper Methods			 //
	//---------------------------------------//

	/**
	 * Run config file
	 */
	public static void resetDb(){
		String strucQuery = "";
        String dataQuery = "";

        //Database items
		DBManager db = new DBManager();
		db.testConnection();

        try {
            // Structure file
            File sqlFile = new File("config/config.sql");
            Scanner sqlScanner = new Scanner(sqlFile);
            while(sqlScanner.hasNext()){
                String item = sqlScanner.nextLine();
                if(!item.isEmpty()){
                    strucQuery += item + " ";
                }
            }
            sqlScanner.close();
            
            // Run structure SQL
            try (PreparedStatement config = db.connection.prepareStatement(strucQuery)) {
                config.execute();
            }
        
            // Reset strucQuery
            strucQuery = "";
        
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        try {
            // Data file
            File sqlFile = new File("config/data.sql");
            Scanner sqlScanner = new Scanner(sqlFile);
            while(sqlScanner.hasNext()){
                String item = sqlScanner.nextLine();
                if(!item.isEmpty()){
                    dataQuery += item + " ";
                }
            }
            sqlScanner.close();
        
            // Run item SQL
            try (PreparedStatement data = db.connection.prepareStatement(dataQuery)) {
                data.execute();
            }
        
            // Reset dataQuery
            dataQuery = "";
        
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }        
	}

	/**
	 * A tester function to ensure the connection to the database is functional
	 */
	private void testConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/gpmanagementsystem?allowMultiQueries=true&user=root&password=root");
			statement = connection.createStatement();
		}
		catch (Exception e) {
			e.printStackTrace();;
		}

	}


	/**
	 * Function to close the connection with the database
	 */
	private void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();;
		}
	}

    private static String[] nameStringToArray(String name){
        String[] nameArr = name.split(" ");
        nameArr[0] = nameArr[0].toLowerCase().replaceAll(" ", "");
        nameArr[1] = nameArr[1].toLowerCase().replaceAll(" ", "");
        
        return nameArr;
    }


    /**
     * Get PID
     *
     * @param name - contains first and last name
     * @return int PID
     */
    public static String getPID(String[] name) {
        DBManager db = new DBManager();
        db.testConnection();

        String query = "SELECT PID FROM gpManagementSystem . patients AS pts WHERE pts.F_Name = ? AND pts.L_Name = ? LIMIT 1;";
        String PID = "";

        try (PreparedStatement pidStat = db.connection.prepareStatement(query)){
            pidStat.setString(1, name[0]);
            pidStat.setString(2, name[1]);

            ResultSet PIDResult = pidStat.executeQuery();
            
            while (PIDResult.next()) {
                PID = PIDResult.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return PID;
    }


   /**
     * Get DID
     *
     * @param name - contains first and last name
     * @return int DID
     */
    private static String getDID(String[] name) {
        DBManager db = new DBManager();
        db.testConnection();

        String query = "SELECT DID FROM gpManagementSystem . doctors AS dts WHERE dts.F_Name = ? AND dts.L_Name = ? LIMIT 1;";
        String DID = "";

        try (PreparedStatement didStat = db.connection.prepareStatement(query)){
            didStat.setString(1, name[0]);
            didStat.setString(2, name[1]);

            ResultSet DIDResult = didStat.executeQuery();

            while (DIDResult.next()) {
                DID = DIDResult.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return DID;
    }


    /**
     * Get list of names of patients
     *
     * @return ArrayList of names (each item is f_name + l_name)
     */
    public static ArrayList<String> getPatientNames() {
        ArrayList<String> names = new ArrayList<>();

        String query = "SELECT DISTINCT F_Name, L_Name FROM gpManagementSystem . patients";

        DBManager db = new DBManager();
        db.testConnection();

        try {
            ResultSet results = db.statement.executeQuery(query);
            while (results.next()) {
                String temp = results.getString(1) + " " + results.getString(2);
                names.add(temp);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return names;
    }

    /**
     * Get list of names of doctors
     *
     * @return ArrayList of names (each item is f_name + l_name)
     */
    public static ArrayList<String> getDoctorNames() {
        ArrayList<String> names = new ArrayList<>();

        String query = "SELECT DISTINCT F_Name, L_Name FROM gpManagementSystem . doctors";

        DBManager db = new DBManager();
        db.testConnection();

        try {
            ResultSet results = db.statement.executeQuery(query);
            while (results.next()) {
                String temp = results.getString(1) + " " + results.getString(2);
                names.add(temp);
            }
        } catch (SQLException e) {
            System.err.println(e);
        }

        return names;
    }

    /**
     * get the max number of days for a specific month
     *
     * @param month
     * @return maxDays
     */
    public static int getMaxDay(String month) {
        List<String> thirtyDays = Arrays.asList(new String[]{"09", "04", "06", "11"});
        if (month.equals("02")) {
            return 28;
        }
        if (thirtyDays.contains(month)) {
            return 30;
        }
		if(Integer.parseInt(month) > 0 && Integer.parseInt(month) < 13){
			return 31;
		}
		return -1;
    }
}