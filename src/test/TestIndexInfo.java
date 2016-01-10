package test;

import java.io.IOException;
import searcher.IndexInfo;

public class TestIndexInfo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws IOException {
		IndexInfo indexInfo = new IndexInfo("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
		System.out.println("Number of sentences: " + indexInfo.getIndexSize());
		System.out.println("Sentence 678: " + indexInfo.getSentenceContent(678));
		System.out.println("Sentence 6780: " + indexInfo.getSentenceContent(6780));

	}

}
