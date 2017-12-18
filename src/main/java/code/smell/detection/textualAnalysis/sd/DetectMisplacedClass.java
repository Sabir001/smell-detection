package code.smell.detection.textualAnalysis.sd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textualAnalysis.FileCreation.FileManipulation;

@Component
public class DetectMisplacedClass implements ISmellDetector {
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;
	

	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods,
			List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods) {
		List<String> result = new ArrayList<String>();
		result.add("Misplaced Class Result - ");
		
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		
		for(int i = 0; i < javaFiles.size(); i++){
			String packageName = getPackageName(mainJavaFiles.get(i));
			String className = getClassName(mainJavaFiles.get(i));
			String data = className + System.lineSeparator() + javaFiles.get(i);
			map.putIfAbsent(packageName, new ArrayList<String>());
			map.get(packageName).add(data);
		}
		
		
		
		return result;
	}
	
	private String getClassName(String string) {
		try {
			if(string != null){
				String firstLine = string.trim().split(System.lineSeparator())[0];
				return firstLine;
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		log.info("getClassName invoked and string was null");
		return "Could not fetch class name";
	}


	private String getPackageName(String string) {
		String firstLine = "";
		try {
			if(string != null){
				firstLine = string.trim().split(System.lineSeparator())[0];
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		String [] partsOfPackage = firstLine.trim().split("/");
		if(partsOfPackage.length < 2){
			return "default";
		}
		String packageName = "";
		for(int i = 0; i < partsOfPackage.length - 1; i++){
			if(i == 0){
				packageName  += partsOfPackage[i];
			}
			else packageName += ("." + partsOfPackage[i]);
		}
		
		return packageName;
	}

}
