package code.smell.detection.textualAnalysis.IR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SpecialCharecterRemover {
	public List<String> removeSpecialCharacterOfAllJavaFiles(List<String> javaFiles){
		List<String> removed = new ArrayList<>();
		
		for(String singleJavaFile : javaFiles) {
			removed.add(singleJavaFile.replaceAll("[^A-Za-z]+", " ").trim());
		}
		
		return removed;
	}
	
	public List<ArrayList<String>> removeSpecialCharacterOfAllMethods(List<ArrayList<String>> methods){
		List<ArrayList<String>> removedMethods = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> singleClass : methods) {
			ArrayList<String> oneCLass = new ArrayList<>();
			for(String method : singleClass) {
				oneCLass.add(method.replaceAll("[^A-Za-z]+", " ").trim());
			}
			removedMethods.add(oneCLass);
		}
		
		return removedMethods;
	}
}
