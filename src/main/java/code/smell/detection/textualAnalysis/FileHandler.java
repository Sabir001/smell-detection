package code.smell.detection.textualAnalysis;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
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
			File tempFile = File.createTempFile("upload", null);
			file.transferTo(tempFile);
			ZipFile zipFile = new ZipFile(tempFile);
			// Proces Zip
			tempFile.delete();
			ZipInputStream zip;
			try {
				zip = new ZipInputStream( file.getInputStream());
				while((zipEntry = zip.getNextEntry()) != null){
					if(zipEntry.getName().endsWith(".java")){
						log.info(IOUtils.toString(zipFile.getInputStream(zipEntry), StandardCharsets.UTF_8));
						javaFiles.add(zipEntry.getName() + IOUtils.toString(zipFile.getInputStream(zipEntry), StandardCharsets.UTF_8));
						
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
