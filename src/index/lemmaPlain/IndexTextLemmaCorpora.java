package index.lemmaPlain;


import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.util.Version;




/**
 * Main class for corpus indexing (with Lucene)
 * @author HZ
 */
public class IndexTextLemmaCorpora
{		
	/**
	 * Indexes a corpus
	 * @param args configuration file (index directory, corpus directory and document reader ({@link DocReader}))
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception
	{
		
	
		long start = new Date().getTime();
		long end = new Date().getTime();
		
		System.out.println("start :"+start);
		String indexFolder = args[0];
		File corpusDir = new File(args[1]);
		Class<?> cls;
		String docReaderClass = args[2];
		cls = Class.forName(docReaderClass);
		LemmaTextDocReader reader = (LemmaTextDocReader) cls.getDeclaredConstructor(File.class).newInstance(corpusDir);

		System.out.println("Indexing dir: " + corpusDir.getName() + " with lucene version: " + Version.LUCENE_48);
		File indexDir = new File(indexFolder);
		LemmaTextIndexer manager = new LemmaTextIndexer();
		Set<LemmaTextIndexer.DocField> fields = new HashSet<LemmaTextIndexer.DocField>();
		fields.add(LemmaTextIndexer.DocField.ID);
		fields.add(LemmaTextIndexer.DocField.TERM_VECTOR);
		fields.add(LemmaTextIndexer.DocField.PERIOD);
		fields.add(LemmaTextIndexer.DocField.SOURCE);
		fields.add(LemmaTextIndexer.DocField.LENGTH);
		fields.add(LemmaTextIndexer.DocField.PLAIN);
		StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
		manager.index(analyzer, reader, indexDir , fields, false, true);
		analyzer.close();
		
		end = new Date().getTime();
		System.out.println("total run time : "+(end-start)/1000+" seconds"+"("+(end-start)/60000+" minutes)");
	}

}
