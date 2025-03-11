package com.epam.training.gen.ai.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.microsoft.semantickernel.implementation.CollectionUtil;
import com.microsoft.semantickernel.services.chatcompletion.ChatCompletionService;
import com.microsoft.semantickernel.services.chatcompletion.ChatMessageContent;

@Service
@Primary
public class OpenAIChatService implements ChatService {

	@Autowired
	private ChatCompletionService chatService;

	@Override
	public String sendMessage(String prompt) {
		List<ChatMessageContent<?>> response = chatService.getChatMessageContentsAsync(prompt, null, null).block();

		ChatMessageContent<?> result = CollectionUtil.getLastOrNull(response);
		return result.getContent();
	}

}
