package code.smell.detection.textualAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class InformationRetrievalTemplate {
	public List<String> javaFiles = new ArrayList<>();
	public List<ArrayList<String>> methods = new ArrayList<ArrayList<String>>();
	
	public void setLists(List<String> javaFiles, List<ArrayList<String>> methods){
		setVariables(javaFiles, methods);
		
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

	private void setVariables(List<String> javaFiles, List<ArrayList<String>> methods) {
		this.javaFiles = javaFiles;
		this.methods = methods;
	}

}
