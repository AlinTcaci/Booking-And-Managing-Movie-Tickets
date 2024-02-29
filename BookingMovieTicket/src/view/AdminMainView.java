package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AdminMainView extends JFrame{
    private JPanel basePanel;
    private JButton SignOutButton;
    private JTextField addTitle;
    private JTextField addDuration;
    private JTable movieTable;
    private JLabel adminNameLabel;
    private JButton addMovie;
    private JComboBox<String> chooseRoom;
    private JComboBox<String> chooseMovie;
    private JButton addTicket;
    public JComboBox<String> addGenre;
    private JComboBox<String> YYYYBox;
    private JComboBox<String> MMBox;
    private JComboBox<String> DDBox;
    private JScrollPane scrollPanel;
    private JLabel incomeLabel;
    private JButton purchasesButton;

    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public AdminMainView(){setDimension(1000, 400);}
    public String getAddTitle(){return this.addTitle.getText();}
    public String getAddDuration(){return this.addDuration.getText();}
    public String getAddGenreItem(){return (String)this.addGenre.getSelectedItem();}
    public String getChooseRoomItem(){return (String) this.chooseRoom.getSelectedItem();}
    public String getChooseMovieItem(){return (String) this.chooseMovie.getSelectedItem();}
    public String getYearItem(){return (String) this.YYYYBox.getSelectedItem();}
    public String getMonthItem(){return (String) this.MMBox.getSelectedItem();}
    public String getDayItem(){return (String) this.DDBox.getSelectedItem();}
    public JComboBox<String> getAddGenre() {return addGenre;}
    public JComboBox<String> getChooseRoom() {return chooseRoom;}
    public JComboBox<String> getChooseMovie() {return chooseMovie;}
    public void setSignOutButtonListener(ActionListener listener){
        this.SignOutButton.addActionListener(listener);
    }
    public void setAddMovieListener(ActionListener listener){
        this.addMovie.addActionListener(listener);
    }
    public void setAddTicketListener(ActionListener listener){
        this.addTicket.addActionListener(listener);
    }
    public void setPurchasesButton(ActionListener listener){ this.purchasesButton.addActionListener((listener));}
    public void setDate(ActionListener listener) {
        this.YYYYBox.addActionListener(listener);
        this.MMBox.addActionListener(listener);
        this.DDBox.addActionListener(listener);
    }
    public void setDateNull(){
        this.YYYYBox.setSelectedIndex(0);
        this.MMBox.setSelectedIndex(0);
        this.DDBox.setSelectedIndex(0);
    }
    public void setAdminNameLabel(String text){this.adminNameLabel.setText(text);}
    public void setIncomeLabel(String price){this.incomeLabel.setText(price);}
    public void setTitleNull(){this.addTitle.setText("");}
    public void setDurationNull(){this.addDuration.setText("");}
    public void setMovieTable(DefaultTableModel model) {
        this.movieTable.setModel(model);
        setScrollPanel(this.movieTable);
    }
    public void setScrollPanel(JTable table){this.scrollPanel.setViewportView(table);}
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(AdminMainView.this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
    public void setCombo( JComboBox<String> jComboBox,ArrayList<String> arrayList){
        jComboBox.setModel(new DefaultComboBoxModel(arrayList.toArray()));
    }
}
