# DBManager Overview

## Main Methods

| Return Type | Method Name | Description | Helper Methods Called |
| ----------- | ----------- | ----------- | --------------------- |
| void | validateBookings | Run at the beginning of each program run. Transfers any past bookings to past visits table | testConnection, closeConnection |
| String | login | Takes username and password form user. Logs them in as a patient, doctor, or not at all | testConnection, closeConnection, sqlInjectionTest |
| 2D ArrayList | bookingsQuery | Obtains all future bookings for a given month, year. Returns them in ArrayList | testConnection, closeConnection, sqlInjectionTest, getMaxDay |
| 2D ArrayList | getVisitDetails | Obtains past visits from past_visits table. Returns each rwo and its data in a 2D ArrayList | testConnection, closeConnection |
| Void | updateVisitDetails | Allows updating comments on previous visits | testConnection, closeConnection, sqlInjectionTest |
| Void | enterPastVisitDetails | Allows entering the comments and prescriptions of a booking just completed. Transfers booking into the past_visits table | testConnection, closeConnection, sqlInjectionTest, getVID |

## Helper Methods

| Return Type | Method Name | Description | Used by |
| ----------- | ----------- | ----------- | ------- |
| Void | cleanDb | Runs the config file to reset the data to default values | Main.java |
| Void | testConnection | Tries to create a connection to the SQL database. Throws appropriate syntax error if unable | All SQL Methods |
| Void | closeConnection | Closes the connection to the server. Throws SQLException if unable | All SQL Methods |
| String | getPID | Selects corresponding patient ID based on full name | enterPastVisitDetails |
| int | getVID | Returns the next available Primary key of past_visits table | validateBookings, enterPastVisitDetails |
| ArrayList<String> | getNames | Returns an ArrayList of first and last names of patients | N/A |
| int | getMaxDay | Returns the last day for a specific month | bookingsQuery |
| String[] | sqlInjectionTest | Removes any unwanted contents from possible SQL injection risks | login, bookingsQuery, updateVisitDetails, enterPastVisitDetails |
