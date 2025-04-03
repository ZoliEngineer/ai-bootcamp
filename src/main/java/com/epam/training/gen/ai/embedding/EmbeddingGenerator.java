package com.epam.training.gen.ai.embedding;

import java.util.List;

public interface EmbeddingGenerator {
	List<Float> getEmbeddingFor(String text);
}
