package com.loan.services;

import com.loan.entities.Admin;
import com.loan.entities.Loan;
import com.loan.entities.User;
import com.loan.models.JwtRequest;
import com.loan.models.JwtResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminService {
    // Admin Management
    ResponseEntity<Admin> getAdminByEmail(String email);
    ResponseEntity<List<Admin>> getAllAdmins();
    ResponseEntity<String> createAdmin(Admin admin);
    ResponseEntity<String> updateAdmin(String email, Admin admin);
    ResponseEntity<String> deleteAdmin(String email);
    ResponseEntity<JwtResponse> loginAdmin(JwtRequest request);

    // Add deleteUser method declaration
    ResponseEntity<String> deleteUser(String email);

    // Loan Management
    ResponseEntity<List<Loan>> getAllLoans();
    ResponseEntity<Loan> updateLoanStatus(String id, String status);

    // User Management
    ResponseEntity<List<User>> getAllUsers();
    ResponseEntity<User> getUserById(String id);
    ResponseEntity<User> updateCibilScore(String id, String cibilScore);

    // Add updateUser method declaration
    ResponseEntity<User> updateUser(String email, User user);
}
