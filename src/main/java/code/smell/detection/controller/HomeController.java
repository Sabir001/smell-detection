package code.smell.detection.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Welcome";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		log.info("Home page requested");
		model.put("message", this.message);
		return "landingPage";
	}

}