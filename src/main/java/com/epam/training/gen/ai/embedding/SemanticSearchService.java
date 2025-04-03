package com.epam.training.gen.ai.embedding;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class SemanticSearchService {
	
	@Autowired
	private VectorDatabase vectorDatabase;
	
	@Autowired
	private EmbeddingGenerator embeddingGenerator;
	
	@PostConstruct
	private void init() {
		try {
			for(String dataLine : new TrainingDataSource().getData()) {
				vectorDatabase.insert(embeddingGenerator.getEmbeddingFor(dataLine), dataLine);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getBestMatch(String prompt) {
		return vectorDatabase.getMostSimilarTo(embeddingGenerator.getEmbeddingFor(prompt));
	}

}
