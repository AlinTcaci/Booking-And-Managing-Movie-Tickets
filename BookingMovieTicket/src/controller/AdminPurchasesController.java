package controller;
import database.Database;
import view.AdminPurchasesView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPurchasesController {
    private final AdminPurchasesView adminPurchasesView;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepare = null;
    private ResultSet resultSet = null;

    public AdminPurchasesController(AdminPurchasesView adminPurchasesView) {
        this.adminPurchasesView = adminPurchasesView;
        adminPurchasesView.setCloseButton(new GoClose());
        fillTable();
    }

    private void fillTable(){
        String sql = "SELECT t.date_ticket, r.room_name, m.title, p.amount_tickets, p.price_tickets, p2.firstname, p2.lastname\n" +
                "FROM booking.purchases p\n" +
                "JOIN booking.tickets t on t.id_ticket = p.id_ticket\n" +
                "JOIN booking.person p2 on p2.id_person = p.id_person\n" +
                "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                "JOIN booking.room r on r.id_room = t.id_room\n" +
                "ORDER BY t.date_ticket, p2.firstname, p2.lastname, r.room_name, m.title;";

        connect = Database.connectDb();
        try {
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);

            DefaultTableModel model = new DefaultTableModel();
            int columns = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columns; i++) {
                model.addColumn(resultSet.getMetaData().getColumnName(i));
            }
            while (resultSet.next()) {
                Object[] row = new Object[columns];
                for (int i = 1; i <= columns; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
            adminPurchasesView.setPurchasesTable(model);
        }catch(Exception e){e.printStackTrace();}
    }

    class GoClose implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            adminPurchasesView.setVisible(false);
        }
    }
}
