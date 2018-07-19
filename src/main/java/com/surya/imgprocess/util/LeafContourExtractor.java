package com.surya.imgprocess.util;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.imgproc.Imgproc;

public class LeafContourExtractor {
	
	
	public static void extractContours(Mat binaryImg,List<MatOfPoint> contourList)
	{
		Mat hierarchy = new Mat();
		Imgproc.findContours(binaryImg,
				contourList,
				hierarchy ,
				Imgproc.RETR_EXTERNAL,
				Imgproc.CHAIN_APPROX_SIMPLE);
	}
	

}
