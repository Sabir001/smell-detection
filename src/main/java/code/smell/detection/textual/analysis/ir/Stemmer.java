package code.smell.detection.textual.analysis.ir;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Stemmer {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<String> stemAllJavaFiles(List<String> javaFiles) {
		log.info("stemAllJavaFiles invoked");
		
		List<String> wholeFiles = new ArrayList<>();
		
		for(String wholeClassString : javaFiles) {
			String classFiles = "";
			String[] splited = wholeClassString.trim().split("\\s+");
			for(String singleWordInClass : splited) {
				PottersStemmer pottersStemmer = new PottersStemmer();
				pottersStemmer.add(singleWordInClass.toCharArray(), singleWordInClass.length());
				pottersStemmer.stem();
				
				classFiles += (" " + pottersStemmer.toString());
			}
			wholeFiles.add(classFiles.trim());
		}
		
		log.info("stemAllJavaFiles done");
		
		return wholeFiles;
	}

	public List<ArrayList<String>> stemAllMethods(List<ArrayList<String>> methods) {
		log.info("stemAllMethods invoked");
		
		List<ArrayList<String>> allMethodsOfFiles = new ArrayList<>();
		
		for(ArrayList<String> singleClass : methods) {
			ArrayList<String> temporaryMethodList = new ArrayList<>();
			for(String wholeClassString : singleClass) {
				String classFiles = "";
				String[] splited = wholeClassString.trim().split("\\s+");
				for(String singleWordInClass : splited) {
					PottersStemmer pottersStemmer = new PottersStemmer();
					pottersStemmer.add(singleWordInClass.toCharArray(), singleWordInClass.length());
					pottersStemmer.stem();
					
					classFiles += (" " + pottersStemmer.toString());
				}
				temporaryMethodList.add(classFiles.trim());
			}
			allMethodsOfFiles.add(temporaryMethodList);
		}
		
		log.info("stemAllMethods done");
		
		return allMethodsOfFiles;
	}

	

}
