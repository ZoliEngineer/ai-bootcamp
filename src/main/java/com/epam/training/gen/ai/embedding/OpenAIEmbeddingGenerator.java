package com.epam.training.gen.ai.embedding;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.EmbeddingItem;
import com.azure.ai.openai.models.EmbeddingsOptions;

@Service
public class OpenAIEmbeddingGenerator implements EmbeddingGenerator {
	
	@Autowired
	private OpenAIAsyncClient openAIAsyncClient;

	@Value("${client-azureopenai-embedding-model}")
	private String embeddingModel;		

	@Override
	public List<Float> getEmbeddingFor(String text) {
		EmbeddingsOptions embeddingsOptions = new EmbeddingsOptions(List.of(text));
		List<EmbeddingItem> embeddings = openAIAsyncClient.getEmbeddings(embeddingModel, embeddingsOptions).block()
				.getData();

		return embeddings.get(0).getEmbedding();
	}
}
