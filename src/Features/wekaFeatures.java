package Features;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import com.sun.tools.xjc.writer.Writer;

import bgu.nlp.seg.vo.Sentence;
import models.MweExample;
import morphologyTools.Tagger;
import searcher.IndexInfo;
import test.TestSearcher;

public class wekaFeatures {
	static IndexInfo indexInfo; 

	private static void initFeatures() throws IOException{
		
  		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String feature;
		while((feature = br.readLine()) != null){
			PrintWriter writer = new PrintWriter("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\"+feature.toUpperCase()+".txt", "UTF-8");
			writer.println(feature.toUpperCase());
			writer.close();
		}
		br.close();
		
	}
	
	private static void initArff() throws IOException{
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String feature;
		
		PrintWriter writer = new PrintWriter("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\features.arff", "UTF-8");
		writer.println("@RELATION MWEfeatures");
		writer.println();
		while((feature = br.readLine()) != null)
			writer.println("@ATTRIBUTE "+feature.toUpperCase()+" REAL");
		
		writer.println("@ATTRIBUTE class {0,1}");
		writer.println();
		writer.println("@DATA");
		
		br.close();
		writer.close();
	}

	private static void mergeFeatures (int size, int val) throws IOException {
		File folder = new File("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\");
		File[] listOfFiles = folder.listFiles();
		ArrayList<File> arffFiles = new ArrayList<>();
		for (File file : listOfFiles)
		    if (file.isFile() && file.getName().contains(".txt")) 
		    	arffFiles.add(file);
		FileInputStream[] arf = new FileInputStream[arffFiles.size()];
		for(int i=0; i<arffFiles.size(); i++)
			arf[i] = new FileInputStream(folder.getPath()+"\\"+arffFiles.get(i).getName());
		BufferedReader[] br = new BufferedReader[arffFiles.size()];
		for(int i=0; i<arffFiles.size(); i++){
			br[i] = new BufferedReader(new InputStreamReader(arf[i],"UTF-8"));
			br[i].readLine();
		}
		
		String filename= "C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\features.arff";
	    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		
		for(int j=0; j<size; j++){
			for(int i=0; i<arffFiles.size(); i++){
				fw.write(br[i].readLine()+",");
			}
			fw.write(val+"\n");
		}
		for(int i=0; i<arffFiles.size(); i++)
			br[i].close();      
		fw.close();


	}
	
	public static void getAllFeatures(List<String> snetences,int val) throws Exception{
		
		Class c = Class.forName(Features.class.getName());
		Object o = c.newInstance();
  		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String feature;
		String filename;
		String sen;
		FileWriter fw = new FileWriter(br.readLine(),true);;
		while((feature = br.readLine()) != null){
			Method m = c.getDeclaredMethod(feature, String.class);
			filename= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\"+feature.toUpperCase()+".txt");
		    fw = new FileWriter(filename,true); //the true will append the new data
			for(int i=0; i<snetences.size(); i++){
				sen = snetences.get(i);
				String result = (m.invoke(o, sen)==null)?"":m.invoke(o, sen).toString();
				fw.write(result);
				fw.write("\n");
			}
//			sen = snetences.get(snetences.size()-1); 
//			String result = (m.invoke(o, sen)==null)?"":m.invoke(o, sen).toString();
//			fw.write(result);
			fw.close();
		}
		br.close();
		mergeFeatures(snetences.size(),val);
		
	}
	
	public static void writeAllFeatures() throws Exception{
		
		Class c = Class.forName(Features.class.getName());
  		Method[] m = c.getMethods();
  		PrintWriter writer = new PrintWriter("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features.txt");
		for(Method m1 : m)
			if(!Modifier.isPrivate(m1.getModifiers()))
				writer.println(m1.getName());
		writer.close();
	}

	public static void main(String[] args) throws Exception {
		
//		indexInfo= new IndexInfo("C:\\Users\\aday\\AppData\\Local\\GitHub\\TutorialRepository_a66c3719071da6d865a984bb8d6bfb5bcd775ec8\\new-repo\\MWE_project\\allMila");
//		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");

//		initFeatures();
//		initArff();
//		List<String> arr = new ArrayList<>();
//		arr = TestSearcher.getMWElist(2);
//		Iterable<MweExample> m2 =IndexInfo.GenerateNegativeExamples(IndexInfo.containVerbSentences(500));
//		Iterable<MweExample> m1 =IndexInfo.GenerateNegativeExamples(IndexInfo.randomizeSentences(indexInfo.getIndexSize(), 500));
//		List<String> _m1 = new ArrayList<>();
//		List<String> _m2 = new ArrayList<>();
//		for(MweExample mweExample : m1)
//			_m1.add(mweExample.getSentence());
//		for(MweExample mweExample : m2)
//			_m2.add(mweExample.getSentence());
//		

//		PrintWriter writer1 = new PrintWriter("C:\\Users\\aday\\Desktop\\randomNegative.txt", "UTF-8");
//		PrintWriter writer2 = new PrintWriter("C:\\Users\\aday\\Desktop\\gootNegative.txt", "UTF-8");
//		int i=1;
//		for(MweExample m : m1){
//			writer1.println((i++)+") "+m.getSentence());
//			writer1.println(m.getMwe());
//			writer1.println();
//		}
//		writer1.close();
//		i=1;
//		for(MweExample m : m2){
//			writer2.println((i++)+") "+m.getSentence());
//			writer2.println(m.getMwe());
//			writer2.println();
//		}
//		writer2.close();
		
//		writeAllFeatures();
//		getAllFeatures(arr,1);
//		getAllFeatures(_m1,0);
//		getAllFeatures(_m2,0);
	}

}