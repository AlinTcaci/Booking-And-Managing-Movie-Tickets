package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame{
    private JTextField emailFieldCheck;
    private JButton signInButton;
    private JButton goCreateAccountButton;
    private JPasswordField passwordFieldCheck;
    private JPanel basePanel;
    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public LoginView(){setDimension(600, 400);}
    public void setEmailNull(){ this.emailFieldCheck.setText("");}
    public void setPasswordNull(){this.passwordFieldCheck.setText("");}
    public String getEmail(){return this.emailFieldCheck.getText();}
    public String getPassword(){return this.passwordFieldCheck.getText();}
    public void createAccountButtonListener(ActionListener listener) {
        this.goCreateAccountButton.addActionListener(listener);
    }
    public void setSignInButtonListener(ActionListener listener){
        this.signInButton.addActionListener(listener);
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(LoginView.this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}
