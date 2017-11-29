package code.smell.detection.textualAnalysis.sd;

import java.io.File;
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
				fileManipulation.deleteFilesInSourceDirectory();
				makeNecessaryFilesFromMethod(methods.get(i));
				
			}catch(Exception e){
				log.error(e.getMessage());
			}
		}
		return result;
	}

	private void makeNecessaryFilesFromMethod(ArrayList<String> arrayList) {
		ArrayList<File> files = new ArrayList<File>();
		for(Integer i = 0; i < arrayList.size(); i++) {
			files.add(new File(i.toString()));
			try {
				files.get(i).createNewFile();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
}
