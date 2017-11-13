package code.smell.detection.textualAnalysis.IR;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TfIdf {
	
	
	public List<ArrayList<String>> tfIdfCheckInAllMethods(List<ArrayList<String>> methods){
		List<ArrayList<String>> allMethodsOfFiles = new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> singleClass : methods) {
			ArrayList<String> temporaryMethodList = new ArrayList<>();
			for(String singleMethod : singleClass) {
				String classFiles = "";
				String[] splited = singleMethod.trim().split("\\s+");
				for(String singleWordInMethod : splited) {
					if(tfIdf(singleClass, methods, singleWordInMethod) > .2)
						classFiles += singleWordInMethod;
				}
				temporaryMethodList.add(classFiles);
			}
			allMethodsOfFiles.add(temporaryMethodList);
		}
		
		return allMethodsOfFiles;
	}
	
	/**
     * @param doc  list of strings
     * @param term String represents a term
     * @return term frequency of term in document
     */
    public double tf(List<String> doc, String term) {
        double result = 0;
        for (String word : doc) {
            if (term.equalsIgnoreCase(word))
                result++;
        }
        return result / doc.size();
    }

    /**
     * @param methods list of list of strings represents the dataset
     * @param term String represents a term
     * @return the inverse term frequency of term in documents
     */
    public double idf(List<ArrayList<String>> methods, String term) {
        double n = 0;
        for (List<String> doc : methods) {
            for (String word : doc) {
                if (term.equalsIgnoreCase(word)) {
                    n++;
                    break;
                }
            }
        }
        return Math.log(methods.size() / n);
    }

    /**
     * @param doc  a text document
     * @param methods all documents
     * @param term term
     * @return the TF-IDF of term
     */
    public double tfIdf(List<String> doc, List<ArrayList<String>> methods, String term) {
        return tf(doc, term) * idf(methods, term);

    }

}
