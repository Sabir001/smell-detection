package code.smell.detection.textualAnalysis.FileCreation;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileManipulation {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public String stopWordFolderName = "Stop Word Directory";
	public String stopWordFileName = "Stop Words.txt";
	public String sourceFolderName = "Source Folder";
	
	
	@Autowired
	private StopWordFileCreation stopWordFileCreation;
	
	File sourceFolder = new File(sourceFolderName);
	File stopWords = new File(stopWordFolderName);
	
	
	public void createSourceDirectory(){
		try {
			if(!sourceFolder.exists()){
				sourceFolder.mkdirs();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void createStopWordDirectory(){
		try {
			if(!stopWords.exists()){
				stopWords.mkdirs();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void manageStopWords(){
		try {
			deleteFilesInStopWordDirectory();
			deleteFilesInSourceDirectory();
			stopWordFileCreation.createFilesInStopWordDirectory();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
	
	public void deleteFilesInStopWordDirectory(){
		try {
			FileUtils.cleanDirectory(new File(stopWordFolderName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
	
	public void deleteFilesInSourceDirectory(){
		try {
			FileUtils.cleanDirectory(new File(sourceFolderName));
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}
}
