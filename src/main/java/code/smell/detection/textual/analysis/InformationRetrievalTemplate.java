package code.smell.detection.textual.analysis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textual.analysis.ir.CamelCaseSpliter;
import code.smell.detection.textual.analysis.ir.LowerCaseConverter;
import code.smell.detection.textual.analysis.ir.SpecialCharecterRemover;
import code.smell.detection.textual.analysis.ir.Stemmer;
import code.smell.detection.textual.analysis.ir.TfIdf;

@Component
public class InformationRetrievalTemplate {
	public List<String> getJavaFiles() {
		return javaFiles;
	}


	public List<ArrayList<ArrayList<String>>> getMethods() {
		return methods;
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private List<String> javaFiles = new ArrayList<>();
	private List<ArrayList<ArrayList<String>>> methods = new ArrayList<>();
	
	@Autowired
	private CamelCaseSpliter camelCaseSpliter;
	
	@Autowired
	private LowerCaseConverter lowerCaseConverter;
	
	@Autowired
	private SpecialCharecterRemover specialCharecterRemover;
	
	@Autowired
	private Stemmer stemmer;
	
	
	
	public void setLists(List<String> javaFiles, List<ArrayList<ArrayList<String>>> methods){
		try{
			setMethods(methods);
			setJavaFiles(javaFiles);
			
			camelCaseSplit();
			makeLowerCase();
			specialCharacterRemoval();
			stemmer();
		} catch(Exception e){
			log.error(e.getMessage(), e);
		}
	}


	private void stemmer() {
		List<ArrayList<ArrayList<String>>> changedMethods = new ArrayList<>();
		for(List<ArrayList<String>> list : methods){
			changedMethods.add((ArrayList<ArrayList<String>>) stemmer.stemAllMethods(list));
		}
		List<String> changedFiles = stemmer.stemAllJavaFiles(javaFiles);
		
		setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	private void specialCharacterRemoval() {
		List<String> changedFiles = specialCharecterRemover.removeSpecialCharacterOfAllJavaFiles(javaFiles);
		List<ArrayList<ArrayList<String>>> changedMethods = new ArrayList<>();
		for(List<ArrayList<String>> list : methods){
			changedMethods.add((ArrayList<ArrayList<String>>) stemmer.stemAllMethods(list));
		}
		
		setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	private void makeLowerCase() {
		List<String> changedFiles = lowerCaseConverter.makeLowerCaseAllJavaFiles(javaFiles);
		List<ArrayList<ArrayList<String>>> changedMethods = new ArrayList<>();
		for(List<ArrayList<String>> list : methods){
			changedMethods.add((ArrayList<ArrayList<String>>) stemmer.stemAllMethods(list));
		}
		
		setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	private void camelCaseSplit() {
		List<String> changedFiles = camelCaseSpliter.splitAllJavaFiles(javaFiles);
		List<ArrayList<ArrayList<String>>> changedMethods = new ArrayList<>();
		for(List<ArrayList<String>> list : methods){
			changedMethods.add((ArrayList<ArrayList<String>>) stemmer.stemAllMethods(list));
		}
		
		setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	private void setJavaFiles(List<String> javaFiles) {
		this.javaFiles = javaFiles;
	}
	
	private void setMethods(List<ArrayList<ArrayList<String>>> methods) {
		this.methods = methods;
	}

}
