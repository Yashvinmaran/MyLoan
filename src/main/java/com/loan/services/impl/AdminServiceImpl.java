package com.loan.services.impl;

import com.loan.entities.Admin;
import com.loan.entities.Loan;
import com.loan.entities.User;
import com.loan.models.JwtRequest;
import com.loan.models.JwtResponse;
import com.loan.repository.AdminRepository;
import com.loan.repository.LoanRepository;
import com.loan.repository.UserRepository;
import com.loan.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

//    @Autowired
//    private JwtUtil jwtUtil;

    // Admin Management

    @Override
    public ResponseEntity<Admin> getAdminByEmail(String email) {
        Admin admin = adminRepository.findByEmail(email);
        return admin != null ? ResponseEntity.ok(admin) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Admin>> getAllAdmins() {
        return ResponseEntity.ok(adminRepository.findAll());
    }

    @Override
    public ResponseEntity<String> createAdmin(Admin admin) {
        if (adminRepository.findByEmail(admin.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Admin already exists");
        }
        adminRepository.save(admin);
        return ResponseEntity.ok("Admin created successfully");
    }

    @Override
    public ResponseEntity<String> updateAdmin(String email, Admin updatedAdmin) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) return ResponseEntity.notFound().build();

        admin.setName(updatedAdmin.getName());
        admin.setPassword(updatedAdmin.getPassword());
        adminRepository.save(admin);

        return ResponseEntity.ok("Admin updated successfully");
    }

    @Override
    public ResponseEntity<String> deleteAdmin(String email) {
        Admin admin = adminRepository.findByEmail(email);
        if (admin == null) return ResponseEntity.notFound().build();

        adminRepository.delete(admin);
        return ResponseEntity.ok("Admin deleted");
    }

    @Override
    public ResponseEntity<JwtResponse> loginAdmin(JwtRequest request) {
        Admin admin = adminRepository.findByEmail(request.getEmail());
        if (admin == null || !admin.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).build();
        }
        String token = UUID.randomUUID().toString()+admin.getEmail();
        return ResponseEntity.ok(new JwtResponse(token,admin.getEmail()));
    }

    // Loan Management

    @Override
    public ResponseEntity<List<Loan>> getAllLoans() {
        return ResponseEntity.ok(loanRepository.findAll());
    }

    @Override
    public ResponseEntity<Loan> updateLoanStatus(String id, String status) {
        Optional<Loan> loanOpt = loanRepository.findById(id);
        if (loanOpt.isEmpty()) return ResponseEntity.notFound().build();

        Loan loan = loanOpt.get();
        loan.setStatus(status);
        loanRepository.save(loan);
        return ResponseEntity.ok(loan);
    }

    // User Management

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @Override
    public ResponseEntity<User> getUserById(String id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<User> updateCibilScore(String id, String cibilScore) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isEmpty()) return ResponseEntity.notFound().build();

        User user = userOpt.get();
        user.setCibilScore(cibilScore);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }

    // Add deleteUser implementation
    @Override
    public ResponseEntity<String> deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();

        userRepository.delete(user);
        return ResponseEntity.ok("User deleted");
    }

    // Add updateUser implementation
    @Override
    public ResponseEntity<User> updateUser(String email, User updatedUser) {
        User user = userRepository.findByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();

        user.setName(updatedUser.getName());
        user.setEmail(updatedUser.getEmail());
        user.setPan(updatedUser.getPan());
        user.setAadhar(updatedUser.getAadhar());
        user.setCibilScore(updatedUser.getCibilScore());
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
