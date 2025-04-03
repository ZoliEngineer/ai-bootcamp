package com.epam.training.gen.ai.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.models.ImageGenerationOptions;
import com.azure.ai.openai.models.ImageGenerations;

@Service
public class OpenAIImageService {
	
	@Autowired
	OpenAIAsyncClient openAIAsyncClient;
	
	public String sendMessage(String prompt) {		
		 ImageGenerationOptions imageGenerationOptions = new ImageGenerationOptions(prompt);
		 
		ImageGenerations response = openAIAsyncClient.getImageGenerations("dall-e-3", imageGenerationOptions).block();
		
		
		return response.getData().get(0).getUrl();
		
		
		
	}
}
