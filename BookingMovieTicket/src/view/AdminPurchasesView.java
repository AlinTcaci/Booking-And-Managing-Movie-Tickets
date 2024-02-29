package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;

public class AdminPurchasesView extends JFrame{
    private JPanel basePanel;
    private JTable purchasesTable;
    private JScrollPane scrollPanel;
    private JButton closeButton;
    public void setDimension(int w, int h) {
        add(basePanel);
        setBounds(300, 200, w, h);
        setLocationRelativeTo(null);
        //setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public AdminPurchasesView(){setDimension(700, 250);}
    public void setCloseButton(ActionListener listener){ this.closeButton.addActionListener((listener));}
    public void setPurchasesTable(DefaultTableModel model) {
        this.purchasesTable.setModel(model);
        setScrollPanel(this.purchasesTable);
    }
    public void setScrollPanel(JTable table){this.scrollPanel.setViewportView(table);}
}
