package code.smell.detection.textualAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;


public class FileHandler {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<String> getJavaFiles(MultipartFile file){
		List<String> javaFiles = new ArrayList<String>();
		
		ZipEntry zipEntry;

		log.info("getJavaFiles");
		try {
			ZipInputStream zip;
			try {
				zip = new ZipInputStream( file.getInputStream());
				while((zipEntry = zip.getNextEntry()) != null){
					if(zipEntry.getName().endsWith(".java")){
						log.info(zipEntry.toString());
						javaFiles.add(zipEntry.getName() + zipEntry.toString());
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return javaFiles;
	}
}
