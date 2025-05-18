package com.loan.services;

import com.loan.entities.DocumentEntity;
import com.loan.entities.Loan;
import com.loan.entities.User;
import com.loan.models.JwtRequest;
import com.loan.models.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    // Auth
    ResponseEntity<String> registerUser(User user);
    ResponseEntity<JwtResponse> loginUser(JwtRequest request);

    // Profile
    ResponseEntity<User> getUserByEmail(String email);
    ResponseEntity<User> updateUser(String email, User user);
    ResponseEntity<String> deleteUser(String email);

    // Document Handling
    ResponseEntity<DocumentEntity> uploadDocuments(String userId, MultipartFile aadharFile, MultipartFile panFile);
    ResponseEntity<DocumentEntity> getDocuments(String userId);

    // Loan Management
    ResponseEntity<String> applyLoan(String userId, Loan loan);
    ResponseEntity<List<Loan>> getUserLoans(String userId);
}
