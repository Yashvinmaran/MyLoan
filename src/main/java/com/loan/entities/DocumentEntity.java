package com.loan.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documents")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentEntity {
    @Id
    private String documentId;

    private String userId; // Optional, for quick lookup
    private String aadharFilePath;
    private String panFilePath;
}
