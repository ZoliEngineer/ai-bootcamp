package com.epam.training.gen.ai.rag;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DocumentStore {

	private List<String> textStore = new ArrayList<>();

	private int maxBatchSize = 100_000;

	public void init(String documentFolder) {
		StringBuilder batch = new StringBuilder();
		try {
			for (Path file : Files.list(Paths.get(documentFolder)).filter(Files::isRegularFile).toList()) {				
				for (String line : Files.readAllLines(file)) {
					List<String> row = parseCSVLine(line);
					if (row.size() > 7 && row.get(7).startsWith("{text:")) {
						String text = row.get(7);
						String cleanText = text.substring(text.lastIndexOf("{text:") + 6, text.length() - 1);
						batch.append(cleanText + "///");
						if (batch.length() > maxBatchSize) {
							textStore.add(batch.toString());
							batch = new StringBuilder();
						}
					}
				}
			}
		} catch (IOException e) {
			log.error("Error while reading document", e);
		}
	}

	public List<String> getAllText() {
		return textStore;
	}

	private static List<String> parseCSVLine(String line) {
		List<String> row = new ArrayList<>();
		StringBuilder currentCell = new StringBuilder();
		boolean inQuotes = false;

		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			// log.info(String.valueOf(c));
			if (c == '"') {
				// log.info("Toggle the inQuotes flag if we encounter a double quote");
				inQuotes = !inQuotes;
			} else if (c == ',' && !inQuotes) {
				// log.info("If we encounter a comma outside of quotes, it's the end of a
				// cell");
				row.add(currentCell.toString().trim());
				currentCell.setLength(0); // Clear the StringBuilder
				// log.info("" + row.size());
			} else {
				// log.info("Otherwise, add the character to the current cell");
				currentCell.append(c);
			}
		}

		// Add the last cell to the row
		row.add(currentCell.toString().trim());

		return row;
	}

}