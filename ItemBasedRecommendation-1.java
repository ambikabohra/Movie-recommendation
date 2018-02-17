package com.Mahout;

import java.io.*;
import java.util.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;
import org.apache.mahout.math.hadoop.similarity.cooccurrence.measures.LoglikelihoodSimilarity;

public class ItemBasedRecommendation {

	public static void main(String[] args) {		
		try{	
		
			DataModel model = new FileDataModel(new File("ratings-2.csv"));
			ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
			Recommender recommender = new GenericItemBasedRecommender(model, similarity);
			List<RecommendedItem> recommendations = recommender.recommend(269, 3);
			System.out.println("Pearson similarity: ");
			for (RecommendedItem recommendation : recommendations) {
				  System.out.println(recommendation);
				}

			// log likely hood similarity
			ItemSimilarity similarity_2 = new LogLikelihoodSimilarity(model);
			Recommender recommender_2 = new GenericItemBasedRecommender(model, similarity_2);
			List<RecommendedItem> recommendations_2 = recommender_2.recommend(269, 3);
			System.out.println("Loglikelyhood similarity: ");
			for (RecommendedItem recommendation_2 : recommendations_2) {
				  System.out.println(recommendation_2);
				}
			//euclidien similarity
			ItemSimilarity similarity_3 = new EuclideanDistanceSimilarity(model);
			Recommender recommender_3 = new GenericItemBasedRecommender(model, similarity_3);
			List<RecommendedItem> e_recommendations = recommender_3.recommend(269, 3);
			System.out.println("Euclidien similarity: ");
			for (RecommendedItem e_recommendation : e_recommendations) {
				System.out.println(e_recommendation);
			}
	}
		catch (Exception e) {
			System.out.println("There was an error.");
			e.printStackTrace();
		}		
		
		}
}
