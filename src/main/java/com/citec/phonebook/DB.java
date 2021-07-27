package com.citec.phonebook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DB {
    
    private static final String DB_URL = "jdbc:mysql://localhost:3306/phonebook";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private Connection conn;

    public DB() {
        initDatabase();
    }

    private void initDatabase() {
        try {
            conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Adatbázis kapcsolat létrehozva!");
        } catch (SQLException e) {
            System.err.println("Hiba az adatbázis létrehozása közben! " + e);
        }
    }
    
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        String query = "select *  from contacts";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Person person = new Person();
                person.setFirstName(rs.getString("firstname"));
                person.setLastName(rs.getString("lastname"));
                person.setPhone(rs.getString("phone"));
                person.setId(rs.getString("id"));
                result.add(person);
            }
        } catch (SQLException e) {
            System.err.println("Hiba a lekérdezés közben: findAll() " + e);
        }
        return result;
    }
    
    public void addContact(Person person) {
        try {
            String query = "insert into contacts (lastname, firstname, phone) values (?,?,?)";
            PreparedStatement ptst = conn.prepareStatement(query);
            ptst.setString(1, person.getLastName());
            ptst.setString(2, person.getFirstName());
            ptst.setString(3, person.getPhone());
            ptst.execute();
        } catch (SQLException e) {
            System.err.println("Hiba a lekérdezés közben: addContact() " + e);
        }
    }
    
    public void updateContact(Person person) {
        try {
            String query = "update contacts set lastname = ?, firstname = ?, phone = ? where id = ?";
            PreparedStatement ptst = conn.prepareStatement(query);
            ptst.setString(1, person.getLastName());
            ptst.setString(2, person.getFirstName());
            ptst.setString(3, person.getPhone());
            ptst.setString(4, person.getId());
            ptst.execute();
        } catch (SQLException e) {
            System.err.println("Hiba a lekérdezés közben: updateContact() " + e);
        }
    }
}
