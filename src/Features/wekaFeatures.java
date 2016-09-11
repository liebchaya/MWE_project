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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.sun.tools.xjc.writer.Writer;

import bgu.nlp.seg.vo.Sentence;
import models.MweExample;
import morphologyTools.Tagger;
import searcher.IndexInfo;
import test.TestSearcher;

public class wekaFeatures {
	static IndexInfo indexInfo; 

	private static String getScore(String key, String feature) throws IOException{
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\good_features\\"+feature);
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile));
		String score = br.readLine();
		while((score=br.readLine())!=null){			
			try{
			String [] tokens = score.split("\\s+");
			if(tokens[0].equals(key)){
				br.close();			
				return tokens[1];
			}
			else if(tokens[1].equals(key)){
				br.close();			
				return tokens[0];
			}
			}
			catch(Exception e){
				System.out.println("file: "+feature + " key: "+key);
			}
		}
		br.close();
		return null;
	}
	
	/*private static boolean isScoreExits(String key, String feature) throws IOException{
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\good_features\\"+feature+".txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile));
		String score = br.readLine();
		while((score=br.readLine())!=null){
			String [] tokens = score.split("\\s+");
			if(tokens[0].equals(key)){
				br.close();
				return true;
			}
		}
		br.close();
		return false;
	}*/
	
	/*private static void initFeatures() throws IOException{
		
  		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features1.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String feature;
		while((feature = br.readLine()) != null){
			String filename= "C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\f\\"+feature.toUpperCase()+".txt";
			if(!(new File(filename)).exists()){
			    FileWriter writer = new FileWriter(filename,true); //the true will append the new data
				writer.write(feature.toUpperCase()+"\n");
				writer.close();
			}
		}	
		br.close();
		
	}*/
	
	private static void initArff(String folder) throws IOException{
		FileInputStream features = new FileInputStream(folder+"\\features.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(features,"UTF-8"));
		String feature;
		String[] subF = folder.split("\\\\");
		PrintWriter writer = new PrintWriter(folder+"\\"+subF[subF.length-1]+".arff", "UTF-8");
		writer.println("@RELATION MWE_FEATURES");
		writer.println();
		while((feature = br.readLine()) != null)
			 writer.println("@ATTRIBUTE "+ feature +((!feature.equals("ID"))?" REAL":" STRING"));
		
		writer.println("@ATTRIBUTE CLASS {0,1}");
		writer.println();
		writer.println("@DATA");
		
		br.close();
		writer.close();
	}

	private static void mergeFeatures (String f, String fileName,List<String> snetences, int val) throws IOException {
		String Sfolder = f+"\\"+fileName;
		File folder = new File("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\good_features");
		File[] listOfFiles = folder.listFiles();
		boolean b = false;
		ArrayList<File> arffFiles = new ArrayList<>();
		
			
				FileInputStream features1 = new FileInputStream(Sfolder+"\\features.txt");
				BufferedReader br1 = new BufferedReader(new InputStreamReader(features1,"UTF-8"));
				String feature;
				String name = null;
				while((feature = br1.readLine())!= null){
					b = false;
					if(feature.equals("V_PREFIX_VERB")){
						b = true;
						b=false;
					}
					for (File file : listOfFiles){
	
					name = file.getName().substring(0,file.getName().length()-4).toUpperCase();
					
					if(name.split(" ")[0].equals(feature.toUpperCase())){
							arffFiles.add(file);
							b=true;
					}
					}
				
				
				if(!b)
					System.out.println(feature);
				
				
		}
		br1.close();
		FileInputStream[] arf = new FileInputStream[arffFiles.size()];
		for(int i=0; i<arffFiles.size(); i++)
			arf[i] = new FileInputStream(folder.getPath()+"\\"+arffFiles.get(i).getName());
		BufferedReader[] br = new BufferedReader[arffFiles.size()];
		for(int i=0; i<arffFiles.size(); i++){
			br[i] = new BufferedReader(new InputStreamReader(arf[i],"UTF-8"));
			br[i].readLine();
		}
		
	    String[] subF = Sfolder.split("\\\\");
		String filename= Sfolder+"\\"+subF[subF.length-1]+".arff";
	    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
	    
		for(int j=0; j<snetences.size(); j++){
			for(int i=0; i<arffFiles.size(); i++){	
				if("LEMMACOSINEWORD2VEC.txt".equals(arffFiles.get(i).getName())){
					int h = 9;
					h++;
				}
				String score = getScore(snetences.get(j).replaceAll("\\s+", "_"), arffFiles.get(i).getName());
				if(score!= null)
					fw.write(score+",");
				else {
			System.out.println(arffFiles.get(i).getName()+" " + snetences.get(j));
				
		}
			}
			fw.write(val+"\n");
		}
		for(int i=0; i<arffFiles.size(); i++)
			br[i].close();      
		fw.close();

	}
	
	/*private static void getAllFeatures(List<String> snetences,int val) throws Exception{
		
		Class c = Class.forName(Features.class.getName());
		Object o = c.newInstance();
  		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile,"UTF-8"));
		String feature;
		String filename;
		String sen;
		int j=0;
		FileWriter fw = new FileWriter("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features.txt",true);
		while((feature = br.readLine()) != null){
			System.out.println("///////////////////////////////"+feature+" number" +j+++ "/////////////////////////////////");
			Method m = c.getDeclaredMethod(feature, String.class);
			filename= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\"+feature.toUpperCase()+".txt");
		    fw = new FileWriter(filename,true); //the true will append the new data
			for(int i=0; i<snetences.size(); i++){
				sen = snetences.get(i);
				if(!isScoreExits(sen.replaceAll("\\s+","_"), feature.toUpperCase())){
					String result = (m.invoke(o, sen)==null)?"":m.invoke(o, sen).toString();
					fw.write(sen.replaceAll("\\s+","_")+" ");
					fw.write(result);
					fw.write("\n");
				}
			}
//			sen = snetences.get(snetences.size()-1); 
//			String result = (m.invoke(o, sen)==null)?"":m.invoke(o, sen).toString();
//			fw.write(result);
			fw.close();
		}
		br.close();
//		mergeFeatures(snetences,val);
		
	}
	
	private static void writeAllFeatures() throws Exception{
		File folder = new File("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\good_features");
		File[] listOfFiles = folder.listFiles();
		String filename1= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\featuresList\\features.txt");
		FileWriter fw1 = new FileWriter(filename1,true);
		for (File file : listOfFiles){
			String name = file.getName().replace(".", " ");
			String[] n = name.split(" ");
			fw1.write((n[0]).toUpperCase()+"\n");
		}
		fw1.close();
	}*/

	
	public static void main(String[] args) throws Exception {
	
		String[] files = {"AllbadExpressionsB1.txt","AllgoodExpressionsB1.txt","AllgoodExpressionsT1.txt","AllbadExpressionsT1.txt"};
	/*	
		String filename= "C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\ID.txt";
		FileWriter writer = new FileWriter(filename,true); //the true will append the new data
	    writer.write("ID\n");
			
	    int j=1;
		for(int i=0; i<files.length; i++){
			String exp="";
			FileInputStream f = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\expressions\\"+files[i]);
			BufferedReader br = new BufferedReader(new InputStreamReader(f));
			while((exp = br.readLine()) != null){
				writer.write(j+" "+exp.replaceAll(" ", "_")+"\n");
				j++;
			}
			br.close();
		}
		writer.close();*/
			
		/*String[] files1 = {"AllExp - All","AllExp - Linguistical","AllExp - Linguistical & Semantical",
				"AllExp - Linguistical & Statistical","AllExp - Semantical",
				"AllExp - Semantical & Statistical","AllExp - Statistical"};*/
		
		String[] files1 = {"WORD2VEC"};
		for(int j=0; j<files1.length; j++){
			String fold="C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\arffFiles - Semanticals";
			String folder = fold+"\\"+files1[j];
			initArff(folder);		
			for(int i=0; i<files.length; i++){
				int b = 1;
				if(files[i].contains("bad"))b=0;
				FileInputStream f = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\expressions\\"+files[i]);
				BufferedReader br = new BufferedReader(new InputStreamReader(f));
				List<String> s = new ArrayList<String>();
				String exp="";
				while((exp=br.readLine())!=null)
					s.add(exp);
				br.close();
				mergeFeatures(fold,files1[j], s, b);
			}
		}
		
		

//		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");

//		writeAllFeatures();
	/*	initArff("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\arffFiles\\ExpB");
		initArff("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\arffFiles\\ExpT");
		initArff("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\arffFiles\\Exp");
		initArff("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\arffFiles\\AllExpB");
		initArff("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\arffFiles\\AllExpT");
		initArff("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\arffFiles\\AllExp");*/
		
	/*
		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\to_fix\\COMPVECTORSLEMMA18.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile));
		String feature;
		FileInputStream MWEfile1 = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\fff.txt");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(MWEfile1));
		String feature1;
		
		List<String> s = new ArrayList<String>();
		br.readLine();
		while((feature = br.readLine())!= null){
			String[] ls = feature.split(" ");
			if(ls[1].split("_").length==2 && ls[0].equals("-1.0")){
				s.add(ls[1]);
			}
		}
		
		int m=1;
		while((feature1 = br1.readLine()) != null){
			String[] ls = feature1.split("_");
			if(m < 6)
			s.add(ls[1]+"_"+ls[2]);
			else if(m<16)
				s.add(ls[0]+"_"+ls[1]);
			else
				s.add(ls[0]+"_"+ls[2]);
			m++;
		}
			
br.close();		
br1.close();		

		String filename1= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\faulty.txt");
		FileWriter fw1 = new FileWriter(filename1,true);

		Class cls = Class.forName(Features.class.getName());
		Object obj = cls.newInstance();
		
		String[] filenames = {"compVectorsLemma11","compVectorsLemma12","compVectorsLemma14","compVectorsLemma15","compVectorsLemma16","compVectorsLemma17","compVectorsLemma19",
				"compVectorsLemma21","compVectorsLemma22","compVectorsLemma24","compVectorsLemma25","compVectorsLemma26","compVectorsLemma27","compVectorsLemma29",
				"compVectorsLemma31","compVectorsLemma32","compVectorsLemma34","compVectorsLemma35","compVectorsLemma36","compVectorsLemma37","compVectorsLemma39",
				"compVectorsLemma41","compVectorsLemma42","compVectorsLemma44","compVectorsLemma45","compVectorsLemma46","compVectorsLemma47","compVectorsLemma49","compVectorsLemma410"};
*/

		
	/*
		for(int i=0; i<filenames.length; i++){
			FileInputStream f = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\fff.txt");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(f));
			String filename= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\"+filenames[i].toUpperCase()+"t.txt");
			FileWriter fw = new FileWriter(filename,true); 
			
						
			Method method = cls.getDeclaredMethod(filenames[i], String.class);
				String sss = "";
			for(int j=0; j<s.size(); j++){
				try{
					if(j > 85) sss=br2.readLine();
					else sss=s.get(j);
				fw.write(method.invoke(obj, s.get(j).replaceAll("_", " "))+" "+sss+"\n");
				}
				catch(Exception e){
					fw1.write(filenames[i]+" : "+s.get(j)+"\n");
					}
			}
			fw.close();
			br2.close();
		}
		fw1.close();*/
		
		/*
		int i=0;
		while(i<filenames.length){
			FileInputStream f1 = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\to_merge\\"+filenames[i].toUpperCase()+".txt");
			BufferedReader br1 = new BufferedReader(new InputStreamReader(f1));
			FileInputStream f2 = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\to_merge\\"+filenames[i].toUpperCase()+"t.txt");
			BufferedReader br2 = new BufferedReader(new InputStreamReader(f2));
			String filename= ("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\Features\\good_features\\"+filenames[i].toUpperCase()+".txt");
			FileWriter fw = new FileWriter(filename,true); 
			
			String feature="";
			fw.write(br1.readLine()+"\n");
			while((feature = br1.readLine()) != null){
				String[] ls = feature.split(" ");
				if(!ls[0].equals("-1.0"))
					fw.write(feature+"\n");
			}
			while((feature = br2.readLine())!=null)
				fw.write(feature+"\n");
			fw.close();
			br1.close();
			br2.close();
			i++;
		}*/
}
}