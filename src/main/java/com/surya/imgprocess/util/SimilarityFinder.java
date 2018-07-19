package com.surya.imgprocess.util;



import java.util.List;
import java.lang.Double;

import com.surya.imgprocess.exception.NotSameSizeException;



public class SimilarityFinder {
	
	public static double calcluateSimilarity(List<Double> img1, List<Double> img2) throws NotSameSizeException
	{
		System.out.println("IMG1 "+img1);
		System.out.println("IMG2 "+img2);
		
		int smallLnth=(img1.size()>img2.size())?img2.size():img1.size();
		
		//if(img1.size()!=img2.size()) throw new NotSameSizeException("Provided two matrices don't have same size");
	
		

		double nominator=0;
		
		for(int i=0;i<smallLnth;i++)
		{
			
			nominator+=(img1.get(i)*img2.get(i));
		}
		
		double denominator1=0;
		double denominator2=0;
		
		for(int i=0;i<smallLnth;i++)
		{
			denominator1+=square(img1.get(i));
			denominator2+=square(img2.get(i));
		}
		
		double mainSim=nominator/Math.sqrt(denominator1*denominator2);
		double forDisplaySim=(mainSim+1)*50;//in percentile format
		
		return round(forDisplaySim,2);
	}
	
	private static double square(double number) {return Math.pow(number,2);}
	
	private static double round(double value, int places) {
	    long factor = (long) Math.pow(10, places);
	    value = value * factor;
	    long tmp = Math.round(value);
	    return (double) tmp / factor;
	}
}
