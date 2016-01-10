package statistics;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import searcher.Searcher;

public class GennerateFreqMweData {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader("F:\\targetTermsVNC.txt"));
		BufferedReader lemmaReader = new BufferedReader(new FileReader("F:\\targetTermsVNC_lemmas.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\freqData\\mweFreqSummary.txt"));
		writer.write("MWE\tNews Text\tSlot 0\tSlot 1\tSlot 2\tSlot 0_bi\tSlot 1_bi\tSlot 2_bi\tNews Lemma\tSlot 0\tSlot 1\tSlot 2\tSlot 0_bi\tSlot 1_bi\tSlot 2_bi\tAll Text\tSlot 0\tSlot 1\tSlot 2\tSlot 0_bi\tSlot 1_bi\tSlot 2_bi\n");
		Searcher newsTextSearcher = new Searcher("F:\\MilaIndexes\\newsMila");
		Searcher newsLemmaSearcher = new Searcher("F:\\MilaIndexes\\newsLemmaMila");
		Searcher allTextSearcher = new Searcher("F:\\MWE_project\\allMila");
		String line = reader.readLine();
		String lemmaLine = lemmaReader.readLine();
		while(line!=null){
			String output = line+"\t";
			output = output + newsTextSearcher.countQueryResults(line, 0, true)+"\t";
			output = output + newsTextSearcher.countQueryResults(line, 1, true)+"\t";
			output = output + newsTextSearcher.countQueryResults(line, 2, true)+"\t";
			output = output + newsTextSearcher.countQueryResults(line, 0, false)+"\t";
			output = output + newsTextSearcher.countQueryResults(line, 1, false)+"\t";
			output = output + newsTextSearcher.countQueryResults(line, 2, false)+"\t";
			
			output = output + newsLemmaSearcher.countQueryResults(lemmaLine, 0, true)+"\t";
			output = output + newsLemmaSearcher.countQueryResults(lemmaLine, 1, true)+"\t";
			output = output + newsLemmaSearcher.countQueryResults(lemmaLine, 2, true)+"\t";
			output = output + newsLemmaSearcher.countQueryResults(lemmaLine, 0, false)+"\t";
			output = output + newsLemmaSearcher.countQueryResults(lemmaLine, 1, false)+"\t";
			output = output + newsLemmaSearcher.countQueryResults(lemmaLine, 2, false)+"\t";
			
			output = output + allTextSearcher.countQueryResults(line, 0, true)+"\t";
			output = output + allTextSearcher.countQueryResults(line, 1, true)+"\t";
			output = output + allTextSearcher.countQueryResults(line, 2, true)+"\t";
			output = output + allTextSearcher.countQueryResults(line, 0, false)+"\t";
			output = output + allTextSearcher.countQueryResults(line, 1, false)+"\t";
			output = output + allTextSearcher.countQueryResults(line, 2, false)+"\n";
			writer.write(output);
			line = reader.readLine();
			lemmaLine = lemmaReader.readLine();
		}
		reader.close();
		lemmaReader.close();
		writer.close();

	}

}
