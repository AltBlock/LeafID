package com.surya.imgprocess.util;

import org.opencv.imgcodecs.Imgcodecs;



import java.io.FileNotFoundException;

import org.opencv.core.Mat;

public class ImageFetcher {


	
	
	public Mat getMatrixFromImage(String path) throws FileNotFoundException 
	{
		Mat img=Imgcodecs.imread(path);
		if(img==null) throw new FileNotFoundException("Image at "+path+" is not found");
		else System.out.println("Image loaded from "+path+" with row and col "+img.rows()+"X"+img.cols());
		return img;
	}
}
