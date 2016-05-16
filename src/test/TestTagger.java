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
		
//		Tagger.init("F:\\BGUTaggerData\\");
		Tagger.init("F:\\tagger\\");
		String posStr = Tagger.getTaggerPOSString("הולך בין הטיפות");
		System.out.println(posStr);
		System.out.println(Tagger.getTaggerPOSList("אוסף את השברים"));
//		System.out.println(Tagger.getTaggerPOSList("אודה ולא אבוש"));
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
//		System.out.println(Tagger.getTaggerPOSList("הילד אוסף את המשחקים בחצר"));
//		System.out.println(Tagger.getTaggerPOSList("כשהלכתי לטיול"));
	}

}
