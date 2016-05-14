package test;

import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import models.MweExample;
import morphologyTools.Tagger;
import searcher.IndexInfo;

public class TestIndexInfo<T> {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static int getLength(Iterable<MweExample> it){
		 if (it instanceof Collection)
			    return ((Collection<MweExample>)it).size();
		  int i = 0;
		  for (@SuppressWarnings("unused") Object obj : it) i++;
		  return i;
	}
	
	public static void main(String[] args) throws Exception {
		IndexInfo indexInfo = new IndexInfo("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
//		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");
		
		
//		Syste.out.println("Number of sentences: " + indexInfo.getIndexSize());
		System.out.println("Sentence 678: " + IndexInfo.getSentenceContent(678));
//		System.out.println("Sentence 6780: " + indexInfo.getSentenceContent(6780));
		
//		Iterable<MweExample> m =indexInfo.GenerateNegativeExamples(indexInfo.randomizeSentences(500000,10));
////		Iterable<MweExample> m =indexInfo.GenerateNegativeExamples(indexInfo.containVerbSentences(100));
//
//		System.out.println("\n"+getLength(m));
//		for(MweExample mweExample : m){
//			System.out.println(mweExample.getMwe());
//			System.out.println(mweExample.getSentence()+"\n");
	//	}
	}

}
