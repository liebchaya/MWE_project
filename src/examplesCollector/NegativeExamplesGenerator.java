/*
 * 
 */
package examplesCollector;

import java.util.List;

import models.MweExample;

/*
 * @author Admin
 *
 */
public interface NegativeExamplesGenerator {
	
	int getIndexSize();
	
	Iterable<Integer> randomizeSentences(int maxValue,int iterableSize);
	
	String getSentenceContent(int docId);
			
	boolean isSuitableDistance(String sentence);
		
	Iterable<MweExample> GenerateNegativeExamples(Iterable<Integer> random);	
}
