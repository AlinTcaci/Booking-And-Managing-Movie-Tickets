package controller;

import database.Database;
import view.AdminMainView;
import view.AdminPurchasesView;
import view.LoginView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

public class AdminController {
    private final AdminMainView adminMainView;
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement prepare = null;
    private ResultSet resultSet = null;

    public AdminController(AdminMainView adminMainView,String adminName){
        this.adminMainView = adminMainView;
        adminMainView.setAdminNameLabel(adminName);
        adminMainView.setSignOutButtonListener(new GoLogOut());
        adminMainView.setAddMovieListener(new AddMovie());
        adminMainView.setAddTicketListener(new AddTicket());
        adminMainView.setDate(new Date());
        adminMainView.setPurchasesButton(new ListPurchases());
        fillIncomeLabel();
        fillComboBoxes();
        fillTicketTable();
    }

    public void fillIncomeLabel(){
        String sql = "SELECT SUM(price_tickets) FROM booking.purchases p";

        connect = Database.connectDb();
        try{
            statement = connect.createStatement();
            resultSet = statement.executeQuery(sql);

            if(resultSet.next()){
                adminMainView.setIncomeLabel(Double.toString(resultSet.getDouble(1)));
            }
            else{
                adminMainView.setIncomeLabel(Double.toString(0.0));
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void fillComboBoxes(){
        try {
            updateComboBox(adminMainView.getAddGenre(),"SELECT booking.genre_movie.genre FROM booking.genre_movie", "genre");
            updateComboBox(adminMainView.getChooseMovie(),"SELECT booking.movies.title FROM booking.movies", "title");
        }catch (Exception e){e.printStackTrace();}
    }

    class AddMovie implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String sql = "INSERT INTO booking.movies(title,duration,id_genre) VALUES (?,?,?)";

            connect = Database.connectDb();

            try{
                prepare = connect.prepareStatement(sql);
                prepare.setString(1, adminMainView.getAddTitle());
                prepare.setString(2, adminMainView.getAddDuration());
                prepare.setInt(3, getIDGenre(adminMainView.getAddGenreItem()));

                if(adminMainView.getAddTitle().isEmpty() || adminMainView.getAddDuration().isEmpty()){
                    adminMainView.showMessage("Please fill all blank fileds");
                }else{
                    prepare.execute();
                    adminMainView.showMessage("Movie added");
                    adminMainView.setTitleNull();
                    adminMainView.setDurationNull();
                    updateComboBox(adminMainView.getChooseMovie(),"SELECT booking.movies.title FROM booking.movies", "title");
                }
            }catch(Exception event){event.printStackTrace();}
        }
    }

    class Date implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String year = adminMainView.getYearItem();
            String month = adminMainView.getMonthItem();
            String day = adminMainView.getDayItem();
            if(!Objects.equals(year, "YYYY") && !Objects.equals(month, "MM") && !Objects.equals(day, "DD")){
                String date = year + "-" + month + "-" + day  ;
                String sql_date = "SELECT r.id_room, r.room_name\n" +
                        "FROM booking.room r\n" +
                        "LEFT JOIN booking.tickets t ON t.id_room = r.id_room AND t.date_ticket = '" + date + "'\n" +
                        "WHERE t.id_room IS NULL\n" +
                        "ORDER BY r.id_room;";
                try {
                    updateComboBox(adminMainView.getChooseRoom(),sql_date,"room_name");
                    if(adminMainView.getChooseRoomItem() == null){
                        adminMainView.setDateNull();
                        adminMainView.showMessage("All rooms are occupied on this calendar date");
                    }
                }catch(Exception e1){e1.printStackTrace();}
            }
        }
    }

    class AddTicket implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String year = adminMainView.getYearItem();
            String month = adminMainView.getMonthItem();
            String day = adminMainView.getDayItem();
            String date = year + "-" + month + "-" + day  ;
            String sql = "INSERT INTO booking.tickets(date_ticket, id_room, id_movie, nr_seats_sold) VALUES (?,?,?,?)";

            connect = Database.connectDb();
            try {
                prepare = connect.prepareStatement(sql);
                if(!Objects.equals(year, "YYYY") && !Objects.equals(month, "MM") && !Objects.equals(day, "DD")){
                    prepare.setString(1, date);
                    prepare.setInt(2, getIdRoom(adminMainView.getChooseRoomItem()));
                    prepare.setInt(3, getIdMovie(adminMainView.getChooseMovieItem()));
                    prepare.setInt(4,0);
                    prepare.execute();
                    adminMainView.showMessage("Ticket added");
                    updateComboBox(adminMainView.getChooseRoom(), "SELECT * FROM booking.tickets WHERE date_ticket = 'YYYY-MM-DD'","date_ticket");
                    fillTicketTable();
                    adminMainView.setDateNull();

                }else{
                    adminMainView.showMessage("Select the calendar date");
                }
            }catch (Exception e1){e1.printStackTrace();}
        }
    }

    public void fillTicketTable() {
        String sql = "SELECT t.date_ticket, r.room_name, mo.title, g.genre, t.nr_seats_sold\n" +
                "FROM booking.tickets t\n" +
                "JOIN booking.movies mo on mo.id_movie = t.id_movie\n" +
                "JOIN booking.genre_movie gm on gm.id_genre = mo.id_genre\n" +
                "JOIN booking.genre_movie g on g.id_genre = mo.id_genre\n" +
                "JOIN booking.room r on r.id_room = t.id_room\n" +
                "ORDER BY t.date_ticket, r.room_name, g.genre";

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
            adminMainView.setMovieTable(model);
        }catch(Exception e){e.printStackTrace();}
    }

    public int getIDGenre(String genre) throws SQLException{
        String sql = "SELECT * FROM booking.genre_movie";

        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            if(genre.equals(resultSet.getString("genre"))){
                return resultSet.getInt("id_genre");
            }
        }
        return 0;
    }

    public int getIdMovie(String title) throws SQLException{
        String sql = "SELECT * FROM booking.movies";

        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            if(title.equals(resultSet.getString("title"))){
                return resultSet.getInt("id_movie");
            }
        }
        return 0;
    }

    public int getIdRoom(String room) throws SQLException{
        String sql = "SELECT * FROM booking.room";

        statement = connect.createStatement();
        resultSet = statement.executeQuery(sql);
        while(resultSet.next()){
            if(room.equals(resultSet.getString("room_name"))){
                return resultSet.getInt("id_room");
            }
        }
        return 0;
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
        adminMainView.setCombo(jComboBox, elements);
    }

    class GoLogOut implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            LoginView loginView = new LoginView();
            LoginController loginController = new LoginController(loginView);
            loginView.setVisible(true);
            adminMainView.setVisible(false);
        }
    }

    class ListPurchases implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            AdminPurchasesView adminPurchasesView =new AdminPurchasesView();
            AdminPurchasesController adminPurchasesController = new AdminPurchasesController(adminPurchasesView);
            adminPurchasesView.setVisible(true);
        }
    }
}
