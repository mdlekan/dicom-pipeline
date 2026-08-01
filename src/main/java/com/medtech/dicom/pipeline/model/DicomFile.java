package com.medtech.dicom.pipeline.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Entity
@Builder
@Table(name = "dicom_files")
public class DicomFile {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(nullable = false)
    private String patientId;

    @Setter
    @Column(nullable = false)
    private String studyInstanceUid;

    @Setter
    private String storagePath;

    @Setter
    private LocalDateTime uploadedAt;

    public DicomFile() {
    }

    public DicomFile(String patientId, String studyInstanceUid, String storagePath) {
        this.patientId = patientId;
        this.studyInstanceUid = studyInstanceUid;
        this.storagePath = storagePath;
        this.uploadedAt = LocalDateTime.now();
    }

}