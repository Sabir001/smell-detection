package code.smell.detection.textualAnalysis.IR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CamelCaseSpliter {
	public List<String> splitAllJavaFiles(List<String> javaFiles){
		List<String> wholeFiles = new ArrayList<>();
		
		for(String wholeClassString : javaFiles) {
			String classFiles = "";
			String[] splited = wholeClassString.trim().split("\\s+");
			for(String singleWordInClass : splited) {
				classFiles += splitWord(singleWordInClass);
			}
			wholeFiles.add(classFiles);
		}
		
		return wholeFiles;
	}
	
	public List<ArrayList<String>> splitAllMethods(List<ArrayList<String>> methods){
		List<ArrayList<String>> allMethodsOfFiles = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> singleClass : methods) {
			ArrayList<String> temporaryMethodList = new ArrayList<>();
			for(String wholeClassString : singleClass) {
				String classFiles = "";
				String[] splited = wholeClassString.trim().split("\\s+");
				for(String singleWordInClass : splited) {
					classFiles += splitWord(singleWordInClass);
				}
				temporaryMethodList.add(classFiles);
			}
			allMethodsOfFiles.add(temporaryMethodList);
		}
		
		return allMethodsOfFiles;
	}
	
	private String splitWord(String word) {
		String words = "";
		for (String w : word.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])")) {
	        words += w;
	    }
		return words;
	}
}
