package searcher;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spans.SpanNearQuery;
import org.apache.lucene.search.spans.SpanQuery;
import org.apache.lucene.search.spans.SpanTermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


public class LemmaSearcher {
	
	
	
	public LemmaSearcher(String indexDir) throws IOException {
		Directory directory = FSDirectory.open(new File(indexDir));
		DirectoryReader reader = DirectoryReader.open(directory);  
		m_indexSearcher = new IndexSearcher(reader);  
	}
	
	public int countQueryResults(String queryString, int slop, boolean isOrder) throws IOException {
		String[] queryTerms = queryString.split(" ");
		SpanQuery[] andQ = new SpanQuery[queryTerms.length];
		int i=0;
		for(String term:queryTerms){
			SpanTermQuery tq = new SpanTermQuery(new Term("TERM_VECTOR",term));
			andQ[i] = tq;
			i++;
		}
		
		SpanNearQuery nearQ = new SpanNearQuery(andQ,slop,isOrder);
		System.out.println(nearQ);
		TopDocs td = m_indexSearcher.search(nearQ,Integer.MAX_VALUE);
		return td.totalHits;
	
}
	
	public List<String> getQueryResultsAsStringList(String queryString, int slop, boolean isOrder) throws IOException {
		String[] queryTerms = queryString.split(" ");
		SpanQuery[] andQ = new SpanQuery[queryTerms.length];
		int i=0;
		for(String term:queryTerms){
			SpanTermQuery tq = new SpanTermQuery(new Term("TERM_VECTOR",term));
			andQ[i] = tq;
			i++;
		}
		
		SpanNearQuery nearQ = new SpanNearQuery(andQ,slop,isOrder);
		System.out.println(nearQ);
		TopDocs td = m_indexSearcher.search(nearQ,Integer.MAX_VALUE);
		LinkedList<String> resultsList = new LinkedList<>();
		for(ScoreDoc sd:td.scoreDocs){
			String docContent =  m_indexSearcher.getIndexReader().document(sd.doc).get("PLAIN");
			resultsList.add(docContent);
		}
		return resultsList;
}
	
	public List<Integer> getUnigramQueryResultsAsIntegerList(String queryString) throws IOException {
		TermQuery query = new TermQuery(new Term("TERM_VECTOR",queryString));
		TopDocs td = m_indexSearcher.search(query,Integer.MAX_VALUE);
		LinkedList<Integer> resultsList = new LinkedList<>();
		for(ScoreDoc sd:td.scoreDocs){
			resultsList.add(sd.doc);
		}
		return resultsList;
}
	
	IndexSearcher m_indexSearcher = null;
}
