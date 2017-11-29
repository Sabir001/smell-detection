package code.smell.detection.textualAnalysis.sd;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DetectMisplacedClass implements ISmellDetector {
	private final Logger log = LoggerFactory.getLogger(this.getClass());


	@Override
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods,
			List<String> mainJavaFiles) {
		List<String> result = new ArrayList<String>();
		
		return result;
	}

}
