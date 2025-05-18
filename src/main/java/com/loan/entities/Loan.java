package com.loan.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    private String loanId;
    private String userId;
    private String amount;
    private String type;
    private String duration;
    private String status;
    private LocalDateTime date;
}
