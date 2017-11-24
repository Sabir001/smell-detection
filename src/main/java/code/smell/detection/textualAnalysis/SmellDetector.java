package code.smell.detection.textualAnalysis;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textualAnalysis.FileCreation.StopWordFileCreation;

@Component
public class SmellDetector {
	@Autowired
	private StopWordFileCreation stopWordFileCreation;
	
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
			e.printStackTrace();
		}
	}
	
	public void createStopWordDirectory(){
		try {
			if(!stopWords.exists()){
				stopWords.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void manageStopWords(){
		stopWordFileCreation.deleteFilesInStopWordDirectory();
	}
}
