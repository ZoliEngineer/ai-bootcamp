package com.epam.training.gen.ai.semantic.configuration;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.azure.ai.openai.OpenAIClientBuilder;
import com.azure.core.credential.AzureKeyCredential;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpResponseLogger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the Azure OpenAI Async Client.
 * <p>
 * This configuration defines a bean that provides an asynchronous client
 * for interacting with the Azure OpenAI Service. It uses the Azure Key
 * Credential for authentication and connects to a specified endpoint.
 */
@Configuration
public class OpenAIConfiguration {

    /**
     * Creates an {@link OpenAIAsyncClient} bean for interacting with Azure OpenAI Service asynchronously.
     *
     * @return an instance of {@link OpenAIAsyncClient}
     */
    @Bean
    public OpenAIAsyncClient openAIAsyncClient(@Value("${client-azureopenai-endpoint}") String endpoint, @Value("${client-azureopenai-key}") String key) {
    	return new OpenAIClientBuilder()
    			.httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS))
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildAsyncClient();
    }
}
