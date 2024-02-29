package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CustomerMainView extends JFrame{
    private JButton signOutButton;
    private JComboBox chooseMovie;
    private JComboBox chooseDate;
    private JComboBox chooseRoom;
    private JComboBox chooseCategory;
    private JComboBox chooseAmount;
    private JTable ownedTicketTable;
    private JButton payButton;
    private JPanel basePanel;
    private JScrollPane scrollPanel;
    private JLabel customerLabel;
    private JLabel priceLabel;
    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public CustomerMainView(){setDimension(800, 400);}
    public JComboBox<String> getChooseDate() {return chooseDate;}
    public JComboBox<String> getChooseRoom() {return chooseRoom;}
    public JComboBox<String> getChooseMovie() {return chooseMovie;}
    public String getPriceLabel(){return this.priceLabel.getText();}
    public String getChooseDateItem(){return (String) this.chooseDate.getSelectedItem();}
    public String getChooseAmountItem(){return (String) this.chooseAmount.getSelectedItem();}
    public String getChooseCategoryItem(){return (String) this.chooseCategory.getSelectedItem();}
    public String getChooseRoomItem(){return (String) this.chooseRoom.getSelectedItem();}
    public String getChooseMovieItem(){return (String) this.chooseMovie.getSelectedItem();}
    public void setCustomerLabel(String text){this.customerLabel.setText(text);}
    public void setPriceLabel(String price){this.priceLabel.setText(price);}
    public void setChooseMovie(ActionListener listener){
        this.chooseMovie.addActionListener(listener);
    }
    public void setChooseDate(ActionListener listener){
        this.chooseDate.addActionListener(listener);
    }
    public void setChooseRoom(ActionListener listener){this.chooseRoom.addActionListener(listener);}
    public void setPriceLabel(ActionListener listener){this.chooseAmount.addActionListener(listener);}
    public void setChooseCategory(ActionListener listener){this.chooseCategory.addActionListener(listener);}
    public void setPayButton(ActionListener listener){this.payButton.addActionListener(listener);}
    public void setChooseAmount(int maxim){
        this.chooseAmount.setMaximumRowCount(maxim);
    }
    public void setOwnedTicketTable(DefaultTableModel model) {
        this.ownedTicketTable.setModel(model);
        setScrollPanel(this.ownedTicketTable);
    }
    public void setScrollPanel(JTable table){this.scrollPanel.setViewportView(table);}
    public void setSignOutButtonListener(ActionListener listener){
        this.signOutButton.addActionListener(listener);
    }
    public void setCombo(JComboBox<String> jComboBox, ArrayList<String> arrayList){
        jComboBox.setModel(new DefaultComboBoxModel(arrayList.toArray()));
    }
}
