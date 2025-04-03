package com.epam.training.gen.ai.semantic.configuration;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QdrantGrpcClient;
import io.qdrant.client.grpc.Collections;
import io.qdrant.client.grpc.Collections.VectorParams;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up the Qdrant Client.
 * <p>
 * This configuration defines a bean that provides a client for interacting with
 * a Qdrant service. The client is built using gRPC to connect to a Qdrant
 * instance running at the specified host and port.
 */

@Configuration
@Slf4j
public class QdrantConfiguration {

	/**
	 * Creates a {@link QdrantClient} bean for interacting with the Qdrant service.
	 *
	 * @return an instance of {@link QdrantClient}
	 */
	@Bean
	public QdrantClient qdrantClient(@Value("${client-azureopenai-embedding-model.size}") int vectorSize) {
		QdrantClient qdrantClient = new QdrantClient(QdrantGrpcClient.newBuilder("localhost", 6334, false).build());
		setCollection(qdrantClient, "ai_collection", vectorSize);
		return qdrantClient;
	}

	private void setCollection(QdrantClient qdrantClient, String collectionName, int vectorSize) {
		qdrantClient.createCollectionAsync(collectionName,
				VectorParams.newBuilder().setDistance(Collections.Distance.Cosine).setSize(vectorSize).build());

	}
}
