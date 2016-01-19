package test;

import java.io.IOException;
import java.util.List;

import morphologyTools.Tagger;
import searcher.IndexInfo;
import searcher.Searcher;

public class TestSearcher {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
//		Searcher searcher = new Searcher("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
//		List<Integer> resultList = searcher.getUnigramQueryResultsAsIntegerList("אכל");
//		System.out.println("Number of sentences: " +  resultList.size());
//		System.out.println("List of sentences: " + resultList);
		
		IndexInfo indexInfo = new IndexInfo("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
		Tagger.init("c:\\BGUTaggerData\\");
		System.out.println(indexInfo.containVerbSentences(20));
		System.out.println(indexInfo.randomizeSentences(600000, 20));
		
		
	}

}
