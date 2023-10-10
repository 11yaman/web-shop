package com.distsys.webshop.db.management;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
    private static DBManager instance = null;
    private final Connection con;
    private static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }
    private DBManager() {
        String server
                = "jdbc:mysql://localhost:3306/web_shop?UseClientEnc=UTF8";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, "client", "client");
            System.out.println("Connected!");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static Connection getConnection() {
        return getInstance().con;
    }
}