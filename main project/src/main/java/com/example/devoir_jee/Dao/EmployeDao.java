package com.example.devoir_jee.Dao;

import com.example.devoir_jee.Model.Employe;
import java.sql.SQLException;
import java.util.List;

public interface EmployeDao {

     public void add(Employe emp);
     public void delete(int empId) throws SQLException;
     public void update(Employe emp);
     public Employe getEmployeById(int empId);
     public List<Employe> getAllEmployes();
}
