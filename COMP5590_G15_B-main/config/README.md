# Download MySQL

* Use the link:
    * https://dev.mysql.com/downloads/installer/ for windows
    * https://dev.mysql.com/downloads/mysql/ for mac (version 8.0.36)

* Download the "Windows (x86, 32-bit), MSI Installer"
* Run the install

# Install Setup

* On one of the pages, there is a selection for which type of development you are doing. Choose the "full" option
* All other options can be default
* When prompted for a default user:
    * username: root
    * password: root

# Once Installed

* Go onto MySQL Workbench
* (in top bar) Database -> Connect to Database
    * Click Ok
    * Login with a password of "root"

# Setting up database

* On the left hand side there is a box called "Navigator"
* At the bottom of this box, two tabs (Administration and Schema)
* Select Schema
* This shows you all the schemas in the database
* (top bar) Click File -> Open SQL Script
    * The SQL script is found in the git repository (Repositories\COMP5590_G15_B\config\config.sql)
* Click the lightning bolt (3rd button from left) at the top of the box that opens with the SQL (this runs it)
* This should remove unnecessary schemas and populate your database (with a schema, followed by a table, table columns
  and data)
* refresh the schema tab (little arrow in the top right corner) to show the new schema

# Class Path Java

* make folder called lib (under COMP5590_G15_B) copy and paste the mysql-connector-j-8.2.0.jar file (in the config
  folder) into the lib folder
* Open class path configs for your editor (depends on editor google it!)
    * If vscode you can search for it in the command palette (CTRL + SHFT + P)
* Add the path of the file you've just copied to the class path

# Extra

* You should now be able to run the Java file (main) and login with one of the profiles added by the sql
* You don't have to connect to the databse when you run the java
* Enjoy xx