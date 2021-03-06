package test;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import models.MweExample;
import morphologyTools.Tagger;
import searcher.IndexInfo;

public class TestIndexInfo {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		IndexInfo indexInfo = new IndexInfo("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
		Tagger.init("c:\\BGUTaggerData\\");
		
//		System.out.println("Number of sentences: " + indexInfo.getIndexSize());
//		System.out.println("Sentence 678: " + indexInfo.getSentenceContent(678));
//		System.out.println("Sentence 6780: " + indexInfo.getSentenceContent(6780));
		
//		Iterable<MweExample> m =indexInfo.GenerateNegativeExamples(indexInfo.randomizeSentences(600, 10));
		Iterable<MweExample> m =indexInfo.GenerateNegativeExamples(indexInfo.containVerbSentences(10));

		for(MweExample mweExample : m)
			System.out.println(mweExample.getMwe());
	}

}
