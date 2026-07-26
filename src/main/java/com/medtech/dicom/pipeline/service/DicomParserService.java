package com.medtech.dicom.pipeline.service;

import org.dcm4che3.data.Attributes;
import org.dcm4che3.data.Tag;
import org.dcm4che3.io.DicomInputStream;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class DicomParserService {

    /**
     * Parses a DICOM input stream and extracts essential clinical header tags.
     */
    public Map<String, String> parseHeader(InputStream inputStream) throws IOException {
        try (DicomInputStream dis = new DicomInputStream(inputStream))
        {
            // readDataset(-1, -1) reads the entire dataset without byte limits
            Attributes attributes = dis.readDataset(-1, -1);

            Map<String, String> metadata = new HashMap<>();

            // Patient Metadata (PHI)
            metadata.put("patientId", attributes.getString(Tag.PatientID, "UNKNOWN"));
            metadata.put("patientName", attributes.getString(Tag.PatientName, "ANONYMOUS"));

            // Study / Series Identifiers
            metadata.put("studyInstanceUid", attributes.getString(Tag.StudyInstanceUID, "N/A"));
            metadata.put("seriesInstanceUid", attributes.getString(Tag.SeriesInstanceUID, "N/A"));

            // Acquisition Details
            metadata.put("modality", attributes.getString(Tag.Modality, "UNKNOWN")); // e.g., CT, MR, US
            metadata.put("studyDate", attributes.getString(Tag.StudyDate, "N/A"));
            metadata.put("seriesDescription", attributes.getString(Tag.SeriesDescription, "N/A"));

            return metadata;
        }
    }

    /**
     * Helper overload for direct File access.
     */
    public Map<String, String> parseHeader(File dicomFile) throws IOException {
        try (DicomInputStream dis = new DicomInputStream(dicomFile)) {
            Attributes attributes = dis.readDataset(-1, -1);

            Map<String, String> metadata = new HashMap<>();
            metadata.put("patientId", attributes.getString(Tag.PatientID, "UNKNOWN"));
            metadata.put("patientName", attributes.getString(Tag.PatientName, "ANONYMOUS"));
            metadata.put("studyInstanceUid", attributes.getString(Tag.StudyInstanceUID, "N/A"));
            metadata.put("modality", attributes.getString(Tag.Modality, "UNKNOWN"));

            return metadata;
        }
    }
}