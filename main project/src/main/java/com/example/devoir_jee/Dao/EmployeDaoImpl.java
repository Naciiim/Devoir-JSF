package com.example.devoir_jee.Dao;

import com.example.devoir_jee.Model.Employe;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeDaoImpl implements EmployeDao{
    private final String INSERT_EMPLOYE_SQL = "INSERT INTO Employes (empId, name, departement, birthday,email) VALUES (?, ?, ?, ?,?)";
    private final String SELECT_ALL_EMPLOYES_SQL = "SELECT * FROM Employes";
    private final String SELECT_EMPLOYE_BY_ID_SQL = "SELECT * FROM Employes WHERE empId = ?";
    private final String DELETE_EMPLOYE_SQL = "DELETE FROM Employes WHERE empId = ?";
    private final String UPDATE_EMPLOYE_SQL = "UPDATE Employes SET name = ?, departement = ?, birthday = ?,email=? WHERE empId = ?";
    private ConnDb conn = new ConnDb();

    @Override
    public void add(Employe emp){
        Connection conn= ConnDb.getConnection();
        try
        {
            PreparedStatement pst = conn.prepareStatement(INSERT_EMPLOYE_SQL);
            pst.setInt(1, emp.getEmpId());
            pst.setString(2, emp.getName());
            pst.setString(3, emp.getDepartement());
            java.sql.Date sqlDate = new java.sql.Date(emp.getBirthday().getTime());
            pst.setDate(4, sqlDate);
            pst.setString(5, emp.getEmail());
            pst.executeUpdate();
            System.out.println("Employe added successfully.");
        }catch (SQLException e){
            e.printStackTrace();
        }


    }
    @Override
    public void delete(int empId)  {
        Connection connection = conn.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_EMPLOYE_SQL);
            statement.setInt(1, empId);
            statement.executeUpdate();
            System.out.println("Employe deleted successfully.");
        }catch (SQLException e ) {
            e.printStackTrace();
        }

    }
    @Override
    public void update(Employe emp) {
        Connection conn = ConnDb.getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(UPDATE_EMPLOYE_SQL);
            pst.setString(1, emp.getName());
            pst.setString(2, emp.getDepartement());
            java.sql.Date sqlDate = new java.sql.Date(emp.getBirthday().getTime());
            pst.setDate(3, sqlDate);
            pst.setString(4, emp.getEmail());
            pst.setInt(5, emp.getEmpId());
            pst.executeUpdate();
            System.out.println("Employe updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Employe getEmployeById(int empId) {
        Connection conn = ConnDb.getConnection();
        Employe emp=null;
        try {
            PreparedStatement pst = conn.prepareStatement(SELECT_EMPLOYE_BY_ID_SQL);
            pst.setInt(1, empId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int emp_id = rs.getInt("empId");
                String name = rs.getString("name");
                String departement = rs.getString("departement");
                java.sql.Date birthday = rs.getDate("birthday");
                String email = rs.getString("email");
                emp = new Employe(emp_id, name, departement, birthday,email);
            }
            System.out.println("Employe retrieved successfully.");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return emp;
    }





    @Override
    public List<Employe> getAllEmployes() {
        List<Employe> listEmp= new ArrayList<Employe>();
        Connection conn = ConnDb.getConnection();
        try {
            PreparedStatement pst = conn.prepareStatement(SELECT_ALL_EMPLOYES_SQL);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int emp_id = rs.getInt("empId");
                String name = rs.getString("name");
                String departement = rs.getString("departement");
                java.sql.Date birthday = rs.getDate("birthday");
                String email = rs.getString("email");
                listEmp.add(new Employe(emp_id, name, departement, birthday,email));
            }
            System.out.println("Employes added successfully.");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return listEmp;
    }


}
