package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import morphologyTools.Tagger;


public class TestTagger {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");
//		String posStr = Tagger.getTaggerPOSString("אודה ולא אבוש");
//		System.out.println(posStr);
//		System.out.println(Tagger.getTaggerPOSList("אסף את השברים"));
//		System.out.println(Tagger.getTaggerPOSList("אודה ולא אבוש"));
//		System.out.println(Tagger.getTaggerPOSList("אכל כדבעי")); 
//		Tagger.getSuffix("כשהלכתי לתומי");
//		Tagger.getSuffix("אחזו השבץ");
//		System.out.println(Tagger.getTaggerPOSList("הגדיל לעשות"));
//		System.out.println(Tagger.getTaggerPOSList("לעשות להגדיל"));
//		posStr = posStr.replaceAll("adverb", "");
//		System.out.println(posStr);
//		if(posStr.contains("noun")&&posStr.contains("verb"))
//			System.out.println("Suitable");
		   //
		
//		BufferedReader reader = new BufferedReader(new FileReader("F:\\targetTermsVNC.txt"));
//		BufferedWriter writer = new BufferedWriter(new FileWriter("F:\\targetTermsVNC_lemmas.txt"));
//		String line = reader.readLine();
//		while(line!=null){
//			String output = "";
//			for(String w:Tagger.getTaggerLemmasList(line))
//				output+=w+" ";
//			writer.write(output.trim()+"\n");
//			line = reader.readLine();
//		}
//		reader.close();
//		writer.close();
//		Tagger.init("c:\\BGUTaggerData\\");
//		System.out.println(Tagger.getTaggerPOSList("אל תעשה את זה"));
//		System.out.println(Tagger.getTaggerPOSList("היא הלכה"));
//		System.out.println(Tagger.getTaggerPOSList("אם אחזור משם"));
//		System.out.println(Tagger.getTaggerPOSList("כן כן קפטן"));
//	not working	//System.out.println(Tagger.getSuffix("נאכל"));
//	not working	//System.out.println(Tagger.getSuffix("שיחקנו"));
		System.out.println(Tagger.getSuffix("ישבתי"));


	}

}
