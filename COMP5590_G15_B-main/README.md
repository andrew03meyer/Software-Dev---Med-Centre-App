# Project Structure

## Documents on Projects

* Found in the Docs folder
* Contains
    * Plan.md - contains information on assignments per member and meeting plans
    * Research.md - contains information about user stories, conversations, and initial ideas

## Configuration Files

* Found in config folder
* Contains
    * config.sql - the base SQL for setting up the database
    * README.md - A step by step guide, running through the setup of the MySQL datbase

## Library Files

* Found in lib folder
* These are the libraries used in the code, and therefore needed to work on each individual device
    * mysql-... - the connector for the database
    * junit-... - the test library (JUnit)

## Program Files

* Split between three folders
    * Engine - Contains the back-end code. Used to do things like querying the database
    * UI - Contains the front-end code. Used to display the processing done by the back-end
    * Tests - Contains the tests on all the components of the program

## Other Files

* Found in the base directory
* These include
    * Git files - used for configuring git (e.g. .gitignore)
    * Main.java - the run file for the whole program
    * GitLab build file - used for automatic testing and building
    * This README!
