package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class RegisterView extends JFrame{
    private JPanel basePanel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField passwordField;
    private JTextField ageField;
    private JButton cancelButton;
    private JButton registerButton;

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public RegisterView(){setDimension(400, 600);}

    public void setFirstNameFieldNull(){this.firstNameField.setText("");}
    public void setLastNameFieldNull(){this.lastNameField.setText("");}
    public void setEmailFieldNull(){this.emailField.setText("");}
    public void setPasswordFieldNull(){this.passwordField.setText("");}
    public void setAgeFieldNull(){this.ageField.setText("");}
    public String getFirstName(){return this.firstNameField.getText();}
    public String getLastName(){return this.lastNameField.getText();}
    public String getEmail(){return this.emailField.getText();}
    public String getPassword(){return this.passwordField.getText();}
    public String getAge(){return this.ageField.getText();}
    public void setCancelButtonListener(ActionListener listener){
        this.cancelButton.addActionListener(listener);
    }
    public void setRegisterButton(ActionListener listener){
        this.registerButton.addActionListener(listener);
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(RegisterView.this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
