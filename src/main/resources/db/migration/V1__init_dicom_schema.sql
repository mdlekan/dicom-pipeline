-- 1. Create DicomStudy table
CREATE TABLE dicom_study (
                             id BIGSERIAL PRIMARY KEY,
                             study_instance_uid VARCHAR(128) NOT NULL UNIQUE,
                             patient_id VARCHAR(64) NOT NULL,
                             patient_name VARCHAR(128),
                             patient_birth_date DATE,
                             patient_sex VARCHAR(16),
                             study_date DATE,
                             study_description VARCHAR(255),
                             accession_number VARCHAR(64),
                             created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                             updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index frequently queried fields
CREATE INDEX idx_study_patient_id ON dicom_study(patient_id);
CREATE INDEX idx_study_date ON dicom_study(study_date);


-- 2. Create DicomSeries table
CREATE TABLE dicom_series (
                              id BIGSERIAL PRIMARY KEY,
                              study_id BIGINT NOT NULL REFERENCES dicom_study(id) ON DELETE CASCADE,
                              series_instance_uid VARCHAR(128) NOT NULL UNIQUE,
                              modality VARCHAR(16) NOT NULL,
                              series_number INT,
                              series_description VARCHAR(255),
                              body_part_examined VARCHAR(64),
                              s3_storage_path VARCHAR(512) NOT NULL,
                              instance_count INT DEFAULT 0,
                              created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index foreign keys and search paths
CREATE INDEX idx_series_study_id ON dicom_series(study_id);
CREATE INDEX idx_series_modality ON dicom_series(modality);