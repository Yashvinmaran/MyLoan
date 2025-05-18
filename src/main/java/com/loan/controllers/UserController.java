package com.loan.controllers;

import com.loan.entities.DocumentEntity;
import com.loan.entities.Loan;
import com.loan.entities.User;
import com.loan.models.JwtRequest;
import com.loan.models.JwtResponse;
import com.loan.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ================= AUTH =================

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        return userService.loginUser(request);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser(@RequestParam String email) {
        // We'll use a simple email parameter for now
        // Later with proper security setup, we can use:
        // Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // String currentUserEmail = authentication.getName();
        return userService.getUserByEmail(email);
    }

    // ================= PROFILE =================

    @GetMapping("/get/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @RequestBody User user) {
        return userService.updateUser(email, user);
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }

    // ================= DOCUMENTS =================

    @PostMapping("/upload-documents/{userId}")
    public ResponseEntity<DocumentEntity> uploadDocuments(
            @PathVariable String userId,
            @RequestParam("aadharFile") MultipartFile aadharFile,
            @RequestParam("panFile") MultipartFile panFile) {

        return userService.uploadDocuments(userId, aadharFile, panFile);
    }

    @GetMapping("/documents/{userId}")
    public ResponseEntity<DocumentEntity> getDocuments(@PathVariable String userId) {
        return userService.getDocuments(userId);
    }

    // ================= LOANS =================

    @PostMapping("/loan/apply/{userId}")
    public ResponseEntity<String> applyLoan(@PathVariable String userId, @RequestBody Loan loan) {
        return userService.applyLoan(userId, loan);
    }

    @GetMapping("/loans/{userId}")
    public ResponseEntity<List<Loan>> getUserLoans(@PathVariable String userId) {
        return userService.getUserLoans(userId);
    }
}
