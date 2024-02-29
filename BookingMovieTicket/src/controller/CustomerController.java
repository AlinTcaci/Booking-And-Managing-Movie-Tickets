package controller;

import database.Database;
import model.*;
import view.CustomerMainView;
import view.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class CustomerController {
    private CustomerMainView customerMainView;
    private Person person;
    //FOR DATABASE
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepare = null;
    private ResultSet resultSet = null;
    public CustomerController (CustomerMainView customerMainView, Person person){
        this.person = person;
        this.customerMainView = customerMainView;
        customerMainView.setSignOutButtonListener(new GoLogOut());
        customerMainView.setChooseMovie(new AvailableMovie());
        customerMainView.setChooseDate(new AvailableDate());
        customerMainView.setChooseRoom(new AvailableRoom());
        customerMainView.setPriceLabel(new ChooseAmount());
        customerMainView.setChooseCategory(new ChooseCategory());
        customerMainView.setPayButton(new Pay());
        setCustomerName(person.getEmail());
        fillTicketTable();
        fillComboBoxes();
        fillPriceLabel();
    }
    class Pay implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String sql = "INSERT INTO booking.purchases(id_ticket, id_person, amount_tickets, price_tickets) VALUES (?,?,?,?)";

            connect = Database.connectDb();
            try{
                    prepare =connect.prepareStatement(sql);
                    prepare.setInt(1,getIdTicket());
                    prepare.setInt(2,person.getId_person());
                    prepare.setInt(3,Integer.parseInt(customerMainView.getChooseAmountItem()));
                    prepare.setDouble(4,Double.parseDouble(customerMainView.getPriceLabel()));
                    prepare.execute();
                    fillTicketTable();
                    updateFreeSeats();
                    fillComboBoxes();

            }catch (Exception e1){e1.printStackTrace();}
        }
    }
    public void updateFreeSeats(){
        String sql = "UPDATE booking.tickets t\n" +
                "SET nr_seats_sold = t.nr_seats_sold + " + Integer.parseInt(customerMainView.getChooseAmountItem()) + " " +
                "WHERE t.id_ticket = " + getIdTicket();
        try {
            prepare = connect.prepareStatement(sql);
            prepare.execute();
        }catch (Exception e){e.printStackTrace();}
    }
    public int getIdTicket(){
        String sql = "SELECT t.id_ticket\n" +
                "FROM booking.tickets t\n" +
                "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                "JOIN booking.room r on r.id_room = t.id_room\n" +
                "WHERE t.date_ticket = '" + customerMainView.getChooseDateItem() + "'" +
                " and m.title = '" + customerMainView.getChooseMovieItem() + "' " +
                " and r.room_name = '" + customerMainView.getChooseRoomItem() + "'";

        try{
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            return resultSet.getInt("id_ticket");
        }catch (Exception e){e.printStackTrace();}
        return 0;
    }
    public void fillTicketTable() {
        String sql = "SELECT t.date_ticket, r.room_name, m.title, p.amount_tickets\n" +
                "FROM booking.tickets t\n" +
                "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                "JOIN booking.room r on r.id_room = t.id_room\n" +
                "JOIN booking.purchases p on t.id_ticket = p.id_ticket\n" +
                "JOIN booking.person p2 on p2.id_person = p.id_person\n" +
                "WHERE p2.id_person = " + person.getId_person() + "\n " +
                "ORDER BY t.date_ticket";

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
                int i;
                for (i = 1; i <= columns; i++) {
                    row[i - 1] = resultSet.getObject(i);
                }
                model.addRow(row);
            }
            customerMainView.setOwnedTicketTable(model);
        }catch(Exception e){e.printStackTrace();}
    }
    class ChooseCategory implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            fillPriceLabel();
        }
    }
    class ChooseAmount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            fillPriceLabel();
        }
    }
    public void fillPriceLabel(){
        int amount = Integer.parseInt(customerMainView.getChooseAmountItem());
        if(customerMainView.getChooseCategoryItem().equals("REGULAR")){
            PriceTicket t1 = new RegularPriceTicket(10.0);
            t1.setPrice(amount * t1.getPrice() - amount * t1.getDiscount());
            customerMainView.setPriceLabel(Double.toString(t1.getPrice()));
        } else if(customerMainView.getChooseCategoryItem().equals("STUDENT")) {
            PriceTicket t2 = new StudentPriceTicket(10.0);
            t2.setPrice(amount * t2.getPrice() - amount * t2.getDiscount());
            customerMainView.setPriceLabel(Double.toString(t2.getPrice()));
        } else if (customerMainView.getChooseCategoryItem().equals("SENIOR")) {
            PriceTicket t3 = new SeniorPriceTicket(10.0);
            t3.setPrice(amount * t3.getPrice() - amount * t3.getDiscount());
            customerMainView.setPriceLabel(Double.toString(t3.getPrice()));
        }
    }
    class AvailableMovie implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                updateComboBox(customerMainView.getChooseDate(), "SELECT DISTINCT t.date_ticket\n" +
                        "FROM booking.tickets t\n" +
                        "JOIN booking.room r on r.id_room = t.id_room\n" +
                        "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                        "WHERE t.nr_seats_sold < r.nr_seats and m.title = '" + customerMainView.getChooseMovieItem() + "'\n" +
                        "ORDER BY t.date_ticket", "date_ticket");

                updateComboBox(customerMainView.getChooseRoom(), "SELECT r.room_name\n" +
                        "FROM booking.room r\n" +
                        "JOIN booking.tickets t on r.id_room = t.id_room\n" +
                        "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                        "WHERE t.nr_seats_sold < r.nr_seats and t.date_ticket = '" + customerMainView.getChooseDateItem() + "' and m.title = '" + customerMainView.getChooseMovieItem() + "'" +
                        "ORDER BY r.room_name", "room_name");

                fillAmountComboBox("SELECT r.nr_seats, t.nr_seats_sold\n" +
                        "FROM booking.room r\n" +
                        "JOIN booking.tickets t on r.id_room = t.id_room\n" +
                        "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                        "WHERE t.date_ticket = '" + customerMainView.getChooseDateItem() +"' and " +
                        "m.title = '" + customerMainView.getChooseMovieItem() + "' and " +
                        "r.room_name = '" + customerMainView.getChooseRoomItem() + "'");

            }catch (Exception e1){e1.printStackTrace();}
        }
    }
    class AvailableRoom implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                fillAmountComboBox("SELECT r.nr_seats, t.nr_seats_sold\n" +
                        "FROM booking.room r\n" +
                        "JOIN booking.tickets t on r.id_room = t.id_room\n" +
                        "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                        "WHERE t.date_ticket = '" + customerMainView.getChooseDateItem() +"' and " +
                              "m.title = '" + customerMainView.getChooseMovieItem() + "' and " +
                              "r.room_name = '" + customerMainView.getChooseRoomItem() + "'");
            }catch (Exception e1){e1.printStackTrace();}
        }
    }
    public void fillAmountComboBox(String sql) throws SQLException{
        connect = Database.connectDb();
        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);

        resultSet.next();
        customerMainView.setChooseAmount(Integer.parseInt(resultSet.getString("nr_seats")) - Integer.parseInt(resultSet.getString("nr_seats_sold")) );
    }
    class AvailableDate implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                updateComboBox(customerMainView.getChooseRoom(), "SELECT r.room_name\n" +
                        "FROM booking.room r\n" +
                        "JOIN booking.tickets t on r.id_room = t.id_room\n" +
                        "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                        "WHERE t.nr_seats_sold < r.nr_seats and t.date_ticket = '" + customerMainView.getChooseDateItem() + "' and m.title = '" + customerMainView.getChooseMovieItem() + "'" +
                        "ORDER BY r.room_name", "room_name");

                fillAmountComboBox("SELECT r.nr_seats, t.nr_seats_sold\n" +
                        "FROM booking.room r\n" +
                        "JOIN booking.tickets t on r.id_room = t.id_room\n" +
                        "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                        "WHERE t.date_ticket = '" + customerMainView.getChooseDateItem() +"' and " +
                        "m.title = '" + customerMainView.getChooseMovieItem() + "' and " +
                        "r.room_name = '" + customerMainView.getChooseRoomItem() + "'");

            }catch (Exception e1){e1.printStackTrace();}
        }
    }
    public void fillComboBoxes(){
        try{
            updateComboBox(customerMainView.getChooseMovie(), "SELECT DISTINCT m.title\n" +
                    "FROM booking.movies m\n" +
                    "JOIN booking.tickets t on m.id_movie = t.id_movie\n" +
                    "JOIN booking.room r on r.id_room = t.id_room\n" +
                    "WHERE t.nr_seats_sold < r.nr_seats\n" +
                    "ORDER BY m.title","title");

            updateComboBox(customerMainView.getChooseDate(),"SELECT DISTINCT t.date_ticket\n" +
                    "FROM booking.tickets t\n" +
                    "JOIN booking.room r on r.id_room = t.id_room\n" +
                    "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                    "WHERE t.nr_seats_sold < r.nr_seats and m.title = '" + customerMainView.getChooseMovieItem() + "'\n" +
                    "ORDER BY t.date_ticket","date_ticket");

            updateComboBox(customerMainView.getChooseRoom(), "SELECT r.room_name\n" +
                    "FROM booking.room r\n" +
                    "JOIN booking.tickets t on r.id_room = t.id_room\n" +
                    "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                    "WHERE t.nr_seats_sold < r.nr_seats and t.date_ticket = '" + customerMainView.getChooseDateItem() + "' and m.title = '" + customerMainView.getChooseMovieItem() + "'" +
                    "ORDER BY r.room_name", "room_name");

            fillAmountComboBox("SELECT r.nr_seats, t.nr_seats_sold\n" +
                    "FROM booking.room r\n" +
                    "JOIN booking.tickets t on r.id_room = t.id_room\n" +
                    "JOIN booking.movies m on m.id_movie = t.id_movie\n" +
                    "WHERE t.date_ticket = '" + customerMainView.getChooseDateItem() +"' and " +
                    "m.title = '" + customerMainView.getChooseMovieItem() + "' and " +
                    "r.room_name = '" + customerMainView.getChooseRoomItem() + "'");

        }catch(Exception e){e.printStackTrace();}
    }
    public void updateComboBox(JComboBox<String> jComboBox, String sql, String columnName) throws SQLException {
        connect = Database.connectDb();
        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);

        ArrayList<String> elements = new ArrayList<>();
        while(resultSet.next()){
            String s = resultSet.getString(columnName);
            elements.add(s);
        }
        customerMainView.setCombo(jComboBox, elements);
    }
    public void setCustomerName(String email){
        String sql = "SELECT p.firstname, p.lastname FROM booking.person p WHERE p.email_person = '" + email +"'";

        connect = Database.connectDb();
        try{
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);
            resultSet.next();
            String name = resultSet.getString("firstname") + " " + resultSet.getString("lastname");
            customerMainView.setCustomerLabel(name);
        }catch (Exception e){e.printStackTrace();}
    }
    class GoLogOut implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginView.setVisible(true);
            customerMainView.setVisible(false);
        }
    }
}
