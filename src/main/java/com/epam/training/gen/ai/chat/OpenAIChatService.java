package com.epam.training.gen.ai.chat;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import com.microsoft.semantickernel.Kernel;
import com.microsoft.semantickernel.implementation.CollectionUtil;
import com.microsoft.semantickernel.orchestration.InvocationContext;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatHistory;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;


@Primary
@Service
@SessionScope
public class OpenAIChatService implements ChatService {

	@Autowired
	private ChatCompletionService chatService;
	
	@Autowired
	private InvocationContext context;
	
	@Autowired
	private Kernel kernel;
	
	private ChatHistory chatHistory = new ChatHistory();

	@Override
	public String sendMessage(String prompt) {
		chatHistory.addUserMessage(prompt);
		List<ChatMessageContent<?>> response = chatService.getChatMessageContentsAsync(chatHistory, null, context).block();
		
		ChatMessageContent<?> result = CollectionUtil.getLastOrNull(response);
		chatHistory.addMessage(result);
				
		return result.getContent();
	}

}
