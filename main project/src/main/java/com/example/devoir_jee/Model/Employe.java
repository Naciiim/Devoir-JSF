package com.example.devoir_jee.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Employe {
    private int empId;
    private String name,departement;
    private Date birthday;
    private String email;

}
