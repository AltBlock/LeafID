package com.surya.imgprocess.util;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public class FeatureVectorCalculator {
	
	private Mat original_image,binaryImg;
	private Point centroid;
	List<MatOfPoint> contourList = new ArrayList<MatOfPoint>();
	private List<Double> featureVectorList = new ArrayList<Double>() ;
	
	
	public FeatureVectorCalculator(Mat original_image)
	{
		this.original_image=original_image;
		//convert image to binary
		this.binaryImg=ColouredImageToBinary.convertImageToBinary(this.original_image);
		
		//extract contour from binary image and save contour to contour list
		LeafContourExtractor.extractContours(binaryImg, contourList);
		
		//assign centroid value
		CentroidFinder cf = new CentroidFinder(contourList);
		this.centroid=cf.getCentroidUsingGreenTheorem();
		
		//calculate feature vector
		for(Point boundryPoint:getMaxAreadContour())
		{
			this.featureVectorList.add(getEuclideanDistance(boundryPoint));
		}
		
	}
		
		public List<Double> getFeatureVector()
		{
			return this.featureVectorList;
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
