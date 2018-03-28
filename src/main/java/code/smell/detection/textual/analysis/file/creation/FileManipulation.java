package code.smell.detection.textual.analysis.file.creation;

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
	
	public static final String STOP_WORD_FOLDER_NAME = "Stop Word Directory";
	public static final String STOP_WORD_FILE_NAME = "Stop Words.txt";
	public static final String SOURCE_FOLDER_NAME = "Source Folder";
	
	
	@Autowired
	private StopWordFileCreation stopWordFileCreation;
	
	File sourceFolder = new File(SOURCE_FOLDER_NAME);
	File stopWords = new File(STOP_WORD_FOLDER_NAME);
	
	
	public void createSourceDirectory(){
		try {
			if(!sourceFolder.exists()){
				sourceFolder.mkdirs();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void createStopWordDirectory(){
		try {
			if(!stopWords.exists()){
				stopWords.mkdirs();
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void manageStopWords(){
		try {
			deleteFilesInStopWordDirectory();
			deleteFilesInSourceDirectory();
			stopWordFileCreation.createFilesInStopWordDirectory();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void deleteFilesInStopWordDirectory(){
		try {
			FileUtils.cleanDirectory(new File(STOP_WORD_FOLDER_NAME));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	public void deleteFilesInSourceDirectory(){
		try {
			FileUtils.cleanDirectory(new File(SOURCE_FOLDER_NAME));
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}
