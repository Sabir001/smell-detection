package code.smell.detection.textualAnalysis.IR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class LowerCaseConverter {
	public List<String> makeLowerCaseAllJavaFiles(List<String> javaFiles){
		return makeListLowerCased(javaFiles);
	}
	
	private List<String> makeListLowerCased(List<String> javaFiles) {
		List<String> lowerCasedFiles = javaFiles.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
		
		return lowerCasedFiles;
	}

	public List<ArrayList<String>> makeLowerCaseAllMethods(List<ArrayList<String>> methods){
		List<ArrayList<String>> lowerCaseMethods = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> method : methods) {
			lowerCaseMethods.add((ArrayList<String>) makeListLowerCased(method));
		}
		
		return lowerCaseMethods;
	}
}
