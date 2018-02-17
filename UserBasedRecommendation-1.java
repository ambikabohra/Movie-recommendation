package com.Mahout;

import java.io.*;
import java.util.*;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.*;
import org.apache.mahout.cf.taste.impl.neighborhood.*;
import org.apache.mahout.cf.taste.impl.recommender.*;
import org.apache.mahout.cf.taste.impl.similarity.*;
import org.apache.mahout.cf.taste.model.*;
import org.apache.mahout.cf.taste.neighborhood.*;
import org.apache.mahout.cf.taste.recommender.*;
import org.apache.mahout.cf.taste.similarity.*;

public class UserBasedRecommendation {

	public static void main(String[] args) {		
		try{	

			DataModel model = new FileDataModel(new File("ratings-2.csv"));
			//Pearson similarity
			UserSimilarity p_similarity = new PearsonCorrelationSimilarity(model);
			UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.1, p_similarity, model);
			UserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, p_similarity);
			List<RecommendedItem> recommendations = recommender.recommend(269, 3);
			System.out.println("PearsonCorelation similarity: ");
			for (RecommendedItem recommendation : recommendations) {
				System.out.println(recommendation);
			}
			// log likely hood similarity
			UserSimilarity l_similarity = new LogLikelihoodSimilarity(model);
			UserNeighborhood neighborhood_2 = new ThresholdUserNeighborhood(0.1, l_similarity, model);
			UserBasedRecommender recommender_2 = new GenericUserBasedRecommender(model, neighborhood_2, l_similarity);
			List<RecommendedItem> l_recommendations = recommender_2.recommend(269, 3);
			System.out.println("Loglikelyhood similarity: ");
			for (RecommendedItem l_recommendation : l_recommendations) {
				System.out.println(l_recommendation);
			}
			//euclidien similarity
			UserSimilarity e_similarity = new EuclideanDistanceSimilarity(model);
			UserNeighborhood neighborhood_3 = new ThresholdUserNeighborhood(0.1, e_similarity, model);
			UserBasedRecommender recommender_3 = new GenericUserBasedRecommender(model, neighborhood_3, l_similarity);
			List<RecommendedItem> e_recommendations = recommender_3.recommend(269, 3);
			System.out.println("Euclidien similarity: ");
			for (RecommendedItem e_recommendation : e_recommendations) {
				System.out.println(e_recommendation);
			}

			//Spearman similarity
			UserSimilarity s_similarity = new SpearmanCorrelationSimilarity(model);
			UserNeighborhood neighborhood_4 = new ThresholdUserNeighborhood(0.1, s_similarity, model);
			UserBasedRecommender recommender_4 = new GenericUserBasedRecommender(model, neighborhood_4, s_similarity);
			List<RecommendedItem> s_recommendations = recommender_4.recommend(269, 3);
			System.out.println("Spearman similarity: ");
			for (RecommendedItem s_recommendation : s_recommendations) {
				System.out.println(s_recommendation);
			}

		}
		catch (Exception e) {
			System.out.println("There was an error.");
			e.printStackTrace();
		}		

	}
}