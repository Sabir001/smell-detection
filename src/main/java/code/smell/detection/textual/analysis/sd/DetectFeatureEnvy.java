package code.smell.detection.textual.analysis.sd;

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

import code.smell.detection.textual.analysis.file.creation.FileManipulation;
import code.smell.detection.textual.analysis.file.creation.StopWordFileCreation;
import code.smell.detection.textual.analysis.lsi.LSI;

@Component
public class DetectFeatureEnvy implements ISmellDetector{
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
					fileManipulation.deleteFilesInSourceDirectory();
					makeNecessaryFilesFromClass(javaFiles.get(i));
					String methodString = "";
					for(String line : methods.get(i).get(j)){
						methodString += line;
					}
					Double LSIValueOfOwn = getLsiValue(methodString);
					if(LSIValueOfOwn == 0.0) {
						LSIValueOfOwn = 1.0;
					}
					String[] wordsInMethod = mainAllmethods.get(i).get(j).trim().split(" ");
					Double otherLsi = -1.0;
					for(int k = 0; k < javaFiles.size(); k++) {
						if(k != i) {
							for(String word : wordsInMethod) {
								if(StopWordFileCreation.ENGLISH_STOP_WORDS.contains(word)
										|| StopWordFileCreation.JAVA_STOP_WORDS.contains(word)) {
									continue;
								}
								if(mainJavaFiles.get(k).contains(word)) {
									fileManipulation.deleteFilesInSourceDirectory();
									makeNecessaryFilesFromClass(javaFiles.get(k));
									String methodStringOfOther = "";
									for(String line : methods.get(i).get(j)){
										methodStringOfOther += line;
									}
									
									double tempLsi = getLsiValue(methodStringOfOther);
									if(tempLsi < .95 && tempLsi > otherLsi )
										otherLsi = tempLsi;
									break;
								}
							}
						}
					}
					if(LSIValueOfOwn < otherLsi) {
						result.add("Class: " + className + System.lineSeparator() + "And method: " + getMethodDeclaration(mainAllmethods.get(i).get(j)));
						log.info("Found Feature envy. Data: " + otherLsi + result.get(result.size() - 1) );
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
			LSI lsi = new LSI(fileManipulation.SOURCE_FOLDER_NAME, 
					fileManipulation.STOP_WORD_FOLDER_NAME + "\\" + fileManipulation.STOP_WORD_FILE_NAME);
			lsi.createTermDocumentMatrix();
			lsi.performSingularValueDecomposition();
			
			List<Double> query = lsi.handleQuery(string);
			Double total = 0.0;
			for(Double num : query) {
				total += num;
			}
			if(query.size() > 0)
				return total / query.size();
			return 0.0;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 0.0;
	}

	private void makeNecessaryFilesFromClass(String string) {
		File file = new File(fileManipulation.SOURCE_FOLDER_NAME + "\\ClassFile.txt");
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
