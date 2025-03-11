package com.epam.training.gen.ai.chat;

import org.springframework.stereotype.Service;

@Service
public class MockChatService implements ChatService {

	@Override
	public String sendMessage(String prompt) {
		return "Generic answer";
	}

}
