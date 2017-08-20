package code.smell.detection;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomeController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// inject via application.properties
	@Value("${welcome.message:test}")
	private String message = "Hello World";

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		 // Log a simple message
	    log.debug("debug level log");
	    log.info("info level log");
	    log.error("error level log");
	    
	    // Log a formatted string with parameters
	    log.info("another info log with {}, {} and {} arguments", 1, "2", 3.0);
		
		model.put("message", this.message);
		return "welcome";
	}

}