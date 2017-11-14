package code.smell.detection.textualAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textualAnalysis.IR.CamelCaseSpliter;
import code.smell.detection.textualAnalysis.IR.LowerCaseConverter;
import code.smell.detection.textualAnalysis.IR.SpecialCharecterRemover;
import code.smell.detection.textualAnalysis.IR.Stemmer;
import code.smell.detection.textualAnalysis.IR.TfIdf;

@Component
public class InformationRetrievalTemplate {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	//public List<String> javaFiles = new ArrayList<>();
	public List<ArrayList<String>> methods = new ArrayList<ArrayList<String>>();
	
	@Autowired
	private CamelCaseSpliter camelCaseSpliter;
	
	@Autowired
	private LowerCaseConverter lowerCaseConverter;
	
	@Autowired
	private SpecialCharecterRemover specialCharecterRemover;
	
	@Autowired
	private Stemmer stemmer;
	
	@Autowired
	private TfIdf tfIdf;
	
	
	public void setLists(List<String> javaFiles, List<ArrayList<String>> methods){
		try{
			//setJavaFiles(javaFiles);
			setMethods(methods);
			
			camelCaseSplit();
			makeLowerCase();
			specialCharacterRemoval();
			stemmer();
			//tfIdf();
		} catch(Exception e){
			log.error(e.getMessage());
		}
	}

	private void tfIdf() {
		List<ArrayList<String>> changedMethods = tfIdf.tfIdfCheckInAllMethods(methods);
		
		setMethods(changedMethods);
	}

	private void stemmer() {
		List<ArrayList<String>> changedMethods = stemmer.stemAllMethods(methods);
		//List<String> changedFiles = stemmer.stemAllJavaFiles(javaFiles);
		
		//setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	private void specialCharacterRemoval() {
		//List<String> changedFiles = specialCharecterRemover.removeSpecialCharacterOfAllJavaFiles(javaFiles);
		List<ArrayList<String>> changedMethods = specialCharecterRemover.removeSpecialCharacterOfAllMethods(methods);
		
		//setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	private void makeLowerCase() {
		//List<String> changedFiles = lowerCaseConverter.makeLowerCaseAllJavaFiles(javaFiles);
		List<ArrayList<String>> changedMethods = lowerCaseConverter.makeLowerCaseAllMethods(methods);
		
		//setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	private void camelCaseSplit() {
		//List<String> changedFiles = camelCaseSpliter.splitAllJavaFiles(javaFiles);
		List<ArrayList<String>> changedMethods = camelCaseSpliter.splitAllMethods(methods);
		
		//setJavaFiles(changedFiles);
		setMethods(changedMethods);
	}

	/*private void setJavaFiles(List<String> javaFiles) {
		this.javaFiles = javaFiles;
	}*/
	
	private void setMethods(List<ArrayList<String>> methods) {
		this.methods = methods;
	}

}
