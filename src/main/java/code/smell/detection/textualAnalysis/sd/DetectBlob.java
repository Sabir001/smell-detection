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
import code.smell.detection.textualAnalysis.LSI.LSI;

@Component
public class DetectBlob implements ISmellDetector{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;
	
	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods,
			List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods) {
		List<String> result = new ArrayList<String>();
		result.add("Blob Result - ");
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
						result.add(getClassName(mainJavaFiles.get(i)));
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

	private Double decideSmell(ArrayList<String> arrayList) {
		try {
			LSI lsi = new LSI(fileManipulation.sourceFolderName, 
					fileManipulation.stopWordFolderName + "\\" + fileManipulation.stopWordFileName);
			lsi.createTermDocumentMatrix();
			lsi.performSingularValueDecomposition();
			Double[] avg = new Double[arrayList.size()];
			for(int i = 0; i < arrayList.size(); i++) {
				Double subTotal = 0.0;
				List<Double> query = lsi.handleQuery(arrayList.get(i));
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
			return total / arrayList.size();
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		
		return 1.0;
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
