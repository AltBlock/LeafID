package com.surya.imgprocess.util;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;

import com.surya.imgprocess.exception.NotSameSizeException;



public class SimilarityFinder {
public double computeSimilarity(Mat image1,Mat image2) throws NotSameSizeException
{
	FeatureVectorCalculator fvc1 = new FeatureVectorCalculator(image1);
	FeatureVectorCalculator fvc2 = new FeatureVectorCalculator(image2);
	
	//get feature vector
	List<Double> fv1=fvc1.getFeatureVector();
	List<Double> fv2=fvc2.getFeatureVector();
	
	
	if(fv1.size()!=fv2.size()) throw new NotSameSizeException("Two feature vectors don't have same size");
	
	double meanFv1=fvc1.computeAverageFeatureVector();
	double meanFv2=fvc2.computeAverageFeatureVector();
	
	
	double nominator=0;
	for(int i=0;i<fv1.size();i++)
	{
		nominator+=((fv1.get(i)-meanFv1)*(fv2.get(i)-meanFv2));
	}
	
	double denominator1=0;
	double denominator2=0;
	
	for(int i=0;i<fv1.size();i++)
	{
		denominator1+=square(fv1.get(i)-meanFv1);
		denominator2+=square(fv2.get(i)-meanFv2);
	}
	
	return nominator/Math.sqrt(denominator1*denominator2);
	
}

private double square(double number) {return Math.pow(number,2);}


}
