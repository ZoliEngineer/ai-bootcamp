package com.epam.training.gen.ai.embedding;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import io.qdrant.client.PointIdFactory;
import io.qdrant.client.QdrantClient;
import io.qdrant.client.QueryFactory;
import io.qdrant.client.ValueFactory;
import io.qdrant.client.VectorsFactory;
import io.qdrant.client.WithPayloadSelectorFactory;
import io.qdrant.client.grpc.Points.PointStruct;
import io.qdrant.client.grpc.Points.QueryPoints;
import io.qdrant.client.grpc.Points.ScoredPoint;
import lombok.extern.slf4j.Slf4j;

@Repository
@Primary
@Slf4j
public class QdrantVectorDatabase implements VectorDatabase {

	@Autowired
	private QdrantClient qdrantClient;

	@Override
	public void insert(List<Float> embedding, String text) {
		PointStruct value = PointStruct.newBuilder().setVectors(VectorsFactory.vectors(embedding))
				.putPayload("text", ValueFactory.value(text)).setId(PointIdFactory.id(System.currentTimeMillis())).build();
		qdrantClient.upsertAsync("ai_collection", List.of(value));
	}

	@Override
	public String getMostSimilarTo(List<Float> embedding) {
		List<ScoredPoint> searchResult = null;
		try {
			searchResult = qdrantClient.queryAsync(QueryPoints.newBuilder()
			            .setCollectionName("ai_collection")
			            .setLimit(1)
			            .setQuery(QueryFactory.nearest(embedding))
			            .setWithPayload(WithPayloadSelectorFactory.enable(true))
			            .build()).get();
		} catch (InterruptedException | ExecutionException e) {
			log.error("Error while searching in database", e);
		}
		String response = searchResult.get(0).getPayloadOrThrow("text").getStringValue();
		return response;
	}

}
