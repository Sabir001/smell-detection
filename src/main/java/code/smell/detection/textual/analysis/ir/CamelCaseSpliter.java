package code.smell.detection.textual.analysis.ir;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CamelCaseSpliter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<String> splitAllJavaFiles(List<String> javaFiles){
		log.info("splitAllJavaFiles invoked");
		
		List<String> wholeFiles = new ArrayList<>();
		
		for(String wholeClassString : javaFiles) {
			String classFiles = "";
			String[] splited = wholeClassString.trim().split("\\s+");
			for(String singleWordInClass : splited) {
				classFiles += splitWord(singleWordInClass);
			}
			wholeFiles.add(classFiles);
		}
		
		log.info("splitAllJavaFiles done");
		
		return wholeFiles;
	}
	
	public List<ArrayList<String>> splitAllMethods(List<ArrayList<String>> methods){
		log.info("splitAllMethods invoked");
		
		List<ArrayList<String>> allMethodsOfFiles = new ArrayList<>();
		
		for(ArrayList<String> singleClass : methods) {
			ArrayList<String> temporaryMethodList = new ArrayList<>();
			for(String wholeClassString : singleClass) {
				String classFiles = "";
				String[] splited = wholeClassString.trim().split("\\s+");
				for(String singleWordInClass : splited) {
					classFiles += splitWord(singleWordInClass.trim());
				}
				temporaryMethodList.add(classFiles);
			}
			allMethodsOfFiles.add(temporaryMethodList);
		}
		
		log.info("splitAllMethods done");
		
		return allMethodsOfFiles;
	}
	
	private String splitWord(String word) {
		String words = "";
		for (String w : word.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
	        words += (" " + w);
	    }
		return words;
	}
}
