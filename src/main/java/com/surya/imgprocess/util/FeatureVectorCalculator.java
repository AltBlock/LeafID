package com.surya.imgprocess.util;

import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.Point;

public class FeatureVectorCalculator {
	
	ContourAtRadialDistance card;
	List<Point> contourPoint;
	List<Double> featureVectors;
	Point centroid;
	double averageFv;
	
	
	public FeatureVectorCalculator(Mat original_image)
	{
		ContourAtRadialDistance card =new ContourAtRadialDistance(original_image);
		contourPoint=card.getPointsAtRadialDistance();
		centroid=card.getCentroid();
		featureVectors =new LinkedList<>();
		calculateFeatureVector();
		computeAverageFeatureVector();
		
	}
	public double getAverageFv() 
	{return this.averageFv;}
		
	public List<Double> getFeatureVector()
	{
			return this.featureVectors;
	}
	
		
	private double getEuclideanDistance(Point edgePoint)
	{
		return Math.sqrt(Math.pow(edgePoint.x-centroid.x, 2)+Math.pow(edgePoint.y-centroid.y, 2));
	}
	
	
	
	public void computeAverageFeatureVector()
	{
		double sum=0;
		for(double vector:this.featureVectors) sum+=vector;
		this.averageFv=sum/this.featureVectors.size();
		
	}
	
	private void calculateFeatureVector()
	{
		for(Point edgePoint:this.contourPoint)
		{
			featureVectors.add(getEuclideanDistance(edgePoint));
		}
	}
	
		
}
