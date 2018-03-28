package code.smell.detection.textual.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class MethodExtractor {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private static final String REGEX = "^(?<indent>\\s*)(?<mod1>\\w+)\\s(?<mod2>\\w+)?\\s*(?<mod3>\\w+)?\\s*(?<return>\\b\\w+)\\s(?<name>\\w+)\\((?<arg>.*?)\\)\\s*\\{(?<body>.+?)^\\k<indent>\\}";
	
	public List<ArrayList<String>> getMethods(List<String> allJavaCLasses){
		List<ArrayList<String>> methods = new ArrayList<>();
		
		log.info("getMethods invoked");
		
		try{
			final Pattern pattern = Pattern.compile(REGEX, Pattern.MULTILINE | Pattern.DOTALL);
			for(String string : allJavaCLasses) {
				Matcher matcher = pattern.matcher(string);
				ArrayList<String> methodInIndividualClass = new ArrayList<>();
				while(matcher.find()) {
					methodInIndividualClass.add(matcher.group());
				}
				methods.add(methodInIndividualClass);
			}
			
		} catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
		return methods;
	}
}
