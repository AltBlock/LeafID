package com.surya.loginTest.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.surya.loginTest.model.Plant;
import com.surya.loginTest.repository.PlantRepository;


@RestController
public class PlantController {
	
	@Autowired
	private PlantRepository repository;
	
	@PostMapping("/addPlant")
	public String savePlant(@RequestBody Plant plant) {
		repository.save(plant);
		return "Added new Plant details with id : "+plant.getId();
		}

	

	@GetMapping("/findAllPlant")
	public List<Plant> getAllPlants() {
		return repository.findAll();
	}

	@GetMapping("/findAllPlant/{id}")
	public Optional<Plant> getPlant(@PathVariable String id) {
		return repository.findById(id);
	}

	@DeleteMapping("/delete/{id}")
	public List<Plant> deletePlant(@PathVariable String id) {
		repository.deleteById(id);
		//return "Plant deleted successfully with id : " + id;
		return repository.findAll();
	}
	
	/*//for image upload to file system
	@RequestMapping(value="/uploadImage", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
		File convertFile=new File(".\\src\\upload\\"+file.getOriginalFilename());
	
		convertFile.createNewFile();
		FileOutputStream fout=new FileOutputStream(convertFile);
		fout.write(file.getBytes());
		fout.close();
		return new ResponseEntity<>("file uploaded", HttpStatus.OK);
	}*/

}
