package test;

import java.io.IOException;

import searcher.IndexInfo;

public class TestIndexInfo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		IndexInfo indexInfo = new IndexInfo("F:\\MWE_project\\allMila");
		System.out.println("Number of sentences: " + indexInfo.getIndexSize());
		System.out.println("Sentence 678: " + indexInfo.getSentenceContent(678));
		System.out.println("Sentence 6780: " + indexInfo.getSentenceContent(6780));

	}

}
