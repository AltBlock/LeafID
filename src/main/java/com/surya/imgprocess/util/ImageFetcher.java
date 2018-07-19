package com.surya.imgprocess.util;

import org.opencv.imgcodecs.Imgcodecs;



import java.io.FileNotFoundException;

import org.opencv.core.Mat;

public class ImageFetcher {

	private String[] path= {"C:/Users/shank/OneDrive/Desktop/archives/outputs/Figworts.jpg",
			"C:/Users/shank/OneDrive/Desktop/archives/outputs/G/Ageratum_houstonianum.jpg",
			"C:/Users/shank/OneDrive/Desktop/archives/outputs/longLeaf.jpg",
			"C:/Users/shank/OneDrive/Desktop/archives/outputs/lleaf2.jpg",
			"C:/Users/shank/OneDrive/Desktop/archives/outputs/big_leaf.jpg"};
	
	
	public Mat getMatrixFromImage(int index) throws FileNotFoundException 
	{
		Mat img=Imgcodecs.imread(this.path[index]);
		if(img==null) throw new FileNotFoundException("Image at "+this.path[index]+" is not found");
		else System.out.println("Image loaded from "+this.path[index]+" with row and col "+img.rows()+"X"+img.cols());
		
		return img;
	}
}
