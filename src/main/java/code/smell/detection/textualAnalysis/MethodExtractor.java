package code.smell.detection.textualAnalysis;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class MethodExtractor {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<ArrayList<String>> getMethods(List<String> allJavaCLasses){
		List<ArrayList<String>> methods = new ArrayList<ArrayList<String>>();
		
		log.info("getMethods invoked");
		
		return methods;
	}
}
