package code.smell.detection.textualAnalysis;

import java.util.ArrayList;
import java.util.List;

public class InformationRetrievalTemplate {
	public List<String> javaFiles;
	public List<ArrayList<String>> methods;
	
	public InformationRetrievalTemplate(){
		initializeVariables();
		
		camelCaseSplit();
		makeLowerCase();
		specialCharacterRemoval();
		stemmer();
		tfIdf();
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

	private void initializeVariables() {
		this.javaFiles = new ArrayList<>();
		this.methods = new ArrayList<ArrayList<String>>();
	}

}
