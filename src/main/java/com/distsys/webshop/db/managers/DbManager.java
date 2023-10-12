package com.distsys.webshop.db.managers;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbManager {
    private static DbManager instance = null;
    private final Connection con;
    private static DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }
    private DbManager() {
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