package com.medtech.dicom.pipeline;

import com.medtech.dicom.pipeline.service.DicomParserService;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class DicomParserServiceTest {

	private final DicomParserService parserService = new DicomParserService();

	@Test
	void shouldExtractHeaderTagsFromDicomFile() throws Exception {
		// Given a sample DICOM file in src/test/resources/samples/sample.dcm
		ClassPathResource resource = new ClassPathResource("samples/sample.dcm");

		try (InputStream inputStream = resource.getInputStream()) {
			// When
			Map<String, String> metadata = parserService.parseHeader(inputStream);

			// Then
			assertNotNull(metadata);
			assertNotNull(metadata.get("modality"));
			System.out.println("Parsed Metadata: " + metadata);
		}
	}
}