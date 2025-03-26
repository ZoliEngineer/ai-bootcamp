package com.epam.training.gen.ai.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.implementation.CollectionUtil;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;
import com.microsoft.semantickernel.services.textcompletion.TextContent;
import com.microsoft.semantickernel.services.textcompletion.TextGenerationService;

import reactor.core.publisher.Mono;

@Service
public class OpenAITextService implements ChatService{
	@Autowired

	@Qualifier("text")
	private ChatCompletionService textService;
	
	@Autowired
	private InvocationContext context;
	
	@Autowired
	private Kernel kernel;
		
	@Override
	public String sendMessage(String prompt) {
		List<ChatMessageContent<?>> response = textService.getChatMessageContentsAsync(prompt, null, null).block();		
		
		ChatMessageContent<?> result = CollectionUtil.getLastOrNull(response);
				
		return result.getContent();
	}
}
