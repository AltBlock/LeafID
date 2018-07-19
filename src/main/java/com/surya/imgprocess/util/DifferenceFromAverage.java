package com.surya.imgprocess.util;


import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;


public class DifferenceFromAverage {
	List<Double> distanceDiff;

	
	
	public DifferenceFromAverage(Mat img)
	{
		System.out.println("Recived image of dim "+img.rows()+"x"+img.cols());
		distanceDiff =new LinkedList<>();
		FeatureVectorCalculator fvc = new FeatureVectorCalculator(img);
		
		double average =fvc.getAverageFv();
		
		for(Double distance:fvc.getFeatureVector())
		{
			 this.distanceDiff.add(new Double(distance-average));
		}
		
		
		
	}
	
	public List<Double> getDistanceDifferenceFromMean(){
		
		return this.distanceDiff;
	}
	
}
