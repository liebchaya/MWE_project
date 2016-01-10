package test;

import java.io.IOException;
import java.util.List;

import searcher.Searcher;

public class TestSearcher {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Searcher searcher = new Searcher("F:\\MWE_project\\allMila");
		List<Integer> resultList = searcher.getUnigramQueryResultsAsIntegerList("אכל");
		System.out.println("Number of sentences: " +  resultList.size());
		System.out.println("List of sentences: " + resultList);
	}

}
