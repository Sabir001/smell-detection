package code.smell.detection.textual.analysis.sd;

import java.util.ArrayList;
import java.util.List;

public interface ISmellDetector {

	public final static double threshold = 0.5;
	
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<ArrayList<String>>> methods, List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods);
}
