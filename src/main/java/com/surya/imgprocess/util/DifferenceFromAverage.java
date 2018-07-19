package com.surya.imgprocess.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;

public class DifferenceFromAverage {
	
	private Mat original_image;
	List<Point> radPoints;
	double average;
	public DifferenceFromAverage(Mat original_image)
	{
		this.original_image=original_image;
		ContourAtRadialDistance card =new ContourAtRadialDistance(
				);
		this.radPoints= new LinkedList<Point>(card.getPointsAtRadialDistance());
	}
	
	
	getAverageFV(List<Point> radPoints)
	{
		double sum_x=0,sum_y=0;
	}

}
