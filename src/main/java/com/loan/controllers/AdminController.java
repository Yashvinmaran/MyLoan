package com.loan.controllers;

import com.loan.entities.Admin;
import com.loan.entities.Loan;
import com.loan.entities.User;
import com.loan.models.JwtRequest;
import com.loan.models.JwtResponse;
import com.loan.services.AdminService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        return adminService.loginAdmin(request);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createAdmin(@RequestBody Admin admin) {
        return adminService.createAdmin(admin);
    }

    // ================= ADMIN PROFILE =================

    @GetMapping("/get/{email}")
    public ResponseEntity<Admin> getAdminByEmail(@PathVariable String email) {
        return adminService.getAdminByEmail(email);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<String> updateAdmin(@PathVariable String email, @RequestBody Admin admin) {
        return adminService.updateAdmin(email, admin);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteAdmin(@PathVariable String email) {
        return adminService.deleteAdmin(email);
    }

    // Add delete user endpoint for admin

    @DeleteMapping("/user/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        return adminService.deleteUser(email);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return adminService.getAllAdmins();
    }

    // ================= USER MANAGEMENT =================

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return adminService.getAllUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return adminService.getUserById(id);
    }

    @PutMapping("/user/cibil/{id}")
    public ResponseEntity<User> updateCibilScore(@PathVariable String id, @RequestParam String cibilScore) {
        return adminService.updateCibilScore(id, cibilScore);
    }

    // ================= LOAN MANAGEMENT =================

    @GetMapping("/loans")
    public ResponseEntity<List<Loan>> getAllLoans() {
        return adminService.getAllLoans();
    }

    @PutMapping("/loan/status/{id}")
    public ResponseEntity<Loan> updateLoanStatus(@PathVariable String id, @RequestParam String status) {
        return adminService.updateLoanStatus(id, status);
    }

    // Add user update endpoint proxy in AdminController
    @PutMapping("/user/update/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        return adminService.updateUser(email, user);
    }
}
