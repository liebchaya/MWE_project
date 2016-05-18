package test;

import java.io.IOException;
import java.util.List;

import morphologyTools.Tagger;
import searcher.IndexInfo;
import searcher.LemmaSearcher;
import searcher.Searcher;

public class TestLemmaSearcher {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		LemmaSearcher searcher = new LemmaSearcher("F:\\MilaIndexes\\milaNewsLemmaText");
		List<String> resultList = searcher.getQueryResultsAsStringList("шад аеш",0,true);
		System.out.println("Number of sentences: " +  resultList.size());
		System.out.println("List of sentences: " + resultList);
		
	
		
		
	}

}
