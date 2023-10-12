package com.distsys.webshop.db.managers;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static boolean isTransactionOngoing = false; //ThreadLocal<>?
    public static void begin() {
        if (isTransactionOngoing)
            throw new IllegalStateException("Transaction is already in progress.");

        Connection con = DbManager.getConnection();
        try {
            con.setAutoCommit(false);
            isTransactionOngoing = true;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to begin a transaction.");
        }
    }

    public static void commit() {
        if (!isTransactionOngoing)
            throw new IllegalStateException("No transaction in progress.");

        Connection con = DbManager.getConnection();
        try {
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            rollback();
            throw new RuntimeException("Error committing transaction: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Database Error: " + e.getMessage());
            }
            isTransactionOngoing = false;
        }
    }

    public static void rollback() {
        if (!isTransactionOngoing)
            throw new IllegalStateException("No transaction in progress.");

        Connection con = DbManager.getConnection();
        try {
            con.rollback();
        } catch (SQLException e) {
            throw new RuntimeException("Error rolling back transaction: " + e.getMessage());
        } finally {
            try {
                con.setAutoCommit(true);
            } catch (SQLException e) {
                throw new RuntimeException("Database Error: " + e.getMessage());
            }
            isTransactionOngoing = false;
        }
    }

    private TransactionManager(){
    }
}
