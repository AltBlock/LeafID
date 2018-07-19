package com.surya.imgprocess.util;


import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;


public class DifferenceFromAverage {
	List<Double> distanceDiff;

	
	
	public DifferenceFromAverage(Mat img)
	{
		distanceDiff =new LinkedList<>();
		FeatureVectorCalculator fvc = new FeatureVectorCalculator(img);
		double average =fvc.getAverageFv();
		for(double distance:fvc.getFeatureVector())
		{
			 distanceDiff.add(distance-average);
		}
		
		
		
	}
	
	public List<Double> getDistanceDifferenceFromMean(){
		return this.distanceDiff;
	}
	
}
