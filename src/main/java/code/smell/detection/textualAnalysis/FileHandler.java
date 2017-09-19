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
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Scope("singleton")
public class FileHandler {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<String> getJavaFiles(MultipartFile file){
		List<String> javaFiles = new ArrayList<String>();
		
		log.info("getJavaFiles method invoked");
		try {
			ZipEntry zipEntry;
			File tempFile = File.createTempFile(file.getOriginalFilename(), null);
			file.transferTo(tempFile);
			ZipFile zipFile = new ZipFile(tempFile);
			
			tempFile.delete();
			ZipInputStream zip;
			try {
				zip = new ZipInputStream( file.getInputStream());
				while((zipEntry = zip.getNextEntry()) != null){
					if(zipEntry.getName().endsWith(".java")){
						String tempFileName = zipEntry.toString();
						String tempFileContent = IOUtils.toString(zipFile.getInputStream(zipEntry), StandardCharsets.UTF_8);
						log.info(tempFileName);
						javaFiles.add(tempFileName + "\n" +  tempFileContent);
						
					}
				}
			} catch (IOException e1) {
				log.error(e1.getMessage());
			}
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return javaFiles;
	}
}