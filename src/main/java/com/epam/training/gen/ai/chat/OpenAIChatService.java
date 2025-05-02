package com.epam.training.gen.ai.chat;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import lombok.extern.slf4j.Slf4j;

@Primary
@Service
@SessionScope
@Slf4j
public class OpenAIChatService implements ChatService {

	@Autowired
	@Qualifier("chat")
	private ChatClient chatClient;


	@Override
	public String sendMessage(String prompt) {
		log.info("Spring AI Prompt:" + prompt);

		return chatClient.prompt().user(prompt).call().content();
	}

}
