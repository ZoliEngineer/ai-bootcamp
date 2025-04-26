package com.epam.training.gen.ai.rag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentStore {

	@Value("${rag.documents.folder}")
	private String documentFolder;

	private List<String> textStore = new ArrayList<>();

	public void init() {
		Tika tika = new Tika();
		try {
			for (Path file : Files.list(Paths.get(documentFolder)).toList()) {
				String documentText = tika.parseToString(file);

				String[] paragraphArray = documentText.split("(?m)(\\n\\n|\\r\\n\\r\\n|\\r\\r|\\r|\\u00B6|\\n\\u00A0\\n)");

				for (String paragraph : paragraphArray) {
					if (!paragraph.trim().isEmpty()) {

						int dotIndex = file.getFileName().toString().lastIndexOf('.');
						String dateLocation = "Approximate date (year and month) and location of the trip: "
								+ file.getFileName().toString().substring(0, dotIndex);
						textStore.add(dateLocation + System.lineSeparator() + paragraph);
					}
				}
			}
		} catch (IOException e) {
			log.error("Error while reading document", e);
		} catch (TikaException e) {
			log.error("Error while parsing document", e);
		}
	}

	public List<String> getAllText() {
		return textStore;
	}

}
