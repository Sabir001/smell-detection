package code.smell.detection.textual.analysis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textual.analysis.sd.DetectBlob;
import code.smell.detection.textual.analysis.sd.DetectFeatureEnvy;
import code.smell.detection.textual.analysis.sd.DetectLongMethod;
import code.smell.detection.textual.analysis.sd.DetectMisplacedClass;
import code.smell.detection.textual.analysis.sd.DetectPromiscousPackage;

@Component
public class SmellDetector {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private DetectBlob detectBlob;
	
	@Autowired
	private DetectFeatureEnvy detectFeatureEnvy;
	
	@Autowired
	private DetectLongMethod detectLongMethod;
	
	@Autowired
	private DetectMisplacedClass detectMisplacedClass;
	
	@Autowired
	private DetectPromiscousPackage detectPromiscousPackage;
	
	
	
	List<String> javaFiles = new ArrayList<>();
	List<String> mainJavaFiles = new ArrayList<>();
	List<ArrayList<ArrayList<String>>> methods = new ArrayList<>();
	List<ArrayList<String>> mainAllmethods = new ArrayList<>();
	
	public void initialization(List<String> javaFiles, List<ArrayList<ArrayList<String>>> methods, List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods){
		this.javaFiles = javaFiles;
		this.methods = methods;
		this.mainJavaFiles = mainJavaFiles;
		this.mainAllmethods = mainAllmethods;
		
	}
	
	public List<ArrayList<String>> detectCodeSmell() {
		List<ArrayList<String>> results = new ArrayList<>();
		
		results.add((ArrayList<String>) detectBlob.detectSmell(javaFiles, methods, mainJavaFiles, mainAllmethods));
		results.add((ArrayList<String>) detectFeatureEnvy.detectSmell(javaFiles, methods, mainJavaFiles, mainAllmethods));
		results.add((ArrayList<String>) detectLongMethod.detectSmell(javaFiles, methods, mainJavaFiles, mainAllmethods));
		results.add((ArrayList<String>) detectMisplacedClass.detectSmell(javaFiles, methods, mainJavaFiles, mainAllmethods));
		results.add((ArrayList<String>) detectPromiscousPackage.detectSmell(javaFiles, methods, mainJavaFiles, mainAllmethods));
		
		
		return results;
	}
	
	
	
}
