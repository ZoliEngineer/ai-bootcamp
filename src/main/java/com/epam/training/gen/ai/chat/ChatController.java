package com.epam.training.gen.ai.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private ChatService chatService;

	@GetMapping("/chat")
	public ResponseEntity<Map<String, String>> search(@RequestParam String prompt) {
		log.info("User prompt: " + prompt);

		String result = chatService.sendMessage(prompt);
		log.info("Chat-bot response: " + result);

		Map<String, String> response = new HashMap<>();
		response.put("output", result);
		return ResponseEntity.ok(response);
	}

}
