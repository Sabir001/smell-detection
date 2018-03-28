package code.smell.detection.textual.analysis.file.creation;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StopWordFileCreation {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static final String JAVA_STOP_WORDS = "abstract assert boolean break byte"
			+ "case catch char class const continue default do double else enum"
			+ "extends final finally float for goto if implements import"
			+ "instanceof int interface long native new package private"
			+ "protected public return short static strictfp super switch"
			+ "synchronized this throw throws transient try void volatile"
			+ "while true false null";
	
	public static final String ENGLISH_STOP_WORDS = "a about above after again against all am an"
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
				PrintWriter writer = new PrintWriter("Stop Word Directory\\Stop Words.txt", "UTF-8");
				) {
			writer.println(JAVA_STOP_WORDS);
			writer.println(ENGLISH_STOP_WORDS);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	
}
