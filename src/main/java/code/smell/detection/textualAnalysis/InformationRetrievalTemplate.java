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
	
	public List<String> javaFiles = new ArrayList<>();
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
			setVariables(javaFiles, methods);
			
			camelCaseSplit();
			makeLowerCase();
			specialCharacterRemoval();
			stemmer();
			tfIdf();
		} catch(Exception e){
			log.error(e.getMessage());
		}
	}

	private void tfIdf() {
		
	}

	private void stemmer() {
		
	}

	private void specialCharacterRemoval() {
		
	}

	private void makeLowerCase() {
		
	}

	private void camelCaseSplit() {
		
	}

	private void setVariables(List<String> javaFiles, List<ArrayList<String>> methods) {
		this.javaFiles = javaFiles;
		this.methods = methods;
	}

}
