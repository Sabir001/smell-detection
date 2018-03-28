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
import code.smell.detection.textual.analysis.lsi.LSI;

@Component
public class DetectBlob implements ISmellDetector{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;
	
	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<ArrayList<String>>> methods,
			List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods) {
		List<String> result = new ArrayList<>();
		for(int i = 0; i < methods.size(); i++) {
			try {
				try{
					fileManipulation.deleteFilesInSourceDirectory();
				}catch(Exception efm){
					log.error(efm.getMessage(), efm);
				}
				makeNecessaryFilesFromMethod(methods.get(i));
				Double LSIValue = decideSmell(methods.get(i));
				if(LSIValue < threshold){
					try {
						result.add("Class: " + getClassName(mainJavaFiles.get(i)));
						log.info("Blob detected. Data: " + result.get(result.size() - 1));
					} catch (Exception e) {
						log.error(e.getMessage(), e);
					}
				}
			}catch(Exception e){
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

	private Double decideSmell(ArrayList<ArrayList<String>> arrayList) {
		try {
			LSI lsi = new LSI(fileManipulation.SOURCE_FOLDER_NAME, 
					fileManipulation.STOP_WORD_FOLDER_NAME + "\\" + fileManipulation.STOP_WORD_FILE_NAME);
			lsi.createTermDocumentMatrix();
			lsi.performSingularValueDecomposition();
			Double[] avg = new Double[arrayList.size()];
			for(int i = 0; i < arrayList.size(); i++) {
				Double subTotal = 0.0;
				String queryString = "";
				for(String line : arrayList.get(i)){
					queryString += line;
				}
				List<Double> query = lsi.handleQuery(queryString);
				for(int j = 0; j < query.size(); j++) {
					if(j != i) subTotal += query.get(j);
				}
				if(query.size() != 0 && query.size() != 1)
					avg[i] = subTotal / (query.size() - 1);
				else avg[i] = 1.0;
			}
			Double total = 0.0;
			for(Double num : avg) {
				total += num;
			}
			if(avg.length > 0)
				return total / avg.length;
			else return 1.0;
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} 
		
		
		return 1.0;
	}

	private void makeNecessaryFilesFromMethod(ArrayList<ArrayList<String>> arrayList) {
		ArrayList<File> files = new ArrayList<File>();
		try {
			for(Integer i = 0; i < arrayList.size(); i++) {
				files.add(new File(fileManipulation.SOURCE_FOLDER_NAME + "\\" +  i.toString() + ".txt"));
				try {
					files.get(i).createNewFile();
					try(BufferedWriter br = new BufferedWriter(new FileWriter(files.get(i).getAbsolutePath()))){
						for(String line : arrayList.get(i)){
							br.write(line);
						}
					} catch(Exception e2) {
						log.error(e2.getMessage(), e2);
					}
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
