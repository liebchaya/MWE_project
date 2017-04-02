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
//		System.out.println(Features.non_Hebrew_letters("אביטלל"));			
//		System.out.println(Features.non_Hebrew_letters("אביטל A5 עהל"));			
//		System.out.println(Features.non_Hebrew_letters("אביטל6ל"));
//		System.out.println(Features.non_Hebrew_letters("אביטלל da"));
//		System.out.println(Features.non_Hebrew_letters("אב יטלל"));	
//		System.out.println();
		
		//2
//		System.out.println("2)");
//		System.out.println(Features.nonsense_word("שלום עליך רבי"));
//		System.out.println(Features.nonsense_word("מה שלומך"));
//		System.out.println(Features.nonsense_word("שפך כמים ליבו"));
//		System.out.println(Features.nonsense_word("מצחיקקקק"));
//		System.out.println(Features.nonsense_word("כמו שאתה"));
//		System.out.println(Features.nonsense_word("אתמול הלכתי הביתה"));
//		System.out.println(Features.nonsense_word("הכל טוב"));
//		System.out.println();
		
//		//3
//		System.out.println("3)");
//		System.out.println(Features.syntactic_properties("נפל על הראש"));	//false - distance is forbidden 	
//		System.out.println(Features.syntactic_properties("דן לכף זכות"));	//true - can be distance between phrases
//		System.out.println();
//		
//		//4
//		System.out.println("4)");
//		System.out.println(Features.constituent_order("נאחז בקרנות המזבח"));	//false - order isn't important 	
//		System.out.println(Features.constituent_order("נצור לשוני מרע"));		//false 	
//		System.out.println(Features.constituent_order("נדדה שנתו"));			//false	
//		System.out.println(Features.constituent_order("נפל לו האסימון"));		//false 	
//		System.out.println(Features.constituent_order("דן לכף זכות"));			//false
//		System.out.println(Features.constituent_order("נתן לו מנה"));			//false	
//		System.out.println(Features.constituent_order("נשא פרי"));				//false	
//		System.out.println(Features.constituent_order("לקח אוטובוס"));			//false 	
//		System.out.println(Features.constituent_order("לקח אחריות"));			//false 	
//		System.out.println(Features.constituent_order("ישב בחיבוק ידיים"));		//false
//		System.out.println();
//		
//		//5
//		System.out.println("5)");
//		System.out.println(Features.syntactic_irregularity("עשה והגדיל"));				//true  - close verbs
//		System.out.println(Features.syntactic_irregularity("מגדלים פורחים באוויר"));	//false - not close verbs
//		System.out.println();
//		
//		//6
//		System.out.println("6)");
//		System.out.println(Features.number_of_words("מגדלים פורחים באוויר"));	
//		System.out.println();
//		
//		//7
//		System.out.println("7)");
//		System.out.println(Features.number_of_characters("מגדלים פורחים באוויר"));	
//		System.out.println();
//		
//		//8
//		System.out.println("8)");
//		System.out.println(Features.average_characters_for_word("מגדלים פורחים באוויר"));
//		System.out.println();
		
		//9
//		System.out.println("9)");
//		System.out.println(Features.prefix_v("כשהלכתי לשם"));	
//		System.out.println(Features.prefix_n("כשמהגינה הלכתי"));	
//		System.out.println(Tagger.getTaggerPOSList("ובהליכתי ראיתי חתול"));	
//		System.out.println(Features.prefix_v("ובהליכתי ראיתי חתול"));	
//		System.out.println("fa".contains("fa"));
//
//		System.out.println();
				
		//10 
//		System.out.println("10)");
//		System.out.println(Features.suffix_verb("אכלתי משהו טעים"));
//		System.out.println(Features.suffix_verb("אני שתיתי"));
//		System.out.println(Features.suffix_verb("אולי נשחק במשחק ששיחקנו אתמול"));
//		System.out.println();

				
		//11
