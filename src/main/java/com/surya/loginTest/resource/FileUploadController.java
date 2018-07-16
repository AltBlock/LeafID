package com.surya.loginTest.resource;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.surya.loginTest.exceptions.StorageFileNotFoundException;
import com.surya.loginTest.model.Plant;
import com.surya.loginTest.repository.PlantRepository;
import com.surya.loginTest.repository.StorageService;



@Controller
public class FileUploadController {
	
	public static final int ID_LENGTH = 10;

    private static StorageService storageService;
    @Autowired
	private PlantRepository repo;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

   // @ResponseBody
    @GetMapping("/files")
    public String listUploadedFiles(Model model) throws IOException {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "plantform";
    }

    @GetMapping("/file/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }
    
  
   /* @PostMapping("/file/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) throws Exception {
        storageService.store(file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/files";
    }*/
    @PostMapping("/file/upload")
    public String submit(
    		@Valid @ModelAttribute("plant")Plant plant,
    		@RequestParam MultipartFile file, @RequestParam String species) { 
    	   	
    	//saving image url
    	plant.setId(generateUniqueId());
    	plant.setImageUrl(saveImageinFile(file));
    	/// saving plant to database
    	repo.save(plant);     
        
        return "redirect:/viewplant";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
    public String generateUniqueId() {
        return RandomStringUtils.randomAlphanumeric(ID_LENGTH);
    }
    
    /*This method saves image in file and return url of image*/
    public String saveImageinFile(MultipartFile file)
    {
    	return storageService.store(file);
    }

}