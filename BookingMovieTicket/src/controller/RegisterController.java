package controller;

import database.Database;
import view.LoginView;
import view.RegisterView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {
    private final RegisterView registerView;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepare = null;
    private ResultSet resultSet = null;

    public RegisterController(RegisterView registerView){
        this.registerView = registerView;
        registerView.setCancelButtonListener(new GoCancel());
        registerView.setRegisterButton(new CreateAccount());
    }
    class CreateAccount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String sql = "INSERT INTO booking.person (firstname,lastname,email_person,password_person,type_person,age_person) VALUES (?,?,?,?,?,?)";

            connect = Database.connectDb();

            try{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, registerView.getFirstName());
                prepare.setString(2, registerView.getLastName());
                prepare.setString(3, registerView.getEmail());
                prepare.setString(4, registerView.getPassword());
                prepare.setInt(5, 2); // 1 = admin, 2 = customer. You can't create an admin account from register Form
                prepare.setInt(6, Integer.parseInt(registerView.getAge()));

                if(registerView.getFirstName().isEmpty() ||
                        registerView.getLastName().isEmpty() ||
                        registerView.getEmail().isEmpty() ||
                        registerView.getPassword().isEmpty() ||
                        registerView.getAge().isEmpty()){
                    registerView.showMessage("Please fill all blank fileds");
                }else{
                    if(validEmail()){
                        if(registerView.getPassword().length() >= 4){
                            if(validName(registerView.getFirstName()) && validName(registerView.getLastName())){
                                prepare.execute();
                                registerView.setFirstNameFieldNull();
                                registerView.setLastNameFieldNull();
                                registerView.setEmailFieldNull();
                                registerView.setPasswordFieldNull();
                                registerView.setAgeFieldNull();
                                registerView.showMessage("Register Successfully.\nPlease try to login");
                            }
                        }else{
                            registerView.showMessage("Password is too short");
                        }
                    }
                }
            }catch (NumberFormatException exception){
                registerView.showMessage("Invalid age");
            }
            catch (Exception event){event.printStackTrace();}
        }
    }
    public boolean validName(String s){
        for (int i = 0; i < s.length(); i++) {
            // checks whether the character is not a letter
            // if it is not a letter, it will return false
            if ((!Character.isLetter(s.charAt(i)))) {
                registerView.showMessage("Invalid Firstname/Lastname");
                return false;
            }
        }
        return true;
    }

    public boolean validEmail() throws SQLException {
        Pattern pattern = Pattern.compile("[A-Za-z0-9._+%-]+@[A-Za-z0-9.-]+[.][A-Za-z]+");

        statement = connect.createStatement();
        resultSet = statement.executeQuery("SELECT booking.person.email_person FROM booking.person");

        while(resultSet.next()) {
            String email = resultSet.getString("email_person");
            if(email.equals(registerView.getEmail())){
                registerView.showMessage("Email already exists");
                return false;
            }
        }

        Matcher match = pattern.matcher(registerView.getEmail());
        if (match.find() && match.group().equals(registerView.getEmail())) {
            return true;
        } else {
            registerView.showMessage("Invalid Email");
            return false;
        }
    }
    class GoCancel implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginView.setVisible(true);
            registerView.setVisible(false);
        }
    }
}
