package com.epam.training.gen.ai.chat;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ChatController.class);

	@Autowired
	private OpenAIChatService chatService;
	
	@Autowired
	private OpenAITextService textService;

	@GetMapping("/chat")
	public ResponseEntity<Map<String, String>> chat(@RequestParam String prompt) {
		log.info("User prompt: " + prompt);

		String result = chatService.sendMessage(prompt);
		log.info("Chat-bot response: " + result);

		Map<String, String> response = new HashMap<>();
		response.put("output", result);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/text")
	public ResponseEntity<Map<String, String>> text(@RequestParam String prompt) {
		log.info("User prompt: " + prompt);

		String result = textService.sendMessage(prompt);
		log.info("Chat-bot response: " + result);

		Map<String, String> response = new HashMap<>();
		response.put("output", result);
		return ResponseEntity.ok(response);
	}

}
