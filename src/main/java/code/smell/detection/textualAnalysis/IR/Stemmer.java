package code.smell.detection.textualAnalysis.IR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Stemmer {
	
	public List<String> stemAllJavaFiles(List<String> javaFiles) {
		List<String> wholeFiles = new ArrayList<>();
		
		for(String wholeClassString : javaFiles) {
			String classFiles = "";
			String[] splited = wholeClassString.trim().split("\\s+");
			for(String singleWordInClass : splited) {
				PottersStemmer pottersStemmer = new PottersStemmer();
				pottersStemmer.add(singleWordInClass.toCharArray(), singleWordInClass.length());
				
				
				classFiles += pottersStemmer.toString();
			}
			wholeFiles.add(classFiles);
		}
		
		return wholeFiles;
	}

	public List<ArrayList<String>> stemAllMethods(List<ArrayList<String>> methods) {
		List<ArrayList<String>> allMethodsOfFiles = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> singleClass : methods) {
			ArrayList<String> temporaryMethodList = new ArrayList<>();
			for(String wholeClassString : singleClass) {
				String classFiles = "";
				String[] splited = wholeClassString.trim().split("\\s+");
				for(String singleWordInClass : splited) {
					PottersStemmer pottersStemmer = new PottersStemmer();
					pottersStemmer.add(singleWordInClass.toCharArray(), singleWordInClass.length());
					
					
					classFiles += pottersStemmer.toString();
				}
				temporaryMethodList.add(classFiles);
			}
			allMethodsOfFiles.add(temporaryMethodList);
		}
		
		return allMethodsOfFiles;
	}

	

}
