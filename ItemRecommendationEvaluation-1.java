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
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

public class ItemRecommendationEvaluation {

	public static void main(String[] args) {		
		try{	

			DataModel model = new FileDataModel(new File("ratings-2.csv"));

			RecommenderBuilder builder = new RecommenderBuilder() {
				public Recommender buildRecommender(DataModel model) throws TasteException {
					ItemSimilarity similarity = new EuclideanDistanceSimilarity(model);
					return new GenericItemBasedRecommender(model,  similarity);
				}
			};
			RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();        
			double score = evaluator.evaluate(builder, null, model, 0.7, 1.0);    
			System.out.println("RMSE: " + score);

			RecommenderIRStatsEvaluator statsEvaluator = new GenericRecommenderIRStatsEvaluator();        
			IRStatistics stats = statsEvaluator.evaluate(builder, null, model, null, 10, -4, 0.7); // evaluate precision recall at 10
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
