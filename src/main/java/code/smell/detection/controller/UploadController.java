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

import code.smell.detection.textual.analysis.FileHandler;
import code.smell.detection.textual.analysis.InformationRetrievalTemplate;
import code.smell.detection.textual.analysis.MethodExtractor;
import code.smell.detection.textual.analysis.SmellDetector;
import code.smell.detection.textual.analysis.file.creation.FileManipulation;

@Controller
public class UploadController {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String REDIRECT_TO_UPLOAD_STATUS = "redirect:/uploadStatus";
	private static final String MESSAGE = "message";
	
	@Autowired
	private FileHandler fileHandler;
	
	@Autowired
	private MethodExtractor methodExtractor;
	
	@Autowired
	private InformationRetrievalTemplate informationRetrievalTemplate;
	
	@Autowired
	private SmellDetector smellDetector;
	
	@Autowired
	private FileManipulation fileManipulation;
	
	
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
                redirectAttributes.addFlashAttribute(MESSAGE, "Please select a file to upload");
                return REDIRECT_TO_UPLOAD_STATUS;
            }

            if(!file.getOriginalFilename().endsWith(".zip")){
            	log.warn("File extension was not zip");
            	redirectAttributes.addFlashAttribute(MESSAGE, "Please upload '.zip' file");
                return REDIRECT_TO_UPLOAD_STATUS;
            }

            List<String> javaFileList = fileHandler.getJavaFiles(file);
            
            
            
            List<ArrayList<String>> allMethods = methodExtractor.getMethods(javaFileList);
            
            List<ArrayList<ArrayList<String>>> statements = getStatements(allMethods);
            
            informationRetrievalTemplate.setLists(javaFileList, statements);
            
            List<ArrayList<ArrayList<String>>>  changedAllMethods = informationRetrievalTemplate.getMethods();
            List<String> changedJavaFiles = informationRetrievalTemplate.getJavaFiles();
            
            
            smellDetector.initialization(changedJavaFiles, changedAllMethods, javaFileList, allMethods);
            fileManipulation.createSourceDirectory();
            fileManipulation.createStopWordDirectory();
            fileManipulation.manageStopWords();
            
            List<ArrayList<String>> results = smellDetector.detectCodeSmell();
            
            redirectAttributes.addFlashAttribute("resultsOfBlob", results.get(0) );
            redirectAttributes.addFlashAttribute("resultsOfFeatureEnvy", results.get(1) );
            redirectAttributes.addFlashAttribute("resultsOfLongMethod", results.get(2) );
            redirectAttributes.addFlashAttribute("resultsOfMisplacedClass", results.get(3) );
            redirectAttributes.addFlashAttribute("resultsOfPromiscuousPackage", results.get(4) );
            
            redirectAttributes.addFlashAttribute(MESSAGE, "Code Smell Result: Successful" );
            
        } catch(Exception e){
        	log.error(e.getMessage(), e);
        }
        
        return REDIRECT_TO_UPLOAD_STATUS;
    }

    private List<ArrayList<ArrayList<String>>> getStatements(List<ArrayList<String>> allMethods) {
    	List<ArrayList<ArrayList<String>>> statements = new ArrayList<>();
    	
    	for(ArrayList<String> list: allMethods){
    		ArrayList<ArrayList<String>> classFiles = new ArrayList<>();
    		for(String method : list){
    			String[] lines = method.trim().split(";");
    			List<String> linesList = new ArrayList<>();
    			for(String line : lines){
    				linesList.add(line);
    			}
    			classFiles.add((ArrayList<String>) linesList);
    		}
    		statements.add(classFiles);
    	}
    	
    	return statements;
	}

	@GetMapping("/uploadStatus")
    public String uploadStatus() {
    	log.info("Upload Status invoked");
        return "uploadStatus";
    }

}
