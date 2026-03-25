package org.rplbo.app.Manager;

import org.rplbo.app.Data.RekamMedis;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RekamMedisManager {
    private Connection connection;

    public RekamMedisManager(Connection connection) {
        this.connection = connection;
    }

    // --- 1. CREATE (Tambah Rekam Medis) ---
    public boolean tambahRekamMedis(String namaDokter, String namaPasien, String diagnosis, String tanggal) {
        String query = "INSERT INTO rekam_medis (nama_dokter, nama_pasien, diagnosis, tanggal) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, namaDokter);
            stmt.setString(2, namaPasien);
            stmt.setString(3, diagnosis);
            stmt.setString(4, tanggal);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR INSERT] " + e.getMessage());
        }
        return false;
    }

    // --- 2. READ ALL ---
    public List<RekamMedis> getAllRekamMedis() {
        List<RekamMedis> rekamMedisList = new ArrayList<>();
        String query = "SELECT * FROM rekam_medis";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RekamMedis rm = new RekamMedis(
                        rs.getInt("id"),
                        rs.getString("nama_pasien"),
                        rs.getString("diagnosis"),
                        rs.getString("tanggal"),
                        rs.getString("nama_dokter")
                );
                rekamMedisList.add(rm);
            }
        } catch (SQLException e) {
            System.err.println("[ERROR SELECT ALL] " + e.getMessage());
        }
        return rekamMedisList;
    }

    // --- 3. UPDATE ---
    public boolean editRekamMedis(int idRekamMedis, String diagnosisBaru) {
        String query = "UPDATE rekam_medis SET diagnosis = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, diagnosisBaru);
            stmt.setInt(2, idRekamMedis);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR UPDATE] " + e.getMessage());
        }
        return false;
    }

    // --- 4. DELETE ---
    public boolean hapusRekamMedis(int idRekamMedis) {
        String query = "DELETE FROM rekam_medis WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idRekamMedis);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("[ERROR DELETE] " + e.getMessage());
        }
        return false;
    }

    // --- 5. READ (SEARCH) ---
    public List<RekamMedis> cariRekamMedisPasien(String nama) {
        List<RekamMedis> resultList = new ArrayList<>();
        String query = "SELECT * FROM rekam_medis WHERE nama_pasien LIKE ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nama + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    RekamMedis rm = new RekamMedis(
                            rs.getInt("id"),
                            rs.getString("nama_pasien"),
                            rs.getString("diagnosis"),
                            rs.getString("tanggal"),
                            rs.getString("nama_dokter")
                    );
                    resultList.add(rm);
                }
            }
        } catch (SQLException e) {
            System.err.println("[ERROR SEARCH] " + e.getMessage());
        }
        return resultList;
    }
}