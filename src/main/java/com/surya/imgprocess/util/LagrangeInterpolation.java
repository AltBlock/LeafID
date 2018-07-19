package com.surya.imgprocess.util;

import java.util.LinkedList;
import java.util.List;

import org.opencv.core.Point;

public class LagrangeInterpolation {
	
	
	List<Point> knownPoints;
	int degree;
	
	public LagrangeInterpolation(List<Point> knownPoints,int degree)
	{
		this.knownPoints=knownPoints;
		this.degree=degree;
		
	}
	public List<Point> interpolate() {
		List<Point> interpolatedPoints = new LinkedList<>();
		Point first=knownPoints.get(0);
		Point last=knownPoints.get(knownPoints.size()-1);
		
		double diff=Math.abs(first.x-last.x)/(degree+1);
		
		for(int i=0;i<degree-1;i++)
		{
			double xToCompute=first.x+diff*(i+1);
			interpolatedPoints.add(new Point(xToCompute,this.computeForX(xToCompute)));
		}
		
		System.out.println(degree+")"+knownPoints+" and interpolated "+interpolatedPoints);
		
		return interpolatedPoints;
	}
	
	private double computeForX(double x) {
		double numerator; //The numerator
        double denominator;  //The denominator
        int count,count2;
        int n= knownPoints.size();
        double y=0;
		for(count = 0; count<n; count++)
        {
             //Initialisation of variable
            numerator = 1; denominator = 1;
             
            //second Loop for the polynomial calculation
            for(count2 = 0; count2<n; count2++)
            {
                if (count2 != count)
                {
                  numerator = numerator * (x - knownPoints.get(count2).x);
                  denominator = denominator * (knownPoints.get(count).x - knownPoints.get(count2).x);
                } 
            }
            y = y + (numerator/denominator) * knownPoints.get(count).y;
        }
       return y;
	}

}
