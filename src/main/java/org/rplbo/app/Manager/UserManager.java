package org.rplbo.app.Manager;

import org.rplbo.app.Data.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {
    private Connection connection;

    // Constructor untuk menerima koneksi dari Main / DBConfig
    public UserManager(Connection connection) {
        this.connection = connection;
    }

    // Constructor kosong (opsional)
    public UserManager() {
    }

    // --- METHOD CARI USER SESUAI ROLE ---
    public List<User> getUsersByRole(String role) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT username, password, email, role FROM users WHERE role = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, role);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getString("role"),
                            rs.getString("password"),
                            rs.getString("email"),
                            rs.getString("username")
                    );
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getUsersByRole: " + e.getMessage());
        }
        return userList;
    }

    // --- METHOD REGISTER (INSERT) ---
    public boolean registerUser(String username, String password, String email, String role) {
        String query = "INSERT INTO users (username, password, email, role) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            stmt.setString(4, role);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error registerUser: " + e.getMessage());
            return false;
        }

    }

    // --- METHOD LOGIN (SELECT) ---
    public boolean authenticateUser(String username, String password) {
        String query = "SELECT username FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
               return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Error authenticateUser: " + e.getMessage());
            return false;
        }
    }

    // --- METHOD UPDATE USER PROFILE ---
    public boolean updateUserProfile(String username, String newPassword, String newEmail, String newRole, String oldUsername) {
        String query = "UPDATE users SET username = ?, password = ?, email = ?, role = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, newPassword);
            stmt.setString(3, newEmail);
            stmt.setString(4, newRole);
            stmt.setString(5, oldUsername);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updateUserProfile: " + e.getMessage());
        }
        return false;
    }

    // --- METHOD CARI ROLE USER ---
    public String getUserRole(String username) {
        String query = "SELECT role FROM users WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role"); // Ambil role-nya
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getUserRole: " + e.getMessage());
        }
        return "guest"; // Default jika terjadi error
    }
}