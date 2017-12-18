package code.smell.detection.textualAnalysis.sd;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import code.smell.detection.textualAnalysis.FileCreation.FileManipulation;
import code.smell.detection.textualAnalysis.LSI.LSI;

@Component
public class DetectPromiscousPackage implements ISmellDetector{
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private FileManipulation fileManipulation;
	

	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods,
			List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods) {
		List<String> result = new ArrayList<String>();
		result.add("Promiscuous Package Result -");
		
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		
		for(int i = 0; i < javaFiles.size(); i++){
			String packageName = getPackageName(mainJavaFiles.get(i));
			String className = getClassName(mainJavaFiles.get(i));
			String data = className + System.lineSeparator() + javaFiles.get(i);
			map.putIfAbsent(packageName, new ArrayList<String>());
			map.get(packageName).add(data);
		}
		
		for (Entry<String, List<String>> mapEntry : map.entrySet()) {
		    String packageName = mapEntry.getKey();
		    List<String> javaClasses = mapEntry.getValue();

		    fileManipulation.deleteFilesInSourceDirectory();
		    makeNecessaryFilesFromPackage(javaClasses);
		    
		    Double LsiValue = getLsiValue(javaClasses);
		    
		    if(LsiValue < threshold){
		    	result.add(packageName);
		    }
		}
		
		
		return result;
	}
	
	private Double getLsiValue(List<String> javaClasses) {
		try {
			LSI lsi = new LSI(fileManipulation.sourceFolderName, 
					fileManipulation.stopWordFolderName + "\\" + fileManipulation.stopWordFileName);
			lsi.createTermDocumentMatrix();
			lsi.performSingularValueDecomposition();
			Double[] avg = new Double[javaClasses.size()];
			for(int i = 0; i < javaClasses.size(); i++) {
				Double subTotal = 0.0;
				List<Double> query = lsi.handleQuery(javaClasses.get(i));
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
			return total / avg.length;
			
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return 1.0;
	}

	private void makeNecessaryFilesFromPackage(List<String> javaClasses) {
		ArrayList<File> files = new ArrayList<File>();
		try {
			for(Integer i = 0; i < javaClasses.size(); i++) {
				files.add(new File(fileManipulation.sourceFolderName + "\\" +  i.toString() + ".txt"));
				try {
					files.get(i).createNewFile();
					try(BufferedWriter br = new BufferedWriter(new FileWriter(files.get(i).getAbsolutePath()))){
						br.write(javaClasses.get(i));
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


	private String getPackageName(String string) {
		String firstLine = "";
		try {
			if(string != null){
				firstLine = string.trim().split(System.lineSeparator())[0];
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		String [] partsOfPackage = firstLine.trim().split("/");
		if(partsOfPackage.length < 2){
			return "default";
		}
		String packageName = "";
		for(int i = 0; i < partsOfPackage.length - 1; i++){
			if(i == 0){
				packageName  += partsOfPackage[i];
			}
			else packageName += ("." + partsOfPackage[i]);
		}
		
		return packageName;
	}

}
