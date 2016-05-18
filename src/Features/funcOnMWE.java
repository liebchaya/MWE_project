package Features;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import morphologyTools.Tagger;
import searcher.Searcher;

public class funcOnMWE {
    	
	public static void DelEmptyLstLine() throws IOException{ 
        RandomAccessFile raf = new RandomAccessFile("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\goodTargetTermsVNC.txt", "rw");
        long length = raf.length();
        raf.setLength(length - 2);
        raf.close();
    }
	public static void goodMWE() throws IOException{
		Searcher searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\milaCorporaWithPunc");
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\goodTargetTermsVNC.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		PrintWriter writer1 = new PrintWriter("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\good.txt", "UTF-8");

		String MWE = "";
		while((MWE = br.readLine()) != null){
			if(searcher.countQueryResults(MWE, 4, true) > 30)
				writer1.println(MWE);
		}
		writer1.close();
		br.close();
		DelEmptyLstLine();
	}
	public static void lemmas2words() throws Exception{
		Searcher searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\milaCorporaWithPunc");
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\lemmas\\bigrams");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String filename= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\lemmas\\good_bigrams");
		FileWriter fw = new FileWriter(filename,true);
		String line;
		String sen;
		while((line=br.readLine()) != null){
			String[] words = line.split("<>");
			sen = words[0]+" "+words[1];
			String tagSen = Tagger.getTaggerPOSString(sen);
			int freq = Integer.parseInt((words[2].split(" "))[0]);
			if(freq>10)
				if(tagSen.contains("verb")){
					fw.write(sen+"\t"+freq+"\n");
				}
		}
		br.close();
		fw.close();
	}
	public static void lemmas3words() throws Exception{
		Searcher searcher = new Searcher("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\bin\\milaCorporaWithPunc");
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\lemmas\\trigrams");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String filename= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\lemmas\\good_trigrams");
		FileWriter fw = new FileWriter(filename,true);
		String line;
		String sen;
		while((line=br.readLine()) != null){
			String[] words = line.split("<>");
			sen = words[0]+" "+words[1]+" "+words[2];
			String tagSen = Tagger.getTaggerPOSString(sen);
			int freq = Integer.parseInt((words[3].split(" "))[0]);
			if(freq>10)
				if(tagSen.contains("verb")){
					fw.write(sen+"\t"+freq+"\n");
				}
		}
		br.close();
		fw.close();
	}

	public static void main(String[] args) throws Exception {
		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");

//		FileInputStream pfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\prefixes\\prefixes.txt");
//		PrintWriter writerP = new PrintWriter("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\prefixes\\goodPrefixes.txt", "UTF-8");
//
//		BufferedReader br = new BufferedReader(new InputStreamReader(pfile,"UTF-8"));
//		
//		String feature;
//		while((feature=br.readLine())!=null){
//			if(feature.charAt(feature.length()-1)=='ë')
//				System.out.println(feature);
//			else{
//				writerP.println(feature);
//			}
//		}
//		br.close();
//		writerP.close();
//		goodMWE();
//		lemmas2words();
		lemmas3words();

	}
}
