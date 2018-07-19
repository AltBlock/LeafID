package com.surya.imgprocess.util;

import java.util.*;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;

import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import com.surya.imgprocess.util.CentroidFinder;

import java.util.Map.Entry;


public class ContourAtRadialDistance {
	private Mat binaryImg;
	private Point centroid;
	private List<MatOfPoint> contourList=new LinkedList<>(); 
	private List<Point> contourPoints ;
	private List<Point> radPoints=new LinkedList<Point>();
	
	public ContourAtRadialDistance(Mat original_image)
	{
		this.binaryImg=ColouredImageToBinary.convertImageToBinary(original_image);
		
		Mat hierarchy = new Mat();
		Imgproc.findContours(binaryImg,
				this.contourList,
				hierarchy ,
				Imgproc.RETR_EXTERNAL,
				Imgproc.CHAIN_APPROX_SIMPLE);
		
		CentroidFinder cf = new CentroidFinder(this.contourList);
		this.centroid=cf.getCentroidUsingGreenTheorem();
		this.contourPoints =new LinkedList<Point>(extractContours());
		alternateMethod();
		 //calcuateAngleAtEachContour();
		
	}
	
	public  List<Point> extractContours()
	{
		
		return getMaxAreadContour().toList();
		
	}
	
	private MatOfPoint getMaxAreadContour()
	{
		int maxIndex=0;
		Point[] maxRegion=this.contourList.get(maxIndex).toArray();
		
		for(int iterator=1;iterator<this.contourList.size();iterator++)
		{
			if(maxRegion.length<this.contourList.get(iterator).toArray().length)
				maxIndex=iterator;	
		}
		maxRegion=this.contourList.get(maxIndex).toArray();
		//System.out.println("The total number of points is "+this.contourList.get(maxIndex).toArray().length);
		
		
		return new MatOfPoint(maxRegion);
	}
	
		
	public Point getCentroid()
	{
		return this.centroid;
	}
	
	
	
	public List<Point> getPointsAtRadialDistance()
	{
		return this.radPoints;
	}
	private void alternateMethod()
	{
	
		this.radPoints=new LinkedList<>(calcuateAngleAtEachContour().values());
		
	}
	
	
	public Map<Integer,Point> calcuateAngleAtEachContour() 
	{
		int currentAngle=0;
		Map<Integer,Point> anglePoints = new HashMap<>();
		List<Point> sameAngledPoints= new LinkedList<>();
		for(int i=0;i<this.contourPoints.size();i++)	{
			Point p=this.contourPoints.get(i);
			
			if(i==0) currentAngle=(int) Math.round(getAngleFromCentroid(p));
			
			int newAngle=(int) Math.round(getAngleFromCentroid(p));
			
			if(currentAngle!=newAngle)
			{
				anglePoints.put(currentAngle, computeAverageOfPoints(sameAngledPoints));
				sameAngledPoints.clear();
				currentAngle=newAngle;
			}
			
			sameAngledPoints.add(p);
		}
		anglePoints=sortByComparator(anglePoints,true);
		
		//duplicate values for insertion purpose
		Map<Integer,Point> dupAnglePoints = new HashMap<>(anglePoints);
		// still points at some angle may be missing so use lagrange interpolation
		
		List<Integer> angleList=new LinkedList<>(anglePoints.keySet());
		
		
		//Iterate till size()-1 to avoid fault value diff between 0 and 359
		for(int i=0;i<angleList.size()-1;i++) {
			int firstAngle=angleList.get(i);
			int secondAngle=angleList.get((i+1)%angleList.size());
			int degree=Math.abs(firstAngle-secondAngle);
			
			if(degree>1)
			{
				//need interpolation of some angle
				Point p1=anglePoints.get(angleList.get(i));
				Point p2=anglePoints.get(angleList.get((i+1)));
				
				List<Point> tempPoints =new LinkedList<Point>();
				tempPoints.add(p1);
				tempPoints.add(p2);
				
				LagrangeInterpolation li = new LagrangeInterpolation(tempPoints,degree);
				
				List<Point> interpolatedPoints=li.interpolate();
				//now insert those points in duplicate matrix
				for(int j=0;j<interpolatedPoints.size();j++)
				{
					dupAnglePoints.put(firstAngle+(j+1), interpolatedPoints.get(j));
				}
				
			}
		}
		
		//final sorting
		dupAnglePoints=sortByComparator(dupAnglePoints,true);
		
		//final check if contour length is greater than 360 remove them
		
		if(dupAnglePoints.size()>360) {
			for(int i=360;i<dupAnglePoints.size();i++)
			{
				dupAnglePoints.remove(i);
			}
		}
		dupAnglePoints.forEach((k,v)->{
			System.out.println("Point " + v + " has angle " + k);});
		return dupAnglePoints;
	}
	
	public double getAngleFromCentroid(Point screenPoint) {
	    double dx = screenPoint.x - centroid.x;
	    // Minus to correct for coord re-mapping
	    double dy = -(screenPoint.y- centroid.y);

	    double inRads = Math.atan2(dy, dx);

	    // We need to map to coord system when 0 degree is at 3 O'clock, 270 at 12 O'clock
	    if (inRads < 0)
	        inRads = Math.abs(inRads);
	    else
	        inRads = 2 * Math.PI - inRads;

	    return Math.toDegrees(inRads);
	}
	
	 private static Map<Integer, Point> sortByComparator(Map<Integer, Point> unsortMap, final boolean order)
	    {

	        List<Entry<Integer, Point>> list = new LinkedList<Entry<Integer, Point>>(unsortMap.entrySet());

	        // Sorting the list based on values
	        Collections.sort(list, new Comparator<Entry<Integer, Point>>()
	        {
	            public int compare(Entry<Integer, Point> o1,
	                    Entry<Integer, Point> o2)
	            {
	                if (order)
	                {
	                    return o1.getKey().compareTo(o2.getKey());
	                }
	                else
	                {
	                    return o2.getKey().compareTo(o1.getKey());

	                }
	            }
	        });

	        // Maintaining insertion order with the help of LinkedList
	        Map<Integer, Point> sortedMap = new LinkedHashMap<Integer, Point>();
	        for (Entry<Integer, Point> entry : list)
	        {
	            sortedMap.put(entry.getKey(), entry.getValue());
	        }

	        return sortedMap;
	    }
	private Point computeAverageOfPoints(List<Point> pointList)
	{
		Point p;
		
		int middle = pointList.size()/2;
		if ((pointList.size()%2) == 1) 
			p=pointList.get(middle);
		else
			p=new Point((pointList.get(middle).x+pointList.get(middle-1).x)/2,
					(pointList.get(middle).y+pointList.get(middle-1).y)/2);
		return p;
	}
	
	
}
