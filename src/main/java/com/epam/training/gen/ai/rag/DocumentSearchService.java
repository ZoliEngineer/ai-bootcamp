package com.epam.training.gen.ai.rag;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.epam.training.gen.ai.embedding.EmbeddingGenerator;
import com.epam.training.gen.ai.embedding.VectorDatabase;
import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.implementation.CollectionUtil;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentSearchService {
	@Autowired
	private VectorDatabase vectorDatabase;

	@Autowired
	private EmbeddingGenerator embeddingGenerator;
	
	@Autowired
	@Qualifier("chat")
	private ChatCompletionService chatService;
	
	@Autowired
	private InvocationContext context;
	
	@Autowired
	private Kernel kernel;
	
	private final ChatHistory chatHistory = new ChatHistory();

	@PostConstruct
	private void init() {
		DocumentStore documentStore = new DocumentStore();
		documentStore.init();

		for (String dataLine : documentStore.getAllText()) {
			vectorDatabase.insert(embeddingGenerator.getEmbeddingFor(dataLine), dataLine);
		}
	}
	
	public String augmentedQuery(String prompt) {
		log.info("Find best match for: " + prompt);
		String supportDocument = getBestMatch(prompt);
		
		log.info(supportDocument);
		
		chatHistory.addSystemMessage("Answer the user query using the following context: " + supportDocument);
		
		chatHistory.addUserMessage(prompt);
		
		List<ChatMessageContent<?>> response = chatService.getChatMessageContentsAsync(chatHistory, kernel, context).block();	
		
		ChatMessageContent<?> result = CollectionUtil.getLastOrNull(response);
		chatHistory.addMessage(result);
				
		return result.getContent();
			
		
	}

	private String getBestMatch(String prompt) {
		return vectorDatabase.getMostSimilarTo(embeddingGenerator.getEmbeddingFor(prompt));
	}
}
