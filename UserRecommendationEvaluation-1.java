package com.Mahout;

import java.io.File;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class UserRecommendationEvaluation {
	public static void main(String[] args) {		
		try{	
			DataModel model = new FileDataModel(new File("ratings-2.csv"));

			RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
				public Recommender buildRecommender(DataModel model) throws TasteException {                
					UserSimilarity e_similarity = new EuclideanDistanceSimilarity(model);
					UserNeighborhood neighborhood_1 = new ThresholdUserNeighborhood(0.1, e_similarity, model);
					return new GenericUserBasedRecommender(model, neighborhood_1,e_similarity);   
				}
			};
			RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();        
			double score = evaluator.evaluate(recommenderBuilder, null, model, 0.7, 1.0);    
			System.out.println("RMSE: " + score);

			RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();        
			IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, -4, 0.7); // evaluate precision recall at 10
			//IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, -3, 0.7); 
			//IRStatistics stats = statsEvaluator.evaluate(recommenderBuilder, null, model, null, 10, -1, 0.7); 
			System.out.println("Precision: " + stats.getPrecision());
			System.out.println("Recall: " + stats.getRecall());
			System.out.println("F1 Score: " + stats.getF1Measure());

		}
		catch (Exception e) {
			System.out.println("There was an error.");
			e.printStackTrace();
		}		

	}

}
