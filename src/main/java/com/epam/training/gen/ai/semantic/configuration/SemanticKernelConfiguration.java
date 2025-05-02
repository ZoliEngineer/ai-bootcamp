package com.epam.training.gen.ai.semantic.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;

import com.epam.training.gen.ai.semantic.plugins.WeatherPlugin;

import lombok.extern.slf4j.Slf4j;


@Configuration
@Slf4j
public class SemanticKernelConfiguration {

	
	@Bean(name = "chat")
	@Primary
	public ChatClient chatClient(@Value("${client-azureopenai-endpoint}") String endpoint, @Value("${client-azureopenai-key}") String key,				
			@Value("${client-azureopenai-deployment-name.chat}") String deploymentOrModelName) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Api-Key", key);
		
		log.info("initializing Spring AI chat service " + endpoint + key + deploymentOrModelName);
		
		OpenAiApi api = OpenAiApi.builder().apiKey(key).baseUrl(endpoint).completionsPath("/openai/deployments/"+deploymentOrModelName + "/chat/completions")
				.headers(headers).build();
		OpenAiChatOptions options = OpenAiChatOptions.builder().temperature(0.5).model(deploymentOrModelName).build();
		OpenAiChatModel model = OpenAiChatModel.builder().openAiApi(api).build();
		
		ChatClient client = ChatClient.builder(model).defaultOptions(options).build(); 
				
		return client;
	}
	


}
