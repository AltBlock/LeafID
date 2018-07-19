package com.surya.imgprocess.util;

import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;


public class ColouredImageToBinary {
	//default min-max value for thresholding
	private static int MAX_VALUE=255;
	private static int MIN_VALUE=185;
	
	/**
	 * @param colouredImage is the image which is to be converted to binary
	 *
	 */
	public static Mat convertImageToBinary(Mat colouredImage)
	{
		return convertImageToBinary(colouredImage,MIN_VALUE,MAX_VALUE);
	}
	public static Mat convertImageToBinary(Mat colouredImage,int min,int max)
	{
		Mat grayImg=new Mat(),thresholdMat=new Mat();
		
		
		//do Gaussian Blur
		Imgproc.GaussianBlur(colouredImage, colouredImage,new Size(0,0), 1.0);
		
		//convert RGB image to grayscale
		Imgproc.cvtColor(colouredImage,grayImg,Imgproc.COLOR_RGB2GRAY);
		
		
		/*
		 * for proper extraction of contours 
		 * image need to be converted to BINARY.
		 * So perform thresholding of image such 
		 * that background is black and part to 
		 * be focused is white(i.e Imgproc.THRESH_BINARY_INV)
		 * */
		Imgproc.threshold(grayImg, 
				thresholdMat, 
				min,
				max,
				Imgproc.THRESH_BINARY_INV);
		
		return thresholdMat;
	}
}
