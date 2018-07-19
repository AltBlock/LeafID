package com.surya.loginTest.controller;

import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.surya.imgprocess.util.BestMatching;
import com.surya.imgprocess.util.DifferenceFromAverage;
import com.surya.imgprocess.util.ImageFetcher;
import com.surya.loginTest.model.Plant;
import com.surya.loginTest.model.Similarity;
import com.surya.loginTest.repository.PlantRepository;
import com.surya.loginTest.repository.StorageService;

@Controller
public class WebUserController {
	ImageFetcher fetcher=new ImageFetcher();
	public static final String base_img_url="upload-dir/";
	
	@Autowired
	private PlantRepository repository;
		
	private static StorageService storageService;
	
	@Autowired
	public WebUserController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String showHomePage() {
		return "webusers/homepage";
	}
	
	@PostMapping(value="/")
	public String showMatchPlant(@RequestParam("file") MultipartFile file,Model model) {
		List<Similarity> matchingleaf= new LinkedList<>();
		
		if (!((file == null)||(file.isEmpty()))) {
			try {
				String savedFileName=saveImageinFile(file);
				
		    	//fetch image and extract feature
		    	Mat imgMat=fetcher.getMatrixFromImage(base_img_url+savedFileName);
		    	
		    	
		    	
		    	DifferenceFromAverage diff = new DifferenceFromAverage(imgMat);
		    	List<Double> diffValues= diff.getDistanceDifferenceFromMean();
		    	
		    	//System.out.println("Size of contour points is "+diffValues);
		    	
		    	BestMatching bm = new BestMatching(diffValues ,repository.findAll());
		    	 matchingleaf= bm.getMatchingValues();
		    	
			}
			catch(FileNotFoundException e)
	    	{
	    		System.out.println("File not found.");
	    	}
	}
		else {System.out.println("file is empty SORRY.");}
		
		model.addAttribute("similarity", matchingleaf);
		return "webusers/showmatch";
}

	@RequestMapping(value="/singleview", method=RequestMethod.GET)
	public String showSinglePlant() {
		return "webusers/singleview";
	}
	 public String saveImageinFile(MultipartFile file)
	    {
	    	return storageService.store(file);
	    }
	 
		

}
