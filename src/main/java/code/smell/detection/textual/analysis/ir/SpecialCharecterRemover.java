package code.smell.detection.textual.analysis.ir;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SpecialCharecterRemover {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<String> removeSpecialCharacterOfAllJavaFiles(List<String> javaFiles){
		log.info("removeSpecialCharacterOfAllJavaFiles invoked");
		List<String> removed = new ArrayList<>();
		
		for(String singleJavaFile : javaFiles) {
			removed.add(singleJavaFile.replaceAll("[^A-Za-z]+", " ").trim());
		}
		
		log.info("removeSpecialCharacterOfAllJavaFiles done");
		
		return removed;
	}
	
	public List<ArrayList<String>> removeSpecialCharacterOfAllMethods(List<ArrayList<String>> methods){
		log.info("removeSpecialCharacterOfAllMethods invoked");
		
		List<ArrayList<String>> removedMethods = new ArrayList<>();
		
		for(ArrayList<String> singleClass : methods) {
			ArrayList<String> oneCLass = new ArrayList<>();
			for(String method : singleClass) {
				oneCLass.add(method.replaceAll("[^A-Za-z]+", " ").trim());
			}
			removedMethods.add(oneCLass);
		}
		
		log.info("removeSpecialCharacterOfAllMethods done");
		
		return removedMethods;
	}
}
