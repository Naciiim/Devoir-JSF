package com.example.devoir_jee.Dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnDb {
    private static Connection conn ;
    private final String url = "jdbc:mysql://mysql-2a1f6936-nassimjee-a06b.a.aivencloud.com:11911/employe";
    private final String username = "avnadmin";
    private final String password = "AVNS_vWBNZH-1JKUBypuT0l-";

    public ConnDb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connexion to database successful.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }



    public static Connection getConnection() {
        if (conn == null) {
            new ConnDb();
        }
        return conn;
    }
}
