package com.epam.training.gen.ai.embedding;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.document.MetadataMode;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.retry.RetryUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class OpenAIEmbeddingGenerator implements EmbeddingGenerator {

	@Value("${client-azureopenai-embedding-model}")
	private String embeddingModelName;

	@Value("${client-azureopenai-key}")
	private String key;

	@Value("${client-azureopenai-endpoint}")
	String endpoint;

	@Override
	public List<Float> getEmbeddingFor(String text) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Api-Key", key);

		OpenAiApi api = OpenAiApi.builder().apiKey(key).baseUrl(endpoint)
				.embeddingsPath("/openai/deployments/" + embeddingModelName + "/embeddings").headers(headers).build();
		OpenAiEmbeddingModel embeddingModel = new OpenAiEmbeddingModel(api, MetadataMode.EMBED,
				OpenAiEmbeddingOptions.builder().model(embeddingModelName).build(), RetryUtils.DEFAULT_RETRY_TEMPLATE);

		float[] embeddingResponse = embeddingModel.embed(text);

		List<Float> floatList = new ArrayList<>();
		for (float value : embeddingResponse) {
			floatList.add(value);
		}
		System.out.println(floatList);
		return floatList;

	}
}
