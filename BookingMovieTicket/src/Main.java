/**
 *  ALIN TCACI
 *  30421
 *  ----------------
 *  PROJECT: "Cinema"
 *  ----------------
 *  This is a Java application for administrating a cinema and booking movie tickets from that cinema.
 *  All the data is stored in a database.
 *  When you start the application, the login interface will appear. From this interface you can choose to create a
 *  client account or to log in into it.
 *  Example of accounts:
 *      - Admin account:
 *              email: alin.tcaci@yahoo.com
 *              password: 1234
 *      - Client account:
 *              email: cata@yahoo.com
 *              password: 1234
 *  If you log in as an admin, admin interface will appear. This GUI has multiple function:
 *      - add a movie;
 *      - add a ticket;
 *      - view oll the available tickets;
 *      - view the total income from the customers purchases;
 *      - "Show purchases" button for viewing a list with all purchases and their details.
 *  If you log in as a customer, customer interface will appear. This GUI has multiple functions:
 *      - buy ticket;
 *      - view all the purchases for the current client.
 *  !!! An admin account can be created only from the database.
 **/

import controller.LoginController;
import database.Database;
import view.LoginView;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Database.connectDb();
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);
        loginView.setVisible(true);
    }
}