package test;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import Features.Features;
import edu.ucla.sspace.common.OnDiskSemanticSpace;
import edu.ucla.sspace.common.SemanticSpace;
import edu.ucla.sspace.common.Similarity;
import edu.ucla.sspace.common.Similarity.SimType;
import edu.ucla.sspace.vector.Vector;
import morphologyTools.Tagger;


public class TestFeatures {

	public static void main(String[] args) throws Exception {
		
		Tagger.init("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\jars\\tagger\\");
		Features f = new Features();
		
		//1
//		System.out.println("1)");
//		System.out.println(Features.non_Hebrew_letters("������"));			
//		System.out.println(Features.non_Hebrew_letters("����� A5 ���"));			
//		System.out.println(Features.non_Hebrew_letters("�����6�"));
//		System.out.println(Features.non_Hebrew_letters("������ da"));
//		System.out.println(Features.non_Hebrew_letters("�� ����"));	
//		System.out.println();
		
		//2
//		System.out.println("2)");
//		System.out.println(Features.nonsense_word("���� ���� ���"));
//		System.out.println(Features.nonsense_word("�� �����"));
//		System.out.println(Features.nonsense_word("��� ���� ����"));
//		System.out.println(Features.nonsense_word("��������"));
//		System.out.println(Features.nonsense_word("��� ����"));
//		System.out.println(Features.nonsense_word("����� ����� �����"));
//		System.out.println(Features.nonsense_word("��� ���"));
//		System.out.println();
		
//		//3
//		System.out.println("3)");
//		System.out.println(Features.syntactic_properties("��� �� ����"));	//false - distance is forbidden 	
//		System.out.println(Features.syntactic_properties("�� ��� ����"));	//true - can be distance between phrases
//		System.out.println();
//		
//		//4
//		System.out.println("4)");
//		System.out.println(Features.constituent_order("���� ������ �����"));	//false - order isn't important 	
//		System.out.println(Features.constituent_order("���� ����� ���"));		//false 	
//		System.out.println(Features.constituent_order("���� ����"));			//false	
//		System.out.println(Features.constituent_order("��� �� �������"));		//false 	
//		System.out.println(Features.constituent_order("�� ��� ����"));			//false
//		System.out.println(Features.constituent_order("��� �� ���"));			//false	
//		System.out.println(Features.constituent_order("��� ���"));				//false	
//		System.out.println(Features.constituent_order("��� �������"));			//false 	
//		System.out.println(Features.constituent_order("��� ������"));			//false 	
//		System.out.println(Features.constituent_order("��� ������ �����"));		//false
//		System.out.println();
//		
//		//5
//		System.out.println("5)");
//		System.out.println(Features.syntactic_irregularity("��� ������"));				//true  - close verbs
//		System.out.println(Features.syntactic_irregularity("������ ������ ������"));	//false - not close verbs
//		System.out.println();
//		
//		//6
//		System.out.println("6)");
//		System.out.println(Features.number_of_words("������ ������ ������"));	
//		System.out.println();
//		
//		//7
//		System.out.println("7)");
//		System.out.println(Features.number_of_characters("������ ������ ������"));	
//		System.out.println();
//		
//		//8
//		System.out.println("8)");
//		System.out.println(Features.average_characters_for_word("������ ������ ������"));
//		System.out.println();
		
		//9
//		System.out.println("9)");
//		System.out.println(Features.prefix_v("������� ���"));	
//		System.out.println(Features.prefix_n("�������� �����"));	
//		System.out.println(Tagger.getTaggerPOSList("�������� ����� ����"));	
//		System.out.println(Features.prefix_v("�������� ����� ����"));	
//		System.out.println("fa".contains("fa"));
//
//		System.out.println();
				
		//10 
//		System.out.println("10)");
//		System.out.println(Features.suffix_verb("����� ���� ����"));
//		System.out.println(Features.suffix_verb("��� �����"));
//		System.out.println(Features.suffix_verb("���� ���� ����� ������� �����"));
//		System.out.println();

				
		//11
//		System.out.println("11)");
//		System.out.println(Features.prefix_noun("��� ����"));
//		System.out.println(Features.prefix_noun("����� �����"));
//		System.out.println();
//
//		
//		//12
//		System.out.println("12)");
//		System.out.println(Features.suffix_noun("���� �����"));
//		System.out.println(Features.suffix_noun("������ ������"));
//		System.out.println(Features.suffix_noun("��� ����� �� �����"));
//
//		System.out.println();
//
//		//13
//		System.out.println("13)");
//		System.out.println(Features.POSpattern("������ ����"));
//		System.out.println(Features.POSpattern("��� ���� ����"));
//		System.out.println(Features.POSpattern("����� ���� ����"));
//		System.out.println(Features.POSpattern("���� �����"));
//		System.out.println(Features.POSpattern("����� ���� ����"));
//		System.out.println(Features.POSpattern("���� ��� �����"));
//		System.out.println(Features.POSpattern("���� ��� ������"));
//		System.out.println(Features.POSpattern("��� ���� ������"));
//		System.out.println(Features.POSpattern("��� ���� ���"));
//		System.out.println(Features.POSpattern("��� �����"));
//		System.out.println();
//
//
//		//14 - mostly in English
//		System.out.println("14)");
//		System.out.println(Features.ARTICLEstopWord("���� ���"));
//		System.out.println();
//
//		//15
//		System.out.println("15)");
//		System.out.println(Features.PRONOUNstopWord("��� ���� �����"));
//		System.out.println();
//
//		//16
//		System.out.println("16)");
//		System.out.println(Features.PARTICLEstopWord("�� �� ����"));
//		System.out.println(Features.PARTICLEstopWord("����� �����, ������"));
//		System.out.println(Features.PARTICLEstopWord("�� ���� ��������"));
//		System.out.println(Features.PARTICLEstopWord("��� ���� ��������"));
//		System.out.println();
//
//
//
//		//17
//		System.out.println("17)");
//		System.out.println(Features.COUNJUNCTIONSstopWord("���� ����� ���� �����"));
//		System.out.println(Features.COUNJUNCTIONSstopWord("��� ��� �����"));
//		System.out.println(Features.COUNJUNCTIONSstopWord("�� ���� �� ���"));
//		System.out.println(Features.PRONOUNstopWord("������ ���"));
//		System.out.println(Features.PRONOUNstopWord("������ ����"));
//		System.out.println(Features.PRONOUNstopWord("������ �����"));
//		System.out.println();
//
//
//		//18 - mostly in English
//		System.out.println("18)");
//		System.out.println(Features.AUXILIARYstopWord("?��� ���� ����� ��"));
//		System.out.println(Features.AUXILIARYstopWord("�� �� ����"));
//		System.out.println();
//
//		//19
//		System.out.println("19)");
		System.out.println(Features.NEGATIONstopWord("�� ������ �����"));
		System.out.println(Tagger.getTaggerPOSList("�� ������ �����"));
		System.out.println(Features.NEGATIONstopWord("�� ���� ���"));
		System.out.println(Tagger.getTaggerPOSList("�� ���� ���"));
		System.out.println(Features.NEGATIONstopWord("��� ����� �� ���"));	
		System.out.println(Tagger.getTaggerPOSList("��� ����� �� ���"));
//		System.out.println();
//
//		//21 & 20 
//		System.out.println("20 & 21)");
//		System.out.println(Features.nounPosTag("���� ����� ���� �����"));
//		System.out.println(Features.verbPosTag("��� ��� �����"));
//		System.out.println();
//
//		//22
//		System.out.println("22)");
//		System.out.println(Features.biblicalSen("������ ���"));
//		System.out.println(Features.biblicalSen("���� ��� ���"));
//		System.out.println(Features.biblicalSen("��� ���"));
//		System.out.println();
//		
//		//23
//		System.out.println("23)");
//		System.out.println(Features.hasHebStopWord("�� ���"));
//		System.out.println(Features.hasHebStopWord("�� ����"));
//		System.out.println(Features.hasHebStopWord("����� ���"));
//		System.out.println(Features.hasHebStopWord("���� ���� ����"));
//		System.out.println();
//		
//		//24
//		System.out.println("24)");
//		System.out.println(Features.senFrequency("�� ���� �� ���"));
//		System.out.println(Features.senFrequency("������ ���"));
//		System.out.println(Features.senFrequency("��� ���� �����"));
//		System.out.println();
		

//		System.out.println(Features.test1());
//		Features.improveFiles("bigramsLLLemma");
//		Features.improveFiles("bigramsLL");
//		System.out.println(Features.trigramsLL("��� ����� ���"));
//		System.out.println(Features.bigramsLL("����� ����"));
//		System.out.println(Features.compVectors("���� ����"));
//		test1();
	}
		
	 static void test1() throws IOException {

//		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\a.txt");
//		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile));
		String term1 = new String("��".getBytes(Charset.forName("utf-8")));
		String term2 = new String("������".getBytes(Charset.forName("utf-8")));
		String fileName = "C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\MilaText1File\\baseSpaces\\coals-semantic-space.sspace";
		SemanticSpace onDisk = new OnDiskSemanticSpace(fileName);
		System.out.println("load");
		Vector vec = onDisk.getVector(term1);
		Vector vec1 = onDisk.getVector(term2);
		double sem = Similarity.getSimilarity(SimType.COSINE, vec1, vec);
		System.out.println(sem);
//		br.close();
	}
	

}
