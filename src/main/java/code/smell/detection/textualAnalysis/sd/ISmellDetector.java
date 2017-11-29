package code.smell.detection.textualAnalysis.sd;

import java.util.ArrayList;
import java.util.List;

public interface ISmellDetector {

	public List<String> detectSmell(List<String> javaFiles, List<ArrayList<String>> methods, List<String> mainJavaFiles);
}
