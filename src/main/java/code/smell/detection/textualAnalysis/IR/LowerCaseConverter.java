package code.smell.detection.textualAnalysis.IR;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LowerCaseConverter {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<String> makeLowerCaseAllJavaFiles(List<String> javaFiles){
		log.info("makeLowerCaseAllJavaFiles invoked");
		
		return makeListLowerCased(javaFiles);
	}
	
	private List<String> makeListLowerCased(List<String> javaFiles) {
		List<String> lowerCasedFiles = javaFiles.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
		
		return lowerCasedFiles;
	}

	public List<ArrayList<String>> makeLowerCaseAllMethods(List<ArrayList<String>> methods){
		log.info("makeLowerCaseAllMethods invoked");
		
		List<ArrayList<String>> lowerCaseMethods = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> singleClass : methods) {
			lowerCaseMethods.add((ArrayList<String>) makeListLowerCased(singleClass));
		}
		
		return lowerCaseMethods;
	}
}
