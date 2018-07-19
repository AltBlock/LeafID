package com.surya.imgprocess.util;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public class FeatureVectorCalculator {
	
	ContourAtRadialDistance card;
	List<Point> contourPoint;
	List<Double> featureVector;
	Point centroid;
	
	
	public FeatureVectorCalculator(Mat original_image)
	{
		ContourAtRadialDistance card =new ContourAtRadialDistance(original_image);
		contourPoint=card.getPointsAtRadialDistance();
		centroid=card.getCentroid();
		
	}
		
		public List<Double> getFeatureVector()
		{
			return this.featureVector;;
		}
	
		
	private double getEuclideanDistance(Point edgePoint)
	{
		return Math.sqrt(Math.pow(edgePoint.x-centroid.x, 2)+Math.pow(edgePoint.y-centroid.y, 2));
	}
	
	
	private Point[] getMaxAreadContour()
	{
		int maxIndex=0;
		Point[] maxRegion=this.contourList.get(maxIndex).toArray();
		
		for(int iterator=1;iterator<this.contourList.size();iterator++)
		{
			if(maxRegion.length<this.contourList.get(iterator).toArray().length)
				maxIndex=iterator;	
		}
		
		return this.contourList.get(maxIndex).toArray();
	}
	public double computeAverageFeatureVector()
	{
		double sum=0;
		for(double vector:this.featureVectorList) sum+=vector;
		double mean=sum/this.featureVectorList.size();
		System.out.println("Mean FV is "+mean);
		
		return mean;
	}
	
	
		
}
