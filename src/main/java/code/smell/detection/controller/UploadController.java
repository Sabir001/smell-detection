package code.smell.detection.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import code.smell.detection.textualAnalysis.FileHandler;
import code.smell.detection.textualAnalysis.InformationRetrievalTemplate;
import code.smell.detection.textualAnalysis.MethodExtractor;

@Controller
public class UploadController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private MethodExtractor methodExtractor;
	
	@Autowired
	private InformationRetrievalTemplate informationRetrievalTemplate;
	
    @GetMapping("/uploadProject")
    public String index() {
    	log.info("Upload Project requested");
        return "upload";
    }

    @PostMapping("/upload") 
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        try{
        	if (file.isEmpty()) {
            	log.debug("file was empty");
                redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
                return "redirect:/uploadStatus";
            }

            if(!file.getOriginalFilename().endsWith(".zip")){
            	log.warn("File extension was not zip");
            	redirectAttributes.addFlashAttribute("message", "Please upload '.zip' file");
                return "redirect:/uploadStatus";
            }

            List<String> javaFileList = fileHandler.getJavaFiles(file);
            
            List<ArrayList<String>> allMethods = methodExtractor.getMethods(javaFileList);
            
            informationRetrievalTemplate.setLists(javaFileList, allMethods);
            
            javaFileList = informationRetrievalTemplate.javaFiles;
            allMethods = informationRetrievalTemplate.methods;
            
            redirectAttributes.addFlashAttribute("message", "Code Smell Result: Successful" );
            
        } catch(Exception e){
        	e.printStackTrace();
        }
        
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
    	log.info("Upload Status invoked");
        return "uploadStatus";
    }

}
