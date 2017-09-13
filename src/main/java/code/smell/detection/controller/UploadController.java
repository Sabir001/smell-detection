package code.smell.detection.controller;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import code.smell.detection.textualAnalysis.FileHandler;

@Controller
public class UploadController {

	
	
	
    @GetMapping("/uploadProject")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") // //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/uploadStatus";
        }

        if(!file.getOriginalFilename().endsWith(".zip")){
        	redirectAttributes.addFlashAttribute("message", "Please upload '.zip' file");
            return "redirect:/uploadStatus";
        }

        FileHandler fileHandler = new FileHandler();
        List<File> javaFileList = fileHandler.getJavaFiles(file);
        
        String names = null;
        for(File f : javaFileList){
        	names += f.getName() + "\n";
        }
        
        
        redirectAttributes.addFlashAttribute("message", "Code Smell Result:" + names);
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

}