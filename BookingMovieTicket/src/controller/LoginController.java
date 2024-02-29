package controller;
import database.Database;
import model.Person;
import view.AdminMainView;
import view.CustomerMainView;
import view.LoginView;
import view.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    private final LoginView loginView;

    //FOR DATABASE
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepare = null;
    private ResultSet resultSet = null;

    public LoginController (LoginView loginView){
        this.loginView = loginView;
        loginView.createAccountButtonListener(new GoCreate());
        loginView.setSignInButtonListener(new SignIn());
    }
    class SignIn implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String sql = "SELECT * FROM booking.person WHERE email_person = ? and password_person = ?";

            connect = Database.connectDb();

            try {
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, loginView.getEmail());
                prepare.setString(2, loginView.getPassword());

                resultSet = prepare.executeQuery();
                if(loginView.getEmail().isEmpty() || loginView.getPassword().isEmpty()){
                    loginView.showMessage("Please fill all blank fileds");
                }else{
                    if(resultSet.next()){
                        if(resultSet.getString("type_person").equals("1")){
                            //Admin Form
                            String nameAdmin = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
                            AdminMainView adminMainView = new AdminMainView();
                            AdminController adminController = new AdminController(adminMainView, nameAdmin);
                            adminMainView.setVisible(true);
                            loginView.setVisible(false);
                            loginView.setEmailNull();
                            loginView.setPasswordNull();

                        }else{
                            //Customer Form
                            Person person =new Person(resultSet.getString("firstname"),resultSet.getString("lastname"),resultSet.getString("email_person"),resultSet.getInt("id_person"));
                            CustomerMainView customerMainView = new CustomerMainView();
                            CustomerController customerController = new CustomerController(customerMainView, person);
                            customerMainView.setVisible(true);
                            loginView.setVisible(false);
                            loginView.setEmailNull();
                            loginView.setPasswordNull();
                        }
                    }else{
                        loginView.showMessage("Wrong Email/Password");
                    }
                }
            } catch(Exception event){ event.printStackTrace();}
        }
    }
    class GoCreate implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            RegisterView registerView = new RegisterView();
            RegisterController registerController = new RegisterController(registerView);
            registerView.setVisible(true);
            loginView.setVisible(false);
        }
    }
}
