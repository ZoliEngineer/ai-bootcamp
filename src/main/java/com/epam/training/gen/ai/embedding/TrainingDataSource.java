package com.epam.training.gen.ai.embedding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TrainingDataSource {

	public List<String> getData() throws IOException {
		Path resourcePath = Paths.get("src/main/resources", "example-texts.txt");		
		List<String> data = Files.readAllLines(resourcePath);
		
		log.info(data.toString());
		
		return data;
	}
}
