package com.epam.training.gen.ai.semantic.configuration;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.azure.ai.openai.OpenAIAsyncClient;
import com.epam.training.gen.ai.semantic.plugins.CalculatorPlugin;
import com.epam.training.gen.ai.semantic.plugins.WeatherPlugin;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.aiservices.openai.chatcompletion.OpenAIChatCompletion;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.orchestration.PromptExecutionSettings;
import com.microsoft.semantickernel.orchestration.ToolCallBehavior;
import com.microsoft.semantickernel.plugin.KernelPlugin;
import com.microsoft.semantickernel.plugin.KernelPluginFactory;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;

/**
 * Configuration class for setting up Semantic Kernel components.
 * <p>
 * This configuration provides several beans necessary for the interaction with
 * Azure OpenAI services and the creation of kernel plugins. It defines beans
 * for chat completion services, kernel plugins, kernel instance, invocation
 * context, and prompt execution settings.
 */
@Configuration
public class SemanticKernelConfiguration {

	/**
	 * Creates a {@link ChatCompletionService} bean for handling chat completions
	 * using Azure OpenAI.
	 *
	 * @param deploymentOrModelName the Azure OpenAI deployment or model name
	 * @param openAIAsyncClient     the {@link OpenAIAsyncClient} to communicate
	 *                              with Azure OpenAI
	 * @return an instance of {@link ChatCompletionService}
	 */
	@Bean(name = "chat")
	@Primary
	public ChatCompletionService chatCompletionService(
			@Value("${client-azureopenai-deployment-name.chat}") String deploymentOrModelName,
			OpenAIAsyncClient openAIAsyncClient) {
		
		return OpenAIChatCompletion.builder().withModelId(deploymentOrModelName)
				.withOpenAIAsyncClient(openAIAsyncClient).build();
	}
	
	@Bean(name = "text")
	public ChatCompletionService textGenerationService(
			@Value("${client-azureopenai-deployment-name.text}") String deploymentOrModelName,
			OpenAIAsyncClient openAIAsyncClient) {
		
		return OpenAIChatCompletion.builder().withModelId(deploymentOrModelName)
				.withOpenAIAsyncClient(openAIAsyncClient).build();
	}

	

	/**
	 * Creates a {@link Kernel} bean to manage AI services and plugins.
	 *
	 * @param chatCompletionService the {@link ChatCompletionService} for handling
	 *                              completions
	 * @param kernelPlugin          the {@link KernelPlugin} to be used in the
	 *                              kernel
	 * @return an instance of {@link Kernel}
	 */
	@Bean
	public Kernel kernel(ChatCompletionService chatCompletionService) {
		return Kernel.builder().withAIService(ChatCompletionService.class, chatCompletionService)
				.withPlugin(KernelPluginFactory.createFromObject(new WeatherPlugin(), "getWeatherForecast"))
				.withPlugin(KernelPluginFactory.createFromObject(new CalculatorPlugin(), "basicArithmeticCalculator"))
				.build();
	}

	/**
	 * Creates an {@link InvocationContext} bean with default prompt execution
	 * settings.
	 *
	 * @return an instance of {@link InvocationContext}
	 */
	@Bean
	public InvocationContext invocationContext(Map<String, PromptExecutionSettings> promptExecutionsSettingsMap,
			@Value("${client-azureopenai-deployment-name.chat}") String deploymentOrModelName) {

		PromptExecutionSettings promptExecutionSettings = promptExecutionsSettingsMap.get(deploymentOrModelName);

		return InvocationContext.builder()
				.withPromptExecutionSettings(promptExecutionSettings)
				.withToolCallBehavior(ToolCallBehavior.allowAllKernelFunctions(true))
				.build();
	}

	/**
	 * Creates a map of {@link PromptExecutionSettings} for different models.
	 *
	 * @param deploymentOrModelName the Azure OpenAI deployment or model name
	 * @return a map of model names to {@link PromptExecutionSettings}
	 */
	@Bean
	public Map<String, PromptExecutionSettings> promptExecutionsSettingsMap(
			@Value("${client-azureopenai-deployment-name.chat}") String deploymentOrModelName, 
			@Value("${llm.settings.temperature}") double temperature) {
		return Map.of(deploymentOrModelName, PromptExecutionSettings.builder().withTemperature(temperature).build());
	}

}
