package code.smell.detection.textualAnalysis.FileCreation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;

@Component
public class StopWordFileCreation {
	private static final String javaStopWords = "abstract assert boolean break byte"
			+ "case catch char class const continue default do double else enum"
			+ "extends final finally float for goto if implements import"
			+ "instanceof int interface long native new package private"
			+ "protected public return short static strictfp super switch"
			+ "synchronized this throw throws transient try void volatile"
			+ "while true false null";
	
	private static final String englishStopWords = "a about above after again against all am an"
			+ "and any are aren't as at be because been before being"
			+ "below between both but by can't cannot could couldn't"
			+ "did didn't do does doesn't doing don't down during each"
			+ "few for from further had hadn't has hasn't have haven't"
			+ "having he he'd he'll he's her here here's hers herself"
			+ "him himself his how how's i i'd i'll i'm i've if in into"
			+ "is isn't it it's its itself let's me more most mustn't"
			+ "my myself no nor not of off on once only or other ought"
			+ "our ours ourselves out over own same shan't she she'd"
			+ "she'll she's should shouldn't so some such than that"
			+ "that's the their theirs them themselves then there there's"
			+ "these they they'd they'll they're they've this those through"
			+ "to too under until up very was wasn't we we'd we'll"
			+ "we're we've were weren't what what's when when's where"
			+ "where's which while who who's whom why why's with won't"
			+ "would wouldn't you you'd you'll you're you've your yours"
			+ "yourself yourselves";
	
	public void createFilesInStopWordDirectory(){
		try(
				PrintWriter javaWriter = new PrintWriter("Stop Word Java.txt", "UTF-8");
				PrintWriter englishWriter = new PrintWriter("Stop Word English.txt", "UTF-8");	
				) {
			javaWriter.println(javaStopWords);
			englishWriter.println(englishStopWords);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFilesInStopWordDirectory(){
		try {
			FileUtils.cleanDirectory(new File("Stop Word Directory"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteFilesInSourceDirectory(){
		try {
			FileUtils.cleanDirectory(new File("Source Folder"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}