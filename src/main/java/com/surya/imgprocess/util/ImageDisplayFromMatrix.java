package com.surya.imgprocess.util;

import java.awt.image.BufferedImage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageDisplayFromMatrix {
 public void displayImage(Mat image,String title) throws IOException
 {
	 //Encoding the image 
     MatOfByte matOfByte = new MatOfByte();       
     Imgcodecs.imencode(".jpg", image, matOfByte); 

     //Storing the encoded Mat in a byte array 
     byte[] byteArray = matOfByte.toArray(); 

     //Preparing the Buffered Image 
     InputStream in = new ByteArrayInputStream(byteArray); 
     BufferedImage bufImage = ImageIO.read(in); 

     //Instantiate JFrame 
     JFrame frame = new JFrame(); 

     //Set Content to the JFrame 
     frame.getContentPane().add(new JLabel(new ImageIcon(bufImage))); 
     frame.setTitle(title);
     frame.pack(); 
     frame.setVisible(true);
     
     System.out.println(title+" image displayed");  
 }
}