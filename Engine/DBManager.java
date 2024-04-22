package Engine;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBManager {
	private Connection connection;
	private Statement statement;
	// public static void main(String[] args) {
	// 	DBManager db = new DBManager();
	// 	db.testConnection();
	// 	// System.out.println(db.login(db, "andrew03meyer", "test"));
	// 	db.closeConnection();
	// }

	/**
	 * A tester function to ensure the connection to the database is functional
	 */
	private void testConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost/details?user=root&password=root");
			statement = connection.createStatement();
			// resultSet = statement.executeQuery("select * from userdetailstable");
			System.out.println("Connection established");
			// while (resultSet.next())
			// System.out.println(resultSet.getString("UID") + " - " + resultSet.getString("F_Name") + " - " + resultSet.getString("L_Name"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Function to close the connection with the database
	 */
	private void closeConnection() {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * A method used in LogInUI that checks whether the user's login details match the database
	 * @param username the username that was inputted in the UI
	 * @param password the password that was inputted in the UI
	 * @return 'doc', 'pat' or 'fail' depending on if the details match a doctor, patient or none
	 */
	public static String login(String username, String password){
		// Check if the username and password are correct
		DBManager db = new DBManager();
		db.testConnection();
		// System.out.println(db.login(db, "andrew03meyer", "test"));

		String query = "select * from doctors where username = '" + username + "' and password = SHA('" + password + "')";
		try{
			// Doctor login
			ResultSet resultSet = db.statement.executeQuery(query);
			if(resultSet.next()){
				db.closeConnection();
				return "doc";
			}

			// Patient login
			query = "select * from patients where username = '" + username + "' and password = SHA('" + password + "')";
			resultSet = db.statement.executeQuery(query);
			if(resultSet.next()){
				db.closeConnection();
				return "pat";
			}
		}
		catch(Exception e){
			System.out.println("Can't login: " + e.getMessage());
		}
		db.closeConnection();
		return "fail";
	}

	/**
	 * Runs the query that gets all the bookings in a given month and year
	 * @param date the date that was inputted (in MM-yyyy format)
	 * @return a list of the data found with the query or null if nothing was found
	 */
	public static List<List<String>> bookingsQuery(String date){
		DBManager db = new DBManager();
		db.testConnection();

		// Execute query
		// String query = "select * from bookings where date = '" + date + "'" ;
		System.out.println(date);
		String query = "SELECT doc.F_Name, doc.L_Name, pat.F_Name, pat.L_Name, book.Date FROM details.bookings AS book JOIN details.doctors AS doc ON doc.DID = book.DID JOIN details.patients AS pat ON pat.pid = book.pid WHERE book.Date = '" + date + "';";

		try{
			// If success
			ResultSet resultSet = db.statement.executeQuery(query);
			System.out.println(resultSet.getFetchSize());
			List<List<String>> bookings = new ArrayList<>();
			System.out.println("Successful query");

			// Store results into an arraylist
			int count = 0;
			while (resultSet.next()){
				bookings.add(new ArrayList<>());
				bookings.get(count).add(resultSet.getString(1)); // Doc F_Name
				bookings.get(count).add(resultSet.getString(2)); // Doc L_Name
				bookings.get(count).add(resultSet.getString(3)); // Pat F_Name
				bookings.get(count).add(resultSet.getString(4)); // Pat L_Name
				bookings.get(count).add(resultSet.getString(5)); // DATE
				count++;
			}

			// Return result
			db.closeConnection();
			return bookings;
		}
		catch (Exception e){
			// Error with query or database, or nothing was found
			System.out.println("Can't search for bookings: " + e.getMessage());
			db.closeConnection();
			return null;
		}
	}
}