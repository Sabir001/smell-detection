package code.smell.detection.textualAnalysis.sd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textualAnalysis.FileCreation.FileManipulation;
import code.smell.detection.textualAnalysis.FileCreation.StopWordFileCreation;
import code.smell.detection.textualAnalysis.LSI.LSI;

@Component
public class DetectFeatureEnvy implements ISmellDetector{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;
	
	
	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods,
			List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods) {
		List<String> result = new ArrayList<String>();
		result.add("Feature Envy Result - ");
		for(int i = 0; i < methods.size(); i++) {
			try {
				String className = getClassName(mainJavaFiles.get(i));
				for(int j = 0; j < methods.get(i).size(); j++) {
					fileManipulation.deleteFilesInSourceDirectory();
					makeNecessaryFilesFromClass(javaFiles.get(i));
					Double LSIValueOfOwn = getLsiValue(methods.get(i).get(j));
					String[] wordsInMethod = mainAllmethods.get(i).get(j).trim().split(" ");
					Double otherLsi = -1.0;
					for(int k = 0; k < javaFiles.size(); k++) {
						if(k != i) {
							for(String word : wordsInMethod) {
								if(StopWordFileCreation.englishStopWords.contains(word)
										|| StopWordFileCreation.javaStopWords.contains(word)) {
									continue;
								}
								if(mainJavaFiles.get(k).contains(word)) {
									fileManipulation.deleteFilesInSourceDirectory();
									makeNecessaryFilesFromClass(javaFiles.get(k));
									otherLsi = Math.max(otherLsi, getLsiValue(methods.get(i).get(j)));
									break;
								}
							}
						}
					}
					if(LSIValueOfOwn < otherLsi) {
						result.add(className + " and method: " + getMethodDeclaration(mainAllmethods.get(i).get(j)));
						log.info("Found Feature envy. Data: " + result.get(result.size() - 1));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage(), e);
			}
		}
		
		return result;
	}
	
	private Double getLsiValue(String string) {
		try {
			LSI lsi = new LSI(fileManipulation.sourceFolderName, 
					fileManipulation.stopWordFolderName + "\\" + fileManipulation.stopWordFileName);
			lsi.createTermDocumentMatrix();
			lsi.performSingularValueDecomposition();
			
			List<Double> query = lsi.handleQuery(string);
			Double total = 0.0;
			for(Double num : query) {
				total += num;
			}
			
			return total / query.size();
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 1.0;
	}

	private void makeNecessaryFilesFromClass(String string) {
		File file = new File(fileManipulation.sourceFolderName + "\\ClassFile.txt");
		try {
			file.createNewFile();
			try(BufferedWriter br = new BufferedWriter(new FileWriter(file.getAbsolutePath()))){
				br.write(string);
			} catch(Exception e2) {
				log.error(e2.getMessage());
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
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


}
