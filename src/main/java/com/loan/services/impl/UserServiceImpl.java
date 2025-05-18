package com.loan.services.impl;

import com.loan.entities.DocumentEntity;
import com.loan.entities.Loan;
import com.loan.entities.User;
import com.loan.models.JwtRequest;
import com.loan.models.JwtResponse;

import com.loan.repository.DocumentRepository;
import com.loan.repository.LoanRepository;
import com.loan.repository.UserRepository;
import com.loan.services.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final DocumentRepository documentRepository;
//    private final JwtUtil jwtUtil;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // ================= AUTH ===================

    @Override
    public ResponseEntity<String> registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }
        user.setRole("USER");
        user.setUserId(java.util.UUID.randomUUID().toString());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    @Override
    public ResponseEntity<JwtResponse> loginUser(JwtRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body(null);
        }
        String token = UUID.randomUUID().toString() + ":" + user.getEmail();
        return ResponseEntity.ok(new JwtResponse(token, user.getName()));
    }

    // ================ PROFILE ===================

    @Override
    public ResponseEntity<User> getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<User> updateUser(String email, User updatedUser) {
        User existingUser = userRepository.findByEmail(email);
        if (existingUser == null) return ResponseEntity.notFound().build();

        updatedUser.setUserId(existingUser.getUserId()); // Keep original ID
        userRepository.save(updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @Override
    public ResponseEntity<String> deleteUser(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) return ResponseEntity.notFound().build();

        userRepository.delete(user);
        return ResponseEntity.ok("User deleted successfully.");
    }

    // =============== DOCUMENT HANDLING =================

    @Override
    public ResponseEntity<DocumentEntity> uploadDocuments(String userId, MultipartFile aadharFile, MultipartFile panFile) {
        try {
            String aadharPath = saveFile(aadharFile);
            String panPath = saveFile(panFile);

            DocumentEntity doc = documentRepository.findByUserId(userId).orElse(new DocumentEntity());
            doc.setUserId(userId);
            doc.setAadharFilePath(aadharPath);
            doc.setPanFilePath(panPath);

            DocumentEntity saved = documentRepository.save(doc);
            return ResponseEntity.ok(saved);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @Override
    public ResponseEntity<DocumentEntity> getDocuments(String userId) {
        return documentRepository.findByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private String saveFile(MultipartFile file) throws IOException {
        File directory = new File(uploadDir);
        if(!directory.exists()){
            directory.mkdirs();
        }
        log.info(directory.getAbsolutePath());
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(directory, fileName);
        try {
            file.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Failed to save file: " + e.getMessage());
        }
        return dest.getAbsolutePath();
    }

    // ============== LOAN MANAGEMENT ==================

    @Override
    public ResponseEntity<String> applyLoan(String userId, Loan loan) {
        loan.setUserId(userId);
        loan.setStatus("PENDING");
        loanRepository.save(loan);
        return ResponseEntity.ok("Loan application submitted.");
    }

    @Override
    public ResponseEntity<List<Loan>> getUserLoans(String userId) {
        List<Loan> loans = loanRepository.findByUserId(userId);
        return ResponseEntity.ok(loans);
    }
}
