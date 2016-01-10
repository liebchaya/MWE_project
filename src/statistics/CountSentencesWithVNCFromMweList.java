package statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import morphologyTools.Tagger;

import searcher.Searcher;

public class CountSentencesWithVNCFromMweList {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Tagger.init("F:\\BGUTaggerData\\");
		BufferedReader reader = new BufferedReader(new FileReader("F:\\targetTermsVNC.txt"));
//		BufferedReader lemmaReader = new BufferedReader(new FileReader("F:\\targetTermsVNC_lemmas.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\freqData\\mweMissingVnSummary.txt"));
		writer.write("MWE\tSlot 0_total\tSlot 0_miss\tSlot 1_total\tSlot 1_miss\tSlot 2_total\tSlot 2_miss\tSlot 0_bi_total\tSlot 0_bi_miss\tSlot 1_bi_total\tSlot 1_bi_miss" +
		"\tSlot 2_bi_total\tSlot 2_bi_miss\n");
		Searcher newsTextSearcher = new Searcher("F:\\MilaIndexes\\newsMila");
//		Searcher newsLemmaSearcher = new Searcher("F:\\MilaIndexes\\newsLemmaMila");
//		Searcher allTextSearcher = new Searcher("F:\\MWE_project\\allMila");
		String line = reader.readLine();
//		String lemmaLine = lemmaReader.readLine();
		while(line!=null){
			String output = line+"\t";
			List<String> resultList = newsTextSearcher.getQueryResultsAsStringList(line, 0, true);
			output = output + resultList.size() +"\t"  + countSentencesMissingVorN(resultList) + "\t";
			resultList = newsTextSearcher.getQueryResultsAsStringList(line, 1, true);
			output = output + resultList.size() +"\t"  + countSentencesMissingVorN(resultList) + "\t";
			resultList = newsTextSearcher.getQueryResultsAsStringList(line, 2, true);
			output = output + resultList.size() +"\t"  + countSentencesMissingVorN(resultList) + "\t";
			resultList = newsTextSearcher.getQueryResultsAsStringList(line, 0, false);
			output = output + resultList.size() +"\t"  + countSentencesMissingVorN(resultList) + "\t";
			resultList = newsTextSearcher.getQueryResultsAsStringList(line, 1, false);
			output = output + resultList.size() +"\t"  + countSentencesMissingVorN(resultList) + "\t";
			resultList = newsTextSearcher.getQueryResultsAsStringList(line, 2, false);
			output = output + resultList.size() +"\t"  + countSentencesMissingVorN(resultList) + "\n";
			
			writer.write(output);
			line = reader.readLine();
//			lemmaLine = lemmaReader.readLine();
		}
		reader.close();
//		lemmaReader.close();
		writer.close();

	}
	
	private static int countSentencesMissingVorN(List<String> listResults) throws Exception{
		int counter = 0;
		for(String sent:listResults){
			if (!containsVN(sent))
				counter++;
		}
		return counter;
	}
	
	private static boolean containsVN(String sentence) throws Exception{
		String posStr = Tagger.getTaggerPOSString(sentence);
		posStr = posStr.replaceAll("adverb", "");
		if(posStr.contains("noun")&&posStr.contains("verb"))
			return true;
		return false;
	}

}
