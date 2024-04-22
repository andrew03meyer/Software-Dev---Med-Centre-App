package src;

import src.engine.DBManager;
import src.ui.LoginUI;

public class Main {
    public static void main(String[] args) {
        DBManager.resetDb();
        DBManager.validateBookings();
        new LoginUI();
    }
}
