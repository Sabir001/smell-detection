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

@Component
public class DetectBlob implements ISmellDetector{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;
	
	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods,
			List<String> mainJavaFiles) {
		List<String> result = new ArrayList<String>();
		for(int i = 0; i < methods.size(); i++) {
			try {
				try{
					fileManipulation.deleteFilesInSourceDirectory();
				}catch(Exception efm){
					log.error(efm.getMessage() + " delete action in file manipulation");
				}
				makeNecessaryFilesFromMethod(methods.get(i));
				Double LSIValue = decideSmell(methods.get(i));
				if(LSIValue < .5){
					try {
						result.add(getClassName(mainJavaFiles.get(i)));
					} catch (Exception e) {
						log.error(e.getMessage());
					}
				}
			}catch(Exception e){
				log.error(e.getMessage() + " in detectSmell()");
			}
		}
		return result;
	}

	private String getClassName(String string) {
		if(string != null){
			String firstLine = string.split(System.lineSeparator())[0];
			return firstLine;
		}
		log.info("getClassName invoked and string was null");
		return null;
	}

	private Double decideSmell(ArrayList<String> arrayList) {
		
		return 0.0;
	}

	private void makeNecessaryFilesFromMethod(ArrayList<String> arrayList) {
		ArrayList<File> files = new ArrayList<File>();
		try {
			for(Integer i = 0; i < arrayList.size(); i++) {
				files.add(new File(fileManipulation.sourceFolderName + "\\" +  i.toString() + ".txt"));
				try {
					files.get(i).createNewFile();
					try(BufferedWriter br = new BufferedWriter(new FileWriter(files.get(i).getAbsolutePath()))){
						br.write(arrayList.get(i));
					} catch(Exception e2) {
						log.error(e2.getMessage() + " in makeNecessaryFilesFromMethod() - 1");
					}
				} catch (IOException e) {
					log.error(e.getMessage() + " in makeNecessaryFilesFromMethod() - 2");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
