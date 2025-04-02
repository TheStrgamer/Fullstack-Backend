package no.ntnu.idatt2105.marketplace.dao;

import no.ntnu.idatt2105.marketplace.model.listing;
import no.ntnu.idatt2105.marketplace.model.user;

import java.sql.*;
import java.util.List;

public class ListingDao {
    private static Connection connection;
    private final String DB_URL = "jdbc:h2:/Users/eskild/Library/Mobile Documents/com~apple~CloudDocs/NTNU/Semester 4/Fullstack/Backend/Fullstack-Backend/data/database/db.mv.db";
    private final String DB_USER = "admin";
    private final String DB_PASSWORD = "admin";

    ListingDao() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    /**
     * Method for converting a ResultSet to a listing object
     *
     * @param rs ResultSet
     * @return listing object
     */

    private listing toListing(ResultSet rs) throws SQLException {
        user user = new user(rs.getString("USER_ID"), rs.getString("USER_NAME"), rs.getString("USER_EMAIL"), rs.getString("USER_PHONE"), rs.getString("USER_PASSWORD"));
        return new listing(rs.getInt("id"),
                rs.getString("title"),
                rs.getString("description"),
                rs.getInt("price"),
                rs.getString("location"),
                rs.getString("category"),
                rs.getString("deliveryType"),
                rs.getString("seller"));
    }

    /**
     * Method for retrieving a listing by id from database
     * @param id of listing
     * @return listing object
     */
    public listing getListingById(int id) {
        String sql = "SELECT * FROM listing WHERE id = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new listing(rs.getInt("id"), rs.getString("title"), rs.getString("description"), rs.getInt("price"), rs.getString("location"), rs.getString("category"), rs.getString("deliveryType"), rs.getString("seller"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving listing", e);
        }
    }
}
