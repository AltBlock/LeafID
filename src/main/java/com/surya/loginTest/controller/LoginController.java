package com.surya.loginTest.controller;


import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.opencv.core.Mat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.surya.imgprocess.util.DifferenceFromAverage;
import com.surya.imgprocess.util.ImageFetcher;
import com.surya.loginTest.model.Plant;
import com.surya.loginTest.repository.PlantRepository;
import com.surya.loginTest.repository.StorageService;





@Controller
public class LoginController {
	ImageFetcher fetcher=new ImageFetcher();
	public static final String base_img_url="upload-dir/";
	@Autowired
	private PlantRepository repository;
		
	private static StorageService storageService;
	
	  @Autowired
	    public LoginController(StorageService storageService) {
	        this.storageService = storageService;
	    }
	  
	  
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String getloginForm() {
		return "login";
	}
	
	
	

	@RequestMapping(value="/plant", method=RequestMethod.GET)
	public String gethomepage(Model model) {
		List<Plant> plantList = repository.findAll();
		System.out.println(plantList);
		model.addAttribute("plantList", plantList);
		return "viewallplant";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/form") 
	public String userAddPage(Model model){
		model.addAttribute("addStatus", true);
		
		return "plantform";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/viewplant", method=RequestMethod.GET)
		public String getPlantList(Model model) {
		List<Plant> plantList = repository.findAll();
		System.out.println(plantList);
		model.addAttribute("plantList", plantList);
		return "viewallplant";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/viewplant/{id}", method=RequestMethod.GET)	
		public String getPlant(@PathVariable String id, Model model) {
		Optional<Plant> selectedPlant =repository.findById(id);
		model.addAttribute("plantDetails", selectedPlant.get());
		return "plantdetails";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/update/{id}") //@RequestMapping(value = "/plant/edit")
	public String editPlant(Model model,@PathVariable String id){
		System.out.println("in console Id: " + id);
		Optional<Plant> plant = repository.findById(id);
		model.addAttribute("plant", plant.get());
		//model.addAttribute("addStatus", false);
		return "updateplant";
	}

	@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(value="/plant/delete", method=RequestMethod.DELETE)
	public String deletePlant(@RequestParam("id") String id){
		repository.deleteById(id);
		return "redirect:/viewplant";
	}
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@SuppressWarnings("null")
	@PostMapping("/update")
	public String updatePlant(@Valid @ModelAttribute("plant")Plant plant,
    		@RequestParam(value = "file", required=false) MultipartFile file) {
		
		if (!((file == null)||(file.isEmpty()))) {
			try {
				String savedFileName=saveImageinFile(file);
				plant.setImageUrl(savedFileName);
		    	//fetch image and extract feature
		    	Mat imgMat=fetcher.getMatrixFromImage(base_img_url+savedFileName);
		    	
		    	
		    	DifferenceFromAverage diff = new DifferenceFromAverage(imgMat);
		    	List<Double> diffValues= diff.getDistanceDifferenceFromMean();
		    	
		    	System.out.println("Size of contour points is "+diffValues);
		    	plant.setDistanceDifferenceFromMean(diffValues);

		    	/// saving plant to database
		    	}
		    	catch(FileNotFoundException e)
		    	{
		    		System.out.println("File not found.");
		    	}
		}
			
		repository.save(plant);
		System.out.println("Added new Plant details with id : "+plant.getId());
		return "redirect:/viewplant";
		}

	 public String saveImageinFile(MultipartFile file)
	    {
	    	return storageService.store(file);
	    }
	 
		
	
	 
	
}
