package code.smell.detection.textualAnalysis.sd;

import java.util.ArrayList;
import java.util.List;

public interface ISmellDetector {

	public final static double threshold = 0.4;
	
	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods, List<String> mainJavaFiles, List<ArrayList<String>> mainAllmethods);
}
