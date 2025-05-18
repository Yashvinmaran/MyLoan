package com.loan.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String userId;
    private String name;
    private String email;
    private String password;
    private String cibilScore;
    private String pan;
    private String aadhar;
    private String role;
    @DBRef
    private DocumentEntity document;

    @DBRef
    private List<Loan> loans;

}
