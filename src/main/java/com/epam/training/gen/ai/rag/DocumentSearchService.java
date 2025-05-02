package com.epam.training.gen.ai.rag;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DocumentSearchService {
	
	@Value("${rag.documents.folder}")
	private String documentFolder;
	
	@Autowired
	@Qualifier("chat")
	private ChatClient chatClient;
	
	@PostConstruct
	private void init() {
		DocumentStore documentStore = new DocumentStore();
		documentStore.init(documentFolder);
		
		
		List<String> summaries = new ArrayList<>();

		for (String batch : documentStore.getAllText()) {
			
			String summary = chatClient.prompt().system("Below are a list of comments from various topics, delimited by ///. "
					+ "Please summarize with details broken down into topics, with at least a pragraph for each topic. "
					+ "Then write a summary about the perceived personality of the person who made these comments.")
					.user(batch).call().content();
			
			try {
			    // Sleep for 1 minute (60,000 milliseconds)
			    Thread.sleep(10000);
			} catch (InterruptedException e) {
			    // Handle the exception if the thread is interrupted
			    e.printStackTrace();
			}
			summaries.add(summary);
			int i = 0;
			System.out.println("file done " + i++);
			appendToFile(documentFolder + "\\summary\\summaries.txt", summary);
		}
		
		
		StringBuilder sumofsums = new StringBuilder();
		for(String sum : summaries) {
			sumofsums.append(sum);
			sumofsums.append(System.lineSeparator() + "----------------------------" + System.lineSeparator());
		}
		
		String summaryOfSummaries = chatClient.prompt().system("In a previous process, we created a sumamry of comments, broken down into various topics, "
				+ "and repeated it multiple times for batches of comments. Please summarize these summaries of every batch into a single summary, "
				+ "representing all the partial summaries. Summary should be made over each similar topics together, while different topics should get different summaries.  The batches are delimited by ----------------------------. Please be detailed and analytical, and make the response as long as required.")
				.user(sumofsums.toString()).call().content();
		
		System.out.println(summaryOfSummaries);
	
	}
	
	 public static void appendToFile(String filePath, String textToAppend) {
	        FileWriter fileWriter = null;
	        try {
	            // Open the file in append mode
	            fileWriter = new FileWriter(filePath, true);
	            // Write the text to the file
	            fileWriter.write(textToAppend);
	            // Optionally add a newline after the appended text
	            fileWriter.write(System.lineSeparator() + "----------------------------------" + System.lineSeparator());
	        } catch (IOException e) {
	            System.err.println("An error occurred while appending to the file: " + e.getMessage());
	        } finally {
	            // Close the FileWriter to release resources
	            if (fileWriter != null) {
	                try {
	                    fileWriter.close();
	                } catch (IOException e) {
	                    System.err.println("Failed to close the file writer: " + e.getMessage());
	                }
	            }
	        }
	 }
}
