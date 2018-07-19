package com.surya.imgprocess.util;


import java.util.List;

import org.opencv.core.Point;

public class Bresenham {
	 /** function findLine() - to find that belong to line connecting the two points **/ 
    public List<Point> findLine(Point p0, Point p1,List<Point> line) 
    {      
    	p0.x=Math.round(p0.x);
    	p0.y=Math.round(p0.y);
    	p1.x=Math.round(p1.x);
    	p1.y=Math.round(p1.y);
        
    	
        int dx = (int)Math.abs(p1.x - p0.x);
        int dy = (int)Math.abs(p1.y - p0.y);
 
        int sx = p0.x < p1.x ? 1 : -1; 
        int sy = p0.y < p1.y ? 1 : -1; 
 
        int err = dx-dy;
        int e2;
 
        while (true) 
        {
            line.add(new Point(p0.x,p0.y));
 
            if (p0.x == p1.x && p0.y == p1.y) 
                break;
 
            e2 = 2 * err;
            if (e2 > -dy) 
            {
                err = err - dy;
                p0.x = p0.x + sx;
            }
 
            if (e2 < dx) 
            {
                err = err + dx;
                p0.y = p0.y + sy;
            }
        }                                
        return line;
    }
}
