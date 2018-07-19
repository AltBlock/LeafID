package com.surya.imgprocess.util;


import java.util.List;

import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;
import org.opencv.core.MatOfPoint;

public class CentroidFinder {

	private List<MatOfPoint> contours;

	public CentroidFinder(List<MatOfPoint> contours)
	{
		this.contours=contours;
	}
	public Point getCentroidUsingGreenTheorem()
	{			
		Point centroid = new Point();
		
		//perform some filtering
		MatOfPoint contour=getMaxAreadContour();
		
		Moments moment = Imgproc.moments(contour);
		centroid.x = moment.get_m10() / moment.get_m00();
		centroid.y = moment.get_m01() / moment.get_m00();
		
		return centroid;
	}
	
	
	
	//This function returns the region with max area and neglects other faulty area captured
	private MatOfPoint getMaxAreadContour()
	{
		int maxIndex=0;
		Point[] maxRegion=this.contours.get(maxIndex).toArray();
		
		for(int iterator=1;iterator<this.contours.size();iterator++)
		{
			if(maxRegion.length<this.contours.get(iterator).toArray().length)
				maxIndex=iterator;	
		}
	
		return this.contours.get(maxIndex);
	}
}
