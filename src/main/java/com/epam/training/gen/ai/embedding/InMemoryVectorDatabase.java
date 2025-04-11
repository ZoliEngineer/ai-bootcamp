package com.epam.training.gen.ai.embedding;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class InMemoryVectorDatabase implements VectorDatabase {

	private List<VectorStoreEntry> vectorStore = new ArrayList<>();

	@Override
	public void insert(List<Float> embedding, String text) {
		vectorStore.add(new VectorStoreEntry(embedding, text));
	}

	@Override
	public String getMostSimilarTo(List<Float> queryVector) {
		String mostSimilartext = "";
		double maxSimilarity = Double.MIN_VALUE;

		for (VectorStoreEntry storedVector : vectorStore) {
			double similarity = calculateCosineSimilarity(queryVector, storedVector.embedding);
			if (similarity > maxSimilarity) {
				maxSimilarity = similarity;
				mostSimilartext = storedVector.text;
			}
		}
		return mostSimilartext;
	}

	public double calculateCosineSimilarity(List<Float> vectorA, List<Float> vectorB) {
		// Check if both vectors are non-null and have the same size
		if (vectorA == null || vectorB == null || vectorA.size() != vectorB.size()) {
			throw new IllegalArgumentException("Vectors must be non-null and of the same size.");
		}

		// Initialize variables for dot product, magnitude of vectorA, and magnitude of
		// vectorB
		double dotProduct = 0.0;
		double magnitudeA = 0.0;
		double magnitudeB = 0.0;

		// Iterate through the vectors to calculate dot product and magnitudes
		for (int i = 0; i < vectorA.size(); i++) {
			float valueA = vectorA.get(i);
			float valueB = vectorB.get(i);

			dotProduct += valueA * valueB;
			magnitudeA += valueA * valueA;
			magnitudeB += valueB * valueB;
		}

		// Calculate the magnitudes
		magnitudeA = Math.sqrt(magnitudeA);
		magnitudeB = Math.sqrt(magnitudeB);

		// Check if magnitudes are non-zero to avoid division by zero
		if (magnitudeA == 0 || magnitudeB == 0) {
			throw new IllegalArgumentException("One of the vectors has zero magnitude.");
		}

		// Calculate and return cosine similarity
		return dotProduct / (magnitudeA * magnitudeB);
	}

	private class VectorStoreEntry {
		List<Float> embedding;
		String text;

		public VectorStoreEntry(List<Float> embedding, String text) {
			this.embedding = embedding;
			this.text = text;
		}

	}

}
