package code.smell.detection.textualAnalysis.sd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textualAnalysis.FileCreation.FileManipulation;
import code.smell.detection.textualAnalysis.LSI.LSI;

@Component
public class DetectLongMethod implements ISmellDetector{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;
	

	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods,
			List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods) {
		List<String> result = new ArrayList<String>();
		
		
		
		for(int i = 0; i < methods.size(); i++) {
			String className = getClassName(mainJavaFiles.get(i));
			for(int j = 0; j < methods.get(i).size(); j++) {
				fileManipulation.deleteFilesInSourceDirectory();
				makeNecessaryFilesFromStatements(methods.get(i).get(j));
				Double LSIValue = decideSmell(methods.get(i).get(j));
				if(LSIValue < .5) {
					result.add(getMethodDeclaration(className + " and method: " + mainAllmethods.get(i).get(j)));
				}
			}
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
			log.error(e.getMessage());
		}
		log.info("getClassName invoked and string was null");
		return "Could not fetch class name";
	}


	private String getMethodDeclaration(String string) {
		try {
			if(string != null){
				String firstLine = string.trim().split(System.lineSeparator())[0];
				return firstLine;
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("getClassName invoked and string was null");
		return "Could not fetch method declaration";

	}


	private Double decideSmell(String string) {
		LSI lsi;
		try {
			lsi = new LSI(fileManipulation.sourceFolderName, 
					fileManipulation.stopWordFolderName + "\\" + fileManipulation.stopWordFileName);
			lsi.createTermDocumentMatrix();
			lsi.performSingularValueDecomposition();
			
			List<String> arrayList = new ArrayList<String>(Arrays.asList(string.split(" ")));
			Double[] avg = new Double[arrayList.size() - 1];
			
			if(arrayList.size() < 2) {
				return 1.0;
			}
			
			for (int i = 0; i < arrayList.size() - 1; i++) {
				List<Double> query = lsi.handleQuery(arrayList.get(i));
				avg[i] = query.get(i+1);
			}
			Double total = 0.0;
			for(Double num : avg) {
				total += num;
			}
			
			return total / (arrayList.size() - 1);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
		
		
		return 1.0;
	}


	private void makeNecessaryFilesFromStatements(String string) {
		List<String> arrayList = new ArrayList<String>(Arrays.asList(string.split(" ")));
		ArrayList<File> files = new ArrayList<File>();
		try {
			for(Integer i = 0; i < arrayList.size(); i++) {
				files.add(new File(fileManipulation.sourceFolderName + "\\" +  i.toString() + ".txt"));
				try {
					files.get(i).createNewFile();
					try(BufferedWriter br = new BufferedWriter(new FileWriter(files.get(i).getAbsolutePath()))){
						br.write(arrayList.get(i));
					} catch(Exception e2) {
						log.error(e2.getMessage() + " in makeNecessaryFilesFromStatements() - 1");
					}
				} catch (IOException e) {
					log.error(e.getMessage() + " in makeNecessaryFilesFromStatements() - 2");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
	}

}
