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
import code.smell.detection.textualAnalysis.FileCreation.StopWordFileCreation;
import code.smell.detection.textualAnalysis.LSI.LSI;

@Component
public class DetectLongMethod implements ISmellDetector{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;

	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<ArrayList<String>>> methods,
			List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods) {
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < methods.size(); i++) {
			try {
				String className = getClassName(mainJavaFiles.get(i));
				for(int j = 0; j < methods.get(i).size(); j++) {
					
					Double LSIValue = 0.0;
					ArrayList <Double> lsiTot = new ArrayList<>();
					for(int k = 0; k < methods.get(i).get(j).size() - 1; k++){
						fileManipulation.deleteFilesInSourceDirectory();
						if(methods.get(i).get(j).get(k).trim().length() < 4 || methods.get(i).get(j).get(k + 1).trim().length() < 4){
							continue;
						}
						makeNecessaryFilesFromStatements(methods.get(i).get(j).get(k), methods.get(i).get(j).get(k + 1));
						lsiTot.add(decideSmell(methods.get(i).get(j).get(k)));
					}
					
					for(Double val : lsiTot){
						LSIValue += val;
					}
					if(lsiTot.size() > 0)
						LSIValue = LSIValue / lsiTot.size();
					else 
						LSIValue = 1.0;
					
					if(LSIValue < threshold) {
						result.add(getMethodDeclaration("Class: " + className + "\nAnd method: " + getMethodDeclaration(mainAllmethods.get(i).get(j))));
						log.info("Long Method found. Data: " + result.get(result.size() - 1));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
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
			log.error(e.getMessage(), e);
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
			log.error(e.getMessage(), e);
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
			
			List<Double> query = lsi.handleQuery(string);
			Double min = 1.0;
			if(query.size() == 2){
				for(int i = 0; i < query.size(); i++){
					min = Math.min(min, query.get(i));
				}
			}
			
			
			return min;
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		
		
		return 1.0;
	}


	private void makeNecessaryFilesFromStatements(String string, String string2) {
		ArrayList<File> files = new ArrayList<File>();
		try {
			files.add(new File(fileManipulation.sourceFolderName + "\\File1.txt"));
			files.add(new File(fileManipulation.sourceFolderName + "\\File2.txt"));
			try {
				files.get(0).createNewFile();
				files.get(1).createNewFile();
				
				try(BufferedWriter br = new BufferedWriter(new FileWriter(files.get(0).getAbsolutePath()))){
					br.write(string);
				} catch(Exception e2) {
					log.error(e2.getMessage(), e2);
				}
				try(BufferedWriter br = new BufferedWriter(new FileWriter(files.get(1).getAbsolutePath()))){
					br.write(string2);
				} catch(Exception e2) {
					log.error(e2.getMessage(), e2);
				}
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
	}

}
