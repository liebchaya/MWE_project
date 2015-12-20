package test;

import morphologyTools.Tagger;


public class TestTagger {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		Tagger.init("F:\\BGUTaggerData\\");
		System.out.println(Tagger.getTaggerPOSList("אבא אכל תפוח"));
	}

}
