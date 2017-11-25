package code.smell.detection.textualAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textualAnalysis.FileCreation.StopWordFileCreation;
import code.smell.detection.textualAnalysis.sd.DetectBlob;
import code.smell.detection.textualAnalysis.sd.DetectFeatureEnvy;
import code.smell.detection.textualAnalysis.sd.DetectLongMethod;
import code.smell.detection.textualAnalysis.sd.DetectMisplacedClass;
import code.smell.detection.textualAnalysis.sd.DetectPromiscousPackage;

@Component
public class SmellDetector {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private StopWordFileCreation stopWordFileCreation;
	
	@Autowired
	private DetectBlob detectBlob;
	
	@Autowired
	private DetectFeatureEnvy detectFeatureEnvy;
	
	@Autowired
	private DetectLongMethod detectLongMethod;
	
	@Autowired
	private DetectMisplacedClass detectMisplacedClass;
	
	@Autowired
	private DetectPromiscousPackage detectPromiscousPackage;
	
	
	File sourceFolder = new File("Source Folder");
	File stopWords = new File("Stop Word Directory");
	
	List<String> javaFiles = new ArrayList<>();
	List<ArrayList<String>> methods = new ArrayList<ArrayList<String>>();
	
	public void initialization(List<String> javaFiles, List<ArrayList<String>> methods){
		this.javaFiles = javaFiles;
		this.methods = methods;
	}
	
	public void detectCodeSmell() {
		
	}
	
	
	public void createSourceDirectory(){
		try {
			if(!sourceFolder.exists()){
				sourceFolder.mkdirs();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void createStopWordDirectory(){
		try {
			if(!stopWords.exists()){
				stopWords.mkdirs();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void manageStopWords(){
		try {
			stopWordFileCreation.deleteFilesInStopWordDirectory();
			stopWordFileCreation.deleteFilesInSourceDirectory();
			stopWordFileCreation.createFilesInStopWordDirectory();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