//		System.out.println("11)");
//		System.out.println(Features.prefix_noun("נשא בנטל"));
//		System.out.println(Features.prefix_noun("הלכתי הביתה"));
//		System.out.println();
//
//		
//		//12
//		System.out.println("12)");
//		System.out.println(Features.suffix_noun("נדדה שנתנו"));
//		System.out.println(Features.suffix_noun("שיחקתי בגינתי"));
//		System.out.println(Features.suffix_noun("אני אוהבת את כולכם"));
//
//		System.out.println();
//
//		//13
//		System.out.println("13)");
//		System.out.println(Features.POSpattern("אלוהים ישמר"));
//		System.out.println(Features.POSpattern("אמת מארץ תצמח"));
//		System.out.println(Features.POSpattern("התחיל ברגל שמאל"));
//		System.out.println(Features.POSpattern("חיפש בנרות"));
//		System.out.println(Features.POSpattern("הגונב מגנב פתור"));
//		System.out.println(Features.POSpattern("ומשה הלך לדרכו"));
//		System.out.println(Features.POSpattern("הבין דבר לאשורו"));
//		System.out.println(Features.POSpattern("טוב הדבר בעיניו"));
//		System.out.println(Features.POSpattern("חטף מכות רצח"));
//		System.out.println(Features.POSpattern("חזר בשאלה"));
//		System.out.println();
//
//
//		//14 - mostly in English
//		System.out.println("14)");
//		System.out.println(Features.ARTICLEstopWord("הילד הלך"));
//		System.out.println();
//
//		//15
//		System.out.println("15)");
//		System.out.println(Features.PRONOUNstopWord("הוא הולך לדרכו"));
//		System.out.println();
//
//		//16
//		System.out.println("16)");
//		System.out.println(Features.PARTICLEstopWord("אם לא אתפש"));
//		System.out.println(Features.PARTICLEstopWord("למרות הקושי, הצלחנו"));
//		System.out.println(Features.PARTICLEstopWord("אז בסוף השתכנעתי"));
//		System.out.println(Features.PARTICLEstopWord("לכן בסוף השתכנעתי"));
//		System.out.println();
//
//
//
//		//17
//		System.out.println("17)");
//		System.out.println(Features.COUNJUNCTIONSstopWord("צריך לוותר למען השלום"));
//		System.out.println(Features.COUNJUNCTIONSstopWord("אבל מחר אצליח"));
//		System.out.println(Features.COUNJUNCTIONSstopWord("או שחור או לבן"));
//		System.out.println(Features.PRONOUNstopWord("החפצים שלי"));
//		System.out.println(Features.PRONOUNstopWord("החפצים שלנו"));
//		System.out.println(Features.PRONOUNstopWord("החפצים אנחנו"));
//		System.out.println();
//
//
//		//18 - mostly in English
//		System.out.println("18)");
//		System.out.println(Features.AUXILIARYstopWord("?האם נוכל להיות שם"));
//		System.out.println(Features.AUXILIARYstopWord("יש לי חתול"));
//		System.out.println();
//
//		//19
//		System.out.println("19)");
		System.out.println(Features.NEGATIONstopWord("לא הספקתי לכתוב"));
		System.out.println(Tagger.getTaggerPOSList("לא הספקתי לכתוב"));
		System.out.println(Features.NEGATIONstopWord("אל תעשה זאת"));
		System.out.println(Tagger.getTaggerPOSList("אל תעשה זאת"));
		System.out.println(Features.NEGATIONstopWord("אין דובים או יער"));	
		System.out.println(Tagger.getTaggerPOSList("אין דובים או יער"));
//		System.out.println();
//
//		//21 & 20 
//		System.out.println("20 & 21)");
//		System.out.println(Features.nounPosTag("צריך לוותר למען השלום"));
//		System.out.println(Features.verbPosTag("אבל מחר אצליח"));
//		System.out.println();
//
//		//22
//		System.out.println("22)");
//		System.out.println(Features.biblicalSen("בראשית ברא"));
//		System.out.println(Features.biblicalSen("והנה טוב מאד"));
//		System.out.println(Features.biblicalSen("הכל טוב"));
//		System.out.println();
//		
//		//23
//		System.out.println("23)");
//		System.out.println(Features.hasHebStopWord("לא אלך"));
//		System.out.println(Features.hasHebStopWord("נו מיכל"));
//		System.out.println(Features.hasHebStopWord("אביטל דאי"));
//		System.out.println(Features.hasHebStopWord("שלום אביב הגיע"));
//		System.out.println();
//		
//		//24
//		System.out.println("24)");
//		System.out.println(Features.senFrequency("או שחור או לבן"));
//		System.out.println(Features.senFrequency("בראשית ברא"));
//		System.out.println(Features.senFrequency("הוא הולך לדרכו"));
//		System.out.println();
		

//		System.out.println(Features.test1());
//		Features.improveFiles("bigramsLLLemma");
//		Features.improveFiles("bigramsLL");
//		System.out.println(Features.trigramsLL("חבר הכנסת סער"));
//		System.out.println(Features.bigramsLL("לימור לבנת"));
//		System.out.println(Features.compVectors("תפוח תפוח"));
//		test1();
	}
		
	 static void test1() throws IOException {

//		FileInputStream MWEfile = new FileInputStream("C:\\Users\\aday\\Documents\\MWE_project\\MWE_project\\a.txt");
//		BufferedReader br = new BufferedReader(new InputStreamReader(MWEfile));
		String term1 = new String("בא".getBytes(Charset.forName("utf-8")));
		String term2 = new String("בטענות".getBytes(Charset.forName("utf-8")));
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
